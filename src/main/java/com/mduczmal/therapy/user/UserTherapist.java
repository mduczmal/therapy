package com.mduczmal.therapy.user;

import com.mduczmal.therapy.therapist.Therapist;

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
