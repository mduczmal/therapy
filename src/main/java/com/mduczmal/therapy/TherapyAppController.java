package com.mduczmal.therapy;

import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private TherapistRepository therapistRepository;
    @Autowired
    private CommentRepository commentRepository;
    private List<Ad> ads;


    private void loadAds(){
        ads = StreamSupport.stream(adRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/")
    String index(Model model) {
        loadAds();
        model.addAttribute("therapist", userService.getCurrentTherapist());
        model.addAttribute("moderator", userService.getCurrentModerator());
        model.addAttribute("ads", ads);
        model.addAttribute("cookies_text", Cookies.TEXT);
        return "index";
    }

    @PostMapping(value = "/delcomment/{ad_id}/{comment_id}")
    public String removeComment(@PathVariable("ad_id") int adID, @PathVariable("comment_id") int commentID) {
        System.out.println("In del comment");
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
        loadAds();
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
