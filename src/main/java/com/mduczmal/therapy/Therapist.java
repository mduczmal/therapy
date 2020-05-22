package com.mduczmal.therapy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

import static java.util.UUID.randomUUID;

@Entity
public class Therapist {
    @Id
    @NotNull
    private UUID id;
    private UUID ad;
    private String login;
    @Embedded
    @AttributeOverride(name="accepted", column=@Column(name = "cookies_accepted"))
    private final Cookies cookies;

    public Therapist() {
        this.id = randomUUID();
        this.cookies = new Cookies();
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

    public Cookies getCookies() {
        return cookies;
    }

    public Optional<Ad> createAd() {
        if (ad != null) return Optional.empty();
        Ad ad = new Ad(this);
        this.ad = ad.getId();
        return Optional.of(ad);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Therapist therapist = (Therapist) o;
        return id.equals(therapist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
