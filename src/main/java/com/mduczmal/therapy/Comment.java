package com.mduczmal.therapy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    private Long id;
    private String author;
    private String content;
    private LocalDateTime dateCreated;
    private boolean selfComment;

    public Comment() {
        this.dateCreated = LocalDateTime.now();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author.isBlank()) {
           this.author = "Komentarz anonimowy";
        } else {
            this.author = author;
        }
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

    public void delete() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
