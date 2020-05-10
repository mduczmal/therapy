package com.mduczmal.therapy;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Ad {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    private UUID therapist;
    private LocalDateTime dateCreated;
    @Embedded
    private AdDetails adDetails;

    public Ad() {
        dateCreated = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getTherapist() {
        throw new UnsupportedOperationException("Not implemented yet");
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
