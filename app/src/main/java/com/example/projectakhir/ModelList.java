package com.example.projectakhir;

public class ModelList {
    String id, title, detail, image;

    public ModelList(){};

    public ModelList(String id, String title, String detail, String image) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
