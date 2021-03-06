package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.Identifiable;
import com.mduczmal.therapy.ad.comment.Comment;
import com.mduczmal.therapy.user.Specialist;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
public abstract class Ad implements Identifiable {
    @Id
    private UUID id;

    private LocalDateTime dateCreated;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ad")
    private List<Comment> comments;
    @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "details_id")
    protected AdDetails adDetails;

    public Ad() {
        id = randomUUID();
        dateCreated = LocalDateTime.now();
        comments = new LinkedList<>();
    }

    public abstract UUID getCreator();

    public abstract void setCreator(UUID id);

    @Override
    public UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
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

    public void addComment(Comment comment) {
        if (comment.getAuthor() == null || comment.getAuthor().isBlank()) {
            comment.setAuthor("Komentarz anonimowy");
        }
        comments.add(comment);
    }

    public void addComment(Comment comment, Specialist specialist) {
        if (specialist != null && id == specialist.getAd()) {
            comment.markAsSelfComment();
        }
        addComment(comment);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", comments=" + comments +
                ", adDetails=" + adDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ad ad = (Ad) o;

        return id != null ? id.equals(ad.id) : ad.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
