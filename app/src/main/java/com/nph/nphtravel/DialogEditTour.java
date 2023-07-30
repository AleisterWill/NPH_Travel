package com.nph.nphtravel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DialogEditTour extends AppCompatDialogFragment {

    EditText editTourName, editPrice, editDescription,
            editLocation, editStartDay, editEndDay, editDiscount;

    ImageView imageViewStartDay, imageViewEndDay , img_tourImageView;

    Button btn_selectTourButton;


    //spinner category
    Spinner spinner_Category;

    ArrayList<Category> dsCategory = new ArrayList<Category>();

    ArrayAdapter myArrayAdapterCategory;
    String idCategory;


    // sử lý avatar
    private static final int REQUEST_SELECT_AVATAR = 1;

    private Uri selectedAvatarUri;

    //

    private Tour tour;

    public DialogEditTour(Tour tour) {
        this.tour = tour;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog_add_tour, null);

        // anh xa
        editTourName = (EditText) view.findViewById(R.id.edit_tourName);
        editPrice = (EditText) view.findViewById(R.id.edit_price);
        editDescription = (EditText)view.findViewById(R.id.edit_description);
        editLocation = (EditText) view.findViewById(R.id.edit_location);
        editStartDay = (EditText)view.findViewById(R.id.edit_start_day);
        editEndDay = (EditText)view.findViewById(R.id.edit_end_day);
        editDiscount = (EditText)view.findViewById(R.id.edit_discount);

        imageViewStartDay = (ImageView)view.findViewById(R.id.imageStartDay);
        imageViewEndDay = (ImageView)view.findViewById(R.id.imageEndDay);

        img_tourImageView = (ImageView)view.findViewById(R.id.img_tourImageView);
        btn_selectTourButton =(Button)view.findViewById(R.id.btn_selectTourButton);

        // lấy dl đổ lên edit Text
        editTourName.setText(tour.getTour_name());
        editPrice.setText(String.valueOf(tour.getPrice()));
        editDescription.setText(tour.getDescription());
        editLocation.setText(tour.getLocation());
        editStartDay.setText(tour.getStart_day());
        editEndDay.setText(tour.getEnd_day());
        editDiscount.setText(String.valueOf(tour.getDiscount()));


        // spinner Category

        spinner_Category =(Spinner)view.findViewById(R.id.spinnerCategory);

        CategoryDatabaseHandler categoryDatabaseHandler = new CategoryDatabaseHandler(getContext());

        dsCategory = categoryDatabaseHandler.getAllCategory();

        myArrayAdapterCategory = new ArrayAdapter<Category>(getContext(), android.R.layout.simple_list_item_1, dsCategory);

        spinner_Category.setAdapter(myArrayAdapterCategory);

        spinner_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                 lấy id category item
                idCategory = dsCategory.get(position).getId();
                Log.i("categorry id :", "là : " + idCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Ảnh
        String imageUriString_old = tour.getAvatar();

        Log.i("image", " la: " +imageUriString_old);

        if (imageUriString_old != null) {
            Glide.with(getActivity())
                    .load(imageUriString_old)
                    .into(img_tourImageView);
        }


        // chọn ảnh

        btn_selectTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Intent để chọn hình đại diện
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_AVATAR);
            }

        });

        spinner_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                 lấy id category item
                idCategory = dsCategory.get(position).getId();
                Log.i("categorry id :", "là : " + idCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view)
                .setTitle("Edit tour: ")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TourDatabaseHandler tourDatabaseHandler = new TourDatabaseHandler(getContext());

                        String tour_name = editTourName.getText().toString();
                        String price = editPrice.getText().toString();
                        String description = editDescription.getText().toString();
                        String location = editLocation.getText().toString();
                        String start_day = editStartDay.getText().toString();
                        String end_day = editEndDay.getText().toString();
                        String discount = editDiscount.getText().toString();

                        Log.i("tour_name", "la : " + tour_name);


                        //Ảnh old
                        String imageUriString_old = tour.getAvatar();

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

                        Tour edit_t = new Tour(tour.getId() ,tour_name , Double.parseDouble(price), description, location,
                                start_day,end_day, Double.parseDouble(discount),iUriString, idCategory);

                        // load
                        editTourDialogListener.onTourEdit(edit_t);


                        long newRowId = tourDatabaseHandler.edit_Tour(edit_t);

                        if (newRowId != -1) {
                            Toast.makeText(getContext(), "Edit tour thành công", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getContext(), "Edit tour thất bại" , Toast.LENGTH_LONG).show();
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
            img_tourImageView.setImageURI(selectedAvatarUri);
        }
    }


    //loadd
    ///
    public interface EditTourDialogListener {
        void onTourEdit(Tour tour);
    }
    private EditTourDialogListener editTourDialogListener;


    // Setter cho listener
    public void setListener(EditTourDialogListener listeneredit) {
        this.editTourDialogListener = listeneredit;
    }


}
