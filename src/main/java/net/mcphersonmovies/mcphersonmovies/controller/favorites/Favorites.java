package net.mcphersonmovies.mcphersonmovies.controller.favorites;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.DAO.FavoriteDAO;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/favorites")
public class Favorites extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if (user == null) {
            session.setAttribute("flashMessageWarning", "You are not logged in");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login?redirect=favorites"));
            return;
        } else if (!user.getStatus().equals("active")){
            session.setAttribute("flashMessageDanger", "Your Account is locked or inactive, Please reset your password");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
            return;
        }

        List<Movie> favorites = FavoriteDAO.getFavoriteMovies(user.getUserId());
        req.setAttribute("favorites", favorites);

        req.setAttribute("pageTitle", "Favorites");
        req.getRequestDispatcher("WEB-INF/users/favorites.jsp").forward(req, resp);
    }
}
