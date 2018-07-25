package com.epam.istore.bean;

import org.springframework.stereotype.Component;

@Component
public class RegFormBean {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String confirmPassword;
    private String gender;
    private String captchaNumbers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCaptchaNumbers() {
        return captchaNumbers;
    }

    public void setCaptchaNumbers(String captchaNumbers) {
        this.captchaNumbers = captchaNumbers;
    }
}
