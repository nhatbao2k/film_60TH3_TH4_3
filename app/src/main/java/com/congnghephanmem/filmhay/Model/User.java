package com.congnghephanmem.filmhay.Model;

public class User {
    private String email;
    private String phone;
    private String name;
    private String gender;
    private String birthday;
    private String role;
    private String avatar;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User() {
    }

    public User(String email, String phone,  String name, String gender, String birthday, String role, String avatar) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.role = role;
        this.avatar = avatar;
    }
}
