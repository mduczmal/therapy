package com.mduczmal.therapy.ad.image;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Image {
    @Id
    @NotNull
    private UUID id;
    private String filename;

    public Image() {
        this.id = UUID.randomUUID();
        this.filename = id.toString();
    }

    public UUID getId() {
        return id;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String path) {
        this.filename = path;
    }
}
