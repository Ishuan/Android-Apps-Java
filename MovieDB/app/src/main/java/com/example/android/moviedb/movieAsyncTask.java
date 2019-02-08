package com.example.android.moviedb;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class movieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private Context context;
    private MovieInterface iMovieInterface;

    public movieAsyncTask(Context context, MovieInterface iMovieInterface) {
        this.context = context;
        this.iMovieInterface = iMovieInterface;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {

        ArrayList<Movie> movieList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        HttpsURLConnection httpsURLConnection = null;
        try {
            URL url = new URL(strings[0]);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                JSONObject rootObject = new JSONObject(builder.toString());
                JSONArray movieArray = rootObject.getJSONArray("results");
                for (int item = 0; item < movieArray.length(); item++) {
                    JSONObject movieObject = movieArray.getJSONObject(item);
                    Movie movie = new Movie();
                    movie.setMovieTitle(movieObject.getString("title"));
                    movie.setMovieReleaseDate(movieObject.getString("release_date"));
                    movie.setMovieOverview(movieObject.getString("overview"));
                    movie.setMovieRating((float) movieObject.getDouble("vote_average"));
                    movie.setMoviePosterPath(movieObject.getString("poster_path"));
                    movie.setMoviePopularity((float) movieObject.getDouble("popularity"));

                    movieList.add(movie);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpsURLConnection.disconnect();
        }
        return movieList;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        iMovieInterface.sendParsedMovies(movies);
    }

    public interface MovieInterface {
        void sendParsedMovies(ArrayList<Movie> sendMovieList);
    }
}
