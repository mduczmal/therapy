package com.mduczmal.therapy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import static java.util.UUID.randomUUID;

@Entity
public class Ad {
    @Id
    private UUID id;
    @NotNull
    private UUID therapist;
    private LocalDateTime dateCreated;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ad")
    private List<Comment> comments;
    @Embedded
    private AdDetails adDetails;

    public Ad() {
        id = randomUUID();
        dateCreated = LocalDateTime.now();
        adDetails = new AdDetails();
        comments = new LinkedList<>();
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

    public void setDetails(AdDetails details) {
        this.adDetails = details;
    }

    public AdDetails getDetails() {
        return adDetails;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void edit() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete() {}

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
