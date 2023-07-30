package com.nph.nphtravel.db.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.tableclasses.Tour;

import java.util.ArrayList;

public class TourDatabaseHandler {

    SQLiteDatabase db;
    DBHelper dbHelper;

    public TourDatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addTour(Tour tour){

        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COT_NAME_TOUR, tour.getTour_name());
        values.put(DBHelper.COT_PRICE_TOUR, tour.getPrice());
        values.put(DBHelper.COT_DESCRIPTION_TOUR, tour.getDescription());
        values.put(DBHelper.COT_LOCATION_TOUR, tour.getLocation());
        values.put(DBHelper.COT_START_DAY_TOUR, tour.getStart_day());
        values.put(DBHelper.COT_END_DAY_TOUR, tour.getEnd_day());
        values.put(DBHelper.COT_DISCOUNT_TOUR, tour.getDiscount());
        values.put(DBHelper.COT_AVATAR, tour.getAvatar());

        values.put(DBHelper.COT_TOUR_CATEGORY_ID, tour.getCategory_id());



        long newRowId = db.insert(DBHelper.TEN_BANG_TOUR, null, values);

        db.close();

        return newRowId;
    }


    //Hiển thị dl
    public ArrayList<Tour> getAllTour()
    {
        ArrayList<Tour> ls = new ArrayList<Tour>();
        Cursor c = db.query(DBHelper.TEN_BANG_TOUR, null, null, null,
                null, null, null);
        c.moveToPosition(-1);
        while (c.moveToNext()) {

            Tour t = new Tour();

            t.setId(c.getString(0));
            t.setTour_name(c.getString(1));
            t.setPrice(Double.parseDouble(c.getString(2)));
            t.setAvatar(c.getString(7));
            t.setLocation(c.getString(4));

            //them
            t.setDescription(c.getString(3));
            t.setStart_day(c.getString(5));
            t.setEnd_day(c.getString(6));
            t.setDiscount(Double.parseDouble(c.getString(8)));
            t.setCategory_id(c.getString(9));

            ls.add(t);
        }
        c.close(); //dongs
        return ls;
    }

    //edit a Tour row
    public long edit_Tour(Tour tour) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COT_NAME_TOUR, tour.getTour_name());
        values.put(DBHelper.COT_PRICE_TOUR, tour.getPrice());
        values.put(DBHelper.COT_DESCRIPTION_TOUR, tour.getDescription());
        values.put(DBHelper.COT_LOCATION_TOUR, tour.getLocation());
        values.put(DBHelper.COT_START_DAY_TOUR, tour.getStart_day());
        values.put(DBHelper.COT_END_DAY_TOUR, tour.getEnd_day());
        values.put(DBHelper.COT_DISCOUNT_TOUR, tour.getDiscount());
        values.put(DBHelper.COT_AVATAR, tour.getAvatar());
        values.put(DBHelper.COT_TOUR_CATEGORY_ID, tour.getCategory_id());

        return dbHelper.getWritableDatabase().update(DBHelper.TEN_BANG_TOUR, values,
                DBHelper.COT_ID + " = "
                        + tour.getId(), null);
    }

    // phương thức xoá User sử dụng ID userName
    public int deleteIDTour(String id)
    {
        String where="_id=?";
        int numberOFEntriesDeleted= dbHelper.getWritableDatabase().delete(DBHelper.TEN_BANG_TOUR, where, new String[]{id}) ;
        return numberOFEntriesDeleted;
    }

}
