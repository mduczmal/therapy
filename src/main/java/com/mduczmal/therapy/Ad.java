package com.mduczmal.therapy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID therapist;
    private LocalDateTime dateCreated;
    /*@Embedded
    private AdDetails adDetails;*/

    public Ad() {
        dateCreated = LocalDateTime.now();
    }

    public Ad(Therapist therapist) {
        this();
        this.therapist = therapist.getId();
    }

    public UUID getId() {
        return id;
    }

    public UUID getTherapist() {
        return therapist;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public AdDetails getDetails() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<Comment> getComments() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void edit() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void addComment(Comment comment) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
