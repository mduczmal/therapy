package com.mduczmal.therapy;

import javax.persistence.Embeddable;

@Embeddable
public class Cookies {
    private boolean accepted = false;
    public static final String TEXT = null;
    public Cookies() {}
    public boolean areAccepted() {
        return accepted;
    }
    public void accept() {
        this.accepted = true;
    }
}
