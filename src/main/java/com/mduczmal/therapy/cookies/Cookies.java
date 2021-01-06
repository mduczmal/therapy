package com.mduczmal.therapy.cookies;

import java.util.LinkedList;
import java.util.List;

public class Cookies implements Subject {
    private boolean accepted = false;
    public static final String TEXT = "Nasza strona korzysta tylko z niezbędnych plików cookies.";
    private final List<Observer> observers;

    public Cookies() {
        this.observers = new LinkedList<>();
    }
    public boolean areAccepted() {
        return accepted;
    }
    public void accept() {
        this.accepted = true;
        notifyObservers();
    }
    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (var o: observers) {
            o.update();
        }
    }
}
