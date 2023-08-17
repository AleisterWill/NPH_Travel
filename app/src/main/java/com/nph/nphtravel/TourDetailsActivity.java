package com.nph.nphtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterComments;
import com.nph.nphtravel.db.handlers.CommentDatabaseHandler;
import com.nph.nphtravel.db.handlers.RatingDatabaseHandler;
import com.nph.nphtravel.db.handlers.TourDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Comment;
import com.nph.nphtravel.db.tableclasses.Tour;

import java.util.ArrayList;

public class TourDetailsActivity extends AppCompatActivity {

    ImageView ivTourAvtPreview;
    TextView tvTourName, tvTourDesc, tvTourRoute, tvTourDate, tvTourPrice;
    RatingBar rbTourScore;
    EditText etComment;
    ImageButton ibtnSend;
    ListView lvComments;
    Button btnBack, btnBooking, btnLoadMore;

    int page = 1, max = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_details);
        bindViews();

        TourDatabaseHandler tdb = new TourDatabaseHandler(this);
        RatingDatabaseHandler rdb = new RatingDatabaseHandler(this);


        String tourId = getIntent().getExtras().getString("detailsTourId");
        Tour tour = tdb.getById(tourId);
        double tourAvgScore = rdb.getAvgScoreByTourId(Integer.parseInt(tour.getId()));

        if (tour.getAvatar() != null)
            Glide.with(this).load(tour.getAvatar()).error(R.drawable.fb).into(ivTourAvtPreview);
        tvTourName.setText(tour.getTour_name());
        tvTourDesc.setText(tour.getDescription());
        tvTourRoute.setText(tour.getLocation());
        tvTourDate.setText(tour.getStart_day());
        tvTourPrice.setText(String.valueOf(tour.getPrice()));
        rbTourScore.setRating((float) tourAvgScore);

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // get currentUser
                    Bundle currentUser = getIntent().getExtras().getBundle("currentUser");
                    if (currentUser == null) throw new NullPointerException();

                    Intent toPayment = new Intent(TourDetailsActivity.this, PaymentActivity.class);

                    //put infos to bundle
                    Bundle orderInfo = new Bundle();
                    orderInfo.putString("ordId", tour.getId());
                    orderInfo.putString("ordName", tour.getTour_name());
                    orderInfo.putString("ordPrice", String.valueOf(tour.getPrice()));

                    //put bundle to intent's extras
                    toPayment.putExtra("orderInfo", orderInfo);
                    toPayment.putExtra("currentUser", currentUser);

                    startActivity(toPayment);
                } catch (Exception ex) {
                    alertLogin();
                }
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        CommentDatabaseHandler cdh = new CommentDatabaseHandler(this);
        ArrayList<Comment> alComments = cdh.getPaginatedListByTourId(Integer.parseInt(tour.getId()), page, max);
        ArrayAdapterComments aac = new ArrayAdapterComments(this, R.layout.item_comment, alComments);
        lvComments.setAdapter(aac);


        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Comment> moreComments = cdh.getPaginatedListByTourId(Integer.parseInt(tour.getId()), ++page, max);
                if (!moreComments.isEmpty()) {
                    alComments.addAll(moreComments);
                    aac.notifyDataSetChanged();
                } else
                    btnLoadMore.setVisibility(View.GONE);
            }
        });


        ibtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get currentUser
                Bundle currentUser = getIntent().getExtras().getBundle("currentUser");

                if (currentUser != null) {
                    Comment comment = new Comment(true);
                    comment.setContent(etComment.getText().toString());
                    comment.setToudId(Integer.parseInt(tour.getId()));
                    comment.setUserId(Integer.parseInt(currentUser.getString("id")));

                    if (cdh.addComment(comment) != -1) {
                        etComment.setText("");
                        alComments.add(0, comment);
                        aac.notifyDataSetChanged();
                    }
                } else alertLogin();
            }
        });
    }

    private void alertLogin() {
        AlertDialog builder = new AlertDialog.Builder(TourDetailsActivity.this)
                .setTitle("Thông báo")
                .setMessage("Bạn phải đăng nhập để thực hiện chức năng này")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        builder.show();
    }


    private void bindViews() {
        ivTourAvtPreview = findViewById(R.id.ivTourAvtPreview);
        tvTourName = findViewById(R.id.tvTourName);
        tvTourDesc = findViewById(R.id.tvTourDesc);
        tvTourRoute = findViewById(R.id.tvTourRoute);
        tvTourDate = findViewById(R.id.tvTourDate);
        tvTourPrice = findViewById(R.id.tvTourPrice);
        rbTourScore = findViewById(R.id.rbTourScore);
        etComment = findViewById(R.id.etComment);
        ibtnSend = findViewById(R.id.ibtnSend);
        lvComments = findViewById(R.id.lvComments);
        btnBack = findViewById(R.id.btnBack);
        btnBooking = findViewById(R.id.btnBooking);
        btnLoadMore = findViewById(R.id.btnLoadMore);
    }
}