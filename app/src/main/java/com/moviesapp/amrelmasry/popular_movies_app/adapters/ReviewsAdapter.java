package com.moviesapp.amrelmasry.popular_movies_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.moviesapp.amrelmasry.popular_movies_app.R;
import com.moviesapp.amrelmasry.popular_movies_app.models.Review;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class ReviewsAdapter extends ArrayAdapter<Review> {

    public ReviewsAdapter(Context context) {
        super(context, R.layout.review_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        } else {
            view = convertView;
        }


        ReviewViewHolder viewHolder = new ReviewViewHolder(view);


        Review review = getItem(position);


        viewHolder.reviewAuthor.setText(review.getAuthor());
        viewHolder.reviewContent.setText(review.getContent());

        return view;
    }


    public static class ReviewViewHolder {
        public TextView reviewAuthor;
        public TextView reviewContent;

        public ReviewViewHolder(View rootView) {
            this.reviewAuthor = (TextView) rootView.findViewById(R.id.review_author);
            this.reviewContent = (TextView) rootView.findViewById(R.id.review_content);
        }
    }

}
