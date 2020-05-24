package com.mduczmal.therapy.ad.comment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID ad;
    private String author;
    @Column(length = 1000)
    private String content;
    private LocalDateTime dateCreated;
    private boolean selfComment;

    public Comment() {
        this.dateCreated = LocalDateTime.now();
    }
    public Comment(UUID ad) {
        this();
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

    public Long getId() {
        return id;
    }

}
