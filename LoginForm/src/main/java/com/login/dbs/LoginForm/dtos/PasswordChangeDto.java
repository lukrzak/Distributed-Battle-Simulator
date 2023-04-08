package com.login.dbs.LoginForm.dtos;

import com.login.dbs.LoginForm.models.User;

public record PasswordChangeDto(User user, String newPassword) {
}
