package com.nph.nphtravel.db.arrayadapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nph.nphtravel.R;
import com.nph.nphtravel.db.handlers.BookingDatabaseHandler;
import com.nph.nphtravel.db.handlers.RatingDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Booking;
import com.nph.nphtravel.db.tableclasses.Rating;
import com.nph.nphtravel.db.tableclasses.Receipt;

import java.util.ArrayList;

public class ArrayAdapterReceipts extends ArrayAdapter {
    Activity activity;

    int layoutId;

    ArrayList<Receipt> listReceipts;


    public ArrayAdapterReceipts(@NonNull Activity ctx, int resource, @NonNull ArrayList<Receipt> list) {
        super(ctx, resource, list);
        this.activity = ctx;
        this.layoutId = resource;
        this.listReceipts = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        if(!listReceipts.isEmpty() && position >= 0) {

            //Bind views
            TextView tvRTID = convertView.findViewById(R.id.tvRTID);
            TextView tvRBID = convertView.findViewById(R.id.tvRBID);
            TextView tvRCD = convertView.findViewById(R.id.tvRCD);
            TextView tvRTotal = convertView.findViewById(R.id.tvRTotal);
            Button btnRDetails = convertView.findViewById(R.id.btnRDetails);
            Button btnRRate = convertView.findViewById(R.id.btnRRate);

            //get Receipt at position
            Receipt receipt = listReceipts.get(position);

            //display Receipt into views
            tvRTID.setText(receipt.getTransactionToken());
            tvRBID.setText(String.valueOf(receipt.getBookingId()));
            tvRCD.setText(receipt.getCreatedDate());

            BookingDatabaseHandler bdh = new BookingDatabaseHandler(convertView.getContext());
            Booking booking = bdh.getById(receipt.getBookingId());

            tvRTotal.setText(String.format("%s", booking.getTotal()));

            //handle btnRDetails
            btnRDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông tin chi tiết");
                    builder.setMessage(String.format("" +
                            "Mã đơn đặt: %s\n" +
                            "Mã tour: %s\n" +
                            "Đơn giá: %s\n" +
                            "Số lượng: %s\n" +
                            "Ngày đặt: %s\n",
                            booking.getId(), booking.getTourId(), booking.getTourPrice(),
                            booking.getQuantity(), booking.getCreated_date()));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                }
            });

            Bundle currentUser = activity.getIntent().getExtras().getBundle("currentUser");
            RatingDatabaseHandler rdb = new RatingDatabaseHandler(activity);
            if (rdb.exists(Integer.parseInt(currentUser.getString("id")), receipt.getId()))
                btnRRate.setVisibility(View.GONE);

            btnRRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle data = new Bundle();
                    data.putString("userId", currentUser.getString("id"));
                    data.putInt("tourId", booking.getTourId());

                    Dialog dialog = new Dialog(activity);
                    dialog.setTitle("Quýnh giá tour");
                    dialog.setContentView(R.layout.layout_dialog_rating);

                    RatingBar dialogRatingBar = dialog.findViewById(R.id.dialogRatingBar);
                    Button dialogBtnRate = dialog.findViewById(R.id.dialogBtnRate);
                    Button dialogBtnCancel = dialog.findViewById(R.id.dialogBtnCancel);

                    dialogBtnRate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                int userId = Integer.parseInt(currentUser.getString("id"));
                                int tourId = booking.getTourId();

                                Rating rating = new Rating();
                                rating.setTourId(tourId);
                                rating.setUserId(userId);
                                rating.setScore(dialogRatingBar.getRating());
                                rating.setReceiptId(receipt.getId());

                                RatingDatabaseHandler rdb = new RatingDatabaseHandler(activity);
                                if (rdb.addRating(rating) == 1) {
                                    btnRRate.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }


                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                    dialogBtnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
        }

        return convertView;
    }
}

