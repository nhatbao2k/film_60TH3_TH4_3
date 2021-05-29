package com.congnghephanmem.filmhay.Model;

public class binhluan {
    private String name;
    private String text;
    private long id;
    private int idcmt;

    public int getIdcmt() {
        return idcmt;
    }

    public void setIdcmt(int idcmt) {
        this.idcmt = idcmt;
    }
    public binhluan() {
    }

    public binhluan(String name, String text, long id) {
        this.name = name;
        this.text = text;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
