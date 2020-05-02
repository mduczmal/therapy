package com.mduczmal.therapy;

import javax.persistence.Embeddable;

@Embeddable
public class Cookies {
    private boolean accepted;
    public static final String TEXT = null;
    public boolean areAccepted() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public void accept() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
