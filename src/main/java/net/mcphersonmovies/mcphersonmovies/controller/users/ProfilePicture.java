package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;
import net.mcphersonmovies.mcphersonmovies.model.Image;
import net.mcphersonmovies.mcphersonmovies.model.User;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

import static net.mcphersonmovies.mcphersonmovies.model.Image.getFileName;

@WebServlet(value = "/profile-picture")
@MultipartConfig(maxFileSize = 16177215)
public class ProfilePicture extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if (user == null) {
            session.setAttribute("flashMessageWarning", "You are not logged in");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login?redirect=profile-picture"));
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

        req.setAttribute("pageTitle", "Edit Profile Picture");
        req.getRequestDispatcher("/WEB-INF/users/profile-picture.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if (user == null) {
            session.setAttribute("flashMessageWarning", "You are not logged in");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login?redirect=profile-picture"));
            return;
        } else if (!user.getStatus().equals("active")){
            session.setAttribute("flashMessageDanger", "Your Account is locked or inactive, Please reset your password");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
            return;
        }

        Part filePart = req.getPart("imageFile");
        String fileName = getFileName(filePart);
        boolean result = UserDAO.saveImage(user.getUserId(), filePart, fileName);

        if(result){
            session.setAttribute("flashMessageSuccess", "Profile Picture Saved");
        } else {
            session.setAttribute("flashMessageDanger", "Failed to Save Profile Picture");
        }

        resp.sendRedirect(req.getContextPath() + "/edit-profile");
    }
}
