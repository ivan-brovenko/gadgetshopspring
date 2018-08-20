package com.epam.istore.repository;


import com.epam.istore.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<User, Integer> {
}
