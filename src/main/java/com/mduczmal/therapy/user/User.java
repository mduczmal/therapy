package com.mduczmal.therapy.user;


import com.mduczmal.therapy.Identifiable;
import com.mduczmal.therapy.cookies.Observer;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "concrete_users")
public abstract class User implements Observer, Identifiable {
    @Id
    protected UUID id;
    protected boolean cookiesAccepted;

    public User() {
        this.id = UUID.randomUUID();
        this.cookiesAccepted = false;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setCookiesAccepted() {
        this.cookiesAccepted = true;
    }

    public boolean getCookiesAccepted() {
        return cookiesAccepted;
    }

    @Override
    public void update() {
        setCookiesAccepted();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
