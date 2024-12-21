package com.anwari.myculture;

public class Blogs {
    private String news_title;
    private String news_content;
    private int news_image;
    private String author;

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content)
    {
        this.news_content = news_content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public int getNews_image() {
        return news_image;
    }

    public void setNews_image(int news_image) {
        this.news_image = news_image;
    }



}
