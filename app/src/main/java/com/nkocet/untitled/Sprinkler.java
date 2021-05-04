package com.nkocet.untitled;

import java.io.Serializable;

public class Sprinkler implements Serializable {
    int status, rate;
    int[] activeDays;
    boolean auto;

    static final int ONLINE = 1;
    static final int OFFLINE = 0;

    public Sprinkler(int status, int rate, int[] activeDays, boolean auto) {
        this.status = status;
        this.rate = rate;
        this.activeDays = activeDays;
        this.auto = auto;
    }
}
