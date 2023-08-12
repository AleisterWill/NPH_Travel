package com.nph.nphtravel.db.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.tableclasses.Receipt;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Receipt> getByUserId(int userId) {
        ArrayList<Receipt> result = new ArrayList<>();
        Cursor c = db.query(
                String.format("%s a, %s b",
                        DBHelper.TEN_BANG_RECEIPT, DBHelper.TEN_BANG_BOOKING),
                new String[]{"a.*"},
                String.format("a.%s = b.%s AND b.%s = %s",
                        DBHelper.COT_BOOKING_ID, DBHelper.COT_ID,
                        DBHelper.COT_BOOKING_USER_ID, userId),
                null,
                null, null,
                String.format("a.%s DESC", DBHelper.COT_CREATED_DATE)
        );

        if (c.moveToFirst()) {
            int colIdxId = c.getColumnIndex(DBHelper.COT_ID);
            int colIdxCD = c.getColumnIndex(DBHelper.COT_CREATED_DATE);
            int colIdxPM = c.getColumnIndex(DBHelper.COT_PAY_METHOD);
            int colIdxTT = c.getColumnIndex(DBHelper.COT_TRANSACTION_TOKEN);
            int colIdxBId = c.getColumnIndex(DBHelper.COT_BOOKING_ID);


            do {
                Receipt receipt = new Receipt();
                receipt.setId(c.getInt(colIdxId));
                receipt.setCreatedDate(c.getString(colIdxCD));
                receipt.setPayMethod(c.getString(colIdxPM));
                receipt.setTransactionToken(c.getString(colIdxTT));
                receipt.setBookingId(c.getInt(colIdxBId));

                result.add(receipt);
            } while (c.moveToNext());
        }

        c.close();
        return result;
    }
}
