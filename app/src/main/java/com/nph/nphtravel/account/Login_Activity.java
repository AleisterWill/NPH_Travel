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
import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.handlers.UserDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.User;

import java.io.Serializable;

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
                User user = databaseHandler.checkLogin(username, pass);

                if (user != null) {
                    //Logged in Successfully
                    Intent toHomePage = new Intent(Login_Activity.this, MainActivity.class);

                    //currentUser bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("id", user.getId());
                    bundle.putString("username", user.getUsername());
                    bundle.putString("avatar", user.getAvatar());
                    bundle.putString("role", String.valueOf(user.getRole()));

                    //put currentUser bunble into MainActivity's Extra
                    toHomePage.putExtra("currentUser", bundle);

                    //switch to MainActivity
                    startActivity(toHomePage);


                } else {
                    // Đăng nhập thất bại, thông báo người dùng
                    Toast.makeText(Login_Activity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}