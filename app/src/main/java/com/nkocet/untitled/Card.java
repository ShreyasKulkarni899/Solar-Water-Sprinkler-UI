package com.nkocet.untitled;

import java.io.Serializable;

public class Card implements Serializable {
    public String name, location, cardBackgroundColor, cardBottomColor, textColor;
    public Sprinkler sprinkler;

    public Card(String name, String location, String[] colors, Sprinkler sprinkler) {
        this.name = name;
        this.location = location;
        this.cardBackgroundColor = colors[0];
        this.cardBottomColor = colors[1];
        this.textColor = colors[2];
        this.sprinkler = sprinkler;
    }
}
