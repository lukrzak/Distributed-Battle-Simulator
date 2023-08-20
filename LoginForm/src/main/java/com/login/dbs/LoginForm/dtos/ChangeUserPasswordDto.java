package com.login.dbs.LoginForm.dtos;

public record ChangeUserPasswordDto(
        String email,
        String password,
        String newPassword) {
}
