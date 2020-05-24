package com.mduczmal.therapy;

import javax.persistence.*;
import java.util.List;

@Entity
public class TherapistPrincipal extends SecurityDetails {
    @OneToOne
    private Therapist therapist;

    public TherapistPrincipal() {
        super();
    }

    public TherapistPrincipal(String username, String password, List<Authority> authorities,
                              Therapist therapist) {
        super(username, password, authorities);
        therapist.setLogin(username);
        this.therapist = therapist;
    }

    public Therapist getTherapist() {
        return therapist;
    }
}
