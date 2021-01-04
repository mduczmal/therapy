package com.mduczmal.therapy.ad.comment;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping(value = "v2/comment/delete",
    produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Boolean> removeComment(@RequestParam("id") UUID id) {
        try {
            commentRepository.deleteById(id);
            return Map.of("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false);
        }
    }
}
