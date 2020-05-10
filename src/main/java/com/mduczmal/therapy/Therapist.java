package com.mduczmal.therapy;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Optional;
import java.util.UUID;

@Entity
public class Therapist {
    @Id
    private UUID id;
    private String login;
    private UUID ad;
    @Embedded
    private final Cookies cookies;

    public Therapist() {
        this.cookies = new Cookies();
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public Optional<Ad> createAd() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
