package com.nkocet.untitled;

import java.io.Serializable;

public class ListModel implements Serializable {
    public String description;
    int imageId;
    String title;

    public ListModel(int imageId, String title, String description) {
        this.description = description;
        this.imageId = imageId;
        this.title = title;
    }
}
