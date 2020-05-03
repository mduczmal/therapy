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
    private Cookies cookies;

    public Therapist() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public UUID getId() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getLogin() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Cookies getCookies() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Optional<Ad> createAd() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
