package com.epam.istore.service;


import com.epam.istore.entity.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.exception.UserServiceException;

import javax.servlet.http.HttpSession;

public interface UserService {
    boolean add(User user) throws UserServiceException;

    boolean containsUser(User user) throws UserServiceException;

    void logout(HttpSession session);

    User getAuthenticatedUser(String login, String password) throws AuthenticationException;
}
