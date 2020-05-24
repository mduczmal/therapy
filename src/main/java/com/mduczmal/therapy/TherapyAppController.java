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
    /*
    Single Responsibility - klasa wyświetla ogłoszenia w systemie. Nie deklaruje żadnych metod poza obsługującymi
    zapytania http. Zamiast korzystać z takich metod używa serwisów, dostarczanych przez wstrzykiwanie zależności.
    Open-Closed Principle - można łatwo rozszerzyć funkcjonalność systemu np. o dodawanie ważnej informacji dla
    użytkowników przez moderatora poprzez dodanie nowej metody
     */

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
