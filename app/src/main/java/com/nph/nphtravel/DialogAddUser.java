package com.nph.nphtravel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogAddUser extends AppCompatDialogFragment {

    EditText editUserName, editPassWord, edit_confirmPassword;
    ImageView img_avatarImageView;
    Button btn_selectAvatarButton;

    // sử lý avatar
    private static final int REQUEST_SELECT_AVATAR = 1;

    private Uri selectedAvatarUri;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog_add_user, null);


        builder.setView(view)
                .setTitle("Add User")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            // xử lý thêm
                            UserDatabaseHandler db = new UserDatabaseHandler(getContext());
                            String username = editUserName.getText().toString();
                            String password = editPassWord.getText().toString();
                            String edit_confirm = edit_confirmPassword.getText().toString();

                            // lấy đường dẫn lưu xuống
                            Uri avatarUri = selectedAvatarUri;
                            // kt đường dẫn và ép kiểu
                            String imageUriString = null;

                            if( avatarUri != null){
                                imageUriString = avatarUri.toString();
                            }

                            if(password.equals(edit_confirm))
                            {
                                User add_u = new User(username , password, imageUriString);
                                long newRowId = db.addUser(add_u);

                                editUserName.setText("");
                                editPassWord.setText("");
                                edit_confirmPassword.setText("");
                                editUserName.requestFocus();


                                // load lại dữ liệu sau khi add
                                User load_u = new User(String.valueOf(newRowId), username , password, imageUriString);
                                listener.onUserAdded(load_u);

                                if (newRowId != -1) {
                                    Toast.makeText(getContext(), "Đăng ký thành công. ID" + newRowId, Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getContext(), "Đăng ký thất bại" , Toast.LENGTH_LONG).show();
                                }

                            }
                            else {
                                Toast.makeText(getContext(), "Mật khẩu không khớp nhau :)) ", Toast.LENGTH_LONG).show();
                            }


                    }
                });

        editUserName = view.findViewById(R.id.edit_username);
        editPassWord = view.findViewById(R.id.edit_password);
        edit_confirmPassword = view.findViewById(R.id.edit_confirmPassword);

        img_avatarImageView = view.findViewById(R.id.img_avatarImageView);
        btn_selectAvatarButton = view.findViewById(R.id.btn_selectAvatarButton);

        // chọn avatr
        btn_selectAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Intent để chọn hình đại diện
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_AVATAR);
            }

        });



        return builder.create();
    }


//    hien avatar len imgaView

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_AVATAR && data != null) {
            // Lấy Uri của hình đại diện được chọn
            selectedAvatarUri = data.getData();

            // Hiển thị hình đại diện trong ImageView
            img_avatarImageView.setImageURI(selectedAvatarUri);
        }
    }

    // load lại dữ liệu sau khi add
    // Khai báo interface để giao tiếp với Activity
    public interface AddUserDialogListener {
        void onUserAdded(User user);
    }

    private AddUserDialogListener listener;


    // Setter cho listener
    public void setListener(AddUserDialogListener listener) {
        this.listener = listener;
    }


}

