package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;
import net.mcphersonmovies.mcphersonmovies.model.Image;
import net.mcphersonmovies.mcphersonmovies.model.User;

import java.io.IOException;

@WebServlet(value = "/view-profile")
public class ViewProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        try{
            id = Integer.parseInt(req.getParameter("id"));
        } catch(NumberFormatException ignored) {}

        User user = UserDAO.get(id);
        req.setAttribute("user", user);

        Image image = UserDAO.getImage(user.getUserId());
        if(image != null){
            image.EncodeImage();
        }
        req.setAttribute("image", image);

        req.setAttribute("pageTitle", "Profile");
        req.getRequestDispatcher("WEB-INF/users/view-profile.jsp").forward(req, resp);
    }
}
