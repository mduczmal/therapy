package com.mduczmal.therapy.ad.comment;

import com.mduczmal.therapy.Identifiable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Comment implements Identifiable {
    /*
    Single Responsibility - klasa odpowiada za stan komentarza
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID ad;
    private String author;
    @Column(length = 1000)
    private String content;
    private LocalDateTime dateCreated;
    private boolean selfComment;

    public Comment() {
        this.dateCreated = LocalDateTime.now();
    }

    public UUID getAd() {
        return ad;
    }

    public void setAd(UUID ad) {
        this.ad = ad;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public boolean isSelfComment() {
        return selfComment;
    }

    public void markAsSelfComment() {
        this.selfComment = true;
    }

    public void delete() {}

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", ad=" + ad +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", dateCreated=" + dateCreated +
                ", selfComment=" + selfComment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id != null ? id.equals(comment.id) : comment.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
