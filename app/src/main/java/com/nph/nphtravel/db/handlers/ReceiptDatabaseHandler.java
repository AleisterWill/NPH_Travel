package com.nph.nphtravel.db.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.tableclasses.Receipt;

public class ReceiptDatabaseHandler {
    SQLiteDatabase db;

    DBHelper dbHelper;

    public ReceiptDatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public long addReceipt(Receipt receipt) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COT_FULLNAME, receipt.getFullName());
        cv.put(DBHelper.COT_PHONENUMBER, receipt.getPhoneNumber());
        cv.put(DBHelper.COT_ADDRESS, receipt.getAddress());
        cv.put(DBHelper.COT_CREATED_DATE, receipt.getCreatedDate());
        cv.put(DBHelper.COT_PAY_METHOD, receipt.getPayMethod());
        cv.put(DBHelper.COT_TRANSACTION_TOKEN, receipt.getTransactionToken());
        cv.put(DBHelper.COT_BOOKING_ID, receipt.getBookingId());

        long result = db.insert(
                DBHelper.TEN_BANG_RECEIPT,
                null,
                cv);

        return result;
    }
}
