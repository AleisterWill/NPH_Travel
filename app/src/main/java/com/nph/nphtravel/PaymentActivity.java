package com.nph.nphtravel.zalopay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.nph.nphtravel.zalopay.Api.CreateOrder;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {
    EditText etFullName, etContact, etAddress, txtAmount;
    TextView tvOrdID, tvOrdName, tvOrdPrice, tvTotal;
    Button btnCreateOrder, btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Bind views
        bindViews();
        btnPay.setVisibility(View.GONE);

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

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





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
        btnPay = findViewById(R.id.btnPay);
    }
}