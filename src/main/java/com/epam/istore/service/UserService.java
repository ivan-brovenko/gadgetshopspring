package com.epam.istore.service;


import com.epam.istore.model.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.exception.UserServiceException;

import javax.servlet.http.HttpSession;

public interface UserService {
    void add(User user) throws UserServiceException;

    boolean containsUser(User user) throws UserServiceException;

    void logout(HttpSession session);

    User getAuthenticatedUser(String login, String password) throws AuthenticationException;
}
