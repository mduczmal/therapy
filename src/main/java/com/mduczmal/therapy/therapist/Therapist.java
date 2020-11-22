package com.mduczmal.therapy.therapist;

import com.mduczmal.therapy.ad.Ad;
import com.mduczmal.therapy.cookies.Observer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
public class Therapist implements Observer {
    /*
    Single Responsibility - klasa odpowiada za stan i akcje wykonywane przez terapeutę
     */
    @Id
    @NotNull
    private UUID id;
    private UUID ad;
    private String login;
    private boolean cookiesAccepted;

    public Therapist() {
        this.id = randomUUID();
        this.cookiesAccepted = false;
    }

    public Therapist(String username) {
        this();
        this.login = username;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAd() { return ad; }

    public String getLogin() {
        return login;
    }

    public void setLogin(String username) {
        this.login = username;
    }

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
