package com.sra.tube;

/**
 * Created by sravan on 16/07/17.
 */

public class EntityPlayListItem {
    /*
  "kind":"youtube#playlist",
"etag":"\"m2yskBQFythfE4irbTIeOgYYfBU/0CbtzVvoNtfNdXD3RwcLX9hCul8\"",
"id":"PLJQxlT7ibxmELXhlhA79e2Jlic-vXgtkq",
"snippet":{}
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private Snippet snippet;


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", snippet=" + snippet +
                '}';
    }


    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }





}
