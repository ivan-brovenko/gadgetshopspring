package com.epam.istore.repository.impl;


import com.epam.istore.model.User;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.messages.Messages;
import com.epam.istore.repository.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private Session session;

    @Override
    public void add(User user) {
        session.save(user);
    }

    @Override
    public List<User> getUsers(){
        return session.createQuery("from user").list();
    }
}
