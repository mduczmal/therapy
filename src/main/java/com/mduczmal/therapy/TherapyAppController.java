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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class TherapyAppController {
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private TherapistRepository therapistRepository;
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
            return therapistPrincipal.getTherapist();
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
        if (ads != null) {
            model.addAttribute("ad", ads.get(id));
        } else {
            loadAds();
            model.addAttribute("ad", ads.get(id));
        }
        return "details";
    }

    @GetMapping("/create")
    String create(Model model) {
        Therapist therapist = getCurrentTherapist();
        if (therapist.getAd() != null) return "redirect:/";
        model.addAttribute("details", new AdDetails());
        return "create";
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
