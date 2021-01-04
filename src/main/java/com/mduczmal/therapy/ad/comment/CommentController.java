package com.mduczmal.therapy.ad.comment;

import com.mduczmal.therapy.ad.Ad;
import com.mduczmal.therapy.ad.AdRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;

    public CommentController(CommentRepository commentRepository, AdRepository adRepository) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
    }

    @PostMapping(value = "/v2/comment/delete",
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

    @PostMapping(value = "/v2/comment/create",
            produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Boolean> createComment(@RequestParam("ad") UUID adId, @RequestBody Comment comment) {
        try {
            Ad ad = adRepository.findById(adId).orElseThrow(() ->
                    new IllegalArgumentException("Ad with id " + adId + " does not exist."));
            comment.setAd(adId);
            Comment saved = commentRepository.save(comment);
            ad.addComment(saved);
            adRepository.save(ad);
            return Map.of("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false);
        }
    }
}
