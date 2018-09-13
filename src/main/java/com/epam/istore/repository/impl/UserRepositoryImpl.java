package com.epam.istore.repository.impl;


import com.epam.istore.model.User;
import com.epam.istore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void add(User user) {
        createEntityManager().persist(user);
    }

    @Override
    public List<User> getUsers() {
        return createEntityManager().createQuery("from user").getResultList();
    }

    private EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
