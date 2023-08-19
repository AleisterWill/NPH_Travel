package com.nph.nphtravel.administration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nph.nphtravel.R;
import com.nph.nphtravel.administration.dialogs.DialogAddCategory;
import com.nph.nphtravel.administration.dialogs.DialogEditCategory;
import com.nph.nphtravel.db.handlers.CategoryDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Category;

import java.util.ArrayList;

public class AdminCategory extends AppCompatActivity {

    ListView lvCategory;
    FloatingActionButton btn_addCategory;

    //quản lý Category
    String idCategory;

    Category edit_Category;

    ArrayList<Category> dsCategory = new ArrayList<Category>();

    ArrayAdapter myArrayAdapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        // quan lý category
        lvCategory =(ListView)findViewById(R.id.lvCategory) ;

        CategoryDatabaseHandler categoryDatabaseHandler = new CategoryDatabaseHandler(this);


        dsCategory = categoryDatabaseHandler.getAllCategory();

        myArrayAdapterCategory = new ArrayAdapter<Category>(AdminCategory.this, android.R.layout.simple_list_item_1, dsCategory);

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



        btn_addCategory = findViewById(R.id.btn_addCategory);
        btn_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialogCategory();
            }
        });

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


                                Toast.makeText(AdminCategory.this, "Xóa thành công ID: " +idCategory, Toast.LENGTH_SHORT).show();
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