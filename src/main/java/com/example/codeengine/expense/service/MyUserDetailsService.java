package com.example.codeengine.expense.service;

import com.example.codeengine.expense.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //get optional with user from database by username (email)
        //optional can hold the object specified in < > or null
        Optional<User> optionalUser = userService.findByEmail(userName);

        //check if optional has value
        if (optionalUser.isPresent()) {
            //get value from optional
            User user = optionalUser.get();

            //getting values from user
            String email = user.getEmail();
            String password = user.getPassword();
            String roles = user.getRoles();

            //create userdetails.User with model.User Data
            return new org.springframework.security.core.userdetails.User(
                    email, password, getGrantedAuthorities(roles)
            );
        }
        throw new UsernameNotFoundException("user with username :" + userName + " not found");

    }

    private List<GrantedAuthority> getGrantedAuthorities(String roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] rolesArray = roles.split("\\s*,\\s*");
        for (String role : rolesArray) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}

