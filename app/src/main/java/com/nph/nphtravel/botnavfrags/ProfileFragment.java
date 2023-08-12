package com.nph.nphtravel.botnavfrags;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nph.nphtravel.MainActivity;
import com.nph.nphtravel.R;
import com.nph.nphtravel.account.MyReceipts;
import com.nph.nphtravel.administration.AdminControlPanel;

public class ProfileFragment extends Fragment {
    
    ImageView ivProfileAvatar;
    TextView tvProfileInfo;
    Button btnAdmin;
    Button btnSignOut;

    Button btnMyReceipts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Mapping views
        ivProfileAvatar = rootView.findViewById(R.id.ivProfileAvatar);
        tvProfileInfo = rootView.findViewById(R.id.tvProfileInfo);
        btnAdmin = rootView.findViewById(R.id.btnAdmin);
        btnSignOut = rootView.findViewById(R.id.btnSignOut);
        btnMyReceipts = rootView.findViewById(R.id.btnMyReceipt);


        // Get currentUser Bundle in Extras
        Bundle currentUser = getActivity().getIntent().getExtras().getBundle("currentUser");

        // Display info into Views
        Glide.with(getActivity())
                .load(currentUser.getString("avatar"))
                .error(R.drawable.fb)
                .into(ivProfileAvatar);
        tvProfileInfo.setText(String.format("User ID: %s\nUsername: %s\nUser Role: %s",
                currentUser.getString("id"),
                currentUser.getString("username"),
                currentUser.getString("role"))
        );


        // Handle btnAdmin visibility depends on currentUser role
        if (!currentUser.getString("role").equals("0")) // View.GONE by Default
            btnAdmin.setVisibility(View.VISIBLE);
        // Handle btnAdmin onClickListener
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Forward to AdminControlPanel
                Intent toAdminControlPanel = new Intent(getActivity(), AdminControlPanel.class);
                startActivity(toAdminControlPanel);
            }
        });

        btnMyReceipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Forward to MyReceipts with currentUser data
                Intent toMyReceipts = new Intent(getActivity(), MyReceipts.class);
                Bundle currentUser = getActivity().getIntent().getExtras().getBundle("currentUser");
                toMyReceipts.putExtra("currentUser", currentUser);
                startActivity(toMyReceipts);
            }
        });


        // Handle btnSignOut onClickListener
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove currentUser Bundle out of Intent's Extras
                getActivity().getIntent().removeExtra("currentUser");
                // Forward to AccountFragment
                ((MainActivity) getActivity()).replaceFragment(new AccountFragment());
            }
        });



        return rootView;
    }
}