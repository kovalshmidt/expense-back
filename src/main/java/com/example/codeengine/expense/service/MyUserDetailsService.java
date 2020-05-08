package com.example.codeengine.expense.service;

import com.example.codeengine.expense.model.User;
import com.example.codeengine.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //get optional with user from database by username (email)
        //optional can hold the object specified in < > or null
        Optional<User> optionalUser = userRepository.findUserByEmail(userName);

        //check if optional has value
        if (optionalUser.isPresent()) {
            //get value from optional
            User user = optionalUser.get();
            //getting values from user
            String email = user.getEmail();
            String password = user.getPassword();
            //create userdetails.User with model.User Data
            return new org.springframework.security.core.userdetails.User(email, password, new ArrayList<>());
        }
        throw new UsernameNotFoundException("user with username :" + userName + " not found");

    }
}

