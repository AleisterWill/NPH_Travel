package com.nph.nphtravel.db.tableclasses;

import androidx.annotation.NonNull;

public class User {
    private String id;
    private String username;
    private String password;
    private String avatar;
    private int role;


    public User(){

    }
    public User(String userName, String passWord){
        username = userName;
        password = passWord;
    }


//    dk avatar
    public User(String userName, String passWord, String Avatar){
        username = userName;
        password = passWord;
        avatar = Avatar;
    }

    // khởi tạo đọc user trong admin

    // dk nếu có gửi role k mặc định là 0
    public User(String userName, String passWord, String Avatar, int Role){
        username = userName;
        password = passWord;
        avatar = Avatar;
        role = Role;
    }


//    khởi tạo dùng để edit User
    public User(String iD, String userName, String passWord, String Avatar){

        id=iD;
        username = userName;
        password = passWord;
        avatar = Avatar;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @NonNull
    @Override
    public String toString() {
        return getId() + ":" + getUsername();
    }
}
