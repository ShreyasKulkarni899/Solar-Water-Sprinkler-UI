package com.nkocet.untitled;

import android.text.format.Time;

import java.io.Serializable;

public class Sprinkler implements Serializable {
    int status, rate;
    int[] activeDays;
    String[] activeTime;
    boolean auto;

    static final int ONLINE = 1;
    static final int OFFLINE = 0;

    public Sprinkler(int status, int rate, int[] activeDays, String[] activeTime, boolean auto) {
        this.status = status;
        this.rate = rate;
        this.activeDays = activeDays;
        this.activeTime = activeTime;
        this.auto = auto;
    }
}
