package com.epam.istore.transaction;


import com.epam.istore.exception.RepositoryException;

public interface CustomSupplier<T> {
    T get() throws RepositoryException;
}
