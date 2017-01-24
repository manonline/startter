package com.manonline.examples.servlet.include;

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
public class MainServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><head><title>MainServlet</title></head>");
        out.println("<body>");

        ServletContext context = getServletContext();
        RequestDispatcher headerDistpacher = context.getRequestDispatcher("/header.htm");
        RequestDispatcher greetDistpacher = context.getRequestDispatcher("/greet");
        RequestDispatcher footerDistpacher = context.getRequestDispatcher("/footer.htm");

        headerDistpacher.include(request, response);
        greetDistpacher.include(request, response);
        footerDistpacher.include(request, response);

        out.println("</body></html>");

        out.close();
    }
}
