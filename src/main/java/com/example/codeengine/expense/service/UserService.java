package com.example.codeengine.expense.service;

import com.example.codeengine.expense.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(String id);

    void deleteById(String id);

    User save(User user);

    Optional<User> findByEmail(String email);
}
