package net.mcphersonmovies.mcphersonmovies.model;

public class MovieFormat {
    public String format_name;
    public int format_id;
    public String format_description;

    public MovieFormat(int format_id, String format_name, String format_description) {
        this.format_name = format_name;
        this.format_id = format_id;
        this.format_description = format_description;
    }

    public String getFormat_name() {
        return format_name;
    }

    public void setFormat_name(String format_name) {
        this.format_name = format_name;
    }

    public int getFormat_id() {
        return format_id;
    }

    public void setFormat_id(int format_id) {
        this.format_id = format_id;
    }

    public String getFormat_description() {
        return format_description;
    }

    public void setFormat_description(String format_description) {
        this.format_description = format_description;
    }

    @Override
    public String toString() {
        return "Format{" +
                "format_name='" + format_name + '\'' +
                ", format_id=" + format_id +
                ", format_description='" + format_description + '\'' +
                '}';
    }
}
