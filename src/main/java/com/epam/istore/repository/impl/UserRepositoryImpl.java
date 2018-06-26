package com.epam.istore.repository.impl;


import com.epam.istore.entity.User;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.messages.Messages;
import com.epam.istore.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepositoryImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;
    private final static String ADD_USER = "INSERT INTO user VALUES(default,?,?,?,?,?,default)";
    private final static String CONTAINS_USER = "SELECT email FROM user WHERE email = ?";
    private final static String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT user.id,user.name,user.surname,user.email,user.password,user.gender,role.name role FROM user INNER JOIN role ON user.role_id = role.id WHERE user.email = ? AND user.password = ?";

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean add(User user) throws RepositoryException {
        if (containsUser(user)) {
            return false;
        }
        insertUser(user);
        return true;
    }

    private void insertUser(User user) throws RepositoryException {
        jdbcTemplate.update(ADD_USER, user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.isMale());
    }

    public boolean containsUser(User user) throws RepositoryException {
        return jdbcTemplate.queryForObject(CONTAINS_USER, new Object[]{user.getEmail()}, new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.next();
            }
        });
    }

    public User getUserByLoginAndPassword(String login, String password) throws RepositoryException {
        return jdbcTemplate.queryForObject(GET_USER_BY_LOGIN_AND_PASSWORD, new Object[]{login, password}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                System.out.println(createUser(resultSet)+"user");
                return createUser(resultSet);
            }
        });
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(Messages.USER_ID));
        user.setName(resultSet.getString(Messages.USER_NAME));
        user.setSurname(resultSet.getString(Messages.USER_SURNAME));
        user.setEmail(resultSet.getString(Messages.USER_EMAIL));
        user.setPassword(resultSet.getString(Messages.USER_PASSWORD));
        user.setGender(resultSet.getBoolean(Messages.USER_GENDER));
        user.setRole(resultSet.getString(Messages.USER_ROLE));
        return user;
    }

}
