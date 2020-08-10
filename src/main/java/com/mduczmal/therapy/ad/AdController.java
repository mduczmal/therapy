package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.ad.comment.Comment;
import com.mduczmal.therapy.ad.comment.CommentRepository;
import com.mduczmal.therapy.therapist.Therapist;
import com.mduczmal.therapy.therapist.TherapistRepository;
import com.mduczmal.therapy.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class AdController {
    /*
    Single Responsibility - klasa kontroluje zmiany stanu ogłoszenia. Nie deklaruje żadnych metod poza obsługującymi
    zapytania http. Zamiast korzystać z takich metod używa serwisów, dostarczanych przez wstrzykiwanie zależności.
    Dependency Inversion - ponieważ klasy repozytoriów są interfejsami,
    kontroler jest niezależny od szegółów implementacyjnych mechanizmu utrwalania danych.
     */

    //Dependency injection design pattern
    private final UserService userService;
    private final AdService adService;
    private final AdRepository adRepository;
    private final TherapistRepository therapistRepository;
    private final CommentRepository commentRepository;

    private List<Ad> ads;

    public AdController(UserService userService, AdService adService, AdRepository adRepository, TherapistRepository therapistRepository, CommentRepository commentRepository) {
        this.userService = userService;
        this.adService = adService;
        this.adRepository = adRepository;
        this.therapistRepository = therapistRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping(value="/comment/{ad_id}")
    public String addComment(@PathVariable("ad_id") int adID,  @ModelAttribute("new_comment") Comment comment) {
        Ad ad = ads.get(adID);
        comment.setAd(ad.getId());
        Comment saved = commentRepository.save(comment);
        ad.addComment(saved);
        adRepository.save(ad);
        return "redirect:/ads/{ad_id}";
    }

    @PostMapping(value = "/delcomment/{ad_id}/{comment_id}")
    public String removeComment(@PathVariable("ad_id") int adID, @PathVariable("comment_id") int commentID) {
        Ad ad = ads.get(adID);
        List<Comment> adComments = ad.getComments();
        Comment comment = adComments.get(commentID);
        adComments.remove(commentID);
        adRepository.save(ad);
        commentRepository.deleteById(comment.getId());
        return "redirect:/ads/{ad_id}";
    }

    @GetMapping(value = "/ads/{id}")
    public String details(@PathVariable("id") int id, Model model) {
        Therapist therapist = userService.getCurrentTherapist();
        model.addAttribute("therapist", therapist);
        model.addAttribute("moderator", userService.getCurrentModerator());
        ads = adService.load();
        Ad ad = ads.get(id);
        model.addAttribute("ad", ad);
        model.addAttribute("new_comment", new Comment());
        model.addAllAttributes(ad.getComments());
        return "details";
    }

    @GetMapping("/create")
    String create(Model model) {
        Therapist therapist = userService.getCurrentTherapist();
        if (therapist.getAd() != null) return "redirect:/";
        model.addAttribute("details", new AdDetails());
        return "create";
    }

    @PostMapping("/create")
    String submitCreate(@Valid @ModelAttribute("details") AdDetails details, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        Therapist therapist = userService.getCurrentTherapist();
        Optional<Ad> opa = therapist.createAd();
        if (opa.isEmpty()) throw new IllegalStateException(
                "Therapist with existing ad was allowed to create another one");
        Ad ad = opa.get();
        ad.setDetails(details);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        return "redirect:/";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable("id") UUID id) {
        Ad ad = adRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Removed ad not in repository"));
        therapistRepository.findById(ad.getTherapist()).ifPresent(t -> {
            t.removeAd();
            therapistRepository.save(t);
        });
        commentRepository.deleteAll(ad.getComments());
        adRepository.deleteById(id);
        return "redirect:/";
    }

}
