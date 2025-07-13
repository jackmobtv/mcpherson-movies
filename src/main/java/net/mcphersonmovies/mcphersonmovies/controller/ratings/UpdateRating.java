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

@WebServlet(value = "/update-rating")
public class UpdateRating extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int id = Integer.parseInt(req.getParameter("movie_id"));
        User user = (User)session.getAttribute("activeUser");

        if(user == null) {
            session.setAttribute("flashMessageDanger", "Please Log In");

            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login"));
        }

        int rating = -1;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch(NumberFormatException ignored) {}

        String comment = req.getParameter("comment") == null ? "" : req.getParameter("comment");

        String ratingErr = "";
        if(rating < 0 || rating > 10) {
            ratingErr = "Invalid Rating";
        }

        String commentErr = "";
        if(comment.length() > 1000) {
            commentErr = "Comment is too long";
        }

        if(!commentErr.isEmpty() || !ratingErr.isEmpty()) {
            req.setAttribute("commentErr", commentErr);
            req.setAttribute("ratingErr", ratingErr);
            req.setAttribute("rating", rating);
            req.setAttribute("comment", comment);

            req.getRequestDispatcher("/view-movies?id=" + id).forward(req, resp);
            return;
        }

        boolean result = RatingDAO.updateRating(user.getUserId(), id, rating, comment);

        if(result) {
            session.setAttribute("flashMessageSuccess", "Rating successfully updated");
        } else {
            session.setAttribute("flashMessageDanger", "Failed to update Rating");
        }

        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/view-movies?id=" + id));
    }
}
