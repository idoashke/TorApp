package com.example.finalproject;

public class BusinessProfiler {
    String busName;
    String email;
    String category;
    String city;
    String address;
    String postalCode;
    String phone;
    String password;

        public BusinessProfiler() {
            this.busName = "";
            this.email = "";
            this.category = "";
            this.city = "";
            this.address = "";
            this.postalCode = "";
            this.phone = "";
    }

    public BusinessProfiler(String busName, String email, String category, String city, String address, String postalCode, String phone) {
//        this.userName = userName;
        this.busName = busName;
        this.email = email;
        this.category = category;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public BusinessProfiler(BusinessProfiler other){
        this.busName = other.busName;
        this.email = other.email;
        this.category = other.category;
        this.city = other.city;
        this.address = other.address;
        this.postalCode = other.postalCode;
        this.phone = other.phone;
    }


    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}



