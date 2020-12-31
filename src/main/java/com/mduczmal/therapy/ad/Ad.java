package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.ad.comment.Comment;
import com.mduczmal.therapy.user.therapist.Therapist;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
public class Ad {
    /*
    Single responsibility - klasa reprezentuje stan ogłoszenia i związane z ogłoszeniem akcje
     */
    @Id
    private UUID id;
    @NotNull
    private UUID therapist;
    private LocalDateTime dateCreated;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ad")
    private List<Comment> comments;
    //Single responsibility - ogłoszenie zawiera dużo danych, więc umieszczenie ich w osobnej klasę zwiększa czytelność
    //kodu. Klasa AdDetails zawiera szczegółowe dane, a klasa Ad określa interakcje z innymi klasami.
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

    protected void setId(UUID id) {
        this.id = id;
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

    //Nie zdążyłem zaimplementować edycji ogłoszenia
    public void edit() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete() {}

    public void addComment(Comment comment) {
        if (comment.getAuthor() == null || comment.getAuthor().isBlank()) {
            comment.setAuthor("Komentarz anonimowy");
        }
        comments.add(comment);
    }

    public void addComment(Comment comment, Therapist therapist) {
        if (therapist != null && id == therapist.getAd()) {
            comment.markAsSelfComment();
        }
        addComment(comment);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", therapist=" + therapist +
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
