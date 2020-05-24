package com.mduczmal.therapy;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserTherapist extends SecurityDetails {
    @OneToOne
    private Therapist therapist;

    public UserTherapist() {
        super();
    }

    public UserTherapist(String username, String password, List<Authority> authorities,
                         Therapist therapist) {
        super(username, password, authorities);
        therapist.setLogin(username);
        this.therapist = therapist;
    }

    public Therapist getTherapist() {
        return therapist;
    }
}
