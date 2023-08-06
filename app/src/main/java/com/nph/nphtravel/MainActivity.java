package com.nph.nphtravel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nph.nphtravel.botnavfrags.AccountFragment;
import com.nph.nphtravel.botnavfrags.BillFragment;
import com.nph.nphtravel.botnavfrags.HomeFragment;
import com.nph.nphtravel.botnavfrags.ProfileFragment;
import com.nph.nphtravel.databinding.ActivityMainBinding;
import com.nph.nphtravel.db.handlers.TourDatabaseHandler;

public class MainActivity extends AppCompatActivity {

    //activity binder
    ActivityMainBinding binding;

    TourDatabaseHandler tourDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //menu bottom
        replaceFragment(new HomeFragment()); //set default fragment to home

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.action_shopping) {
                replaceFragment(new BillFragment());
            } else if (item.getItemId() == R.id.action_profile) {
                try {
                    //throw NullPointerException if bundle doesn't exist
                    Bundle currentUser = getIntent().getExtras().getBundle("currentUser");
                    replaceFragment(new ProfileFragment());
                } catch (NullPointerException ex) {
                    replaceFragment(new AccountFragment());
                }
            }
            return true;
        });


    }


    //replace fragment function
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

    }


}