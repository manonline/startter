package com.manonline.examples.servlet.redirect;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by davidqi on 1/24/17.
 */
public class CheckServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String message = null;
        if (username == null) {
            message = "Please input username";
        } else {
            message = "Hello, " + username;
        }

        request.setAttribute("msg", message);

        out.println("Output from CheckServlet before redirecting.");
        System.out.println("Output from CheckServlet before redirecting.");

        response.sendRedirect("/helloapp/output1?msg=" + message);

        out.println("Output from CheckServlet after redirecting.");
        System.out.println("Output from CheckServlet after redirecting.");
    }
}