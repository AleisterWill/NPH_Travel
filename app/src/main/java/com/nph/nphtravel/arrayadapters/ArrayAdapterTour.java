package com.nph.nphtravel.arrayadapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.nph.nphtravel.R;
import com.nph.nphtravel.db.tableclasses.Tour;

import java.util.ArrayList;

public class ArrayAdapterTour extends ArrayAdapter {
    Activity context = null;

    ArrayList<Tour> myArrayTour = null;

    int layOutId;

    public ArrayAdapterTour(@NonNull Activity context, int resource, @NonNull ArrayList<Tour>listTour) {
        super(context, resource, listTour);
        this.context = context;
        this.myArrayTour = listTour;
        this.layOutId = resource; }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layOutId, null);
        if(myArrayTour.size() > 0 && pos >=0)
        {
            ImageView img =(ImageView) convertView.findViewById(R.id.tourImageView);
            TextView tvTour = (TextView) convertView.findViewById(R.id.txtTourName);
            TextView tvPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            TextView tvLocation = (TextView) convertView.findViewById(R.id.txtLocation);

            //Thêm ID
            TextView tvID = (TextView)convertView.findViewById(R.id.idTour);

            Tour emp = myArrayTour.get(pos);

            // đọc avatar

            String imageUriString = emp.getAvatar();

            if (imageUriString != null) {
                Glide.with(context)
                        .load(imageUriString)
                        .error(R.drawable.fb)
                        .into(img);
            }




            tvTour.setText(emp.getTour_name());
            tvPrice.setText(String.valueOf(emp.getPrice()));
            tvLocation.setText(String.valueOf(emp.getLocation()));

            //Id tour
            tvID.setText(emp.getId());

        }

        return convertView;
    }
}
