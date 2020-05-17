package com.example.codeengine.expense.controller;

import com.example.codeengine.expense.model.User;
import com.example.codeengine.expense.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public Collection<User> users() {
        return userService.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getUser(@PathVariable("id") String id) {
        Optional<User> user = userService.findById(id);
        return user.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<User> user(@Valid @RequestBody User user) throws URISyntaxException {
        User result = userService.save(user);
        return ResponseEntity.created(new URI("/api/user/" + result.getId())).body(result);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User result = userService.save(user);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
