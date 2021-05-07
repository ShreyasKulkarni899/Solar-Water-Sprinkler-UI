package com.nkocet.untitled;

import java.io.Serializable;

public class Card implements Serializable {
    public String id, name, location;
    String[] colors;
    public Sprinkler sprinkler;

    public Card(String id, String name, String location, String[] colors, Sprinkler sprinkler) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.colors = colors;
        this.sprinkler = sprinkler;
    }
}
