package com.login.dbs.LoginForm;

import com.login.dbs.LoginForm.dtos.BasicUserInfoDto;
import com.login.dbs.LoginForm.dtos.ChangeUserPasswordDto;
import com.login.dbs.LoginForm.exceptions.UserAlreadyExistException;
import com.login.dbs.LoginForm.models.User;
import com.login.dbs.LoginForm.repositories.UserRepository;
import com.login.dbs.LoginForm.services.UserService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    private UserRepository userRepository = mock(UserRepository.class);
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private JavaMailSender mailSender = mock(JavaMailSender.class);
    private UserService userService = new UserService(userRepository, encoder, mailSender);

    @Test
    void testUserRegistration() throws UserAlreadyExistException {
        BasicUserInfoDto user = new BasicUserInfoDto("login", "pas", "email@em.com");
        when(userRepository.findByUsernameOrEmail("login", "email@em.com")).thenReturn(Optional.empty());

        User savedUser = userService.registerUser(user);

        assertNotEquals(savedUser.getPassword(), user.password(), "password should be encrypted");
    }

    @Test
    void testUserRegistrationWithTakenCredentials() {
        BasicUserInfoDto user = new BasicUserInfoDto("login", "pas", "email@em.com");
        when(userRepository.findByUsernameOrEmail("login", "email@em.com")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(user));
    }

    @Test
    void testUserAuthentication() {
        String password = "Abc1@3";
        String encodedPassword = encoder.encode(password);
        User existingUser = new User("name", encodedPassword, "email@email.com");
        BasicUserInfoDto userToAuthenticate = new BasicUserInfoDto("name", password, "email@email.com");
        BasicUserInfoDto userWithWrongCredentials = new BasicUserInfoDto("name", "abc1@3", "email@email.com");
        when(userRepository.findByUsernameOrEmail("name", "email@email.com")).thenReturn(Optional.of(existingUser));

        assertTrue(userService.authenticateUser(userToAuthenticate));
        assertFalse(userService.authenticateUser(userWithWrongCredentials));
    }

    @Test
    void testChangePassword() throws AuthenticationException {
        String password = "Abc1@3";
        String newPassword = "123";
        String encodedPassword = encoder.encode(password);
        User existingUser = new User("name", encodedPassword, "email@email.com");
        ChangeUserPasswordDto changeUserPasswordDto = new ChangeUserPasswordDto("email@email.com", password, newPassword);
        ChangeUserPasswordDto changeWithBadCredentials = new ChangeUserPasswordDto("email@email.com", "AA", newPassword);
        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsernameOrEmail("name", "email@email.com")).thenReturn(Optional.of(existingUser));

        User changedUser = userService.changePassword(changeUserPasswordDto);

        assertNotEquals(changedUser.getPassword(), encodedPassword);
        assertThrows(AuthenticationException.class, () -> userService.changePassword(changeWithBadCredentials));
    }

    @Test
    void testRecoveryPassword() throws AuthenticationException, IOException {
        String password = "Abc1@3";
        String encodedPassword = encoder.encode(password);
        User existingUser = new User("name", encodedPassword, "email@email.com");
        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.of(existingUser));
        doNothing().when(mailSender).send((MimeMessage) any());

        User changedUser = userService.recoverPassword("email@email.com");

        assertNotEquals(changedUser.getPassword(), encodedPassword);
        assertThrows(AuthenticationException.class, () -> userService.recoverPassword("aaa"));
    }
}
