package com.mduczmal.therapy.user;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class UserModerator extends SecurityDetails {
    public UserModerator() {
        super();
    }
    public UserModerator(String username, String password, List<Authority> authorities) {
        super(username, password, authorities);
    }
}
