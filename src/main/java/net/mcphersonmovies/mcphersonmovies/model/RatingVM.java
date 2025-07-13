package net.mcphersonmovies.mcphersonmovies.model;

import java.time.Instant;

public class RatingVM extends Rating {
    private User user;

    public RatingVM() {}

    public RatingVM(int movie_id, int user_id, int rating, String comment, Instant created_at, User user) {
        super(movie_id, user_id, rating, comment, created_at);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
