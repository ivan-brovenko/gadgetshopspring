package com.epam.istore.validator;

import com.epam.istore.bean.RegFormBean;
import com.epam.istore.captcha.Captcha;
import com.epam.istore.messages.Messages;
import com.epam.istore.service.CaptchaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {
    private final static String INVALID_STRING = "$$$invalidString";
    private final static String INVALID_PASSWORD_VALUE = "111";
    private final static String NAME_VALUE = "name";
    private final static String SURNAME_VALUE = "surname";
    private final static String EMAIL_VALUE = "email.test@gmail.com.png";
    private final static String PASSWORD_VALUE = "1111111111";
    private final static String GENDER_VALUE = "male";
    private final static String CAPTCHA_ID_TEST_VALUE = "666";
    private final static String CAPTCHA_SERVICE = "captchaService";
    @Mock
    private RegFormBean regRegFormBean;
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext servletContext;
    @Mock
    private Captcha captcha;
    @Mock
    private CaptchaService captchaService;
    @InjectMocks
    private RegFormValidator validator;

    @Before
    public void setUp() throws IOException {
        when(regRegFormBean.getName()).thenReturn(NAME_VALUE);
        when(regRegFormBean.getSurname()).thenReturn(SURNAME_VALUE);
        when(regRegFormBean.getEmail()).thenReturn(EMAIL_VALUE);
        when(regRegFormBean.getPassword()).thenReturn(PASSWORD_VALUE);
        when(regRegFormBean.getConfirmPassword()).thenReturn(PASSWORD_VALUE);
        when(regRegFormBean.getCaptchaNumbers()).thenReturn(CAPTCHA_ID_TEST_VALUE);
        when(servletContext.getAttribute(CAPTCHA_SERVICE)).thenReturn(captchaService);
        when(captchaService.getId(request)).thenReturn(CAPTCHA_ID_TEST_VALUE);
        when(captchaService.generateCaptcha(anyLong())).thenReturn(captcha);
        when(captchaService.getCaptchaById(CAPTCHA_ID_TEST_VALUE, request)).thenReturn(captcha);
        when(regRegFormBean.getGender()).thenReturn(GENDER_VALUE);
        when(request.getServletContext()).thenReturn(servletContext);
        when(captcha.getCaptchaNumber()).thenReturn(CAPTCHA_ID_TEST_VALUE);
    }

    @Test
    public void testValidateOnReturnErrorMessageIfCaptchaIsExpired() {
        when(captcha.isExpired()).thenReturn(true);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        assertEquals(1, errorMap.size());
        assertEquals(Messages.CAPTCHA_TIME_EXPIRED, errorMap.get(Messages.CAPTCHA));
    }

    @Test
    public void testValidateOnReturnErrorMessageIfNameIsInvalid() {
        when(regRegFormBean.getName()).thenReturn(INVALID_STRING);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        assertEquals(1, errorMap.size());
        assertEquals(Messages.INVALID_NAME, errorMap.get(Messages.NAME));
    }

    @Test
    public void testValidatorOnReturnErrorMessageIfCaptchaNumbersNotMatch() {
        when(regRegFormBean.getCaptchaNumbers()).thenReturn(INVALID_STRING);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        assertEquals(1, errorMap.size());
        assertEquals(Messages.INVALID_CAPTCHA, errorMap.get(Messages.CAPTCHA));
    }

    @Test
    public void testValidatorOnReturnErrorMessageOnNotMatchPasswords() {
        when(regRegFormBean.getConfirmPassword()).thenReturn(INVALID_STRING);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        assertEquals(1, errorMap.size());
        assertEquals(Messages.INVALID_CONFIRM_PASSWORD, errorMap.get(Messages.CONFIRM_PASSWORD));
    }

    @Test
    public void testValidatorOnReturnErrorMessageOnInvalidGender() {
        when(regRegFormBean.getGender()).thenReturn(INVALID_STRING);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        assertEquals(1, errorMap.size());
        assertEquals(Messages.INVALID_GENDER, errorMap.get(Messages.GENDER));
    }

    @Test
    public void testValidatorOnReturnErrorMessageOnInvalidEmail() {
        when(regRegFormBean.getEmail()).thenReturn(INVALID_STRING);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        assertEquals(1, errorMap.size());
        assertEquals(Messages.INVALID_EMAIL, errorMap.get(Messages.EMAIL));
    }

    @Test
    public void testValidatorOnReturnErrorMessageOnPasswordLengthLessThan8() {
        when(regRegFormBean.getPassword()).thenReturn(INVALID_PASSWORD_VALUE);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        assertEquals(2, errorMap.size());
        assertEquals(Messages.INVALID_CONFIRM_PASSWORD, errorMap.get(Messages.CONFIRM_PASSWORD));
        assertEquals(Messages.INVALID_PASSWORD, errorMap.get(Messages.PASSWORD));
    }

}