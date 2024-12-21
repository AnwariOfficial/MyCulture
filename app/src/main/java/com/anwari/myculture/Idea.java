package com.anwari.myculture;

import java.io.File;
import java.util.Date;

public class Idea {
    private int profilePhoto;
    private String userName;
    private Date postDate;
    private String ideaPost;

    private int image;
    private File video;
    private File audio;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public File getVideo() {
        return video;
    }

    public void setVideo(File video) {
        this.video = video;
    }

    public File getAudio() {
        return audio;
    }

    public void setAudio(File audio) {
        this.audio = audio;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
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
