package com.nph.nphtravel.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.nph.nphtravel.R;
import com.nph.nphtravel.db.arrayadapters.ArrayAdapterReceipts;
import com.nph.nphtravel.db.handlers.ReceiptDatabaseHandler;
import com.nph.nphtravel.db.tableclasses.Receipt;

import java.util.ArrayList;
import java.util.List;

public class MyReceipts extends AppCompatActivity {

    ListView lvMyReceipts;
    ArrayAdapterReceipts aaReceipt;

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_receipts);

        //get currentUser
        Bundle currentUser = getIntent().getExtras().getBundle("currentUser");

        lvMyReceipts = findViewById(R.id.lvMyReceipts);
        ReceiptDatabaseHandler rdb = new ReceiptDatabaseHandler(this);
        ArrayList<Receipt> listReceipts = rdb.getByUserId(Integer.parseInt(currentUser.getString("id")));
        aaReceipt = new ArrayAdapterReceipts(this, R.layout.item_my_receipts, listReceipts);
        lvMyReceipts.setAdapter(aaReceipt);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}