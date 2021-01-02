package com.mduczmal.therapy.user;

import com.mduczmal.therapy.ad.AdCreator;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public abstract class Specialist extends User implements AdCreator {
    /*
        Single Responsibility - klasa odpowiada za stan i akcje wykonywane przez terapeutę
         */
    private UUID ad;

    public UUID getAd() { return ad; }

    public void setAd(UUID ad) {
        this.ad = ad;
    }

    public void removeAd() {
        this.ad = null;
    }
}
