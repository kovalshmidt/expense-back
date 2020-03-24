package com.example.codeengine.expense.controller;

import com.example.codeengine.expense.model.User;
import com.example.codeengine.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Collection<User> users(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable("id") String id) {
        Optional<User> user = userRepository.findById(UUID.fromString(id));
        return user.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user")
    public ResponseEntity<User> user(@Valid @RequestBody User user) throws URISyntaxException {
        User result = userRepository.save(user);
        return ResponseEntity.created(new URI("/api/user/" + result.getId())).body(result);
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User result = userRepository.save(user);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userRepository.deleteById(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

}
