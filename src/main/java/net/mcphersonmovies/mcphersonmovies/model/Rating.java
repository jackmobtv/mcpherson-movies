package net.mcphersonmovies.mcphersonmovies.model;

import java.time.Instant;
import java.util.Date;

public class Rating {
    private int movie_id;
    private int user_id;
    private int rating;
    private String comment;
    private Instant created_at;

    public Rating() {}

    public Rating(int movie_id, int user_id, int rating, String comment, Instant created_at) {
        this.movie_id = movie_id;
        this.user_id = user_id;
        this.rating = rating;
        this.comment = comment;
        this.created_at = created_at;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Date getCreatedAtDate() {
        return Date.from(created_at);
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "movie_id=" + movie_id +
                ", user_id=" + user_id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}