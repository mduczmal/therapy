package com.mduczmal.therapy.user;


import javax.persistence.*;
import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "concrete_users")
public abstract class User {
    @Id
    protected UUID id;
    public User() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
