package com.congnghephanmem.filmhay.Model;

public class History {
    private String imageFilm;
    private String nameFilm;
    private String information;
    private String link;
    private String time;

    public String getImageFilm() {
        return imageFilm;
    }

    public void setImageFilm(String imageFilm) {
        this.imageFilm = imageFilm;
    }

    public String getNameFilm() {
        return nameFilm;
    }

    public void setNameFilm(String nameFilm) {
        this.nameFilm = nameFilm;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public History() {
    }

    public History(String imageFilm, String nameFilm, String information, String link, String time) {
        this.imageFilm = imageFilm;
        this.nameFilm = nameFilm;
        this.information = information;
        this.link = link;
        this.time = time;
    }

}
