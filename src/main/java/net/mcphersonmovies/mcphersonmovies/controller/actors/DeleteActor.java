package net.mcphersonmovies.mcphersonmovies.controller.actors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.DAO.ActorDAO;

import java.io.IOException;

@WebServlet(value="/delete-actors")
public class DeleteActor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int actor_id = 0;
        try{
            actor_id = Integer.parseInt(req.getParameter("id"));
        } catch(NumberFormatException ignored) {}

        boolean success = false;
        success = ActorDAO.deleteActor(actor_id);

        HttpSession session = req.getSession();
        if(success) {
            session.setAttribute("flashMessageSuccess", "Actor has been deleted");
        } else {
            session.setAttribute("flashMessageDanger", "Actor could not be deleted");
        }

        resp.sendRedirect(req.getContextPath() + "/actors");
    }
}
