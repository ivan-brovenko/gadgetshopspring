package com.epam.istore.repository.impl;

import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.repository.GadgetRepository;
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
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryImplTest {
    private static final String CATEGORY_NAME_VALUE = "categoryNameValue";
    private final static String GET_ALL_CATEGORIES = "SELECT id,category_name FROM category;";
    @Mock
    private Connection connection;
    @Mock
    private ConnectionHolder connectionHolder;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private GadgetRepositoryImpl gadgetRepository;

    @Before
    public void setUp() throws SQLException {
        when(connectionHolder.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(GET_ALL_CATEGORIES)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void testResultSetOnReturnEmptyListOfCategories() throws RepositoryException, SQLException {
        when(resultSet.next()).thenReturn(false);
        assertTrue(gadgetRepository.getAllCategories().isEmpty());
    }

    @Test
    public void testResultSetOnContainsCategoryWithProperName() throws SQLException, RepositoryException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("category_name")).thenReturn(CATEGORY_NAME_VALUE);
        assertEquals(CATEGORY_NAME_VALUE,gadgetRepository.getAllCategories().get(0).getCategoryName());
    }

}