package com.mduczmal.therapy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/")
    String index() {
        return "index";
    }
    @GetMapping("/boring")
    String boring() {
        return "boring";
    }
}
