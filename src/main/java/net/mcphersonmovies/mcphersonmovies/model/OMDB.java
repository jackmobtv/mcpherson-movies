package net.mcphersonmovies.mcphersonmovies.model;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import net.mcphersonmovies.shared.Config;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class OMDB {
    public static String getPoster(String title) throws IOException {
        //https://www.omdbapi.com/?t=__&apikey=__

        String key = Config.getEnv("OMDB_API_KEY");

        String url = "https://www.omdbapi.com/?t=" + title + "&apikey=" + key;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

//        System.out.println(responseBody);
        JSONObject jsonObject = new JSONObject(responseBody);
        String poster = null;
        try{
            poster = jsonObject.getString("Poster");
        } catch (JSONException ex) {
            poster = "https://media.istockphoto.com/id/1472933890/vector/no-image-vector-symbol-missing-available-icon-no-gallery-for-this-moment-placeholder.jpg?s=612x612&w=0&k=20&c=Rdn-lecwAj8ciQEccm0Ep2RX50FCuUJOaEM8qQjiLL0=";
        }

        if (poster.equals("N/A")){
            poster = "https://media.istockphoto.com/id/1472933890/vector/no-image-vector-symbol-missing-available-icon-no-gallery-for-this-moment-placeholder.jpg?s=612x612&w=0&k=20&c=Rdn-lecwAj8ciQEccm0Ep2RX50FCuUJOaEM8qQjiLL0=";
        }

        return poster;
    }

    public static String getPlot(String title) throws IOException {
        //https://www.omdbapi.com/?t=__&apikey=__
        Dotenv dotenv = null;
        try {
            dotenv = Dotenv.load();
        } catch(DotenvException e) {
            throw new RuntimeException("Could not find .env file");
        }
        String key = dotenv.get("OMDB_API_KEY");

        String url = "https://www.omdbapi.com/?t=" + title + "&apikey=" + key;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

//        System.out.println(responseBody);
        JSONObject jsonObject = new JSONObject(responseBody);
        String plot = null;
        try{
            plot = jsonObject.getString("Plot");
        } catch (JSONException ex) {
            plot = "";
        }
        return plot;
    }

    public static String[] getMovieData(String title) throws IOException {
        //https://www.omdbapi.com/?t=__&apikey=__
        Dotenv dotenv = null;
        try {
            dotenv = Dotenv.load();
        } catch(DotenvException e) {
            throw new RuntimeException("Could not find .env file");
        }
        String key = dotenv.get("OMDB_API_KEY");

        String url = "https://www.omdbapi.com/?t=" + title + "&apikey=" + key;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

//        System.out.println(responseBody);
        JSONObject jsonObject = new JSONObject(responseBody);
        String[] data = new String[2];
        try{
            data[0] = jsonObject.getString("Poster");
            data[1] = jsonObject.getString("Plot");
        } catch (JSONException ex) {
            data[0] = "https://media.istockphoto.com/id/1472933890/vector/no-image-vector-symbol-missing-available-icon-no-gallery-for-this-moment-placeholder.jpg?s=612x612&w=0&k=20&c=Rdn-lecwAj8ciQEccm0Ep2RX50FCuUJOaEM8qQjiLL0=";
            data[1] = "";
        }

        if (data[0].equals("N/A")){
            data[0] = "https://media.istockphoto.com/id/1472933890/vector/no-image-vector-symbol-missing-available-icon-no-gallery-for-this-moment-placeholder.jpg?s=612x612&w=0&k=20&c=Rdn-lecwAj8ciQEccm0Ep2RX50FCuUJOaEM8qQjiLL0=";
        }
        return data;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getPoster("Spaceballs"));
        System.out.println(getPlot("Spaceballs"));
    }
}
