package com.login.dbs.LoginForm.dtos;

public record BasicUserInfoDto(
        String username,
        String password,
        String email) {
}
