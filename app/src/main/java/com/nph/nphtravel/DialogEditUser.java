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

import com.bumptech.glide.Glide;

public class DialogEditUser extends AppCompatDialogFragment {

    EditText editUserName, editPassWord, edit_confirmPassword;
    ImageView img_avatarImageView;
    Button btn_selectAvatarButton;

    // sử lý avatar
    private static final int REQUEST_SELECT_AVATAR = 1;

    private Uri selectedAvatarUri;


    //dữ liệu từ đối tượng User bên administrator
    private User user;

    public DialogEditUser(User user) {
        this.user = user;
    }





    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog_add_user, null);


        editUserName = view.findViewById(R.id.edit_username);
        editPassWord = view.findViewById(R.id.edit_password);
        edit_confirmPassword = view.findViewById(R.id.edit_confirmPassword);

        img_avatarImageView = view.findViewById(R.id.img_avatarImageView);
        btn_selectAvatarButton = view.findViewById(R.id.btn_selectAvatarButton);


        // lấy dl đổ lên edit Text
        editUserName.setText(user.getUsername());
        editPassWord.setText(user.getPassword());
        edit_confirmPassword.setText(user.getPassword());

        //Ảnh
        String imageUriString = user.getAvatar();

        if (imageUriString != null) {
            Glide.with(getActivity())
                    .load(imageUriString)
                    .into(img_avatarImageView);
        }


        // chọn ảnh

        btn_selectAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Intent để chọn hình đại diện
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_AVATAR);
            }

        });



        builder.setView(view)
                .setTitle("Edit User: " + user.getId())
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // xử lý thêm
                        UserDatabaseHandler db = new UserDatabaseHandler(getContext());
                        String username = editUserName.getText().toString();
                        String password = editPassWord.getText().toString();
                        String edit_confirm = edit_confirmPassword.getText().toString();


                        //Ảnh old
                        String imageUriString_old = user.getAvatar();

                        // đường dẫn avatar mới
                        // kiểm tra nếu người dùng không chọn

                        String iUriString;
                        if(selectedAvatarUri == null)
                        {
                            iUriString = imageUriString_old;
                        }
                        else {
                            iUriString = selectedAvatarUri.toString();
                        }



                        if(password.equals(edit_confirm))
                        {
//                            Toast.makeText(getContext(), "đường dẫn avatar: " + iUriString, Toast.LENGTH_SHORT).show();

                            User u = new User(user.getId(),username , password, iUriString);
                            long newRowId = db.edit_User(u);

                            editUserName.setText("");
                            editPassWord.setText("");
                            edit_confirmPassword.setText("");
                            editUserName.requestFocus();

                            // edit load
                            editUserDialogListener.onUserEdit(u);


                            if (newRowId != -1) {
                                Toast.makeText(getContext(), "Edit thành công", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), "Edit thất bại" , Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(getContext(), "Mật khẩu không khớp nhau :)) ", Toast.LENGTH_SHORT).show();
                        }




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


    ///
    public interface EditUserDialogListener {
        void onUserEdit(User user);
    }
    private EditUserDialogListener editUserDialogListener;


    // Setter cho listener
    public void setListener(EditUserDialogListener listeneredit) {
        this.editUserDialogListener = listeneredit;
    }






}
