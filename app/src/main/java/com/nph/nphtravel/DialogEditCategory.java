package com.nph.nphtravel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogEditCategory extends AppCompatDialogFragment {

    EditText editCategoryName;

    //dữ liệu từ đối tượng User bên administrator
    private Category category;

    public DialogEditCategory(Category category) {
        this.category = category;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog_add_category, null);

        editCategoryName = (EditText) view.findViewById(R.id.edit_name_category);

        // lấy dl đổ lên edit Text
        editCategoryName.setText(category.getCategory_name());

        builder.setView(view)
                .setTitle("Edit category: " + category.getId())
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CategoryDatabaseHandler db = new CategoryDatabaseHandler(getContext());
                        String category_name = editCategoryName.getText().toString();

                        Category c = new Category(category.getId(),category_name);
                        long newRowId = db.edit_Category(c);

                        editCategoryDialogListener.onCategoryEdit(c);

                        if (newRowId != -1) {
                            Toast.makeText(getContext(), "Edit thành công", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Edit thất bại" , Toast.LENGTH_SHORT).show();
                        }



                    }
                });


        return builder.create();
    }

    ///
    public interface EditCategoryDialogListener {
        void onCategoryEdit(Category category);
    }
    private EditCategoryDialogListener editCategoryDialogListener;


    // Setter cho listener
    public void setListener(EditCategoryDialogListener listeneredit) {
        this.editCategoryDialogListener = listeneredit;
    }
}
