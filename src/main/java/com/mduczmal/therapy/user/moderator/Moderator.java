package com.mduczmal.therapy.user.moderator;

import com.mduczmal.therapy.user.User;

import javax.persistence.Entity;

@Entity
public class Moderator extends User {
    /*
    Single responsibility - klasa reprezentuje moderatora jako użytkownika, który może się zalogować
    Liskov substitution  - klasa może być użyta wszędzie tam, gdzie potrzebne są dane uwierzytelniające
     */
    public Moderator() {
        super();
    }
}
