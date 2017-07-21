package com.sra.tube;

/**
 * Created by sravan on 16/07/17.
 */

public class Item {
    /*
  "kind" : youtube#searchResult,
"etag" : \"m2yskBQFythfE4irbTIeOgYYfBU/Smmj0goWonIUdnwzv7Fy8l-z_ls\",
"id" : +{ ... },
"snippet" : +{ ... }
     */

    private Id id;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", snippet=" + snippet +
                '}';
    }

    private Snippet snippet;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }





}
