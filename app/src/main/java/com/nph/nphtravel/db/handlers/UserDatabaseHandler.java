package com.nph.nphtravel.db.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.tableclasses.User;

import java.util.ArrayList;


public class UserDatabaseHandler {

    SQLiteDatabase database;
    DBHelper dbHelper;

    public UserDatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long addUser(User user) {

        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COT_USERNAME, user.getUsername());
        values.put(DBHelper.COT_PASSWORD, user.getPassword());

        // thêm avatar
        values.put(DBHelper.COT_AVATAR, user.getAvatar());
        // nếu có truyền thì lưu không thì default là 0;
        values.put(DBHelper.COT_USER_ROLE, user.getRole());

        long newRowId = database.insert(DBHelper.TEN_BANG_USER, null, values);

        database.close();

        return newRowId;
    }

    // xử lý đăng nhập
    public User checkLogin(String username, String password) {
        User user = null;

        database = dbHelper.getReadableDatabase();
        String selection = DBHelper.COT_USERNAME + " = ? AND " + DBHelper.COT_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = database.query(
                DBHelper.TEN_BANG_USER,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            try {
                int idColumnIndex = cursor.getColumnIndex(DBHelper.COT_ID);
                int usernameColumnIndex = cursor.getColumnIndex(DBHelper.COT_USERNAME);
                int avatarColumnIndex = cursor.getColumnIndex(DBHelper.COT_AVATAR);
                int roleColumnIndex = cursor.getColumnIndex(DBHelper.COT_USER_ROLE);

                user = new User();
                user.setId(cursor.getString(idColumnIndex));
                user.setUsername(cursor.getString(usernameColumnIndex));
                user.setAvatar(cursor.getString(avatarColumnIndex));
                user.setRole(cursor.getInt(roleColumnIndex));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        cursor.close();
        return user;
    }

    //Hiển thị dl
    public ArrayList<User> getAllUser() {
        ArrayList<User> ls = new ArrayList<User>();
        Cursor c = database.query(DBHelper.TEN_BANG_USER, null, null, null,
                null, null, null);
        c.moveToPosition(-1);
        while (c.moveToNext()) {

            User u = new User();

            u.setId(c.getString(0));
            u.setUsername(c.getString(1));
            u.setPassword(c.getString(2));
            u.setAvatar(c.getString(3));
            u.setRole(c.getInt(4));
            ls.add(u);
        }
        c.close(); //dongs
        return ls;
    }

    // phương thức xoá User sử dụng  username
    public int deleteNameUser(String username) {
        String where = "_username=?";
        int numberOFEntriesDeleted = dbHelper.getWritableDatabase().delete(DBHelper.TEN_BANG_USER, where, new String[]{username});
        return numberOFEntriesDeleted;
    }

    // phương thức xoá User sử dụng ID userName
    public int deleteIDUser(String id) {
        String where = "_id=?";
        int numberOFEntriesDeleted = dbHelper.getWritableDatabase().delete(DBHelper.TEN_BANG_USER, where, new String[]{id});
        return numberOFEntriesDeleted;
    }

    //edit a User row
    public long edit_User(User user) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COT_USERNAME, user.getUsername());
        values.put(DBHelper.COT_PASSWORD, user.getPassword());
        values.put(DBHelper.COT_AVATAR, user.getAvatar());

        return dbHelper.getWritableDatabase().update(DBHelper.TEN_BANG_USER, values,
                DBHelper.COT_ID + " = "
                        + user.getId(), null);
    }


    public User getById(int id) {
        Cursor c = database.query(
                DBHelper.TEN_BANG_USER,
                null,
                DBHelper.COT_ID + "=" + id,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            int idColIdx = c.getColumnIndex(DBHelper.COT_ID);
            int usernameColIdx = c.getColumnIndex(DBHelper.COT_USERNAME);
            int avatarColIdx = c.getColumnIndex(DBHelper.COT_AVATAR);

            User user = new User();
            user.setId(c.getString(idColIdx));
            user.setUsername(c.getString(usernameColIdx));
            user.setAvatar(c.getString(avatarColIdx));

            c.close();
            return user;
        }

        c.close();
        return null;
    }


}
