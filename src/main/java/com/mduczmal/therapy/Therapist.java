package com.mduczmal.therapy;

import javax.persistence.*;
import java.util.*;

import static java.util.UUID.randomUUID;

@Entity
public class Therapist {
    @Id
    private UUID id;
    private UUID ad;
    private String login;
    @Embedded
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
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
