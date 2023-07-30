package com.nph.nphtravel.db.tableclasses;

public class Category {
    private String id;
    private String category_name;

    public Category() {

    }
    public Category(String category_Name) {
        this.category_name = category_Name;
    }
    public Category(String iD, String category_Name){
        this.id = iD;
        this.category_name = category_Name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public String toString() {
        return getId() + " : " + getCategory_name();
    }
}
