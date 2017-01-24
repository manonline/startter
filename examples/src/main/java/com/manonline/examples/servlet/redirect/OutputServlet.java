package com.manonline.examples.servlet.redirect;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by davidqi on 1/24/17.
 */
public class OutputServlet extends GenericServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String message = (String) request.getAttribute("msg");
        System.out.println("Message carried in the request : " + message);

        message = request.getParameter("msg");
        System.out.println("Message carried in the parameter : " + message);
        PrintWriter out = response.getWriter();

        out.println(message);
        out.close();
    }
}
