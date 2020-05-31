package com.example.codeengine.expense.model;

import java.util.List;

public class AuthenticationResponse {

    private String jwt;

    private List<String> roles;

    public AuthenticationResponse(String jwt, List<String> roles) {
        this.jwt = jwt;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public List<String> getRoles() {
        return roles;
    }
}
