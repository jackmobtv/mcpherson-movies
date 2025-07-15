package net.mcphersonmovies.mcphersonmovies.model.DAO;

import net.mcphersonmovies.mcphersonmovies.model.Image;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.Rating;
import net.mcphersonmovies.mcphersonmovies.model.RatingVM;
import net.mcphersonmovies.shared.Helpers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static net.mcphersonmovies.shared.MySQL_Connect.getConnection;

public class RatingDAO {
    public static void main(String[] args) {
        System.out.println(getMovieRating(1,1));
    }

    public static Rating getMovieRating(int user_id, int movie_id) {
        Rating rating = new Rating();

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_movie_rating(?,?)}");
            statement.setInt(1, user_id);
            statement.setInt(2, movie_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                rating.setMovie_id(movie_id);
                rating.setUser_id(user_id);
                rating.setRating(rs.getInt("rating"));
                rating.setComment(rs.getString("comment"));
                rating.setCreated_at(rs.getTimestamp("created_at").toInstant());
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return rating;
    }

    public static boolean addRating(int user_id, int movie_id, int rating, String comment) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_add_movie_rating(?,?,?,?)}");
            statement.setInt(1, user_id);
            statement.setInt(2, movie_id);
            statement.setInt(3, rating);
            statement.setString(4, comment);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    public static boolean updateRating(int user_id, int movie_id, int rating, String comment) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_update_movie_rating(?,?,?,?)}");
            statement.setInt(1, user_id);
            statement.setInt(2, movie_id);
            statement.setInt(3, rating);
            statement.setString(4, comment);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    public static List<RatingVM> getAllMovieRatings(int movie_id, int limit, int offset) {
        List<RatingVM> ratings = new ArrayList<>();

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_all_movie_ratings(?,?,?)}");
            statement.setInt(1, movie_id);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                RatingVM rating = new RatingVM();

                rating.setMovie_id(movie_id);
                rating.setUser_id(rs.getInt("user_id"));
                rating.setRating(rs.getInt("rating"));
                rating.setComment(rs.getString("comment"));
                rating.setCreated_at(rs.getTimestamp("created_at").toInstant());
                rating.setUser(UserDAO.get(rs.getInt("user_id")));

                Image image = UserDAO.getImage(rating.getUser_id());
                if(image != null){
                    image.EncodeImage();
                }
                rating.setImage(image);

                ratings.add(rating);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return ratings;
    }

    public static List<RatingVM> getAllUserRatings(int user_id, int limit, int offset) {
        List<RatingVM> ratings = new ArrayList<>();

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_all_user_ratings(?,?,?)}");
            statement.setInt(1, user_id);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                RatingVM rating = new RatingVM();

                rating.setMovie_id(rs.getInt("movie_id"));
                rating.setUser_id(user_id);
                rating.setRating(rs.getInt("rating"));
                rating.setComment(rs.getString("comment"));
                rating.setCreated_at(rs.getTimestamp("created_at").toInstant());
                rating.setMovie(MovieDAO.getMovie(rs.getInt("movie_id")));

                ratings.add(rating);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        return ratings;
    }

    public static boolean deleteRating(int user_id, int movie_id) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_delete_movie_rating(?,?)}");
            statement.setInt(1, user_id);
            statement.setInt(2, movie_id);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    public static int getRatingCountByMovie(int movie_id) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_total_movie_ratings(?)}");
            statement.setInt(1, movie_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("count");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }
    }

    public static int getRatingCountByUser(int user_id) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_total_user_ratings(?)}");
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("count");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }
    }

    public static String getAverageMovieRating(int movie_id) {
        List<Double> ratings = new ArrayList<>();

        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_get_average_rating_for_movie(?)}");
            statement.setInt(1, movie_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int rating = 0;

                rating = rs.getInt("rating");

                ratings.add((double)rating);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error  -  " + ex.getMessage());
        }

        double combinedRating = 0;

        for(Double rating : ratings) {
            combinedRating += rating;
        }

        return Helpers.round(combinedRating / ratings.size(),2);
    }
}
