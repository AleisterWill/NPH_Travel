package com.nph.nphtravel.botnavfrags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nph.nphtravel.R;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterTour;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterTourPreview;
import com.nph.nphtravel.db.handlers.TourDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Tour;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    ListView lvTour;
    ArrayList<Tour> listTour = new ArrayList<>();
    ArrayAdapterTourPreview arrayAdapterTourPreview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        lvTour = (ListView) rootView.findViewById(R.id.lvTour);
        TourDatabaseHandler tourDatabaseHandler = new TourDatabaseHandler(getActivity());
        listTour = tourDatabaseHandler.getAllTour();
        arrayAdapterTourPreview = new ArrayAdapterTourPreview(HomeFragment.this.getActivity(), R.layout.item_tour_preview, listTour);
        lvTour.setAdapter(arrayAdapterTourPreview);

        return rootView;
    }
}