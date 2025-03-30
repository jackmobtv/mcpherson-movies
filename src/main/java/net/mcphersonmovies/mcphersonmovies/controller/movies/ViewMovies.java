package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.*;
import net.mcphersonmovies.shared.Helpers;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/view-movies")
public class ViewMovies extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movie_id = Integer.parseInt(req.getParameter("id"));
        Movie movie = MovieDAO.getMovie(movie_id);

        if(movie != null) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("activeUser");
            String[] movieData = OMDB.getMovieData(movie.getTitle());
            List<Actor> actors = ActorDAO.getActorsByMovieId(movie_id);
            req.setAttribute("plot", movieData[1]);
            req.setAttribute("posterURL", movieData[0]);
            req.setAttribute("movie", movie);
            req.setAttribute("actors", actors);
            req.setAttribute("lastMovie", movie_id == MovieDAO.getLastID() ? "true" : "false");
            if(user != null) {
                req.setAttribute("favorited", FavoriteDAO.isFavoriteMovie(new Favorite(user.getUserId(), movie_id)));
            }
        }

        req.setAttribute("pageTitle", "View Movie");

        if (Helpers.isMobile(req)) {
            req.getRequestDispatcher("WEB-INF/movies/view-movies-mobile.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("WEB-INF/movies/view-movies.jsp").forward(req, resp);
        }
//        req.getRequestDispatcher("WEB-INF/movies/view-movies.jsp").forward(req, resp);
    }
}
