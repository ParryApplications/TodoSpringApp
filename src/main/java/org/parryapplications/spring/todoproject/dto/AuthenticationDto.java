package org.parryapplications.spring.todoproject.dto;


import jakarta.validation.constraints.NotNull;

public class AuthenticationDto {
    @NotNull
    private String username;
    @NotNull
    private String password;

    public AuthenticationDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
