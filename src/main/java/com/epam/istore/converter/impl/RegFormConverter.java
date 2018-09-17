package com.epam.istore.converter.impl;

import com.epam.istore.model.UserDto;
import com.epam.istore.converter.Converter;
import com.epam.istore.model.Role;
import com.epam.istore.model.User;
import com.epam.istore.util.Genders;


public class RegFormConverter implements Converter<UserDto, User> {
    public User convert(UserDto userDto) {
        User user = new User();
        Role userRole = new Role();
        userRole.setId(2);
        userRole.setName("user");
        user.setRole(userRole);
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPassword(userDto.getPassword());
        user.setGender(Genders.valueOf(userDto.getGender().toUpperCase()).isMale());
        return user;
    }
}
