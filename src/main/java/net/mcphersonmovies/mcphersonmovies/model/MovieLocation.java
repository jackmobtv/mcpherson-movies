package net.mcphersonmovies.mcphersonmovies.model;

public class MovieLocation {
    private int location_id;
    private String location_name;
    private String location_description;

    public MovieLocation(int location_id, String location_name, String location_description) {
        this.location_id = location_id;
        this.location_name = location_name;
        this.location_description = location_description;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation_description() {
        return location_description;
    }

    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }

    @Override
    public String toString() {
        return "MovieLocation{" +
                "location_id=" + location_id +
                ", location_name='" + location_name + '\'' +
                ", location_description='" + location_description + '\'' +
                '}';
    }
}
