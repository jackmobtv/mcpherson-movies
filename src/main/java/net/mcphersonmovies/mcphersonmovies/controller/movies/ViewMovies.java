package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.MovieDAO;
import net.mcphersonmovies.mcphersonmovies.model.OMDB;

import java.io.IOException;

@WebServlet(value="/view-movies")
public class ViewMovies extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movie_id = Integer.parseInt(req.getParameter("id"));
        Movie movie = MovieDAO.getMovie(movie_id);
        String[] movieData = OMDB.getMovieData(movie.getTitle());
        req.setAttribute("plot", movieData[1]);
        req.setAttribute("posterURL", movieData[0]);
        req.setAttribute("movie", movie);
        req.setAttribute("pageTitle", "View Movie");
        req.getRequestDispatcher("WEB-INF/movies/view-movies.jsp").forward(req, resp);
    }
}
