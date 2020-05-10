package com.example.codeengine.expense.repository;

import com.example.codeengine.expense.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    //SELECT * FROM User WHERE email = ?
    Optional<User> findByEmail(String email);

    //SELECT * FROM USer WHERE email = ? and name = ?
    Optional<User> findByEmailAndName(String email, String name);
}
