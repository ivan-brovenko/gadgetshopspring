package com.epam.istore.messages;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class Messages {

    private Messages(){
    }
    public final static String USER_ID = "id";
    public final static String USER_NAME = "name";
    public final static String USER_SURNAME = "surname";
    public final static String USER_EMAIL= "email";
    public final static String USER_PASSWORD = "password";
    public final static String USER_GENDER = "gender";
    public final static String USER_ROLE = "role";
    public final static String NAME = "name";
    public final static String SURNAME = "surname";
    public final static String EMAIL = "email";
    public final static String PASSWORD = "password";
    public final static String CONFIRM_PASSWORD = "password2";
    public final static String GENDER = "gender";
    public final static String CAPTCHA = "captchaNumbers";
    public final static String PHOTO = "photo";
    public final static String INVALID_NAME = "Name shouldn't contains numbers or forbidden symbols and be less than 15 symbols!";
    public final static String INVALID_SURNAME = "Surname shouldn't contains numbers or forbidden symbols and be than 15 symbols!";
    public final static String INVALID_EMAIL = "Email should be written like example.email@gmail.com!";
    public final static String INVALID_PASSWORD = "Password should contains more than 8 symbols!";
    public final static String INVALID_CONFIRM_PASSWORD = "Passwords don't match";
    public final static String INVALID_GENDER = "Invalid gender";
    public final static String INVALID_CAPTCHA = "Are you not a robot?";
    public final static String CAPTCHA_TIME_EXPIRED = "Captcha time expired";
    public final static String DUPLICATE_USER = "User with this login already registered";
    public final static String AUTHENTICATE_ERROR = "Can't authenticate user";
    public final static String EMPTY_PHOTO_ERROR = "Add your photo!";
    public final static String CAPTCHA_ID = "id";
    public final static String HIDDEN_ID = "hiddenId";
    public final static String DUPLICATE = "duplicate";
    public final static String APP_CONTEXT = "appContext";
    public final static String TIMEOUT = "timeout";
    public final static String CAPTCHA_SERVICE = "captchaService";
    public final static String USER_ATTRIBUTE_NAME = "_user";
    public final static String PNG = ".png";
    public final static String AVATAR_DIRECTORY = "avatarDirectory";
    public static final String MAIN_PAGE_LINK = "/";
    public static final String LIMIT = " LIMIT ";
    public static final String OFFSET = " OFFSET ";
    public static final String STRING_QUOTE = "'";
    public static final String COMMA = ",";
    public static final String DEFAULT_PRODUCT_LIMIT = "6";
    public static final String DEFAULT_CURRENT_PAGE_VALUE = "1";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String PRICE_FROM = "priceFrom";
    public static final String PRICE_TO = "priceTo";
    public static final String PRODUCER_COUNTRY = "producerCountry";
    public static final String PRODUCT_NAME = "productName";
    public static final String LIMIT_ARGUMENT = "limit";
    public static final String SORT = "sort";
    public static final String CATEGORIES_CHECK = "categoriesCheck";
    public static final String GADGETS = "gadgets";
    public static final String CATEGORIES = "categories";
    public static final String COUNTRIES = "countries";
    public static final String NUMBER_OF_PAGES = "nOfPages";
    public static final String RECORDS_PER_PAGE = "recordsPerPage";
    public static final String PAGES_PRODUCTS_JSP = "/pages/products.jsp";

}
