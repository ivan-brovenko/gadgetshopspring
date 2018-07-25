package com.epam.istore.context;


import com.epam.istore.captcha.CaptchaGenerator;
import com.epam.istore.converter.impl.OrderConverter;
import com.epam.istore.exception.ConnectionPoolException;
import com.epam.istore.repository.OrderRepository;
import com.epam.istore.repository.impl.GadgetRepositoryImpl;
import com.epam.istore.repository.impl.OrderRepositoryImpl;
import com.epam.istore.repository.impl.UserRepositoryImpl;
import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.connection.ConnectionPool;
import com.epam.istore.service.*;
import com.epam.istore.service.impl.GadgetServiceImpl;
import com.epam.istore.service.impl.OrderServiceImpl;
import com.epam.istore.transaction.TransactionManager;
import com.epam.istore.service.impl.UserServiceImpl;
import com.epam.istore.validator.RegFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


public class ApplicationContext {
    private UserRepositoryImpl userRepositoryImpl;
    private UserService userService;
    private RegFormValidator validator;
    private CaptchaGenerator captchaGenerator;
    private AvatarService avatarService = new AvatarService();
    private GadgetService gadgetService;
    private GadgetRepositoryImpl gadgetRepository;
    private OrderConverter orderConverter;
    private OrderService orderService;
    private OrderRepository orderRepository;
    private JdbcTemplate jdbcTemplate;

    public ApplicationContext() {
        this.validator = new RegFormValidator();
        this.captchaGenerator = new CaptchaGenerator();
        this.orderConverter = new OrderConverter();
//        org.springframework.context.ApplicationContext applicationContext = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring-servlet.xml");
//        this.jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
//        this.userRepositoryImpl = new UserRepositoryImpl(jdbcTemplate);
        this.userService = new UserServiceImpl(userRepositoryImpl);
        this.gadgetRepository = new GadgetRepositoryImpl(jdbcTemplate);
        this.gadgetService = new GadgetServiceImpl(gadgetRepository);
        this.orderRepository = new OrderRepositoryImpl(jdbcTemplate);
        this.orderService = new OrderServiceImpl(orderRepository);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    public RegFormValidator getValidator() {
        return validator;
    }

    public void setValidator(RegFormValidator validator) {
        this.validator = validator;
    }

    public CaptchaGenerator getCaptchaGenerator() {
        return captchaGenerator;
    }

    public void setCaptchaGenerator(CaptchaGenerator captchaGenerator) {
        this.captchaGenerator = captchaGenerator;
    }

    public UserRepositoryImpl getUserRepositoryImpl() {
        return userRepositoryImpl;
    }

    public void setUserRepositoryImpl(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    public AvatarService getAvatarService() {
        return avatarService;
    }

    public void setAvatarService(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    public GadgetService getGadgetService() {
        return gadgetService;
    }

    public void setGadgetService(GadgetService gadgetService) {
        this.gadgetService = gadgetService;
    }

    public OrderConverter getOrderConverter() {
        return orderConverter;
    }

    public void setOrderConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
