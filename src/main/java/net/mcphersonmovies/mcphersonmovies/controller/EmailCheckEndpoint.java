package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import net.mcphersonmovies.mcphersonmovies.model.UserDAO;
import java.io.IOException;

@ServerEndpoint("/signup/checkEmail")
public class EmailCheckEndpoint {
    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("Connected to server");
    }
    @OnMessage
    public void onMessage(String email, Session session) {
        boolean emailExists = UserDAO.get(email) != null;
        try {
            session.getBasicRemote().sendText(String.valueOf(emailExists));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}
