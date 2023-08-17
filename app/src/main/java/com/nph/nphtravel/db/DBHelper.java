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
            "CREATE TABLE %s (%s, %s);",
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
            "CREATE TABLE %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", // 1 table name & 11 columns
            TEN_BANG_TOUR,
            String.format("%s %s", COT_ID, "integer primary key autoincrement"),
            String.format("%s %s", COT_NAME_TOUR, "text"),
            String.format("%s %s", COT_PRICE_TOUR, "double default '0'"),
            String.format("%s %s", COT_DESCRIPTION_TOUR, "text"),
            String.format("%s %s", COT_LOCATION_TOUR, "text"),
            String.format("%s %s", COT_START_DAY_TOUR, "text"),
            String.format("%s %s", COT_END_DAY_TOUR, "text"),
            String.format("%s %s", COT_AVATAR, "text"),
            String.format("%s %s", COT_DISCOUNT_TOUR, "double default '0'"),
            String.format("%s %s, FOREIGN KEY (%s) REFERENCES %s (%s)",
                    COT_TOUR_CATEGORY_ID, "integer", //col_name & data type
                    COT_TOUR_CATEGORY_ID, TEN_BANG_CATEGORY, COT_ID //col that is FK to tablename(col_name)
            )
    );


    public static final String TEN_BANG_BOOKING = "Booking";
    public static final String COT_DAY_BOOKING = "_day_booking";
    public static final String COT_STATUS_BOOKING = "_status_booking";
    public static final String COT_SO_LUONG = "_quantity";
    public static final String COT_TONG_TIEN = "_total";
    public static final String COT_BOOKING_USER_ID = "_user_id";
    public static final String COT_BOOKING_TOUR_ID = "_tour_id";


    private static final String CREATE_TABLE_BOOKING = String.format(
            "CREATE TABLE %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s);",
            TEN_BANG_BOOKING,
            String.format("%s %s", COT_ID, "integer primary key autoincrement"),
            String.format("%s %s", COT_DAY_BOOKING, "text"),
            String.format("%s %s", COT_STATUS_BOOKING, "text"),
            String.format("%s %s", COT_SO_LUONG, "integer"),
            String.format("%s %s", COT_PRICE_TOUR, "double"),
            String.format("%s %s", COT_TONG_TIEN, "double"),
            String.format("%s %s", COT_BOOKING_TOUR_ID, "integer"),
            String.format("%s %s", COT_BOOKING_USER_ID, "integer"),
            String.format("FOREIGN KEY (%s) REFERENCES %s(%s)",
                    COT_BOOKING_TOUR_ID, TEN_BANG_TOUR, COT_ID),
            String.format("FOREIGN KEY (%s) REFERENCES %s(%s)",
                    COT_BOOKING_USER_ID, TEN_BANG_USER, COT_ID)
    );


    public static final String TEN_BANG_RECEIPT = "Receipt";
    public static final String COT_FULLNAME = "_fullname";
    public static final String COT_PHONENUMBER = "_phonenumber";
    public static final String COT_ADDRESS = "_address";
    public static final String COT_CREATED_DATE = "_created_date";
    public static final String COT_PAY_METHOD = "_pay_method";
    public static final String COT_TRANSACTION_TOKEN = "_trans_token";
    public static final String COT_BOOKING_ID = "_booking_id";
    public static final String COT_RATED = "_rated";


    private static final String CREATE_TABLE_RECEIPT = String.format(
            "CREATE TABLE %s (%s, %s, %s, %s, %s, %s, %s, %s, %s);",
            TEN_BANG_RECEIPT,
            String.format("%s %s", COT_ID, "integer primary key autoincrement"),
            String.format("%s %s", COT_FULLNAME, "text"),
            String.format("%s %s", COT_PHONENUMBER, "text"),
            String.format("%s %s", COT_ADDRESS, "text"),
            String.format("%s %s", COT_CREATED_DATE, "text"),
            String.format("%s %s", COT_PAY_METHOD, "text"),
            String.format("%s %s", COT_TRANSACTION_TOKEN, "text"),
            String.format("%s %s", COT_BOOKING_ID, "integer"),
            String.format("FOREIGN KEY (%s) REFERENCES %s (%s)",
                    COT_BOOKING_ID, TEN_BANG_BOOKING, COT_ID)
    );


    public static final String TEN_BANG_RATING = "Tour_Rating";
    public static final String COT_SCORE = "_score";
    public static final String COT_RECEIPT_ID = "_receipt_id";
    public static final String COT_USER_ID = "_user_id";

    public static final String COT_TOUR_ID = "_tour_id";
    public static final String CREATE_TABLE_RATING = String.format(
            "CREATE TABLE %s(%s, %s, %s, %s, %s, %s, %s, %s);",
            TEN_BANG_RATING,
            String.format("%s %s", COT_ID, "integer primary key autoincrement"),
            String.format("%s %s", COT_SCORE, "double"),
            String.format("%s %s", COT_TOUR_ID, "integer"),
            String.format("%s %s", COT_USER_ID, "integer"),
            String.format("%s %S", COT_RECEIPT_ID, "integer"),
            String.format("FOREIGN KEY (%s) REFERENCES %s (%s)",
                    COT_TOUR_ID, TEN_BANG_TOUR, COT_ID),
            String.format("FOREIGN KEY (%s) REFERENCES %s (%s)",
                    COT_USER_ID, TEN_BANG_USER, COT_ID),
            String.format("FOREIGN KEY (%s) REFERENCES %s (%s)",
                    COT_RECEIPT_ID, TEN_BANG_RECEIPT, COT_ID)
    );


    public static final String TEN_BANG_COMMENT = "Comment";
    public static final String COT_COMMENT_CONTENT = "_content";

    public static final String CREATE_TABLE_COMMENT = String.format(
            "CREATE TABLE %s (%s, %s, %s, %s, %s, %s, %s);",
            TEN_BANG_COMMENT,
            String.format("%s %s", COT_ID, "integer primary key autoincrement"),
            String.format("%s %s", COT_USER_ID, "integer"),
            String.format("%s %s", COT_TOUR_ID, "integer"),
            String.format("%s %s", COT_COMMENT_CONTENT, "text"),
            String.format("%s %s", COT_CREATED_DATE, "text"),
            String.format("FOREIGN KEY (%s) REFERENCES %s (%s)",
                    COT_USER_ID, TEN_BANG_USER, COT_ID),
            String.format("FOREIGN KEY (%s) REFERENCES %s (%s)",
                    COT_TOUR_ID, TEN_BANG_TOUR, COT_ID)
    );



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
        db.execSQL(CREATE_TABLE_RECEIPT);
        db.execSQL(CREATE_TABLE_RATING);
        db.execSQL(CREATE_TABLE_COMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_TOUR);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_BOOKING);
        db.execSQL("DROP TABLE IF EXISTS " + "Tour_detail");
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_RECEIPT);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_RATING);
//        if (oldVersion < 1) {
//            onCreate(db);
//        }
        //Tiến hành tạo bảng mới
        onCreate(db);
    }
}
