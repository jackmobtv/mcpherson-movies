package net.mcphersonmovies.mcphersonmovies.controller.ratings;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.DAO.RatingDAO;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;
import net.mcphersonmovies.mcphersonmovies.model.RatingVM;
import net.mcphersonmovies.mcphersonmovies.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/view-profile/ratings")
public class UserRatings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        try{
            id = Integer.parseInt(req.getParameter("id"));
        } catch(NumberFormatException ignored) {}
        User user = UserDAO.get(id);
        req.setAttribute("user", user);

        int limit = 5;
        int totalRatings = RatingDAO.getRatingCountByUser(user.getUserId());
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

        List<RatingVM> ratings = RatingDAO.getAllUserRatings(user.getUserId(), 5, offset);
        req.setAttribute("ratings", ratings);

        req.setAttribute("pageTitle", "Ratings");
        req.getRequestDispatcher("/WEB-INF/ratings/user-ratings.jsp").forward(req, resp);
    }
}
