package com.mduczmal.therapy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class TherapyAppController {
    @Autowired
    private AdRepository adRepository;
    private List<Ad> ads;

    private void loadAds(){
        ads = StreamSupport.stream(adRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    @GetMapping("/")
    String index(Model model) {
        loadAds();
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
        /*TherapistPrincipal therapistPrincipal = (TherapistPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Therapist therapist = therapistPrincipal.getTherapist();
        Optional<Ad> opa = therapist.createAd();
        if (opa.isEmpty()) return "index";
        AdDetails details = opa.get().getDetails();
        model.addAttribute(details);*/
        model.addAttribute("details", new AdDetails());
        return "create";
    }

    @PostMapping("/create")
    String submitCreate(@Valid @ModelAttribute("details") AdDetails details, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        return "redirect:/";
    }
}
