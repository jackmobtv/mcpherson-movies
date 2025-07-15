package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.*;
import net.mcphersonmovies.mcphersonmovies.model.DAO.*;
import net.mcphersonmovies.shared.Helpers;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/view-movies")
public class ViewMovies extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movie_id = 0;
        if(req.getParameter("movie_id") != null) {
            movie_id = Integer.parseInt(req.getParameter("movie_id"));
        } else {
            try{
                movie_id = Integer.parseInt(req.getParameter("id"));
            } catch(NumberFormatException ignored) {}
        }

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

            int limit = 5;
            int totalRatings = RatingDAO.getRatingCountByMovie(movie_id);
            int totalPages = 0;
            totalPages = totalRatings / limit;

            if(totalRatings % limit != 0) {
                totalPages++;
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

//            int pageLinks = 5;
//            int beginPage = page / pageLinks * pageLinks > 0 ? page / pageLinks * pageLinks : 1;
//            int endPage = beginPage + pageLinks - 1 > totalPages ? totalPages : beginPage + pageLinks - 1;

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

            List<RatingVM> ratings = RatingDAO.getAllMovieRatings(movie_id, 5, offset);

            String avgRate = "_";
            try{
                avgRate = RatingDAO.getAverageMovieRating(movie_id);
            } catch(NumberFormatException ignored) {}
            req.setAttribute("averageRating", avgRate);
            req.setAttribute("ratings", ratings);

            req.setAttribute("lastMovie", movie_id == MovieDAO.getLastID() ? "true" : "false");

            if(user != null) {
                req.setAttribute("favorited", FavoriteDAO.isFavoriteMovie(new Favorite(user.getUserId(), movie_id)));
                Rating rating = RatingDAO.getMovieRating(user.getUserId(), movie_id);
                req.setAttribute("myRating", rating);
                req.setAttribute("fullName", user.getFullName());

                Image image = UserDAO.getImage(user.getUserId());
                if(image != null){
                    image.EncodeImage();
                }
                req.setAttribute("image", image);
            }
        }

        req.setAttribute("pageTitle", "View Movie");
        req.setAttribute("mobile", Helpers.isMobile(req));

//        if (Helpers.isMobile(req)) {
//            req.getRequestDispatcher("WEB-INF/movies/view-movies-mobile.jsp").forward(req, resp);
//        } else {
//            req.getRequestDispatcher("WEB-INF/movies/view-movies.jsp").forward(req, resp);
//        }
        req.getRequestDispatcher("WEB-INF/movies/view-movies.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");
        int movie_id = Integer.parseInt(req.getParameter("movie_id"));

        if(user == null) {
            session.setAttribute("flashMessageDanger", "Please Log In");

            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login"));
        }

        int rating = -2;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch(NumberFormatException ignored) {}

        String comment = req.getParameter("comment") == null ? "" : req.getParameter("comment");

        String commentErr = "";
        if(comment.length() > 1000) {
            commentErr = "Comment is too long";
        }

        String ratingErr = "";
        if(rating < 0 || rating > 10) {
            ratingErr = "Invalid Rating";
        }

        if(!commentErr.isEmpty() || !ratingErr.isEmpty()) {
            req.setAttribute("rating", rating);
            req.setAttribute("comment", comment);
            req.setAttribute("commentErr", commentErr);
            req.setAttribute("ratingErr", ratingErr);
            doGet(req, resp);
            return;
        }

        boolean result = RatingDAO.addRating(user.getUserId(), movie_id, rating, comment);

        if(result) {
            session.setAttribute("flashMessageSuccess", "Rating successfully added");
        } else {
            session.setAttribute("flashMessageDanger", "Failed to add Rating");
        }

        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/view-movies?id=" + movie_id));
    }
}
