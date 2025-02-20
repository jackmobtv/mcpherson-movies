package net.mcphersonmovies.mcphersonmovies.model;

public class Movie {
    private int movie_id;
    private String title;
    private String genre;
    private String sub_genre;
    private int release_year;
    private String location_name;
    private String format_name;
    private int location_id;
    private int format_id;

    public Movie(){}

    public Movie(int movie_id, String title, String genre, String sub_genre, int release_year, String location_name, String format_name) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.sub_genre = sub_genre;
        this.release_year = release_year;
        this.location_name = location_name;
        this.format_name = format_name;
    }

    public Movie(int movie_id, String title, String genre, String sub_genre, int release_year, int location_id, int format_id) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.sub_genre = sub_genre;
        this.release_year = release_year;
        this.location_id = location_id;
        this.format_id = format_id;
    }

    public Movie(int movie_id, String title, String genre, String sub_genre, int release_year, int location_id) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.sub_genre = sub_genre;
        this.release_year = release_year;
        this.location_id = location_id;
    }

    public Movie(int movie_id, String title, String genre, String sub_genre, int release_year) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.sub_genre = sub_genre;
        this.release_year = release_year;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSub_genre() {
        return sub_genre;
    }

    public void setSub_genre(String sub_genre) {
        this.sub_genre = sub_genre;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getFormat_name() {
        return format_name;
    }

    public void setFormat_name(String format_name) {
        this.format_name = format_name;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movie_id=" + movie_id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", sub_genre='" + sub_genre + '\'' +
                ", release_year=" + release_year +
                ", location_name='" + location_name + '\'' +
                ", format_name='" + format_name + '\'' +
                '}';
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getFormat_id() {
        return format_id;
    }

    public void setFormat_id(int format_id) {
        this.format_id = format_id;
    }
}