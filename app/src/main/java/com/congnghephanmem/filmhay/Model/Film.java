package com.congnghephanmem.filmhay.Model;

public class Film {
    private  long id;
    private String img;
    private String tenTheLoai;
    private String mota;
    private String link;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setImg(String img) {
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

    public Film(long id, String img, String tenTheLoai, String mota, String link) {
        this.id = id;
        this.img = img;
        this.tenTheLoai = tenTheLoai;
        this.mota = mota;
        this.link = link;
    }
}
