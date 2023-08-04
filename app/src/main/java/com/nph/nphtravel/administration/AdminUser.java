package com.nph.nphtravel.administration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nph.nphtravel.R;
import com.nph.nphtravel.administration.dialogs.DialogAddUser;
import com.nph.nphtravel.administration.dialogs.DialogEditUser;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterUser;
import com.nph.nphtravel.db.handlers.UserDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.User;

import java.util.ArrayList;

public class AdminUser extends AppCompatActivity {

    ListView lvUsers;



    //add User
    FloatingActionButton btn_AddUser;

    //chuan bi du lieu cho listview
    ArrayList<User> ds = new ArrayList<User>();

    ArrayAdapterUser myArrayAdapterUser;

    // lấy dl id user của listview
    String idUser;

    //edit User
    User edit_User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        lvUsers = (ListView) findViewById(R.id.lvUser);

        // Tạo đối tượng tạo database
        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(this);

        //add sl vao
        ds = userDatabaseHandler.getAllUser();
        // set adater

        myArrayAdapterUser = new ArrayAdapterUser(this, R.layout.item_list_user,ds);
        lvUsers.setAdapter(myArrayAdapterUser);

        // Add User
        btn_AddUser = findViewById(R.id.btn_addUser);

        btn_AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDiaLog();
            }
        });

        //xử lý listView
        lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi phần tử trong ListView được giữ lâu
                // lấy id username item
                idUser = ds.get(position).getId();

                // lấy đối tượng user trong listView
                edit_User = ds.get(position);

                showEditDeleteDialog();
                return true;
            }
        });
    }

    // xử lý add dialog
    public void openAddDiaLog(){
        DialogAddUser dialogAddUser = new DialogAddUser();
        // load lại dữ liệu sau khi add
        dialogAddUser.setListener(new DialogAddUser.AddUserDialogListener() {
            @Override
            public void onUserAdded(User user) {
                // Thêm User vào danh sách
                ds.add(user);

                // Tải lại ListView
                myArrayAdapterUser.notifyDataSetChanged();
            }

        });
        dialogAddUser.show(getSupportFragmentManager(), "add_user_dialog");

    }

    private void showEditDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Tạo đối tượng tạo database
        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(this);


        builder.setTitle("Thao tác")
                .setItems(new CharSequence[]{"Chỉnh sửa", "Xóa"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Thực hiện chỉnh sửa
                                //1
                                openEditDiaLog();


                                break;
                            case 1:
                                // Thực hiện xóa
                                userDatabaseHandler.deleteIDUser(idUser);

//                                cập nhật lại listView
                                ds.remove(edit_User);
                                myArrayAdapterUser.notifyDataSetChanged();




                                Toast.makeText(AdminUser.this, "Xóa thành công ID: " +idUser, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }


    // goi dialog edit
    public void openEditDiaLog(){
        DialogEditUser dialogEditUser = new DialogEditUser(edit_User);

        //
        dialogEditUser.setListener(new DialogEditUser.EditUserDialogListener() {
            @Override
            public void onUserEdit(User user) {

                // Thêm User vào danh sách
                ds.remove(edit_User);
                ds.add(user);

                // Tải lại ListView
                myArrayAdapterUser.notifyDataSetChanged();
            }

        });
        //

        dialogEditUser.show(getSupportFragmentManager(), "edit user dialog");
    }
}