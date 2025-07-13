package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.*;
import net.mcphersonmovies.mcphersonmovies.model.DAO.MovieDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/add-movies")
public class AddMovies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if(user == null || !user.getStatus().equals("active") || !user.getPrivileges().equals("Admin")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<MovieLocation> locations = MovieDAO.getAllLocations();
        req.setAttribute("locations", locations);
        List<MovieFormat> formats = MovieDAO.getAllFormats();
        req.setAttribute("formats", formats);

        req.setAttribute("pageTitle", "Add Movie");
        req.getRequestDispatcher("WEB-INF/movies/add-movies.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = MovieDAO.getLastID() + 1;
        String title = req.getParameter("title");
        String genre = req.getParameter("genre");
        String sub_genre = req.getParameter("sub_genre");
        String release_year = req.getParameter("release_year");
        String locationId = req.getParameter("locationId");
        String formatId = req.getParameter("formatId");
        String actorName = req.getParameter("actorName");

        String error = "";

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

        req.setAttribute("movie_id", id);
        req.setAttribute("title", title);
        req.setAttribute("genre", genre);
        req.setAttribute("sub_genre", sub_genre);
        req.setAttribute("release_year", release_year);
        req.setAttribute("locationId", locationId);
        req.setAttribute("formatId", formatId);
        req.setAttribute("actorName", actorName);

        List<MovieLocation> locations = MovieDAO.getAllLocations();
        req.setAttribute("locations", locations);
        List<MovieFormat> formats = MovieDAO.getAllFormats();
        req.setAttribute("formats", formats);

        if(!error.isEmpty()){
            req.setAttribute("error", error);
            req.setAttribute("pageTitle", "Add Movie");
            req.getRequestDispatcher("WEB-INF/movies/add-movies.jsp").forward(req, resp);
            return;
        }

        Movie movie = new Movie(id,title,genre,sub_genre,Integer.parseInt(release_year));
        String success = "";

        if(MovieDAO.addNewMovie(movie, Integer.parseInt(locationId), Integer.parseInt(formatId), actorName)){
            success = "MOVIE SUCCESSFULLY ADDED";
        } else {
            error += "INSERT FAILED";
        }
        req.setAttribute("error", error);
        req.setAttribute("success", success);

        req.setAttribute("pageTitle", "Add Movie");
        req.getRequestDispatcher("WEB-INF/movies/add-movies.jsp").forward(req, resp);
    }
}
