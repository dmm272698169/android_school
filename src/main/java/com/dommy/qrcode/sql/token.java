package com.dommy.qrcode.sql;

public class token {
    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final String USER = "user";
    private int id;
    private String user;
    private String content;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
