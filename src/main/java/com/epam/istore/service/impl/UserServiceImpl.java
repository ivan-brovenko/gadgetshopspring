package com.epam.istore.service.impl;


import com.epam.istore.entity.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.UserServiceException;
import com.epam.istore.repository.impl.UserRepositoryImpl;
import com.epam.istore.service.UserService;
import org.apache.log4j.Logger;
import com.epam.istore.transaction.TransactionManager;

import javax.servlet.http.HttpSession;


public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = Logger.getRootLogger();
    private UserRepositoryImpl repository;
    private TransactionManager transactionManager;

    public UserServiceImpl(UserRepositoryImpl repository, TransactionManager transactionManager) {
        this.repository = repository;
        this.transactionManager = transactionManager;
    }

    public boolean add(User user) throws UserServiceException {
        try {
            return transactionManager.doInTransaction(() -> repository.add(user));
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new UserServiceException(e);
        }
    }

    public boolean containsUser(User user) throws UserServiceException {
        try {
            return transactionManager.doInTransaction(() -> repository.containsUser(user));
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new UserServiceException(e);
        }
    }

    public User getAuthenticatedUser(String login, String password) throws AuthenticationException {
        if (login != null && password != null) {
            try {
                return transactionManager.doInTransaction(() -> repository.getUserByLoginAndPassword(login, password));
            } catch (RepositoryException e) {
                LOGGER.error(e);
                throw new AuthenticationException(e);
            }
        }
        return null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
