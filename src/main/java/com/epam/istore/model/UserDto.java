package com.epam.istore.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Setter
@Getter
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String confirmPassword;
    private String gender;
    private String captchaNumbers;
    private MultipartFile file;
}
