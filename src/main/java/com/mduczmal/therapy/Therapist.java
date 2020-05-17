package com.mduczmal.therapy;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;
import static java.util.UUID.randomUUID;

@Entity
@Table
public class Therapist {
    @Id
    private UUID id;
    private String login;
    private UUID ad;
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

    public Cookies getCookies() {
        return cookies;
    }

    public Optional<Ad> createAd() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
