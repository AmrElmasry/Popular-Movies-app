package com.moviesapp.amrelmasry.popular_movies_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviesapp.amrelmasry.popular_movies_app.R;
import com.moviesapp.amrelmasry.popular_movies_app.models.Trailer;
import com.squareup.picasso.Picasso;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class TrailersAdapter extends ArrayAdapter<Trailer> {

//    private ArrayList<Trailer> mtrailers;


    private Context mContext;

    public TrailersAdapter(Context context) {
        super(context, R.layout.trailer_item);
        mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;


        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        } else {
            view = convertView;
        }

        TrailerViewHolder viewHolder = new TrailerViewHolder(view);



        Trailer trailer = getItem(position);

        viewHolder.trailerName.setText(trailer.getName());
        Picasso.with(mContext).load(trailer.getThumbnailUri()).into(viewHolder.trailerThumbnail);


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

}
