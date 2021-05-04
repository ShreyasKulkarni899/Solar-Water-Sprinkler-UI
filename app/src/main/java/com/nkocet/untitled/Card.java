package com.nkocet.untitled;

import java.io.Serializable;

public class Card implements Serializable {
    public String id, name, location, cardBackgroundColor, cardBottomColor, textColor;
    public Sprinkler sprinkler;

    public Card(String id, String name, String location, String[] colors, Sprinkler sprinkler) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cardBackgroundColor = colors[0];
        this.cardBottomColor = colors[1];
        this.textColor = colors[2];
        this.sprinkler = sprinkler;
    }
}
