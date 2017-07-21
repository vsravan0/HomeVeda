package com.sra.tube;

/**
 * Created by sravan on 16/07/17.
 */

public class Id {

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /*
        "kind" : youtube#video,
    "videoId" : vskmlfwgvvs
         */
    private String kind;
    private String videoId;
}
