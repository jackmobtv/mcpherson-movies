package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.*;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/update-movies")
public class UpdateMovies extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if(user == null || !user.getStatus().equals("active") || !user.getPrivileges().equals("Admin")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int movie_id = 0;
        try{
            movie_id = Integer.parseInt(req.getParameter("id"));
        } catch(NumberFormatException ignored) {}

        Movie movie = MovieDAO.getMovieTable(movie_id);
        List<Actor> actors = ActorDAO.getActorsByMovieId(movie_id);
        req.setAttribute("movie", movie);
        req.setAttribute("actors", actors);

        List<MovieLocation> locations = MovieDAO.getAllLocations();
        req.setAttribute("locations", locations);
        List<MovieFormat> formats = MovieDAO.getAllFormats();
        req.setAttribute("formats", formats);

        req.setAttribute("pageTitle", "Update Movie");
        req.getRequestDispatcher("WEB-INF/movies/update-movies.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String movie_id = req.getParameter("movie_id");
        String title = req.getParameter("title");
        String genre = req.getParameter("genre");
        String sub_genre = req.getParameter("sub_genre");
        String release_year = req.getParameter("release_year");
        String locationId = req.getParameter("locationId");
        String formatId = req.getParameter("formatId");

        String error = "";

        if (movie_id.isEmpty()) {
            error += "Movie ID is empty<br>";
        } else {
            try {
                int id = Integer.parseInt(movie_id);
                List<Movie> movies = MovieDAO.getAllMovies();
                List<Actor> actors = ActorDAO.getActorsByMovieId(Integer.parseInt(movie_id));
                req.setAttribute("actors", actors);
                boolean found = false;
                for(Movie movie : movies) {
                    if(movie.getMovie_id() == id){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    error += "Movie ID Does Not Exist<br>";
                }
            } catch (Exception ex){
                error += "Invalid Movie ID<br>";
            }
        }



        if(title.isEmpty()){
            error += "Title is empty<br>";
        }

        if(!release_year.isEmpty()){
            try {
                if(Integer.parseInt(release_year) <= 1900 || Integer.parseInt(release_year) >= 2100){
                    error += "Invalid Year, Year Must Be Between 1900 and 2100<br>";
                }
            } catch (Exception ex){
                error += "Invalid Year<br>";
            }
        } else {
            release_year = "0";
        }

        if(locationId.isEmpty()){
            error += "Location ID is empty<br>";
        } else {
            try {
                Integer.parseInt(locationId);
            } catch (Exception ex){
                error += "Invalid Location ID<br>";
            }
        }

        if(formatId.isEmpty()){
            error += "Format ID is empty<br>";
        } else {
            try {
                Integer.parseInt(formatId);
            } catch (Exception ex){
                error += "Invalid Format ID<br>";
            }
        }

        req.setAttribute("movie_id", movie_id);
        req.setAttribute("title", title);
        req.setAttribute("genre", genre);
        req.setAttribute("sub_genre", sub_genre);
        req.setAttribute("release_year", release_year);
        req.setAttribute("locationId", locationId);
        req.setAttribute("formatId", formatId);

        List<MovieLocation> locations = MovieDAO.getAllLocations();
        req.setAttribute("locations", locations);
        List<MovieFormat> formats = MovieDAO.getAllFormats();
        req.setAttribute("formats", formats);

        if(!error.isEmpty()){
            req.setAttribute("error", error);
            Movie oldMovie = MovieDAO.getMovieTable(Integer.parseInt(movie_id));
            req.setAttribute("movie", oldMovie);
            req.setAttribute("pageTitle", "Update Movie");
            req.getRequestDispatcher("WEB-INF/movies/update-movies.jsp").forward(req, resp);
        }

        Movie movie = null;
        try {
            movie = new Movie(Integer.parseInt(movie_id),title,genre,sub_genre,Integer.parseInt(release_year), Integer.parseInt(locationId), Integer.parseInt(formatId));
        } catch (Exception ex){
            error += "Invalid Data";
        }
        String success = "";

        if(MovieDAO.updateMovie(movie)){
            success = "MOVIE SUCCESSFULLY UPDATED";
        } else {
            error += "UPDATE FAILED";
        }
        req.setAttribute("error", error);
        req.setAttribute("success", success);

        Movie newMovie = MovieDAO.getMovieTable(Integer.parseInt(movie_id));
        req.setAttribute("movie", newMovie);

        req.setAttribute("pageTitle", "Update Movie");
        req.getRequestDispatcher("WEB-INF/movies/update-movies.jsp").forward(req, resp);
    }
}
