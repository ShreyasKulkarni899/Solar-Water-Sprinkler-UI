package com.nkocet.untitled;

import java.io.Serializable;

public class HelpModel implements Serializable {
    public String descriptionHelp, headerTextHelp;

    public HelpModel(String headerTextHelp, String descriptionHelp) {
        this.headerTextHelp = headerTextHelp;
        this.descriptionHelp = descriptionHelp;
    }
}
