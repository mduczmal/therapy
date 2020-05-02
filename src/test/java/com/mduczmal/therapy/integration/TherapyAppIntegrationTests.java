package com.mduczmal.therapy.integration;

import com.mduczmal.therapy.Ad;
import com.mduczmal.therapy.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TherapyAppIntegrationTests {
    @Test
    void commentIsAddedToAd() {
        Ad ad = new Ad();
        Comment comment = new Comment();

        ad.addComment(comment);
        List<Comment> comments = ad.getComments();

        assertThat(comments, hasSize(1));
        assertEquals(comment, comments.get(0));
    }
    @Test
    void commentIsDeleted() {
        Ad ad = new Ad();
        Comment comment = new Comment();

        ad.addComment(comment);
        ad.getComments().get(0).delete();
        List<Comment> comments = ad.getComments();

        assertThat(comments, empty());
    }
}
