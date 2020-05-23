package com.mduczmal.therapy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class TherapyAppController {
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private TherapistRepository therapistRepository;
    @Autowired
    private CommentRepository commentRepository;
    private List<Ad> ads;

    private TherapistPrincipal getCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (principal instanceof TherapistPrincipal){
            return (TherapistPrincipal) principal;
        } else {
            return null;
        }
    }

    private Therapist getCurrentTherapist() {
        TherapistPrincipal therapistPrincipal = getCurrentUser();
        if (therapistPrincipal == null) {
            return null;
        } else {
            Therapist oldTherapist = therapistPrincipal.getTherapist();
            return therapistRepository.findById(oldTherapist.getId()).orElse(null);
        }
    }

    private void loadAds(){
        ads = StreamSupport.stream(adRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    private List<Comment> loadComments() {
        return StreamSupport.stream(commentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    @GetMapping("/")
    String index(Model model) {
        loadAds();
        model.addAttribute("therapist", getCurrentTherapist());
        //TODO: getmoderator instead of null
        model.addAttribute("moderator", null);
        model.addAttribute("ads", ads);
        model.addAttribute("cookies_text", Cookies.TEXT);
        return "index";
    }

    @GetMapping("/boring")
    String boring() {
        return "boring";
    }

    @GetMapping(value = "/ads/{id}")
    public String details(@PathVariable("id") int id, Model model) {
        Therapist therapist = getCurrentTherapist();
        model.addAttribute("therapist", therapist);
        //TODO: getmoderator instead of null
        model.addAttribute("moderator", null);
        if (ads == null) {
            loadAds();
        }
        Ad ad = ads.get(id);
        model.addAttribute("ad", ad);
        Comment comment;
        if (therapist != null && therapist.getAd() == ad.getId()) {
            comment = new Comment(true);
        } else {
            comment = new Comment();
        }
        model.addAttribute("new_comment", comment);
        model.addAllAttributes(ad.getComments());
        return "details";
    }

    @GetMapping("/create")
    String create(Model model) {
        Therapist therapist = getCurrentTherapist();
        if (therapist.getAd() != null) return "redirect:/";
        model.addAttribute("details", new AdDetails());
        return "create";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable("id") UUID id) {
        Ad ad = adRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Removed ad not in repository"));
        therapistRepository.findById(ad.getTherapist()).ifPresent(t -> {
            t.removeAd();
            therapistRepository.save(t);
        });
        adRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/create")
    String submitCreate(@Valid @ModelAttribute("details") AdDetails details, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        Therapist therapist = getCurrentTherapist();
        Optional<Ad> opa = therapist.createAd();
        if (opa.isEmpty()) throw new IllegalStateException(
                "Therapist with existing ad was allowed to create another one");
        Ad ad = opa.get();
        ad.setDetails(details);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        return "redirect:/";
    }
}
