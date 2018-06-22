package com.epam.istore.repository.impl;

import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.entity.User;
import com.epam.istore.exception.RepositoryException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest {
    private final static String ADD_USER = "INSERT INTO user VALUES(default,?,?,?,?,?,default)";
    private final static String CONTAINS_USER = "SELECT email FROM user WHERE email = ?";

    @Mock
    private Connection connection;
    @Mock
    private ConnectionHolder connectionHolder;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private User user;
    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Before
    public void setUp() throws SQLException {
        when(connectionHolder.getConnection()).thenReturn(connection);
    }

    @Test
    public void testResultSetOnContainsCategoryWithProperName() throws SQLException, RepositoryException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    public void testContainsUserMethodOnSettingRightUserEmail() throws SQLException, RepositoryException {
        when(connection.prepareStatement(CONTAINS_USER)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(user.getEmail()).thenReturn("email");
        userRepository.containsUser(user);
        verify(preparedStatement).setString(1, "email");
    }


}