package net.mcphersonmovies.mcphersonmovies.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static net.mcphersonmovies.shared.MySQL_Connect.getConnection;

public class MovieDAO {
    public static void main(String[] args) {
//        getAllMovies().forEach(System.out::println);
//        Movie movie = new Movie(10000, "Test", "Action", "Comedy", 2000);
//        System.out.println(addNewMovie(movie, 1, 1, "Leslie Nielsen"));
        System.out.println(getRandomMovie());
    }
    public static List<Movie> getAllMovies(){
        List<Movie> movies = new ArrayList<Movie>();

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_all_movies(0,0)}");
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

    public static boolean addNewMovie(Movie movie, int locationId, int formatId, String actorName){
        try(Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            CallableStatement statement = connection.prepareCall("{CALL sp_insert_movie(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, movie.getMovie_id());
            statement.setString(2, movie.getTitle());
            if(movie.getGenre() == null || movie.getGenre().isEmpty()){
                statement.setNull(3, Types.VARCHAR);
            } else {
                statement.setString(3, movie.getGenre());
            }
            if(movie.getSub_genre() == null || movie.getSub_genre().isEmpty()){
                statement.setNull(4, Types.VARCHAR);
            } else {
                statement.setString(4, movie.getSub_genre());
            }

            Integer year = movie.getRelease_year();

            if(movie.getRelease_year() == 0 || year == null){
                statement.setNull(5, Types.INTEGER);
            } else {
                statement.setInt(5, movie.getRelease_year());
            }
            statement.setInt(6, locationId);
            statement.setInt(7, formatId);
            statement.setString(8, actorName);
            statement.setBoolean(9, !actorName.isEmpty());

            int rowsAffected = statement.executeUpdate();
            return true;
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean updateMovie(Movie movie) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_update_movie(?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, movie.getMovie_id());
            statement.setString(2, movie.getTitle());
            statement.setString(3, movie.getGenre());
            statement.setString(4, movie.getSub_genre());
            if(movie.getRelease_year() == 0){
                statement.setNull(5, Types.INTEGER);
            } else {
                statement.setInt(5, movie.getRelease_year());
            }
            statement.setInt(6, movie.getLocation_id());
            statement.executeUpdate();
            return true;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Movie getMovie(int id){
        Movie movie = null;

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_movie_table_by_id(?)}");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                int movieId = rs.getInt("movie_id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String sub_genre = rs.getString("sub_genre");
                int release_year = rs.getInt("release_year");
                int location_name = rs.getInt("location_id");
                int format_name = rs.getInt("format_id");
                if(genre == null){
                    genre = "";
                }
                if(sub_genre == null){
                    sub_genre = "";
                }

                movie = new Movie(movieId, title, genre, sub_genre, release_year, location_name, format_name);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return movie;
    }

    public static Movie getRandomMovie(){
        Movie movie = null;

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_random_movie()}");
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                int movieId = rs.getInt("movie_id");
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

                movie = new Movie(movieId, title, genre, sub_genre, release_year, location_name, format_name);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return movie;
    }
}
