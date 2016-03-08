package sze.kenneth.popmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Movie> movieList;

    public ImageAdapter(Context c, List<Movie> movieList) {
        mContext = c;
        this.movieList = movieList;
    }

    public int getCount() {
        return movieList.size();
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }
        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";
        String path = POSTER_BASE_URL + movieList.get(position).getPosterPath();
        Picasso.with(mContext).load(path).resize(600, 900).centerCrop().into(imageView);
        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }


}
