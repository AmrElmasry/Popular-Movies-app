package com.moviesapp.amrelmasry.popular_movies_app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.moviesapp.amrelmasry.popular_movies_app.adapters.MoviesRecyclerAdapter.SimpleViewHolder;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AmrELmasry on 9/28/2015.
 */

public class MoviesRecyclerAdapter extends CursorRecyclerAdapter<SimpleViewHolder> {


    private int mLayout;
    private Context mContext;
    SimpleViewHolder.ViewHolderClicksListener mListener;
    private ArrayList<String> moviesApiIDs;


    public MoviesRecyclerAdapter(int layout, Cursor c, Context context, SimpleViewHolder.ViewHolderClicksListener listener) {
        super(c);
        mLayout = layout;
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

//        Log.i("MOVIE_CILICK", "Movie Name " + cursor.getString(Utilities.COL_TITLE));

        String posterPath = cursor.getString(Utilities.COL_POSTER_PATH);

//        moviesApiIDs.add(cursor.getString(Utilities.COL_API_ID));


        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + posterPath).into(holder.imageView);


    }


    public String getMovieApiID(int position) {

        Log.i("MOVIE_CILICK", "position a " + position);
        Log.i("MOVIE_CILICK", "api id b " + moviesApiIDs.get(position));
        Log.i("MOVIE_CILICK", "Array Length " + moviesApiIDs.size());


        return moviesApiIDs.get(position);
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        if (c != null) {
            // TODO OPTIMIZE THIS LATER
            this.clearMoviesApiIDs();

            while (c.moveToNext()) {
                moviesApiIDs.add(c.getString(Utilities.COL_API_ID));
            }
            for (int i = 0; i < c.getCount(); i++) {

                Log.i("MOVIE_CILICK", "Movie Name is : " + moviesApiIDs.get(i));

            }
            Log.i("MOVIE_CILICK", "Cursor size is : " + c.getCount());
            Log.i("MOVIE_CILICK", "Array size is : " + c.getCount());

        } else {
            Log.i("MOVIE_CILICK", "Array size is : " + moviesApiIDs.size());
        }
        return super.swapCursor(c);
    }

    public void clearMoviesApiIDs() {
        this.moviesApiIDs.clear();

        Log.i("MOVIE_CILICK", "ArrayList Clreared");

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public ViewHolderClicksListener viewHolderClicksListener;

        public SimpleViewHolder(View itemView, ViewHolderClicksListener listener) {
            super(itemView);
            viewHolderClicksListener = listener;
            imageView = (ImageView) itemView;
            imageView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            viewHolderClicksListener.onMovieClick(v, this.getAdapterPosition());

        }

        public static interface ViewHolderClicksListener {
            public void onMovieClick(View view, int position);
        }
    }
}



