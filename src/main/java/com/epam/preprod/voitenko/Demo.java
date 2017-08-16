package com.epam.preprod.voitenko;

import com.epam.preprod.voitenko.bean.UserBean;
import com.epam.preprod.voitenko.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class Demo extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        System.out.println(userService.getAllUsers());
        final UserBean userById = userService.getUserById(1);
        System.out.println(userById);

        userById.setFullName("SSSSSSSS");
        System.out.println(userService.getUserById(77));
        System.out.println(userService.updateUser(userById));
        System.out.println(userService.deleteUser(1));
        System.out.println(userService.deleteUser(999));
    }
}
