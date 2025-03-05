package net.mcphersonmovies.mcphersonmovies.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static net.mcphersonmovies.shared.MySQL_Connect.getConnection;

public class ActorDAO {
    public static void main(String[] args) {
        getAllActors().forEach(System.out::println);
    }

    public static List<Actor> getAllActors() {
        List<Actor> actors = new ArrayList<>();
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_all_actors(0,0)}");
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("actor_id");
                String name = rs.getString("actor_name");
                actors.add(new Actor(id, name));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }
        return actors;
    }
}
