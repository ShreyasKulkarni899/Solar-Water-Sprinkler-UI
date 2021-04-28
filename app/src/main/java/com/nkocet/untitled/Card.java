package com.nkocet.untitled;

public class Card {
    public String name, location;
    public int cardBackgroundColor, cardBottomColor;
    static final int ONLINE = 1;
    static final int OFFLINE = 0;

    public Card(String name, String location, int cardBackgroundColor, int cardForegroundColor) {
        this.name = name;
        this.location = location;
        this.cardBackgroundColor = cardBackgroundColor;
        this.cardBottomColor = cardForegroundColor;
    }
}
