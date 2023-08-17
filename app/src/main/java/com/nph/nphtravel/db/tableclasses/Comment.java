package com.nph.nphtravel.db.tableclasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    private int id;
    private int toudId;
    private int userId;
    private String created_date;
    private String content;

    public Comment() {

    }

    public Comment(boolean withDate) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.created_date = df.format(new Date());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToudId() {
        return toudId;
    }

    public void setToudId(int toudId) {
        this.toudId = toudId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
