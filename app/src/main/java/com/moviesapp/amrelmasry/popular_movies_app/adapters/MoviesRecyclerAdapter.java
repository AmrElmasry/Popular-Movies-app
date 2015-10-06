package com.moviesapp.amrelmasry.popular_movies_app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviesapp.amrelmasry.popular_movies_app.R;
import com.moviesapp.amrelmasry.popular_movies_app.adapters.MoviesRecyclerAdapter.SimpleViewHolder;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.DatabaseUtilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AmrELmasry on 9/28/2015.
 */

public class MoviesRecyclerAdapter extends CursorRecyclerAdapter<SimpleViewHolder> {


    private int mLayout;
    private Context mContext;
    private SimpleViewHolder.ViewHolderClicksListener mListener;
    private ArrayList<String> moviesApiIDs;


    public MoviesRecyclerAdapter(Cursor c, Context context, SimpleViewHolder.ViewHolderClicksListener listener) {
        super(c);
        mLayout = R.layout.movie_item;
        this.mContext = context;
        this.mListener = listener;
        moviesApiIDs = new ArrayList<>();
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);
        return new SimpleViewHolder(v, mListener);
    }

    int i = 0;

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, Cursor cursor) {


        String posterPath = cursor.getString(DatabaseUtilities.COL_POSTER_PATH);

        holder.movieVoteAverage.setText(cursor.getString(DatabaseUtilities.COL_VOTE_AVERAGE));
        holder.movieTitle.setText(cursor.getString(DatabaseUtilities.COL_TITLE));

        final String BASE_MOVIE_IMAGE_URL = "http://image.tmdb.org/t/p";

        Uri uri = Uri.parse(BASE_MOVIE_IMAGE_URL)
                .buildUpon()
                .appendPath("w185")
                .appendEncodedPath(posterPath)
                .build();

        Picasso.with(mContext).load(uri).into(holder.moviePoster);
        holder.moviePoster.setContentDescription(mContext.getString(R.string.movie_poster_desc) + cursor.getString(DatabaseUtilities.COL_TITLE));


    }


    public String getMovieApiID(int position) {


        return moviesApiIDs.get(position);
    }

    @Override
    public Cursor swapCursor(Cursor c) {


        if (c != null) {
            clearMoviesApiIDs();


            c.moveToPosition(-1);
            while (c.moveToNext()) {
                moviesApiIDs.add(c.getString(DatabaseUtilities.COL_API_ID));
            }


        }
        return super.swapCursor(c);
    }

    private void clearMoviesApiIDs() {
        this.moviesApiIDs.clear();


    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView moviePoster;
        public final ViewHolderClicksListener viewHolderClicksListener;
        public final TextView movieTitle;
        public final TextView movieVoteAverage;

        public SimpleViewHolder(View itemView, ViewHolderClicksListener listener) {
            super(itemView);

            viewHolderClicksListener = listener;

            moviePoster = (ImageView) itemView.findViewById(R.id.main_movie_poster);

            movieTitle = (TextView) itemView.findViewById(R.id.main_movie_title);
            movieVoteAverage = (TextView) itemView.findViewById(R.id.main_movie_vote_average);

            moviePoster.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            viewHolderClicksListener.onMovieClick(v, this.getAdapterPosition());

        }

        public interface ViewHolderClicksListener {
            void onMovieClick(View view, int position);
        }
    }
}



