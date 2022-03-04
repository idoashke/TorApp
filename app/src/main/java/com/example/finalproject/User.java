package com.example.finalproject;

public class User {
//    String userName;
    String email;
    String firstName;
    String lastName;
    String phone;
    String password;

    public User() {
    }

    public User(String email, String firstName, String lastName, String phone) {
//        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
//        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

//    public String getUserName() { return userName; }
//
//    public void setUserName(String userName) { this.userName = userName; }
}
