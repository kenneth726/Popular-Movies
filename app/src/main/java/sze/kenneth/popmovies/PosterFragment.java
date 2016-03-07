package sze.kenneth.popmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class PosterFragment extends Fragment {
    // references to our images
    private String[] mThumbIds = {
            "http://image.tmdb.org/t/p/w185/inVq3FRqcYIRl2la8iZikYYxFNR.jpg",
            "http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
            "http://image.tmdb.org/t/p/w185/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg",
            "http://image.tmdb.org/t/p/w185/5W794ugjRwYx6IdFp1bXJqqMWRg.jpg",
            "http://image.tmdb.org/t/p/w185/5aGhaIHYuQbqlHWvWYqMCnj40y2.jpg",
            "http://image.tmdb.org/t/p/w185/hE24GYddaxB9MVZl1CaiI86M3kp.jpg",
            "http://image.tmdb.org/t/p/w185/p11Ftd4VposrAzthkhF53ifYZRl.jpg",
            "http://image.tmdb.org/t/p/w185/ngKxbvsn9Si5TYVJfi1EGAGwThU.jpg",
            "http://image.tmdb.org/t/p/w185/yTdTuJww8NnL9YLaxL2LxDG5uQ7.jpg",

    };


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

        GridView posterGridView = (GridView) rootView.findViewById(R.id.posters_grid_view);
        posterGridView.setAdapter(new ImageAdapter(getActivity(), mThumbIds));

        posterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
