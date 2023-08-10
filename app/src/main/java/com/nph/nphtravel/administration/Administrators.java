package com.nph.nphtravel.administration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nph.nphtravel.administration.dialogs.DialogAddCategory;
import com.nph.nphtravel.administration.dialogs.DialogAddTour;
import com.nph.nphtravel.administration.dialogs.DialogAddUser;
import com.nph.nphtravel.administration.dialogs.DialogEditCategory;
import com.nph.nphtravel.administration.dialogs.DialogEditTour;
import com.nph.nphtravel.administration.dialogs.DialogEditUser;
import com.nph.nphtravel.R;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterTour;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterUser;
import com.nph.nphtravel.db.handlers.CategoryDatabaseHandler;
import com.nph.nphtravel.db.handlers.TourDatabaseHandler;
import com.nph.nphtravel.db.handlers.UserDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Category;
import com.nph.nphtravel.db.tableclasses.Tour;
import com.nph.nphtravel.db.tableclasses.User;

import java.util.ArrayList;



public class Administrators extends AppCompatActivity {

    ListView lvTour, lvCategory;

    Button btn_AddTour, btn_addCategory;

    //quản lý tour

    String idTour;
    Tour edit_Tour;
    ArrayList<Tour> dsTour = new ArrayList<Tour>();

    ArrayAdapterTour myArrayAdapterTour;

    //quản lý Category
    String idCategory;

    Category edit_Category;

    ArrayList<Category> dsCategory = new ArrayList<Category>();

    ArrayAdapter myArrayAdapterCategory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrators);


        //Quan ly Tour
        lvTour = (ListView)findViewById(R.id.lvTour);

        TourDatabaseHandler tourDatabaseHandler = new TourDatabaseHandler(this);

        dsTour = tourDatabaseHandler.getAllTour();

        myArrayAdapterTour = new ArrayAdapterTour(this, R.layout.item_list_tour,dsTour);
        lvTour.setAdapter(myArrayAdapterTour);

        btn_AddTour = (Button) findViewById(R.id.btn_addTour);
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

        // quan lý category
        lvCategory =(ListView)findViewById(R.id.lvCategory) ;

        CategoryDatabaseHandler categoryDatabaseHandler = new CategoryDatabaseHandler(this);


        dsCategory = categoryDatabaseHandler.getAllCategory();

        myArrayAdapterCategory = new ArrayAdapter<Category>(Administrators.this, android.R.layout.simple_list_item_1, dsCategory);

        lvCategory.setAdapter(myArrayAdapterCategory);

        //xử lý listView cate
        lvCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi phần tử trong ListView được giữ lâu
                // lấy id category item
                idCategory = dsCategory.get(position).getId();

                // lấy đối tượng category trong listView
                edit_Category = dsCategory.get(position);

                showEditDeleteDialogCategory();
                return true;
            }
        });



        btn_addCategory = (Button) findViewById(R.id.btn_addCategory);
        btn_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialogCategory();
            }
        });

    }


    // quan ly tour
    public void openAddDialogTour(){
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

    public void openEditDiaLogTour(){
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


    // quan ly category
    public void openAddDialogCategory(){
        DialogAddCategory dialogAddCategory = new DialogAddCategory();
        dialogAddCategory.setListener(new DialogAddCategory.AddCategoryDialogListener() {
            @Override
            public void onCategoryAdded(Category category) {
                dsCategory.add(category);

                myArrayAdapterCategory.notifyDataSetChanged();
            }
        });
        dialogAddCategory.show(getSupportFragmentManager(), "add category dialog");
    }

    private void showEditDeleteDialogCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Tạo đối tượng tạo database
        CategoryDatabaseHandler categoryDatabaseHandler = new CategoryDatabaseHandler(this);


        builder.setTitle("Thao tác")
                .setItems(new CharSequence[]{"Chỉnh sửa", "Xóa"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Thực hiện chỉnh sửa
                                openEditDiaLogCategory();

                                break;
                            case 1:
                                // Thực hiện xóa
                                categoryDatabaseHandler.deleteIDCategory(idCategory);

//                                cập nhật lại listView
                                dsCategory.remove(edit_Category);
                                myArrayAdapterCategory.notifyDataSetChanged();


                                Toast.makeText(Administrators.this, "Xóa thành công ID: " +idCategory, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    public void openEditDiaLogCategory(){
        DialogEditCategory dialogEditCategory = new DialogEditCategory(edit_Category);
        //
        dialogEditCategory.setListener(new DialogEditCategory.EditCategoryDialogListener() {
            @Override
            public void onCategoryEdit(Category category) {
                // Thêm Category vào danh sách
                dsCategory.remove(edit_Category);
                dsCategory.add(category);

                // Tải lại ListView
                myArrayAdapterCategory.notifyDataSetChanged();
            }
        });


        //

        dialogEditCategory.show(getSupportFragmentManager(), "edit category dialog");
    }


}



