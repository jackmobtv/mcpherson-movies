package net.mcphersonmovies.mcphersonmovies.model;

public class Favorite {
    public int user_id;
    public int movie_id;

    public Favorite(int user_id, int movie_id) {
        this.user_id = user_id;
        this.movie_id = movie_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "user_id=" + user_id +
                ", movie_id=" + movie_id +
                '}';
    }
}
