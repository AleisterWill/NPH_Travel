package com.nph.nphtravel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String TEN_DATABASE = "QuanLyTour.db";
    // Tên bảng
    public static final String TEN_BANG_USER = "User";
    // Bảng gồm 5 cột
    public static final String COT_ID = "_id";
    public static final String COT_USERNAME = "_username";
    public static final String COT_PASSWORD = "_password";
    public static final String COT_AVATAR = "_avatar";
    public static final String COT_USER_ROLE = "_user_role";

    private static final String CREATE_TABLE_USERS = ""
            + "create table " + TEN_BANG_USER + " ( "
            + COT_ID + " integer primary key autoincrement ,"
            + COT_USERNAME + " text not null, "
            + COT_PASSWORD + " text not null, "
            + COT_AVATAR + " text, "
            + COT_USER_ROLE + " integer default '0');";


    public static final String TEN_BANG_CATEGORY = "Category";
    public static final String COT_NAME_CATEGORY = "_name_category";

//    private static final String CREATE_TABLE_CATEGORY = ""
//            + " create table " + TEN_BANG_CATEGORY + " ( "
//            + COT_ID + " integer primary key autoincrement,"
//            + COT_NAME_CATEGORY + " text);";
    private static final String CREATE_TABLE_CATEGORY = String.format(
            "CREATE TABLE %s(%s, %s, %s);",
        TEN_BANG_CATEGORY,
        COT_ID + " integer primary key autoincrement",
        COT_NAME_CATEGORY + "text"
    );


    public static final String TEN_BANG_TOUR = "Tour";

    public static final String COT_NAME_TOUR = "_name_tour";
    public static final String COT_PRICE_TOUR = "_price_tour";
    public static final String COT_DESCRIPTION_TOUR = "_description_tour";
    public static final String COT_LOCATION_TOUR = "_location_tour";
    public static final String COT_START_DAY_TOUR = "_start_day_tour";
    public static final String COT_END_DAY_TOUR = "_end_day_tour";
    public static final String COT_DISCOUNT_TOUR = "_discount_tour";
    public static final String COT_TOUR_CATEGORY_ID = "_category_id";


    private static final String CREATE_TABLE_TOUR = String.format(
            "CREATE TABLE %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", // 1 table name & 11 columns
            TEN_BANG_TOUR,
            String.format("%s %s", COT_ID, "integer primary key autoincrement"),
            String.format("%s %s", COT_NAME_TOUR, "text"),
            String.format("%s %s", COT_PRICE_TOUR, "double default '0'"),
            String.format("%s %s", COT_DESCRIPTION_TOUR, "text"),
            String.format("%s %s", COT_LOCATION_TOUR, "text"),
            String.format("%s %s", COT_START_DAY_TOUR, "date"),
            String.format("%s %s", COT_END_DAY_TOUR, "date"),
            String.format("%s %s", COT_AVATAR, "text"),
            String.format("%s %s", COT_DISCOUNT_TOUR, "double default '0'"),
            String.format("%s %s, FOREIGN KEY(%s) REFERENCES %s(%s)",
                    COT_TOUR_CATEGORY_ID, "integer", //col_name & data type
                    COT_TOUR_CATEGORY_ID, TEN_BANG_CATEGORY, COT_ID //col that is FK to tablename(col_name)
            )
    );



    public static final String TEN_BANG_BOOKING = "Booking";
    public static final String COT_DAY_BOOKING = "_day_booking";
    public static final String COT_STATUS_BOOKING = "_status_booking";
    public static final String COT_BOOKING_USER_ID = "_user_id";
    public static final String COT_BOOKING_TOUR_ID = "_tour_id";

    private static final String CREATE_TABLE_BOOKING = ""
            + " create table " + TEN_BANG_BOOKING + " ( "
            + COT_ID + " integer primary key autoincrement ,"
            + COT_DAY_BOOKING + " text,"
            + COT_STATUS_BOOKING + " text,"
            + COT_BOOKING_USER_ID + " integer, "
            + COT_BOOKING_TOUR_ID + " integer,"
            + "FOREIGN KEY(" + COT_BOOKING_USER_ID + ") REFERENCES " + TEN_BANG_USER + "(" + COT_ID + "),"
            + "FOREIGN KEY(" + COT_BOOKING_TOUR_ID + ") REFERENCES " + TEN_BANG_TOUR + "(" + COT_ID + ")"
            + ")";


    public static final String TEN_BANG_TOUR_DETAIL = "Tour_detail";
    public static final String COT_TOUR_DETAIL_HIGHLIGHT = "_highlight";
    public static final String COT_TOUR_DETAIL_SERVICE_REGULATION = "_service_regulation";
    public static final String COT_TOUR_DETAIL_TOUR_ID = "_tour_id";

    private static final String CREATE_TABLE_TOUR_DETAIL = ""
            + " create table " + TEN_BANG_TOUR_DETAIL + "("
            + COT_ID + " integer primary key autoincrement ,"
            + COT_TOUR_DETAIL_HIGHLIGHT + " text , "
            + COT_TOUR_DETAIL_SERVICE_REGULATION + " text,"
            + COT_TOUR_DETAIL_TOUR_ID + " integer,"
            + "FOREIGN KEY(" + COT_TOUR_DETAIL_TOUR_ID + ") REFERENCES " + TEN_BANG_TOUR + "(" + COT_ID + ")"
            + ")";


    public static final String TEN_BANG_SCHEDULE = "Schedule";
    public static final String COT_SCHEDULE_CONTENT = "_content_schedule";
    public static final String COT_SCHEDULE_TOUR_DETAIL_ID = "_tour_detail_id";

    private static final String CREATE_TABLE_SCHEDULE = ""
            + " create table " + TEN_BANG_SCHEDULE + "("
            + COT_ID + " integer primary key autoincrement ,"
            + COT_SCHEDULE_CONTENT + " text , "
            + COT_SCHEDULE_TOUR_DETAIL_ID + " integer,"
            + "FOREIGN KEY(" + COT_SCHEDULE_TOUR_DETAIL_ID + ") REFERENCES " + TEN_BANG_TOUR_DETAIL + "(" + COT_ID + ")"
            + ")";

    public static final String TEN_BANG_REVIEW = "Review";
    public static final String COT_REVIEW_RATING = "_rating_review";
    public static final String COT_REVIEW_COMMENT = "_comment_review";
    public static final String COT_REVIEW_USER_ID = "_user_id";
    public static final String COT_REVIEW_TOUR_DETAIL_ID = "_tour_detail_id";

    private static final String CREATE_TABLE_REVIEW = ""
            + " create table " + TEN_BANG_REVIEW + "("
            + COT_ID + " integer primary key autoincrement ,"
            + COT_REVIEW_RATING + " integer , "
            + COT_REVIEW_COMMENT + " text , "
            + COT_REVIEW_USER_ID + " integer,"
            + COT_REVIEW_TOUR_DETAIL_ID + " integer,"
            + "FOREIGN KEY(" + COT_REVIEW_USER_ID + ") REFERENCES " + TEN_BANG_USER + "(" + COT_ID + "),"
            + "FOREIGN KEY(" + COT_REVIEW_TOUR_DETAIL_ID + ") REFERENCES " + TEN_BANG_TOUR_DETAIL + "(" + COT_ID + ")"
            + ")";



    public DBHelper(@Nullable Context context) {
        super(context, TEN_DATABASE, null, 3);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create all the tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TOUR);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_BOOKING);
        db.execSQL(CREATE_TABLE_TOUR_DETAIL);
        db.execSQL(CREATE_TABLE_SCHEDULE);
        db.execSQL(CREATE_TABLE_REVIEW);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_TOUR);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_BOOKING);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_TOUR_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_REVIEW);
//        if (oldVersion < 1) {
//            onCreate(db);
//        }
        //Tiến hành tạo bảng mới
        onCreate(db);
    }
}
