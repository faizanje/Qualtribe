package com.example.qualtribe.models;

public class User {
    public String email, password, confirmPassword;

    public User(){

    }
    public User(String email, String password){
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
