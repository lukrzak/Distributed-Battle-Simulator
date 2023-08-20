package com.login.dbs.LoginForm.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.dbs.LoginForm.dtos.BasicUserInfoDto;
import com.login.dbs.LoginForm.dtos.ChangeUserPasswordDto;
import com.login.dbs.LoginForm.exceptions.UserAlreadyExistException;
import com.login.dbs.LoginForm.models.User;
import com.login.dbs.LoginForm.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JavaMailSender mailSender;
    private final String PASSWORD_CREATOR_URL = "https://makemeapassword.ligos.net/api/v1/alphanumeric/json?l=16";

    @Value("${config.email.login}")
    private String emailUsername;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.mailSender = mailSender;
    }

    public User registerUser(BasicUserInfoDto userToCreate) throws UserAlreadyExistException {
        Optional<User> existingUser = userRepository.findByUsernameOrEmail(userToCreate.username(), userToCreate.email());
        if (existingUser.isPresent())
            throw new UserAlreadyExistException(userToCreate);

        String encodedPassword = encoder.encode(userToCreate.password());
        User newUser = new User(userToCreate.username(), encodedPassword, userToCreate.email());
        LOGGER.debug("Created user: " + newUser);

        userRepository.save(newUser);
        return newUser;
    }

    public boolean authenticateUser(BasicUserInfoDto userToAuthenticate) {
        Optional<User> existingUser = userRepository.findByUsernameOrEmail(userToAuthenticate.username(), userToAuthenticate.email());
        return existingUser.isEmpty()
                ? false
                : checkIfEncodedPasswordsMatch(userToAuthenticate.password(), existingUser.get().getPassword());
    }

    public User changePassword(ChangeUserPasswordDto changeUserPasswordDto) throws AuthenticationException {
        Optional<User> userToBeChanged = userRepository.findByEmail(changeUserPasswordDto.email());
        if (userToBeChanged.isEmpty()) {
            LOGGER.debug("User " + changeUserPasswordDto.email() + " doesn't exist");
            throw new AuthenticationException("User with given email doesn't exist");
        }
        BasicUserInfoDto user = new BasicUserInfoDto(
                userToBeChanged.get().getUsername(), changeUserPasswordDto.password(), userToBeChanged.get().getEmail());

        if (authenticateUser(user)) {
            userToBeChanged.get().setPassword(encoder.encode(changeUserPasswordDto.newPassword()));
            userRepository.save(userToBeChanged.get());
            LOGGER.debug("New password for user " + user + " has been saved");
        } else throw new AuthenticationException("Failed to authenticate");

        return userToBeChanged.get();
    }

    public User recoverPassword(String email) throws IOException, AuthenticationException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            LOGGER.debug("User with email " + email + " doesn't exist");
            throw new AuthenticationException("User with given email doesn't exist");
        }
        String generatedPassword = getGeneratedPassword();
        user.get().setPassword(encoder.encode(generatedPassword));
        userRepository.save(user.get());

        String text =
                "Your recovery password:\n" + generatedPassword +
                        "\nPlease change your password immediately after logging in";
        sendEmail(email, "Password Recovery", text);

        return user.get();
    }

    private boolean checkIfEncodedPasswordsMatch(String providedPassword, String expectedPassword) {
        return encoder.matches(providedPassword, expectedPassword);
    }

    private void sendEmail(String receiver, String title, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(emailUsername);
        mail.setTo(receiver);
        mail.setText(text);
        mail.setSubject(title);

        mailSender.send(mail);
        LOGGER.info("Recovery email sent to " + receiver);
    }

    private String getGeneratedPassword() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(PASSWORD_CREATOR_URL, String.class);
        JsonNode root = mapper.readTree(response.getBody());

        return root.path("pws").get(0).toString().replace("\"", "");
    }
}
