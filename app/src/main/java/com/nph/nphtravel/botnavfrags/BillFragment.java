package com.nph.nphtravel.botnavfrags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.nph.nphtravel.R;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterTourPreview;
import com.nph.nphtravel.db.handlers.CategoryDatabaseHandler;
import com.nph.nphtravel.db.handlers.TourDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Category;
import com.nph.nphtravel.db.tableclasses.Tour;

import java.util.ArrayList;

public class BillFragment extends Fragment {

    EditText etKeyword, etDate;
    Spinner spinnerCate;
    ListView lvTour;
    Button btnSearch;


    //Prepare query data
    String kw = "", date = "";
    String cateId = "0";
    ArrayList<Tour> listTours;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        bindViews(view);


        //Spinner Category
        CategoryDatabaseHandler cateDH = new CategoryDatabaseHandler(this.getContext());
        ArrayList<Category> categories = cateDH.getAllCategory();
        ArrayAdapter cateAAdapter = new ArrayAdapter<Category>(
                this.getContext(), android.R.layout.simple_list_item_1, categories);
        spinnerCate.setAdapter(cateAAdapter);

        spinnerCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                cateId = categories.get(pos).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TourDatabaseHandler tourDH = new TourDatabaseHandler(this.getContext());
        listTours = tourDH.filterByCateId(kw, date, cateId);
        ArrayAdapterTourPreview tpAA = new ArrayAdapterTourPreview(
                getActivity(), R.layout.item_tour_preview, listTours );
        lvTour.setAdapter(tpAA);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kw = etKeyword.getText().toString();
                date = etDate.getText().toString();
                listTours.clear();
                listTours.addAll(tourDH.filterByCateId(kw, date, cateId));
                tpAA.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void bindViews(View view) {
        etKeyword = view.findViewById(R.id.etKeyword);
        etDate = view.findViewById(R.id.etDate);
        spinnerCate = view.findViewById(R.id.spinnerCate);
        lvTour = view.findViewById(R.id.lvTour);
        btnSearch = view.findViewById(R.id.btnSearch);
    }
}