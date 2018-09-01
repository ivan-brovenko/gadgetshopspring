package com.epam.istore.repository;

import com.epam.istore.model.User;
import com.epam.istore.exception.RepositoryException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository {

    void add(User user) ;

    List<User> getUsers();
}
