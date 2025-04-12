package com.LoginAuth.demo.repository;

import com.LoginAuth.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
