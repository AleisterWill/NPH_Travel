package com.nph.nphtravel.administration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nph.nphtravel.R;
import com.nph.nphtravel.databinding.ActivityMainBinding;

public class AdminControlPanel extends AppCompatActivity {

    Button btnAdminUser;
    Button btnAdminCategory;
    Button btnAdminTour;
    Button btnAdminBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_panel);

        btnAdminUser = findViewById(R.id.btnAdminUser);
        btnAdminCategory = findViewById(R.id.btnAdminCategory);
        btnAdminTour = findViewById(R.id.btnAdminTour);
        btnAdminBooking = findViewById(R.id.btnAdminBooking);

        //Handle btnAdminUser onClick event forward to its respective functioning class.
        btnAdminUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAdminUser = new Intent(AdminControlPanel.this, AdminUser.class);
                startActivity(toAdminUser);
            }
        });

        btnAdminCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAdminCategory = new Intent(AdminControlPanel.this, AdminUser.class);
                startActivity(toAdminCategory);
            }
        });

        btnAdminTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAdminTour = new Intent(AdminControlPanel.this, AdminTour.class);
                startActivity(toAdminTour);
            }
        });



    }
}