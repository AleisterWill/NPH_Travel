package com.nph.nphtravel;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Register_Activity extends AppCompatActivity {

    TextView txtSignin;
    EditText editusername, editpassword, confirmPassword;
    Button registerButton;

    UserDatabaseHandler userDatabaseHandler;

    // sử lý avatar
    private static final int REQUEST_SELECT_AVATAR = 1;

    ImageView avatarImageView;
    Button selectAvatarButton;
    private Uri selectedAvatarUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDatabaseHandler = new UserDatabaseHandler(Register_Activity.this);


        //chuyen login
        txtSignin =(TextView)findViewById(R.id.signinText);

        // đăng ký
        editusername = (EditText) findViewById(R.id.username);
        editpassword = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        registerButton =(Button) findViewById(R.id.registerButton);


        //chuyen login
        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        // sử lý avatar
        avatarImageView = findViewById(R.id.avatarImageView);
        selectAvatarButton = findViewById(R.id.selectAvatarButton);


        selectAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Intent để chọn hình đại diện
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, REQUEST_SELECT_AVATAR);

            }
        });





        // xử lý đăng ký
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editusername.getText().toString();
                String password = editpassword.getText().toString();
                String comfirmPassword = confirmPassword.getText().toString();

//                Cách 1
//                // lấy đường dẫn lưu xuống
//                Uri avatarUri = selectedAvatarUri;
//                // kt đường dẫn và ép kiểu
//                String imageUriString = null;

//                if( avatarUri != null){
//                    imageUriString = avatarUri.toString();
//                }

                //        Cách 2
                String iUriString;
                if(selectedAvatarUri != null)
                {
                    iUriString = selectedAvatarUri.toString();
                }
                else {
                    iUriString = null;
                }

                //dk admin
//                int role =1;

                if(password.equals(comfirmPassword))
                {
                    User u = new User(username , password, iUriString);
                    long newRowId = userDatabaseHandler.addUser(u);

                    editusername.setText("");
                    editpassword.setText("");
                    confirmPassword.setText("");
                    editusername.requestFocus();

                    if (newRowId != -1) {
                        Toast.makeText(Register_Activity.this, "Đăng ký thành công: " + newRowId, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Register_Activity.this, "Đăng ký thất bại" , Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(Register_Activity.this, "Mật khẩu không khớp nhau :)) ", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    //xử lý ảnh đường dẫn gắn vào avatarImageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_AVATAR && resultCode == RESULT_OK && data != null) {
            // Lấy Uri của hình đại diện được chọn
            selectedAvatarUri = data.getData();



            // Hiển thị hình đại diện trong ImageView
            avatarImageView.setImageURI(selectedAvatarUri);
        }
    }
}