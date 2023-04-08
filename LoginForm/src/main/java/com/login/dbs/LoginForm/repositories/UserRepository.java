package com.login.dbs.LoginForm.repositories;


import com.login.dbs.LoginForm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameOrMail(String username, String mail);
    User findByMail(String mail);
}
