package com.nph.nphtravel.administration.dialogs;

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

import com.nph.nphtravel.R;
import com.nph.nphtravel.db.handlers.CategoryDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Category;

public class DialogAddCategory extends AppCompatDialogFragment {
    EditText editCategoryName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog_add_category, null);

        editCategoryName = (EditText)view.findViewById(R.id.edit_name_category);

        builder.setView(view)
                .setTitle("Add Category")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CategoryDatabaseHandler db = new CategoryDatabaseHandler(getContext());
                        String name_category = editCategoryName.getText().toString();

                        Category add_c = new Category(name_category);
                        long newRowId = db.addCategory(add_c);

                        // load lại dữ liệu sau khi add

                        Category load_c = new Category(String.valueOf(newRowId),name_category);
                        listener.onCategoryAdded(load_c);

                        if (newRowId != -1) {
                            Toast.makeText(getContext(), "Tạo thành công. ID" + newRowId, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getContext(), "Tạo thất bại" , Toast.LENGTH_LONG).show();
                        }

                    }
                });

        return builder.create();

    }

    //loadd
    public interface AddCategoryDialogListener {
        void onCategoryAdded(Category category);
    }

    private AddCategoryDialogListener listener;


    // Setter cho listener
    public void setListener(AddCategoryDialogListener listener) {
        this.listener = listener;
    }
}
