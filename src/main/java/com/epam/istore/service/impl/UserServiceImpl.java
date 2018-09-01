package com.epam.istore.service.impl;


import com.epam.istore.exception.GadgetShopException;
import com.epam.istore.model.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.UserServiceException;
import com.epam.istore.repository.UserRepository;
import com.epam.istore.repository.impl.UserRepositoryImpl;
import com.epam.istore.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public void add(User user) {
        repository.add(user);
    }

    @Transactional
    public boolean containsUser(User user) {
        return repository.getUsers().contains(user);
    }

    @Transactional
    public User getAuthenticatedUser(String login, String password) {
        return repository.getUsers()
                .stream()
                .filter(user->user.getEmail().equals(login) &&
                        user.getPassword().equals(password))
                .findFirst().get();
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
