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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AdController {
    //Dependency injection design pattern
    @Autowired
    private UserService userService;
    @Autowired
    private AdService adService;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private TherapistRepository therapistRepository;
    @Autowired
    private CommentRepository commentRepository;

    private List<Ad> ads;


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
        ads = adService.load();
        Ad ad = ads.get(id);
        model.addAttribute("ad", ad);
        Comment comment = new Comment(ad.getId());
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
