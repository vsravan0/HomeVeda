package com.sra.tube;

import java.util.Arrays;

/**
 * Created by sravan on 16/07/17.
 */

public class Tube {

    /*
    "kind" : youtube#searchListResponse,
"etag" : \"m2yskBQFythfE4irbTIeOgYYfBU/zMrIEFty0KXk0xMWhN1f8R7NBaM\",
"nextPageToken" : CDIQAA,
"regionCode" : IN,
"pageInfo" : +{ ... },
"items" : +[ ... ]
     */

    private String kind;
    private String etag;
    private String nextPageToken;
    private String regionCode;
    private PageInfo pageInfo;
    private Item items[];


    @Override
    public String toString() {
        return "Tube{" +
                "kind='" + kind + '\'' +
                ", etag='" + etag + '\'' +
                ", nextPageToken='" + nextPageToken + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", pageInfo=" + pageInfo +
                ", items=" + Arrays.toString(items) +
                '}';
    }



    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
