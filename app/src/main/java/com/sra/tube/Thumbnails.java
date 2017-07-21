package com.sra.tube;

/**
 * Created by sravan on 16/07/17.
 */

public class Thumbnails {

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }



    private Medium medium;



    @Override
    public String toString() {
        return "Thumbnails{" +
                "medium=" + medium +
                '}';
    }


}
