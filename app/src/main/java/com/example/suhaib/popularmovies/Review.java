package com.example.suhaib.popularmovies;

import java.io.Serializable;

public class Review implements Serializable {
    private  String author;
    private  String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
