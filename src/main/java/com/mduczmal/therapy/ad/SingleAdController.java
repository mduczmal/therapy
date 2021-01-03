package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.ad.comment.CommentRepository;
import com.mduczmal.therapy.user.Specialist;
import com.mduczmal.therapy.user.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class SingleAdController {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public SingleAdController(AdRepository adRepository, UserRepository userRepository,
                              CommentRepository commentRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping(value = "/v2/ad",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Ad ad(@RequestParam UUID id) {
        return adRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping(value = "/v2/remove",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Boolean> remove(@RequestParam("id") UUID id) {
        Ad ad = adRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Removed ad not in repository"));
        userRepository.findById(ad.getCreator()).ifPresent(u -> {
            Specialist s = (Specialist) u;
            s.removeAd();
            userRepository.save(s);
        });
        commentRepository.deleteAll(ad.getComments());
        adRepository.deleteById(id);
        return Map.of("success", true);
    }

}