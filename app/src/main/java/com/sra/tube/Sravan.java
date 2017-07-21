package com.sra.tube;

import java.io.Serializable;

/**
 * Created by sravan on 16/07/17.
 */

public class Sravan implements Serializable {

    private String nextPageToken,regionCode

    , videoId
    , publishedAt
    , channelId
    , title
    , description
    , channelTitle
    , url;
    int totalResults,resultsPerPage;

    public Sravan(Tube tube, int pos){
        nextPageToken= tube.getNextPageToken();
        regionCode = tube.getRegionCode();
        totalResults = tube.getPageInfo().getTotalResults();
        resultsPerPage = tube.getPageInfo().getResultsPerPage();
        Item item = tube.getItems()[pos];
        videoId = item.getId().getVideoId();
        Snippet snippet = item.getSnippet();
        publishedAt = snippet.getPublishedAt();
        channelId = snippet.getChannelId();
        title = snippet.getTitle();
        description = snippet.getDescription();
        channelTitle = snippet.getChannelTitle();
        url = snippet.getThumbnails().getMedium().getUrl();
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Sravan{" +
                "nextPageToken='" + nextPageToken + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", totalResults=" + totalResults +
                ", resultsPerPage=" + resultsPerPage +
                ", videoId='" + videoId + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", channelId='" + channelId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", channelTitle='" + channelTitle + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
