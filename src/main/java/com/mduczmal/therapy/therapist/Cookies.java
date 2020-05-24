package com.mduczmal.therapy.therapist;

import javax.persistence.Embeddable;

@Embeddable
public class Cookies {
    /*
    Single Responsibility - klasa odpowiada za stan ciasteczek i związane z nimi akcje
     */
    private boolean accepted = false;
    public static final String TEXT = "Nasza strona korzysta tylko z niezbędnych plików cookies.";
    public Cookies() {}
    public boolean areAccepted() {
        return accepted;
    }
    public void accept() {
        this.accepted = true;
    }
}
