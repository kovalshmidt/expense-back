package com.example.codeengine.expense.controller;

import com.example.codeengine.expense.config.SecurityConfigurer;
import com.example.codeengine.expense.model.AuthenticationRequest;
import com.example.codeengine.expense.model.AuthenticationResponse;
import com.example.codeengine.expense.service.MyUserDetailsService;
import com.example.codeengine.expense.utility.JwtUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SecurityController {

    private final SecurityConfigurer securityConfigurer;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtility jwtUtility;

    public SecurityController(SecurityConfigurer securityConfigurer, MyUserDetailsService userDetailsService, JwtUtility jwtUtility) {
        this.securityConfigurer = securityConfigurer;
        this.userDetailsService = userDetailsService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            securityConfigurer.authenticationManagerBean().authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrec  password", e);
        } catch (UsernameNotFoundException e) {
            throw new Exception("No such user is present", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtility.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
