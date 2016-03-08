package sze.kenneth.popmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PosterFragment extends Fragment {
    private ImageAdapter posterAdapter;
    private GridView posterGridView;

    public PosterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_poster, container, false);

        posterGridView = (GridView) rootView.findViewById(R.id.posters_grid_view);

        posterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String message = posterAdapter.getItem(position).getTitle();
                //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                Intent movieDetailIntent = new Intent(getActivity(), MovieDetailActivity.class);
                startActivity(movieDetailIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMoviePoster();
    }

    private void updateMoviePoster() {
        FetchMovieTask movieTask = new FetchMovieTask();
        movieTask.execute();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;

            String sort_option = "popularity.desc";

            try {
                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM = "sort_by";
                final String KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, sort_option)
                        .appendQueryParameter(KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());
                //Log.v(LOG_TAG, "LINK: " + builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }

                movieJsonStr = buffer.toString();
                //Log.v(LOG_TAG, "CONTENT: " + movieJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "error", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try{
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieArrayListFromJson(movieJsonStr);

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> popularMovieList) {
            for (Movie mov : popularMovieList) {
                Log.v("PosterFragment", "Title: " + mov.getTitle());
                Log.v("PosterFragment", "Path: " + mov.getPosterPath());
            }
            posterAdapter = new ImageAdapter(getActivity(), popularMovieList);
            posterGridView.setAdapter(posterAdapter);
        }

        private List<Movie> getMovieArrayListFromJson(String movieJsonString) throws JSONException {
            List<Movie> movieList = new ArrayList<>();
            final String TMDB_LIST = "results";

            JSONObject movieJson = new JSONObject(movieJsonString);
            JSONArray movieArray = movieJson.getJSONArray(TMDB_LIST);

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject individualMovieData = movieArray.getJSONObject(i);
                int id = individualMovieData.getInt("id");
                String title = individualMovieData.getString("title");
                String overview = individualMovieData.getString("overview");
                String poster_path = individualMovieData.getString("poster_path");
                String rel_date = individualMovieData.getString("release_date");
                double vote_avg = individualMovieData.getDouble("vote_average");
                Movie movie = new Movie(id, title, overview, poster_path, rel_date, vote_avg);
                movieList.add(movie);
            }
            return movieList;
        }
    }
}
