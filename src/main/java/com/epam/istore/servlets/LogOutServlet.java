package com.epam.istore.servlets;

import com.epam.istore.context.ApplicationContext;
import com.epam.istore.entity.User;
import com.epam.istore.messages.Messages;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.istore.messages.Messages.APP_CONTEXT;
import static com.epam.istore.messages.Messages.USER_ATTRIBUTE_NAME;


@WebServlet(name = "LogOutServlet", urlPatterns = "/logout")
public class LogOutServlet extends HttpServlet {
    public static final String REFERER = "referer";
    private ApplicationContext applicationContext;
    private UserService userService;
    private AvatarService avatarService;

    @Override
    public void init() throws ServletException {
        this.applicationContext = (ApplicationContext) getServletContext().getAttribute(APP_CONTEXT);
        this.userService = applicationContext.getUserService();
        this.avatarService = applicationContext.getAvatarService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE_NAME);
        avatarService.removeTempAvatar(request, user.getEmail());
        userService.logout(session);
        response.sendRedirect(request.getHeader(REFERER));
    }
}
