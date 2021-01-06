package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.ad.comment.CommentRepository;
import com.mduczmal.therapy.user.Specialist;
import com.mduczmal.therapy.user.UserRepository;
import com.mduczmal.therapy.user.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class SingleAdController {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final AdFactory adFactory;

    public SingleAdController(AdRepository adRepository, UserRepository userRepository,
                              CommentRepository commentRepository, UserService userService,
                              AdFactory adFactory) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.adFactory = adFactory;
    }

    @GetMapping(value = "/v2/ad",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Ad ad(@RequestParam UUID id) {
        return adRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping(value = "/v2/ad",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Boolean> createAd(@RequestBody AdDetails adDetails) {
        try {
            Specialist specialist  = (Specialist) userService.getCurrentUser();
            Ad ad = adFactory.createAd(specialist);
            ad.setDetails(adDetails);
            userRepository.save(specialist);
            adRepository.save(ad);
            return Map.of("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false);
        }
    }

    @DeleteMapping(value = "/v2/ad",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Boolean> remove(@RequestParam("id") UUID id) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false);
        }
    }

    @PutMapping(value = "/v2/ad",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Boolean> edit(@RequestParam("id") UUID id, @RequestBody AdDetails details) {
        try {
            Ad ad = adRepository.findById(id).orElseThrow(
                    () -> new IllegalStateException("Edited ad not in repository"));
            ad.setDetails(details);
            adRepository.save(ad);
            return Map.of("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false);
        }
    }
}