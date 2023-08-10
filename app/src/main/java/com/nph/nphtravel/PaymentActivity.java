package com.nph.nphtravel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nph.nphtravel.R;
import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.handlers.BookingDatabaseHandler;
import com.nph.nphtravel.db.handlers.ReceiptDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Booking;
import com.nph.nphtravel.db.tableclasses.Receipt;
import com.nph.nphtravel.zalopay.Api.CreateOrder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {

    Booking booking;

    EditText etFullName, etContact, etAddress, txtAmount;
    TextView tvOrdID, tvOrdName, tvOrdPrice, tvTotal;
    Button btnCreateOrder, btnPayMoMo;

    BookingDatabaseHandler bookingDatabaseHandler;
    ReceiptDatabaseHandler receiptDatabaseHandler;

    //MOMO vars
    int environment = 0;//developer default
    String merchantName = "Symphony!";
    String merchantCode = "MOMOL5XA20220707";
    String merchantNameLabel = "Test";
    String description = "Booking Tour";
    String fee = "0";
    String amount = "10000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        //Bind views
        bindViews();

        //Map bundle to views
        Bundle orderInfo = getIntent().getExtras().getBundle("orderInfo");
        tvOrdID.setText(orderInfo.getString("ordId"));
        tvOrdName.setText(orderInfo.getString("ordName"));
        tvOrdPrice.setText(orderInfo.getString("ordPrice"));
        tvTotal.setText(calTotal().toString());

        txtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvTotal.setText(calTotal().toString());
            }
        });


        //MOMO
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        receiptDatabaseHandler = new ReceiptDatabaseHandler(this);
        bookingDatabaseHandler = new BookingDatabaseHandler(this);
        btnPayMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle currentUser = getIntent().getExtras().getBundle("currentUser");

                booking = new Booking(true);
                //Tao don hang
                booking.setStatus("Chờ thanh toán");
                booking.setTourId(Integer.parseInt(tvOrdID.getText().toString()));
                booking.setTourPrice(Double.parseDouble(tvOrdPrice.getText().toString()));
                booking.setQuantity(Integer.parseInt(txtAmount.getText().toString()));
                booking.setTotal(Double.parseDouble(tvTotal.getText().toString()));
                booking.setUserId(Integer.parseInt(currentUser.getString("id")));

                bookingDatabaseHandler.addBooking(booking);
                requestPayment();
            }
        });
    }


    //Get token through MoMo app
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", (int) booking.getTotal()); //Kiểu integer
        eventValue.put("orderId", booking.getId()); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ NPHTravel");//gán nhãn
        eventValue.put("fee", fee); //Kiểu integer
        eventValue.put("description", String.format("Thanh toán cho đơn hàng %s", booking.getId())); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);

        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }


    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {

                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order

                        // Update Booking's status on on success
                        bookingDatabaseHandler.updateById(
                                booking.getId(),
                                DBHelper.COT_STATUS_BOOKING,
                                "Thanh toán thành công");

                        // Create Receipt for that Booking
                        Receipt receipt = new Receipt(true);
                        receipt.setFullName(etFullName.getText().toString());
                        receipt.setPhoneNumber(etContact.getText().toString());
                        receipt.setAddress(etAddress.getText().toString());
                        receipt.setPayMethod("MoMoPay");
                        receipt.setTransactionToken(token);
                        receipt.setBookingId(booking.getId());

                        // Add Receipt to DB
                        receiptDatabaseHandler.addReceipt(receipt);

                        // Alert Dialog
                        AlertDialog builder = new AlertDialog.Builder(this)
                                .setTitle("Thông báo")
                                .setMessage("Thanh toán thành công!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        finish();
                                    }
                                })
                                .create();
                        builder.show();
                    }
                } else {
                    //TOKEN FAIL
                    bookingDatabaseHandler.updateById(
                            booking.getId(),
                            DBHelper.COT_STATUS_BOOKING,
                            "Thanh toán thất bại");

                    AlertDialog builder = new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Thanh toán thất bại!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    finish();
                                }
                            })
                            .create();
                    builder.show();
                }
            }
        } else {
            AlertDialog builder = new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Quá trình tạo đơn bị lỗi. Thử lại sau!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            finish();
                        }
                    })
                    .create();
            builder.show();
        }
    }

    private Double calTotal() {
        try {
            return 1.0 *
                    Double.valueOf(tvOrdPrice.getText().toString()) *
                    Double.valueOf(txtAmount.getText().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }

    private void bindViews() {
        etFullName = findViewById(R.id.etFullName);
        etContact = findViewById(R.id.etContact);
        etAddress = findViewById(R.id.etAddress);
        txtAmount = findViewById(R.id.etQuantity);

        tvOrdID = findViewById(R.id.tvOrdID);
        tvOrdName = findViewById(R.id.tvOrdName);
        tvOrdPrice = findViewById(R.id.tvOrdPrice);
        tvTotal = findViewById(R.id.tvTotal);

        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        btnPayMoMo = findViewById(R.id.btnPayMoMo);
    }
}