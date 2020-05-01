package com.mduczmal.therapy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Ad {
    private UUID id;
    private UUID therapist;
    private LocalDateTime dateCreated;
    private AdDetails details;

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

    public boolean obligatoryFieldsPresent() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
