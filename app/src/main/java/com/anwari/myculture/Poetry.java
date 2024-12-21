package com.anwari.myculture;

import java.util.Date;

public class Poetry {
    private String userName;
    private Date postDate;
    private String ideaPost;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getIdeaPost() {
        return ideaPost;
    }

    public void setIdeaPost(String ideaPost) {
        this.ideaPost = ideaPost;
    }
}