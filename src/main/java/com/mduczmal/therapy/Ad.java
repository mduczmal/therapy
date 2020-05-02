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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public UUID getId() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public UUID getTherapist() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public LocalDateTime getDateCreated() {
        throw new UnsupportedOperationException("Not implemented yet");
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
