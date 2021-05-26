package com.congnghephanmem.filmhay.Model;

public class Film {
    private  int id;
    private int img;
    private String tenTheLoai;
    private String mota;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getImg() {
        return img;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public Film() {
    }

    public Film(int id, int img, String tenTheLoai, String mota, String link) {
        this.id = id;
        this.img = img;
        this.tenTheLoai = tenTheLoai;
        this.mota = mota;
        this.link = link;
    }
}
