package com.login.dbs.LoginForm.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.dbs.LoginForm.models.User;
import com.login.dbs.LoginForm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${config.email.login}")
    private String emailUsername;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) throws Exception {
        User existingUser = userRepository.findByUsernameOrMail(user.getUsername(), user.getMail());
        if (existingUser != null) throw new Exception("Username or email already taken");
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean authenticateUser(User user) throws Exception{
        User existingUser = userRepository.findByUsernameOrMail(user.getUsername(), user.getMail());
        if (existingUser == null) throw new Exception("User with given username or login does not exist");
        if (!encoder.matches(user.getPassword(), existingUser.getPassword())) throw new Exception("Password incorrect");

        return true;
    }

    public void changePassword(User user, String newPassword) throws Exception {
        if(authenticateUser(user)){
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    public void recoverPassword(String email) throws Exception {
        String generatedPassword = getGeneratedPassword();
        User user = userRepository.findByMail(email);
        if(user == null) throw new Exception("User with this email does not exist");
        user.setPassword(encoder.encode(generatedPassword));
        userRepository.save(user);

        String text =
                "Your recovery password:\n" + generatedPassword +
                "\nPlease change your password immediately after logging in";
        sendEmail(email, "Password Recovery", text);
    }

    public void sendEmail(String receiver, String title, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(emailUsername);
        mail.setTo(receiver);
        mail.setText(text);
        mail.setSubject(title);

        mailSender.send(mail);
        System.out.println("Sent");
    }

    public String getGeneratedPassword() throws IOException {
        URL url = new URL("https://makemeapassword.ligos.net/api/v1/alphanumeric/json?l=16");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestMethod("GET");
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseStream);

        return root.path("pws").get(0).toString().replace("\"", "");
    }
}
