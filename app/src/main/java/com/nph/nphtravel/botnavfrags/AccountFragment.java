package com.nph.nphtravel.botnavfrags;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nph.nphtravel.MainActivity;
import com.nph.nphtravel.account.Login_Activity;
import com.nph.nphtravel.R;

import org.w3c.dom.Text;


public class AccountFragment extends Fragment {

    TextView txtDangNhap_DangKy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootVIew = inflater.inflate(R.layout.fragment_account, container, false);
        // Inflate the layout for this fragment

        //Anh xạ
        txtDangNhap_DangKy = rootVIew.findViewById(R.id.id_DangNhap_DangKy);

        // link tới Login_Activity
        txtDangNhap_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login_Activity.class);
                startActivity(intent);
            }
        });

        return rootVIew;

    }
}