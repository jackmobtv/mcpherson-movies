package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;
import net.mcphersonmovies.shared.ChargeCreditCard;

import java.io.IOException;

@WebServlet(value="/subscribe")
public class Subscribe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if (user == null) {
            session.setAttribute("flashMessageWarning", "You are not logged in");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login?redirect=subscribe"));
            return;
        } else if (!user.getStatus().equals("active")){
            session.setAttribute("flashMessageDanger", "Your Account is locked or inactive, Please reset your password");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
            return;
        } else if (!user.getPrivileges().equals("User")) {
            session.setAttribute("flashMessageWarning", "Your Account already has Premium Access");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
            return;
        }

        req.setAttribute("pageTitle", "Subscribe");
        req.getRequestDispatcher("WEB-INF/subscribe.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        boolean errorFound = false;

        String expiration = req.getParameter("expiration");
        String ccNumber = req.getParameter("number");
        String cvv = req.getParameter("cvv");

        if(expiration == null || expiration.isEmpty()){
            errorFound = true;
        } else {
            expiration = expiration.replace("/", "");
        }

        if(ccNumber == null || ccNumber.isEmpty()){
            errorFound = true;
        }

        if(cvv == null || cvv.isEmpty()){
            errorFound = true;
        }

        String[] ccInfo = new String[4];
        ccInfo[0] = ccNumber;
        ccInfo[1] = expiration;
        ccInfo[2] = cvv;

        String authResponse = null;

        if(!errorFound){
            authResponse = ChargeCreditCard.run(15.00, ccInfo, user.getEmail());
        }

        if (authResponse != null) {
            if (authResponse.contains("Success")) {
                boolean success = UserDAO.upgrade(user.getUserId());
                if (success) {
                    session.removeAttribute("activeUser");
                    session.setAttribute("flashMessageSuccess", "Your Account has been given Premium Status, Please log in");
                    resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login"));
                    return;
                } else {
                    session.setAttribute("flashMessageDanger", "An error occurred. Please try again later.");
                }
            } else {
                session.setAttribute("flashMessageDanger", authResponse);
            }
        } else {
            session.setAttribute("flashMessageDanger", "Invalid Credit Card Info");
        }

        req.setAttribute("pageTitle", "Subscribe");
        req.getRequestDispatcher("WEB-INF/subscribe.jsp").forward(req, resp);
    }
}
