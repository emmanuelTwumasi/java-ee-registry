package com.emma.temp.model.request;

import jakarta.json.bind.annotation.JsonbProperty;

public class PasswordReset {
    @JsonbProperty("email")
    private String email;
    @JsonbProperty("password")
    private String password;
    @JsonbProperty("confirmPassword")
    private String confirmPassword;

    public PasswordReset() {
    }

    public PasswordReset(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}