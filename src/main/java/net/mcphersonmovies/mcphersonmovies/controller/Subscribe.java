package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.TransactionResponse;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.UserDAO;
import net.mcphersonmovies.shared.authorize_net.ChargeCreditCard;

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

        String expiration = req.getParameter("expiration");
        expiration = expiration.replace("/", "");

        String[] ccInfo = new String[4];
        ccInfo[0] = req.getParameter("number");
        ccInfo[1] = expiration;
        ccInfo[2] = req.getParameter("cvv");

        CreateTransactionResponse authResponse = (CreateTransactionResponse) ChargeCreditCard.run(15.00, ccInfo, user.getEmail());

        if (authResponse != null) {
            if (authResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {
                TransactionResponse result = authResponse.getTransactionResponse();
                if (result.getMessages() != null) {
                    boolean success = false;
                    success = UserDAO.upgrade(user.getUserId());
                    if (success) {
                        session.removeAttribute("activeUser");
                        session.setAttribute("flashMessageSuccess", "Your Account has been given Premium Status, Please log in");
                        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login"));
                        return;
                    } else {
                        session.setAttribute("flashMessageDanger", "Something Went Wrong");
                    }
                } else {
                    session.setAttribute("flashMessageDanger", "Something Went Wrong");
                }
            } else {
                session.setAttribute("flashMessageDanger", "Invalid Credit Card Info");
            }
        } else {
            session.setAttribute("flashMessageDanger", "Invalid Credit Card Info");
        }

        req.setAttribute("pageTitle", "Subscribe");
        req.getRequestDispatcher("WEB-INF/subscribe.jsp").forward(req, resp);
    }
}
