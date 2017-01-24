package com.manonline.examples.servlet.forward;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by davidqi on 1/24/17.
 */
public class CheckServlet extends GenericServlet {

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String message = null;
        if (username == null) {
            message = "Please input username";
        } else {
            message = "Hello, " + username;
        }

        request.setAttribute("msg", message);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/output");

        PrintWriter out = response.getWriter();

        out.println("Output from CheckServlet before forwarding request.");
        System.out.println("Output from CheckServlet before forwarding request.");

        dispatcher.forward(request, response);

        out.println("Output from CheckServlet after forwarding request.");
        System.out.println("Output from CheckServlet after forwarding request.");
    }
}
