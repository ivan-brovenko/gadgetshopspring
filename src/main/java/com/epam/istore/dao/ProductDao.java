package com.epam.istore.dao;


import com.epam.istore.model.Product;
import com.epam.istore.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductDao extends CrudRepository<User, Integer> {
    List<User> findAll();
}
