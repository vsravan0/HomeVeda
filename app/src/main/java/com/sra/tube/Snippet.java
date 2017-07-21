package com.sra.tube;

/**
 * Created by sravan on 16/07/17.
 */

public class Snippet {
    /*
    "publishedAt" : 2015-06-15T06:46:31.000Z,
"channelId" : UC74TAQxOYvQDPyGt1klqTqw,
"title" : Breast Feeding a Baby,
"description" : Don't forget to check out our brand new website - http://bit.ly/hmvparen Learn how to breast feed a baby properly and safely! Subscribe to HomeVeda Parenting ...,
"thumbnails" : +{ ... },
"channelTitle" : Homeveda Parenting,
"liveBroadcastContent" : none
     */

    private String publishedAt;
    private String channelId;
    private String title;
    private String description;
    private Thumbnails thumbnails;
    private String channelTitle;


    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    @Override
    public String toString() {
        return "Snippet{" +
                "publishedAt='" + publishedAt + '\'' +
                ", channelId='" + channelId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnails=" + thumbnails +
                ", channelTitle='" + channelTitle + '\'' +
                '}';
    }



}
