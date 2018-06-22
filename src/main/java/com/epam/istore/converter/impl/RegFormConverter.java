package com.epam.istore.converter.impl;

import com.epam.istore.bean.RegFormBean;
import com.epam.istore.converter.Converter;
import com.epam.istore.entity.User;
import com.epam.istore.util.Genders;


public class RegFormConverter implements Converter<RegFormBean, User> {
    public User convert(RegFormBean regFormBean) {
        User user = new User();
        user.setEmail(regFormBean.getEmail());
        user.setName(regFormBean.getName());
        user.setSurname(regFormBean.getSurname());
        user.setPassword(regFormBean.getPassword());
        user.setGender(Genders.valueOf(regFormBean.getGender().toUpperCase()).isMale());
        return user;
    }
}
