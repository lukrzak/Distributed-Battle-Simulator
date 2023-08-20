package com.login.dbs.LoginForm.repositories;

import com.login.dbs.LoginForm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameOrEmail(String username, String mail);

    Optional<User> findByEmail(String mail);
}
