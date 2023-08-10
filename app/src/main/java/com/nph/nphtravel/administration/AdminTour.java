package com.nph.nphtravel.administration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nph.nphtravel.R;
import com.nph.nphtravel.administration.dialogs.DialogAddTour;
import com.nph.nphtravel.administration.dialogs.DialogEditTour;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterTour;
import com.nph.nphtravel.db.handlers.TourDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Tour;

import java.util.ArrayList;

public class AdminTour extends AppCompatActivity {

    ListView lvTour;

    FloatingActionButton btn_AddTour;

    //quản lý tour

    String idTour;
    Tour edit_Tour;
    ArrayList<Tour> dsTour = new ArrayList<Tour>();

    ArrayAdapterTour myArrayAdapterTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tour);

        //Quan ly Tour
        lvTour = (ListView) findViewById(R.id.lvTour);

        TourDatabaseHandler tourDatabaseHandler = new TourDatabaseHandler(this);

        dsTour = tourDatabaseHandler.getAllTour();

        myArrayAdapterTour = new ArrayAdapterTour(this, R.layout.item_list_tour, dsTour);
        lvTour.setAdapter(myArrayAdapterTour);

        btn_AddTour = findViewById(R.id.btn_addTour);
        btn_AddTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialogTour();

            }
        });

        lvTour.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi phần tử trong ListView được giữ lâu
                // lấy id category item
                idTour = dsTour.get(position).getId();

                // láy Tour
                edit_Tour = dsTour.get(position);
                showEditDeleteDialogTour();
                return true;
            }
        });
    }

    // quan ly tour
    public void openAddDialogTour() {
        DialogAddTour dialogAddTour = new DialogAddTour();

        // load lại dữ liệu sau khi add
        dialogAddTour.setListenerTour(new DialogAddTour.AddTourDialogListener() {
            @Override
            public void onTourAdded(Tour tour) {
                dsTour.add(tour);

                myArrayAdapterTour.notifyDataSetChanged();
            }
        });
        dialogAddTour.show(getSupportFragmentManager(), "add_tour_dialog");
    }

    private void showEditDeleteDialogTour() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Tạo đối tượng tạo database
        TourDatabaseHandler tourDatabaseHandler = new TourDatabaseHandler(this);


        builder.setTitle("Thao tác")
                .setItems(new CharSequence[]{"Chỉnh sửa", "Xóa"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Thực hiện chỉnh sửa

                                openEditDiaLogTour();
                                break;
                            case 1:
                                // Thực hiện xóa
                                tourDatabaseHandler.deleteIDTour(idTour);

//                                cập nhật lại listView
                                dsTour.remove(edit_Tour);
                                myArrayAdapterTour.notifyDataSetChanged();
                                break;
                        }
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    public void openEditDiaLogTour() {
        DialogEditTour dialogEditTour = new DialogEditTour(edit_Tour);

        dialogEditTour.setListener(new DialogEditTour.EditTourDialogListener() {
            @Override
            public void onTourEdit(Tour tour) {
                // Thêm tour vào danh sách
                dsTour.remove(edit_Tour);
                dsTour.add(tour);

                // Tải lại ListView
                myArrayAdapterTour.notifyDataSetChanged();
            }
        });

        dialogEditTour.show(getSupportFragmentManager(), "edit category dialog");
    }
}