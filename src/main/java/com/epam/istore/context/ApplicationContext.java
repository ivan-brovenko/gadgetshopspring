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

public class ApplicationContext {
    private final static Logger LOGGER = Logger.getRootLogger();
    private UserRepositoryImpl userRepositoryImpl;
    private UserService userService;
    private RegFormValidator validator;
    private CaptchaGenerator captchaGenerator;
    private TransactionManager transactionManager;
    private AvatarService avatarService = new AvatarService();
    private GadgetService gadgetService;
    private GadgetRepositoryImpl gadgetRepository;
    private OrderConverter orderConverter;
    private OrderService orderService;
    private OrderRepository orderRepository;

    public ApplicationContext() {
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error(e);
        }
        ConnectionHolder connectionHolder = new ConnectionHolder();
        this.userRepositoryImpl = new UserRepositoryImpl(connectionHolder);
        this.transactionManager = new TransactionManager(connectionPool, connectionHolder);
        this.userService = new UserServiceImpl(userRepositoryImpl, transactionManager);
        this.validator = new RegFormValidator();
        this.captchaGenerator = new CaptchaGenerator();
        this.gadgetRepository = new GadgetRepositoryImpl(connectionHolder);
        this.gadgetService = new GadgetServiceImpl(gadgetRepository,transactionManager);
        this.orderConverter = new OrderConverter();
        this.orderRepository = new OrderRepositoryImpl(connectionHolder);
        this.orderService = new OrderServiceImpl(orderRepository,transactionManager);
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
}
