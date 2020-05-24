package com.mduczmal.therapy;

import com.mduczmal.therapy.ad.*;
import com.mduczmal.therapy.therapist.Cookies;
import com.mduczmal.therapy.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TherapyAppController {
    //dependency injection design pattern
    @Autowired
    private UserService userService;
    @Autowired
    private AdService adService;

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("therapist", userService.getCurrentTherapist());
        model.addAttribute("moderator", userService.getCurrentModerator());
        model.addAttribute("ads", adService.load());
        model.addAttribute("cookies_text", Cookies.TEXT);
        return "index";
    }
}
