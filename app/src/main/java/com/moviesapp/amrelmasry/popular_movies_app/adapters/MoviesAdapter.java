package com.moviesapp.amrelmasry.popular_movies_app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.moviesapp.amrelmasry.popular_movies_app.R;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;
import com.squareup.picasso.Picasso;

/**
 * Created by AmrELmasry on 9/23/2015.
 */
public class MoviesAdapter extends CursorAdapter {

    private Context mContext;

    public MoviesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        ViewHodler viewHodler = new ViewHodler(view);
        view.setTag(viewHodler);

        return view;


    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHodler viewHodler = (ViewHodler) view.getTag();


        String posterPath = cursor.getString(Utilities.COL_POSTER_PATH);

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + posterPath).into(viewHodler.imageView);

    }


    public static class ViewHodler {

        public final ImageView imageView;

        public ViewHodler(View view) {
            imageView = (ImageView) view;
            imageView.setLayoutParams(new GridView.LayoutParams(160, 160));
        }


    }


}
