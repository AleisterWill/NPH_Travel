package com.nph.nphtravel.db.arrayadapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.nph.nphtravel.R;
import com.nph.nphtravel.db.handlers.RatingDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Tour;
import com.nph.nphtravel.PaymentActivity;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterTourPreview extends ArrayAdapter {

    Activity context;
    int layoutId;
    public ArrayList<Tour> alTour;

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
            ImageView img = convertView.findViewById(R.id.ivTourAvtPreview);
            TextView tvTour = convertView.findViewById(R.id.tvTourName);
            RatingBar rbTourScore = convertView.findViewById(R.id.rbTourScore);
            Button price = convertView.findViewById(R.id.btnBooking);
            TextView tvDate = convertView.findViewById(R.id.tvTourDate);
            TextView tvLocation = convertView.findViewById(R.id.tvTourRoute);

            Tour tour = alTour.get(position);
            tvTour.setText(tour.getTour_name());
            RatingDatabaseHandler rdbh = new RatingDatabaseHandler(context);
            rbTourScore.setRating((float) rdbh.getAvgScoreByTourId(Integer.parseInt(tour.getId().toString())));
            price.setText(String.valueOf(tour.getPrice()));
            tvLocation.setText(tour.getLocation());
            tvDate.setText(tour.getStart_day());

            // đọc avatar

            String imageUriString = tour.getAvatar();

            if (imageUriString != null) {
                Glide.with(context).load(imageUriString).error(R.drawable.fb).into(img);
            }

            price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // get currentUser
                        Bundle currentUser = context.getIntent().getExtras().getBundle("currentUser");

                        Intent toPayment = new Intent(context, PaymentActivity.class);

                        //put infos to bundle
                        Bundle orderInfo = new Bundle();
                        orderInfo.putString("ordId", tour.getId());
                        orderInfo.putString("ordName", tour.getTour_name());
                        orderInfo.putString("ordPrice", String.valueOf(tour.getPrice()));

                        //put bundle to intent's extras
                        toPayment.putExtra("orderInfo", orderInfo);
                        toPayment.putExtra("currentUser", currentUser);

                        context.startActivity(toPayment);

                    } catch (Exception ex) {
                        AlertDialog builder = new AlertDialog.Builder(context)
                                .setTitle("Thông báo")
                                .setMessage("Bạn phải đăng nhập để thực hiện chức năng này")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).create();
                        builder.show();
                    }
                }
            });
        }
        return convertView;
    }

}
