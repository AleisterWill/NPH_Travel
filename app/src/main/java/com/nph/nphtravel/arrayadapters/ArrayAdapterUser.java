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
import com.nph.nphtravel.db.tableclasses.User;

import java.util.ArrayList;


public class ArrayAdapterUser extends ArrayAdapter {
    Activity context = null;

    ArrayList<User> myArrayUser = null;

    int layOutId;

    public ArrayAdapterUser(@NonNull Activity context, int resource, @NonNull ArrayList<User>listusers) {
        super(context, resource, listusers);
        this.context = context;
        this.myArrayUser = listusers;
        this.layOutId = resource;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layOutId, null);
        if(myArrayUser.size() > 0 && pos >=0)
        {
            ImageView img =(ImageView) convertView.findViewById(R.id.avatarImageView);
            TextView tvUser = (TextView) convertView.findViewById(R.id.txtUserName);
            TextView tvPass = (TextView) convertView.findViewById(R.id.txtPassWord);
            TextView tvRole = (TextView) convertView.findViewById(R.id.txtRole);

            //Thêm ID
            TextView tvID = (TextView)convertView.findViewById(R.id.idUser);

            User emp = myArrayUser.get(pos);

            // đọc avatar

            String imageUriString = emp.getAvatar();

            if (imageUriString != null) {
                Glide.with(context)
                        .load(imageUriString)
                        .error(R.drawable.fb)
                        .into(img);
            } else {
                // Xử lý khi uriString là null

               //Hoặc sử dụng giá trị mặc định
//                img.setImageResource(R.drawable.avtar);
            }




            tvUser.setText(emp.getUsername());
            tvPass.setText(emp.getPassword());
            tvRole.setText(String.valueOf(emp.getRole()));

            //Id user
            tvID.setText(emp.getId());

        }

        return convertView;
    }



}
