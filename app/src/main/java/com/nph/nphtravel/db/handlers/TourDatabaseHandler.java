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
//        dbHelper.onUpgrade(db, 1, 2);
//        db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TEN_BANG_COMMENT);
//        db.execSQL(DBHelper.CREATE_TABLE_COMMENT);
//        db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TEN_BANG_CATEGORY);
//        db.execSQL(DBHelper.CREATE_TABLE_CATEGORY);
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


    public ArrayList<Tour> getPaginatedTours(int page, int max) {
        ArrayList<Tour> listTour = new ArrayList<>();
        Cursor c = db.query(DBHelper.TEN_BANG_TOUR, null, null, null,
                null, null, null, String.format("%s, %s", (page-1)*max, max));

        if (c.moveToFirst()) {
            do {
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

                listTour.add(t);
            } while (c.moveToNext());

        }

        return listTour;
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

    public Tour getById(String id) {
        Cursor c = db.query(
                DBHelper.TEN_BANG_TOUR,
                null,
                DBHelper.COT_ID + " = " + id,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            int idColIdx = c.getColumnIndex(DBHelper.COT_ID);
            int nameColIdx = c.getColumnIndex(DBHelper.COT_NAME_TOUR);
            int descColIdx = c.getColumnIndex(DBHelper.COT_DESCRIPTION_TOUR);
            int routeColIdx = c.getColumnIndex(DBHelper.COT_LOCATION_TOUR);
            int dateColIdx = c.getColumnIndex(DBHelper.COT_START_DAY_TOUR);
            int priceColIdx = c.getColumnIndex(DBHelper.COT_PRICE_TOUR);
            int imgColIdx = c.getColumnIndex(DBHelper.COT_AVATAR);
            int cateColIdx = c.getColumnIndex(DBHelper.COT_TOUR_CATEGORY_ID);


            Tour tour = new Tour();
            tour.setId(c.getString(idColIdx));
            tour.setTour_name(c.getString(nameColIdx));
            tour.setDescription(c.getString(descColIdx));
            tour.setLocation(c.getString(routeColIdx));
            tour.setStart_day(c.getString(dateColIdx));
            tour.setPrice(Double.parseDouble(c.getString(priceColIdx)));
            tour.setAvatar(c.getString(imgColIdx));
            tour.setCategory_id(c.getString(cateColIdx));

            c.close();
            return tour;
        }

        c.close();
        return null;
    }

    public ArrayList<Tour> filterByCateId(String kw, String date, String cateId) {
        ArrayList<Tour> result = new ArrayList<>();

        String projection = DBHelper.COT_TOUR_CATEGORY_ID + " = " + cateId;

        if (!kw.isEmpty())
            projection += " AND ("
                    + String.format("%s LIKE '%%%s%%'", DBHelper.COT_NAME_TOUR, kw) + " OR "
                    + String.format("%s LIKE '%%%s%%'", DBHelper.COT_LOCATION_TOUR, kw)
                    + ")"
            ;

        if (!date.isEmpty())
            projection += " AND "
                    + DBHelper.COT_START_DAY_TOUR + ">=" + date
            ;

        Cursor c = db.query(
                DBHelper.TEN_BANG_TOUR,
                null,
                projection,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                int idColIdx = c.getColumnIndex(DBHelper.COT_ID);
                int nameColIdx = c.getColumnIndex(DBHelper.COT_NAME_TOUR);
                int descColIdx = c.getColumnIndex(DBHelper.COT_DESCRIPTION_TOUR);
                int routeColIdx = c.getColumnIndex(DBHelper.COT_LOCATION_TOUR);
                int dateColIdx = c.getColumnIndex(DBHelper.COT_START_DAY_TOUR);
                int priceColIdx = c.getColumnIndex(DBHelper.COT_PRICE_TOUR);
                int imgColIdx = c.getColumnIndex(DBHelper.COT_AVATAR);
                int cateColIdx = c.getColumnIndex(DBHelper.COT_TOUR_CATEGORY_ID);


                Tour tour = new Tour();
                tour.setId(c.getString(idColIdx));
                tour.setTour_name(c.getString(nameColIdx));
                tour.setDescription(c.getString(descColIdx));
                tour.setLocation(c.getString(routeColIdx));
                tour.setStart_day(c.getString(dateColIdx));
                tour.setPrice(Double.parseDouble(c.getString(priceColIdx)));
                tour.setAvatar(c.getString(imgColIdx));
                tour.setCategory_id(c.getString(cateColIdx));

                result.add(tour);
            } while(c.moveToNext());
        }

        c.close();
        return result;
    }

}
