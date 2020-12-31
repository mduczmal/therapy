package com.mduczmal.therapy.ad;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class TherapyAd extends Ad {
    @NotNull
    private UUID therapist;

    public TherapyAd(UUID therapistId) {
        super();
        this.therapist = therapistId;
    }

    public TherapyAd() {
        super();
    }

    private UUID getTherapist() {
        return therapist;
    }

    public void setTherapist(UUID therapist) {
        this.therapist = therapist;
    }

    @Override
    public UUID getCreator() {
        return getTherapist();
    }

    @Override
    public void setCreator(UUID id) {
        setTherapist(id);

    }
}
