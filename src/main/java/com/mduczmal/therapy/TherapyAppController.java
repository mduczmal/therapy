package com.mduczmal.therapy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
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

    private SecurityDetails getCurrentUser() {
        Object user = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (user instanceof SecurityDetails) {
            return (SecurityDetails) user;
        }
        else {
            return null;
        }
    }

    private Therapist getCurrentTherapist() {
        SecurityDetails currentUser = getCurrentUser();
        if (currentUser == null) return null;
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
        if (authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_THERAPIST"))) {
            Therapist oldTherapist = ((TherapistPrincipal) currentUser).getTherapist();
            return therapistRepository.findById(oldTherapist.getId()).orElse(null);
        } else {
            return null;
        }
    }
    private UserModerator getCurrentModerator() {
        SecurityDetails currentUser = getCurrentUser();
        if (currentUser == null) return null;
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
        if (authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_MODERATOR"))) {
            return (UserModerator) currentUser;
        } else {
            return null;
        }
    }

    private void loadAds(){
        ads = StreamSupport.stream(adRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/")
    String index(Model model) {
        loadAds();
        model.addAttribute("therapist", getCurrentTherapist());
        model.addAttribute("moderator", getCurrentModerator());
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
        model.addAttribute("moderator", getCurrentModerator());
        if (ads == null) {
            loadAds();
        }
        Ad ad = ads.get(id);
        model.addAttribute("ad", ad);
        boolean selfComment = therapist != null && therapist.getAd() == ad.getId();
        Comment comment = new Comment(ad.getId(), selfComment);
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
        commentRepository.deleteAll(ad.getComments());
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
