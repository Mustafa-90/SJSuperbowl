package com.example.Superbowl;


public class Video {

    int id, companyId, genreId, categoryId, decade;
    String embeddedUrl, videoName, actor, description, companyName;
    boolean isPK;

    public Video() {

    }

    public Video(String videoName, String description, String companyName, String embeddedUrl) {
        this.embeddedUrl = embeddedUrl;
        this.videoName = videoName;
        this.description = description;
        this.companyName = companyName;
    }

    public Video(int id, int companyId, int genreId, int categoryId, String embeddedUrl, boolean isPK, int decade, String videoName, String actor, String description) {
        this.id = id;
        this.companyId = companyId;
        this.genreId = genreId;
        this.categoryId = categoryId;
        this.decade = decade;
        this.embeddedUrl = embeddedUrl;
        this.videoName = videoName;
        this.actor = actor;
        this.description = description;
        this.isPK = isPK;
    }

    public int getId() {
        return id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getGenreId() {
        return genreId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getDecade() {
        return decade;
    }

    public String getEmbeddedUrl() {
        return embeddedUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getActor() {
        return actor;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPK() {
        return isPK;
    }

    public String getCompanyName() {
        return companyName;
    }
}
