package com.mduczmal.therapy.user.therapist;

import com.mduczmal.therapy.ad.Ad;
import com.mduczmal.therapy.cookies.Observer;
import com.mduczmal.therapy.user.Specialist;

import javax.persistence.Entity;
import java.util.Optional;
import java.util.UUID;

@Entity
public class Therapist extends Specialist implements Observer {
    /*
    Single Responsibility - klasa odpowiada za stan i akcje wykonywane przez terapeutę
     */
    private UUID ad;
    private boolean cookiesAccepted;

    public Therapist() {
        super();
        this.cookiesAccepted = false;
    }

    public UUID getAd() { return ad; }

    public void setCookiesAccepted() {
        this.cookiesAccepted = true;
    }
    public boolean getCookiesAccepted() {
        return cookiesAccepted;
    }

    public Optional<Ad> createAd() {
        if (ad != null) return Optional.empty();
        Ad ad = new Ad(this);
        this.ad = ad.getId();
        return Optional.of(ad);
    }

    public void removeAd() {
        this.ad = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Therapist therapist = (Therapist) o;

        return id != null ? id.equals(therapist.id) : therapist.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public void update() {
        setCookiesAccepted();
    }
}
