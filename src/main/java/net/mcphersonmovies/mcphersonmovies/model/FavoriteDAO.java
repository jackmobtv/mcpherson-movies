package net.mcphersonmovies.mcphersonmovies.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static net.mcphersonmovies.shared.MySQL_Connect.getConnection;

public class FavoriteDAO {
    public static void main(String[] args) {
//        System.out.println(isFavoriteMovie(1,2));
        getFavoriteMovies(1).forEach(System.out::println);
//        System.out.println(favoriteMovie(1,3));
//        System.out.println(unfavoriteMovie(1,3));
    }
    public static boolean favoriteMovie(int userId, int movieId) {
        boolean result = false;

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_favorite_movie(?,?)}");
            statement.setInt(1, userId);
            statement.setInt(2, movieId);
            result = statement.executeUpdate() >= 1;
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return result;
    }

    public static boolean unfavoriteMovie(int userId, int movieId) {
        boolean result = false;

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_unfavorite_movie(?,?)}");
            statement.setInt(1, userId);
            statement.setInt(2, movieId);
            result = statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return result;
    }

    public static boolean isFavoriteMovie(int userId, int movieId) {
        boolean favorite = false;

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_is_movie_favorited(?,?,?)}");
            statement.setInt(1, userId);
            statement.setInt(2, movieId);
            statement.registerOutParameter(3, Types.BOOLEAN);
            statement.execute();
            favorite = statement.getBoolean(3);
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return favorite;
    }

    public static List<Movie> getFavoriteMovies(int userId) {
        List<Movie> movies = new ArrayList<>();

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_favorite_movies(?)}");
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("movie_id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String sub_genre = rs.getString("sub_genre");
                int release_year = rs.getInt("release_year");
                String location_name = rs.getString("location_name");
                String format_name = rs.getString("format_name");
                if(genre == null){
                    genre = "";
                }
                if(sub_genre == null){
                    sub_genre = "";
                }

                movies.add(new Movie(id, title, genre, sub_genre, release_year, location_name, format_name));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return movies;
    }
}
