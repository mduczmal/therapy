package com.mduczmal.therapy;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class TherapistPrincipal extends User {
    private final UUID therapistID;
    public TherapistPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Therapist getTherapist() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
