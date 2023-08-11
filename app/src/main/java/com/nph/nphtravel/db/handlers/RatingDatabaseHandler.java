package com.nph.nphtravel.db.handlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nph.nphtravel.db.DBHelper;
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

        return result;
    }
}
