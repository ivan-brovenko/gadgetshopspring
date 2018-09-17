package com.epam.istore.service;


import com.epam.istore.model.User;

import javax.servlet.http.HttpSession;

public interface UserService {

    void registerUser(User user);

    User getAuthenticatedUser(String login, String password);
}
