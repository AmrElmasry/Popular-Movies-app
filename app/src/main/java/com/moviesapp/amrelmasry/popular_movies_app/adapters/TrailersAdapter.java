package com.moviesapp.amrelmasry.popular_movies_app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviesapp.amrelmasry.popular_movies_app.R;
import com.moviesapp.amrelmasry.popular_movies_app.models.Trailer;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class TrailersAdapter extends ArrayAdapter<Trailer> {

//    private ArrayList<Trailer> mtrailers;

    public TrailersAdapter(Context context) {
        super(context, R.layout.trailer_item);
//        this.mtrailers = trailers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = super.getView(position, convertView, parent);

        View view;


        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        } else {
            view = convertView;
        }

        TrailerViewHolder viewHolder = new TrailerViewHolder(view);


        Log.i("LoaderUpdate", "Adapter popualting data");

        Trailer trailer = getItem(position);
        Log.i("LoaderUpdate", trailer.getName());

        viewHolder.trailerName.setText(trailer.getName());
        viewHolder.trailerThumbnail.setImageResource(R.drawable.movie_poster_small);
//
//        TextView name = (TextView) view.findViewById(R.id.trailer_name);
//        name.setText(trailer.getName());

        return view;

    }

    public static class TrailerViewHolder {
        public TextView trailerName;
        public ImageView trailerThumbnail;

        public TrailerViewHolder(View rootView) {
            this.trailerName = (TextView) rootView.findViewById(R.id.trailer_name);

            this.trailerThumbnail = (ImageView) rootView.findViewById(R.id.trailer_thumbnail);
        }
    }

//
//
//    public void addTrailers(ArrayList<Trailer> trailers) {
//        for (Trailer trailer : trailers) {
//            mtrailers.add(trailer);
//        }
//
//    }
}
