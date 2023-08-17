package com.nph.nphtravel.db.arrayadapters;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.nph.nphtravel.R;
import com.nph.nphtravel.db.handlers.UserDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Comment;
import com.nph.nphtravel.db.tableclasses.User;

import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ArrayAdapterComments extends ArrayAdapter {

    Activity context;
    int resource;
    ArrayList<Comment> alComments;

    public ArrayAdapterComments(@NonNull Activity context, int resource, ArrayList<Comment> alComments) {
        super(context, resource, alComments);
        this.context = context;
        this.resource = resource;
        this.alComments = alComments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);

        if (!alComments.isEmpty() && position >= 0) {
            ImageView ivUserAvatar = convertView.findViewById(R.id.ivUserAvatar);
            TextView tvUsername = convertView.findViewById(R.id.tvUsername);
            TextView tvContent = convertView.findViewById(R.id.tvContent);
            TextView tvCreatedDate = convertView.findViewById(R.id.tvCreatedDate);

            Comment comment = alComments.get(position);
            UserDatabaseHandler udh = new UserDatabaseHandler(context);

            User user = udh.getById(comment.getUserId());

            Glide.with(context).load(user.getAvatar()).error(R.drawable.fb).into(ivUserAvatar);
            tvUsername.setText(user.getUsername());
            tvContent.setText(comment.getContent());

            //Handle createDate to display "...ago"
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date d = sdf.parse(comment.getCreated_date());
                CharSequence momentAgo = DateUtils.getRelativeTimeSpanString(
                        d.getTime(),
                        Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS
                );
                tvCreatedDate.setText(momentAgo);
            } catch (ParseException e) {
                e.printStackTrace();
                tvCreatedDate.setText(comment.getCreated_date());
            }
        }

        return convertView;
    }
}
