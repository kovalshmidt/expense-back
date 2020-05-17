package com.example.codeengine.expense.service;

import com.example.codeengine.expense.model.User;
import com.example.codeengine.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(UUID.fromString(id));
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public User save(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        //Check if such user exists, if it does, update
//        Optional<User> userByEmail = findByEmail(user.getEmail());
//        if(userByEmail.isPresent()) {
//            return  userRepository.save(user);
//        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
