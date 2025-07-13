package net.mcphersonmovies.mcphersonmovies.controller.ratings;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.DAO.RatingDAO;
import net.mcphersonmovies.mcphersonmovies.model.User;

import java.io.IOException;

@WebServlet(value="/delete-rating")
public class DeleteRating extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");

        int user_id = Integer.parseInt(req.getParameter("user_id"));
        int movie_id = Integer.parseInt(req.getParameter("movie_id"));

        if(!user.getPrivileges().equals("Admin") && user.getUserId() != user_id){
            session.setAttribute("flashMessageDanger", "You are not allowed to perform this action");
        }

        boolean result = RatingDAO.deleteRating(user_id, movie_id);

        if(result){
            session.setAttribute("flashMessageSuccess", "Rating deleted successfully");
        } else {
            session.setAttribute("flashMessageDanger", "Rating failed to delete");
        }

        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/view-movies?id=" + movie_id));
    }
}
