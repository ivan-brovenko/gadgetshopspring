package com.epam.istore.service.impl;


import com.epam.istore.entity.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.UserServiceException;
import com.epam.istore.repository.impl.UserRepositoryImpl;
import com.epam.istore.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = Logger.getRootLogger();
    private UserRepositoryImpl repository;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    @Transactional
    public boolean add(User user) throws UserServiceException {
        try {
            return repository.add(user);
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new UserServiceException(e);
        }
    }

    @Transactional
    public boolean containsUser(User user) throws UserServiceException {
        try {
            return repository.containsUser(user);
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new UserServiceException(e);
        }
    }

    @Transactional
    public User getAuthenticatedUser(String login, String password) throws AuthenticationException {
        if (login != null && password != null) {
            try {
                return repository.getUserByLoginAndPassword(login, password);
            } catch (RepositoryException e) {
                LOGGER.error(e);
                throw new AuthenticationException(e);
            }
        }
        return new User();
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
