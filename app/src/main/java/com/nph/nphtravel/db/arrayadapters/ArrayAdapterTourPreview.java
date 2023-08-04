package com.nph.nphtravel.db.arrayadapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.nph.nphtravel.R;
import com.nph.nphtravel.db.tableclasses.Tour;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterTourPreview extends ArrayAdapter {

    Activity context = null;
    int layoutId;
    ArrayList<Tour> alTour = null;

    public ArrayAdapterTourPreview(@NonNull Activity context, int resource, @NonNull ArrayList<Tour> listTour) {
        super(context, resource, listTour);
        this.context = context;
        this.layoutId = resource;
        this.alTour = listTour;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        if (alTour.size() > 0 && position >= 0) {
            ImageView img =(ImageView) convertView.findViewById(R.id.ivTourAvtPreview);
            TextView tvTour = (TextView) convertView.findViewById(R.id.tvTourName);
            TextView tvDesc = convertView.findViewById(R.id.tvTourDesc);
            Button price = (Button) convertView.findViewById(R.id.btnBooking);
            TextView tvDate = convertView.findViewById(R.id.tvTourDate);
            TextView tvLocation = (TextView) convertView.findViewById(R.id.tvTourRoute);

            Tour tour = alTour.get(position);
            tvTour.setText(tour.getTour_name());
            tvDesc.setText(tour.getDescription());
            price.setText(String.valueOf(tour.getPrice()));
            tvLocation.setText(tour.getLocation());
            tvDate.setText(tour.getStart_day());

            // đọc avatar

            String imageUriString = tour.getAvatar();

            if (imageUriString != null) {
                Glide.with(context)
                        .load(imageUriString)
                        .error(R.drawable.fb)
                        .into(img);
            }
        }

        return convertView;
    }
}
