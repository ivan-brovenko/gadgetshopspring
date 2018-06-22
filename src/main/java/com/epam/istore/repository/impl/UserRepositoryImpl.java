package com.epam.istore.repository.impl;


import com.epam.istore.entity.User;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.messages.Messages;
import com.epam.istore.repository.UserRepository;
import com.epam.istore.connection.ConnectionHolder;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepositoryImpl implements UserRepository {
    private final static Logger LOGGER = Logger.getRootLogger();
    private ConnectionHolder connectionHolder;
    private final static String ADD_USER = "INSERT INTO user VALUES(default,?,?,?,?,?,default)";
    private final static String CONTAINS_USER = "SELECT email FROM user WHERE email = ?";
    private final static String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT user.id,user.name,user.surname,user.email,user.password,user.gender,role.name role FROM user INNER JOIN role ON user.role_id = role.id WHERE user.email = ? AND user.password = ?";

    public UserRepositoryImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    public boolean add(User user) throws RepositoryException {
        if (containsUser(user)) {
            return false;
        }
        insertUser(user);
        return true;
    }

    private void insertUser(User user) throws RepositoryException {
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER);) {
            int k = 1;
            preparedStatement.setString(k++, user.getName());
            preparedStatement.setString(k++, user.getSurname());
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k++, user.getPassword());
            preparedStatement.setBoolean(k++, user.isMale());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
    }

    public boolean containsUser(User user) throws RepositoryException {
        Connection connection = connectionHolder.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER);) {
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
    }

    public User getUserByLoginAndPassword(String login, String password) throws RepositoryException {
        User user = null;
        ResultSet resultSet = null;
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD);) {
            int k = 1;
            preparedStatement.setString(k++, login);
            preparedStatement.setString(k++, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
        return user;
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
