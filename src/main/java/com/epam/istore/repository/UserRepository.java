package com.epam.istore.repository;

import com.epam.istore.model.User;

import java.util.List;


public interface UserRepository {

    void add(User user) ;

    List<User> getUsers();
}
