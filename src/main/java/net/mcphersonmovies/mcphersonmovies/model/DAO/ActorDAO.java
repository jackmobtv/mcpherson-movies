package net.mcphersonmovies.mcphersonmovies.model.DAO;

import net.mcphersonmovies.mcphersonmovies.model.Actor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static net.mcphersonmovies.shared.MySQL_Connect.getConnection;

public class ActorDAO {
    public static void main(String[] args) {
//        getAllActors().forEach(System.out::println);
//        getActorsByMovieId(1).forEach(System.out::println);
        addActor(1,"Joe");
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

    public static List<Actor> getActorsByMovieId(int movieId) {
        List<Actor> actors = new ArrayList<>();
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_actors_by_movie_id(?,0,0)}");
            statement.setInt(1, movieId);
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

    public static Actor getActorByActorId(int actor_id) {
        Actor actor = null;
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_actor_by_id(?)}");
            statement.setInt(1, actor_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("actor_id");
                String name = rs.getString("actor_name");
                actor = new Actor(id, name);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }
        return actor;
    }

    public static boolean updateActor(Actor actor) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_update_actor(?,?)}");
            statement.setInt(1, actor.getActor_id());
            statement.setString(2, actor.getActor_name());
            return statement.executeUpdate() > 0;
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean deleteActor(int actor_id) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_delete_actor(?)}");
            statement.setInt(1, actor_id);
            return statement.executeUpdate() > 0;
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean deleteMovieActor(int movie_id, int actor_id) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_delete_movie_actor(?,?)}");
            statement.setInt(1, movie_id);
            statement.setInt(2, actor_id);
            return statement.executeUpdate() > 0;
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean addActor(int movie_id, String actor_name) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_insert_actor(?,?)}");
            statement.setInt(1, movie_id);
            statement.setString(2, actor_name);
            statement.executeUpdate();
            return true;
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
