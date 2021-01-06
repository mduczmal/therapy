package com.mduczmal.therapy.ad;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonDeserialize(as = TherapyAdDetails.class)
public abstract class AdDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @OneToOne(mappedBy = "adDetails")
    private Ad ad;
}
