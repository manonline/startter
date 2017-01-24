package com.manonline.examples.servlet.concurrency;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by davidqi on 1/24/17.
 */
public class HelloServlet extends GenericServlet {
    private String username = null;

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username != null) {
            username = new String(username.getBytes("ISO-8859-1"), "GB2312");
        }

        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        response.setContentType("text/html;charset=GB2312");

        PrintWriter out = response.getWriter();

        out.println("<html><head><title>HelloServlet</title></head>");
        out.println("<body>");
        out.println("Hello : " + username);
        out.println("</body></html>");

        out.close();
    }
}
