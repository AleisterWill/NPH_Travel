package com.nph.nphtravel.db.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.tableclasses.Booking;

public class BookingDatabaseHandler {
    SQLiteDatabase db;

    DBHelper dbHelper;

    public BookingDatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public long addBooking(Booking booking) {
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.COT_ID, booking.getId());
        cv.put(DBHelper.COT_DAY_BOOKING, booking.getCreated_date());
        cv.put(DBHelper.COT_STATUS_BOOKING, booking.getStatus());
        cv.put(DBHelper.COT_SO_LUONG, booking.getQuantity());
        cv.put(DBHelper.COT_PRICE_TOUR, booking.getTourPrice());
        cv.put(DBHelper.COT_TONG_TIEN, booking.getTotal());
        cv.put(DBHelper.COT_BOOKING_TOUR_ID, booking.getTourId());
        cv.put(DBHelper.COT_BOOKING_USER_ID, booking.getUserId());

        long result = dbHelper.getWritableDatabase().insert(DBHelper.TEN_BANG_BOOKING, null, cv);

        return result;
    }


    public Booking getById(int id) {
        Booking result = null;

        Cursor c = db.query(
                DBHelper.TEN_BANG_BOOKING,
                null, // all cols
                "WHERE id=?",
                new String[]{"1"},
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            result = new Booking();
            int idColIndx = c.getColumnIndex(DBHelper.COT_ID);
            int createdDateColIndx = c.getColumnIndex(DBHelper.COT_DAY_BOOKING);
            int statusColIndx = c.getColumnIndex(DBHelper.COT_STATUS_BOOKING);
            int priceColIndx = c.getColumnIndex(DBHelper.COT_PRICE_TOUR);
            int quantityColIndx = c.getColumnIndex(DBHelper.COT_SO_LUONG);
            int totalColIndx = c.getColumnIndex(DBHelper.COT_TONG_TIEN);
            int tourIdColIndx = c.getColumnIndex(DBHelper.COT_BOOKING_TOUR_ID);
            int userIdColIndx = c.getColumnIndex(DBHelper.COT_BOOKING_USER_ID);

            try {
                result.setId(Integer.parseInt(c.getString(idColIndx)));
                result.setCreated_date(c.getString(createdDateColIndx));
                result.setStatus(c.getString(statusColIndx));
                result.setTourPrice(c.getDouble(priceColIndx));
                result.setQuantity(c.getInt(quantityColIndx));
                result.setTotal(c.getDouble(totalColIndx));
                result.setTourId(c.getInt(tourIdColIndx));
                result.setUserId(c.getInt(userIdColIndx));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        c.close();

        return result;
    }

    public long updateById(int id, String colName, String data) {
        ContentValues cv = new ContentValues();
        cv.put(colName, data);

        return db.update(
                DBHelper.TEN_BANG_BOOKING,
                cv,
                DBHelper.COT_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
