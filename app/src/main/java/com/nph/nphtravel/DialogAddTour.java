package com.nph.nphtravel;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;


public class DialogAddTour extends AppCompatDialogFragment {

    EditText editTourName, editPrice, editDescription,
            editLocation, editStartDay, editEndDay, editDiscount;

    ImageView imageViewStartDay, imageViewEndDay , img_tourImageView;

    Button btn_selectTourButton;

    //ngày
    private int year, month, day;


    // sử lý avatar
    private static final int REQUEST_SELECT_AVATAR = 1;

    private Uri selectedAvatarUri;

    //spinner category
    Spinner spinner_Category;

    ArrayList<Category> dsCategory = new ArrayList<Category>();

    ArrayAdapter myArrayAdapterCategory;

    String idCategory;


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


        //ngay
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        imageViewStartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        imageViewEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogEnd();
            }
        });

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




        builder.setView(view)
                .setTitle("Add Tour")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // xử lý thêm
                        TourDatabaseHandler db = new TourDatabaseHandler(getContext());
                        String tourname = editTourName.getText().toString();
                        String price = editPrice.getText().toString();
                        String description = editDescription.getText().toString();
                        String location = editLocation.getText().toString();
                        String startday = editStartDay.getText().toString();
                        String endday = editEndDay.getText().toString();
                        String discount = editDiscount.getText().toString();



                        // lấy đường dẫn lưu xuống
                        Uri avatarUri = selectedAvatarUri;
                        // kt đường dẫn và ép kiểu
                        String imageUriString = null;

                        if( avatarUri != null){
                            imageUriString = avatarUri.toString();
                        }


                            Tour add_t = new Tour(tourname , Double.parseDouble(price), description, location,
                                    startday,endday, Double.parseDouble(discount),imageUriString, idCategory);
                            long newRowId = db.addTour(add_t);



                            //loadd
                            Tour add_load = new Tour(String.valueOf(newRowId), tourname , Double.parseDouble(price), description, location,
                                    startday,endday, Double.parseDouble(discount),imageUriString, idCategory);

                            listener_t.onTourAdded(add_load);




                            if (newRowId != -1) {
                                Toast.makeText(getContext(), "Tạo tour thành công. ID" + newRowId, Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getContext(), "Tạo tour thất bại" , Toast.LENGTH_LONG).show();
                            }




                    }
                });

        // chọn avatr
        btn_selectTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Intent để chọn hình đại diện
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_AVATAR);
            }

        });



        return builder.create();
    }


    //ngày bắt đầu
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                this::onDateSet,
                year, month, day
        );
        datePickerDialog.show();
    }
    private void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Lưu lại ngày, tháng và năm đã chọn
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        // Cập nhật TextView với ngày đã chọn
        updateStartDay();
    }
    private void updateStartDay() {
        String selectedDate = day + "/" + (month + 1) + "/" + year;
        editStartDay.setText(selectedDate);
    }

    //ngày kết thúc
    private void showDatePickerDialogEnd() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                this::onDateSetEnd,
                year, month, day
        );
        datePickerDialog.show();
    }
    private void onDateSetEnd(DatePicker view, int year, int month, int dayOfMonth) {
        // Lưu lại ngày, tháng và năm đã chọn
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        // Cập nhật TextView với ngày đã chọn
        updateStartDayEnd();
    }
    private void updateStartDayEnd() {
        String selectedDate = day + "/" + (month + 1) + "/" + year;
        editEndDay.setText(selectedDate);
    }



    // avatar
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


    // load lại dữ liệu sau khi add
    // Khai báo interface để giao tiếp với Activity
    public interface AddTourDialogListener {
        void onTourAdded(Tour tour);
    }

    private AddTourDialogListener listener_t;


    // Setter cho listener
    public void setListenerTour(AddTourDialogListener listener) {
        this.listener_t = listener;
    }



}
