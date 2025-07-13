package net.mcphersonmovies.mcphersonmovies.controller.favorites;

import jakarta.json.JsonObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import net.mcphersonmovies.mcphersonmovies.model.Favorite;
import net.mcphersonmovies.mcphersonmovies.model.DAO.FavoriteDAO;
import net.mcphersonmovies.shared.MyDecoder;
import net.mcphersonmovies.shared.MyEncoder;
import net.mcphersonmovies.shared.MyJson;

import java.io.IOException;

@ServerEndpoint(
        value="/view-movies/unfavorite",
        encoders = {MyEncoder.class},
        decoders = {MyDecoder.class}
)
public class UnfavoriteEndpoint {
    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("Connected to server");
    }
    @OnMessage
    public void onMessage(MyJson favorite, Session session) throws IOException {
        JsonObject jsonObject = favorite.getJson();
        int movie_id = 0;
        int user_id = 0;
        try{
            movie_id = Integer.parseInt(jsonObject.getString("movie"));
            user_id = Integer.parseInt(jsonObject.getString("user"));
        } catch (Exception ignored) {}
        FavoriteDAO.unfavoriteMovie(new Favorite(user_id, movie_id));
        session.getBasicRemote().sendText("Removed from Favorites");
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
