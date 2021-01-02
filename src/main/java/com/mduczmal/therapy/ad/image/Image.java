package com.mduczmal.therapy.ad.image;

import com.mduczmal.therapy.Identifiable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Image implements Identifiable {
    @Id
    @NotNull
    private UUID id;
    private String filename;

    public Image() {
        this.id = UUID.randomUUID();
        this.filename = id.toString();
    }

    @Override
    public UUID getId() {
        return id;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
