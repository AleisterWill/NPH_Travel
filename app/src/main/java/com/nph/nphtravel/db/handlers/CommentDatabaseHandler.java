package com.nph.nphtravel.db.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nph.nphtravel.db.DBHelper;
import com.nph.nphtravel.db.tableclasses.Comment;

import java.util.ArrayList;

public class CommentDatabaseHandler {

    SQLiteDatabase db;

    DBHelper dbHelper;

    public CommentDatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addComment(Comment comment) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COT_TOUR_ID, comment.getToudId());
        cv.put(DBHelper.COT_USER_ID, comment.getUserId());
        cv.put(DBHelper.COT_COMMENT_CONTENT, comment.getContent());
        cv.put(DBHelper.COT_CREATED_DATE, comment.getCreated_date());

        long result = db.insert(
                DBHelper.TEN_BANG_COMMENT,
                null,
                cv
        );

        return result;
    }

    public ArrayList<Comment> getPaginatedListByTourId(int tourId, int page, int max) {
        ArrayList<Comment> result = new ArrayList<>();

        Cursor c = db.query(
                DBHelper.TEN_BANG_COMMENT,
                null,
                DBHelper.COT_TOUR_ID + "=" + tourId,
                null,
                null,
                null,
                DBHelper.COT_CREATED_DATE + " DESC",
                String.format("%s, %s", (page-1)*max, max)
        );

        if (c.moveToFirst()) {
            int idColIdx = c.getColumnIndex(DBHelper.COT_ID);
            int tourIdColIdx = c.getColumnIndex(DBHelper.COT_TOUR_ID);
            int userIdColIdx = c.getColumnIndex(DBHelper.COT_USER_ID);
            int contentColIdx = c.getColumnIndex(DBHelper.COT_COMMENT_CONTENT);
            int cdColIdx = c.getColumnIndex(DBHelper.COT_CREATED_DATE);

            do {
                Comment comment = new Comment();
                comment.setId(Integer.parseInt(c.getString(idColIdx)));
                comment.setUserId(Integer.parseInt(c.getString(userIdColIdx)));
                comment.setToudId(Integer.parseInt(c.getString(tourIdColIdx)));
                comment.setContent(c.getString(contentColIdx));
                comment.setCreated_date(c.getString(cdColIdx));

                result.add(comment);
            } while(c.moveToNext());
        }

        c.close();
        return result;
    }

}
