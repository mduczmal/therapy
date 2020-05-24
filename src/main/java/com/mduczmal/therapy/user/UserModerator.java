package com.mduczmal.therapy.user;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class UserModerator extends SecurityDetails {
    /*
    Single responsibility - klasa reprezentuje moderatora jako użytkownika, który może się zalogować
    Liskov substitution  - klasa może być użyta wszędzie tam, gdzie potrzebne są dane uwierzytelniające
     */
    public UserModerator() {
        super();
    }
    public UserModerator(String username, String password, List<Authority> authorities) {
        super(username, password, authorities);
    }
}
