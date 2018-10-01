package com.epam.istore.service.impl;


import com.epam.istore.dao.UserDao;
import com.epam.istore.model.User;
import com.epam.istore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void registerUser(User user) {
        userDao.save(user);
    }

    public User getAuthenticatedUser(String login, String password) {
        return userDao.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(login) &&
                        user.getPassword().equals(password))
                .findFirst().orElse(null);
    }
}
