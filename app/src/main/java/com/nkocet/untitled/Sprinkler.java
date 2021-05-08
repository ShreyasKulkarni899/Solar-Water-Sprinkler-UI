package com.nkocet.untitled;

import android.text.format.Time;

import java.io.Serializable;

public class Sprinkler implements Serializable {
    int status, rate;
    int[] activeDays;
    Time[] activeTime;
    boolean auto;

    static final int ONLINE = 1;
    static final int OFFLINE = 0;

    public Sprinkler(int status, int rate, int[] activeDays, boolean auto) {
        this.status = status;
        this.rate = rate;
        this.activeDays = activeDays;
        this.activeTime = null;
        this.auto = auto;
    }

    public Sprinkler(int status, int rate, int[] activeDays, Time[] activeTime, boolean auto) {
        this.status = status;
        this.rate = rate;
        this.activeDays = activeDays;
        this.activeTime = activeTime;
        this.auto = auto;
    }
}
