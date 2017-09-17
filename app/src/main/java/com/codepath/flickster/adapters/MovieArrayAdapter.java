package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flickster.R.id.tvOverview;

/**
 * Created by chenrangong on 9/16/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // View Lookup cache
    private static class ViewHolder{
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }
    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the data item from position
        Movie movie = getItem(position);

        // check the existing view being resued
        ViewHolder viewHolder; // view lookup cache stored in tag
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(tvOverview);
            // cache the viewHolder object inseide the fresh view
            convertView.setTag(viewHolder);
        } else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // clear out image from convertView
        viewHolder.ivImage.setImageResource(0);

        // populate data
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        int orientation = getContext().getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Picasso.with(getContext()).load(movie.getPosterPath())
                    .placeholder(R.drawable.default_movie_portrait)
                    .resize(342, 0)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(viewHolder.ivImage);
        } else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            Picasso.with(getContext()).load(movie.getBackdrop())
                    .placeholder(R.drawable.default_land_images)
                    .resize(500, 0)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(viewHolder.ivImage);
        }


        return convertView;
    }
}
