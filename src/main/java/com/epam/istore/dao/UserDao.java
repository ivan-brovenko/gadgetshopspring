package com.epam.istore.dao;

import com.epam.istore.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    List<User> findAll();
}
