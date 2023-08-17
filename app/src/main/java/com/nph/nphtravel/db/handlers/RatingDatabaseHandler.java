package com.nph.nphtravel.db.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.tableclasses.Rating;
import com.nph.nphtravel.db.tableclasses.Tour;

public class RatingDatabaseHandler {
    SQLiteDatabase db;
    DBHelper dbHelper;

    public RatingDatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public double getAvgScoreByTourId(int tourId) {
        double result = 0;

        Cursor c = db.query(
                DBHelper.TEN_BANG_RATING,
                new String[]{"AVG("+DBHelper.COT_SCORE+")"},
                DBHelper.COT_BOOKING_TOUR_ID + " = ?",
                new String[]{String.valueOf(tourId)},
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            result = c.getDouble(0);
        }

        c.close();
        return result;
    }

    public long addRating (Rating rating) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COT_SCORE, rating.getScore());
        cv.put(DBHelper.COT_TOUR_ID, rating.getTourId());
        cv.put(DBHelper.COT_USER_ID, rating.getUserId());
        cv.put(DBHelper.COT_RECEIPT_ID, rating.getReceiptId());

        long result = db.insert(DBHelper.TEN_BANG_RATING, null, cv);

        db.close();
        return result;
    }

    public boolean exists (int userId, int receiptId) {
        Cursor c = db.query(
                DBHelper.TEN_BANG_RATING,
                null,
                String.format("%s = %s AND %s = %s",
                        DBHelper.COT_USER_ID, userId,
                        DBHelper.COT_RECEIPT_ID, receiptId),
                null,
                null,
                null,
                null
        );

        boolean result = c.moveToFirst();
        c.close();

        return result;

    }
}
