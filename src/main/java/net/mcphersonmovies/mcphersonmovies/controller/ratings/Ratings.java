package net.mcphersonmovies.mcphersonmovies.controller.ratings;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.DAO.RatingDAO;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;
import net.mcphersonmovies.mcphersonmovies.model.Image;
import net.mcphersonmovies.mcphersonmovies.model.RatingVM;
import net.mcphersonmovies.mcphersonmovies.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/ratings")
public class Ratings extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");

        if (user == null) {
            session.setAttribute("flashMessageWarning", "You are not logged in");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login?redirect=favorites"));
            return;
        } else if (!user.getStatus().equals("active")){
            session.setAttribute("flashMessageDanger", "Your Account is locked or inactive, Please reset your password");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
            return;
        }

        Image image = UserDAO.getImage(user.getUserId());
        if(image != null){
            image.EncodeImage();
        }
        req.setAttribute("image", image);

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
        req.getRequestDispatcher("WEB-INF/ratings/ratings.jsp").forward(req, resp);
    }
}
