package net.mcphersonmovies.mcphersonmovies.controller.favorites;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.DAO.FavoriteDAO;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/view-profile/favorites")
public class UserFavorites extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        try{
            id = Integer.parseInt(req.getParameter("id"));
        } catch(NumberFormatException ignored) {}

        User user = UserDAO.get(id);
        req.setAttribute("user", user);

        List<Movie> favorites = FavoriteDAO.getFavoriteMovies(user.getUserId());
        req.setAttribute("favorites", favorites);

        req.setAttribute("pageTitle", "Favorites");
        req.getRequestDispatcher("/WEB-INF/favorites/user-favorites.jsp").forward(req, resp);
    }
}
