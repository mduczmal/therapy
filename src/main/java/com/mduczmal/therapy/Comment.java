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

    public String getAuthor() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setAuthor(String author) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getContent() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setContent(String content) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public LocalDateTime getDateCreated() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isSelfComment() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
