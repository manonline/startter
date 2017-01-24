package com.manonline.examples.servlet.forward;

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
        PrintWriter out = response.getWriter();

        out.println(message);
        out.close();
    }
}
