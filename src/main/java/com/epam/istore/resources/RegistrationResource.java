package com.epam.istore.resources;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static com.epam.istore.messages.Messages.PHOTO;


public interface RegistrationResource {

    @GetMapping("/reg")
    String createRegistrationPage(HttpServletRequest request, HttpServletResponse response);

    @PostMapping(name = "/reg")
    String submitRegistration(@RequestParam("file")MultipartFile file, HttpServletRequest request);
}
