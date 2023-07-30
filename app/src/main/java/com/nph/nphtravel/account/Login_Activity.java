package com.nph.nphtravel.account;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nph.nphtravel.MainActivity;
import com.nph.nphtravel.R;
import com.nph.nphtravel.administration.Administrators;
import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.handlers.UserDatabaseHandler;

public class Login_Activity extends AppCompatActivity {

    TextView signupText;

    //Login
    EditText editUserName, editPassword;
    Button btnLogin;

    SQLiteDatabase database;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupText = (TextView) findViewById(R.id.signupText);

        // ánh xạ Login
        editUserName = (EditText) findViewById(R.id.inputUsername);
        editPassword = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button)findViewById(R.id.loginButton);


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent1);
            }
        });



        // xử lý login :))
        UserDatabaseHandler databaseHandler = new UserDatabaseHandler(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // lấy dl login
                String username = editUserName.getText().toString();
                String pass = editPassword.getText().toString();

                // Kiểm tra thông tin đăng nhập
                int checkrole = databaseHandler.checkLogin(username, pass);

                if (checkrole == 0) {
                    // Đăng nhập user
                    Intent intent2 = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(intent2);
                } else if (checkrole == 1) {
                    // Đăng nhập Admin
                    Intent intent3 = new Intent(Login_Activity.this, Administrators.class);
                    startActivity(intent3);
                    Toast.makeText(Login_Activity.this, "Đăng nhập admin", Toast.LENGTH_SHORT).show();
                }else {
                    // Đăng nhập thất bại, thông báo người dùng
                    Toast.makeText(Login_Activity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}