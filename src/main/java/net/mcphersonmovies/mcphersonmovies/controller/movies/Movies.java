package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.MovieDAO;
import net.mcphersonmovies.mcphersonmovies.model.MovieFormat;
import net.mcphersonmovies.mcphersonmovies.model.MovieLocation;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(value="/movies")
public class Movies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String limitStr = req.getParameter("limit");
        int limit = 0;
        try{
            limit = Integer.parseInt(limitStr);
        } catch(NumberFormatException ignored) {}

        String[] formatsArr = req.getParameterValues("formats");
        String formatFilter = "";
        if(formatsArr != null && formatsArr.length > 0) {
            formatFilter = String.join(",", formatsArr);
        }
        req.setAttribute("formatArr", formatsArr);

        String[] locationsArr = req.getParameterValues("locations");
        String locationFilter = "";
        if(locationsArr != null && locationsArr.length > 0) {
            locationFilter = String.join(",", locationsArr);
        }
        req.setAttribute("locationArr", locationsArr);

        String search = req.getParameter("search");
        if(search == null) {
            search = "";
        }

        String sort = req.getParameter("sort");
        if(sort == null) {
            sort = "default";
        }

        int totalMovies = MovieDAO.getMovieCount(formatFilter, locationFilter, search);
        int totalPages = 0;
        if (limit != 0) {
            totalPages = totalMovies / limit;

            if(totalMovies % limit != 0) {
                totalPages++;
            }
        }

        String pageStr = req.getParameter("page");
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException ignored) {}
        req.setAttribute("page", page);

        if (page < 1){
            page = 1;
        } else if (page > totalPages){
            page = totalPages;
        }

        int offset = (page - 1) * limit;

//        int pageLinks = 5;
//        int beginPage = page / pageLinks * pageLinks > 0 ? page / pageLinks * pageLinks : 1;
//        int endPage = beginPage + pageLinks - 1 > totalPages ? totalPages : beginPage + pageLinks - 1;

        int beginPage = 1;
        int endPage = totalPages;

        if (totalPages > 3) {
            if (page == 1 || page == 2) {
                endPage = totalPages == 4 ? 4 : 5;
            } else if (page == totalPages || page == totalPages - 1) {
                beginPage = ((page == 3 || page == 4) ? 1 : (totalPages - 4));
            } else {
                beginPage = page - 2;
                endPage = page + 2;
            }
        }

        beginPage = Math.max(1, beginPage);
        endPage = Math.min(totalPages, endPage);
        req.setAttribute("beginPage", beginPage);
        req.setAttribute("endPage", endPage);

        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalMovies", totalMovies);

//        req.setAttribute("formatFilter", formatFilter);
//        req.setAttribute("locationFilter", locationFilter);
        req.setAttribute("limit", limit);
        req.setAttribute("search", search);
        req.setAttribute("sort", sort);

        List<Movie> movies = MovieDAO.getAllMoviesFiltered(offset, limit, formatFilter, locationFilter, search, sort);
        List<MovieFormat> formats = MovieDAO.getAllFormats();
        List<MovieLocation> locations = MovieDAO.getAllLocations();


//        if(sort == null) {
//            sort = "default";
//        } else if (sort.equals("title")) {
//            movies.sort(Comparator.comparing(Movie::getTitle));
//        } else if (sort.equals("genre")) {
//            movies.sort(Comparator.comparing(Movie::getGenre).thenComparing(Movie::getSub_genre));
//        } else if (sort.equals("year")) {
//            movies.sort(Comparator.comparingInt(Movie::getRelease_year));
//        } else if (sort.equals("location")) {
//            movies.sort(Comparator.comparing(Movie::getLocation_name));
//        } else if (sort.equals("format")) {
//            movies.sort(Comparator.comparing(Movie::getFormat_name));
//        } else {
//            sort = "default";
//        }


        req.setAttribute("movies", movies);
        req.setAttribute("formats", formats);
        req.setAttribute("locations", locations);
        req.setAttribute("pageTitle", "Movies");
        req.getRequestDispatcher("WEB-INF/movies/movies.jsp").forward(req, resp);
    }
}
