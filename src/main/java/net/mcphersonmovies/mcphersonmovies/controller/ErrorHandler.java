package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.shared.AzureEmail;

import static jakarta.servlet.RequestDispatcher.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/errorHandler")
public class ErrorHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMsg = "<strong>Error code:</strong>" + req.getAttribute(ERROR_STATUS_CODE) + "<br>";
        errorMsg += "<strong>Exception:</strong>" + req.getAttribute(ERROR_EXCEPTION_TYPE) + "<br>";
        errorMsg += "<strong>Message:</strong>" + req.getAttribute(ERROR_MESSAGE) + "<br>";
        errorMsg += "<strong>Servlet:</strong>" + req.getAttribute(ERROR_SERVLET_NAME) + "<br>";
        errorMsg += "<strong>Request URI:</strong>" + req.getAttribute(ERROR_REQUEST_URI) + "<br>";
        if(!getServletContext().getInitParameter("debugging").equals("true")) {
            errorMsg += AzureEmail.sendEmail("jacob-mcpherson@student.kirkwood.edu", "ERROR MESSAGE", errorMsg);
        }
        req.setAttribute("errorMsg", errorMsg);
        req.setAttribute("pageTitle", "Error");
        req.getRequestDispatcher("WEB-INF/error.jsp").forward(req, resp);
    }
}
