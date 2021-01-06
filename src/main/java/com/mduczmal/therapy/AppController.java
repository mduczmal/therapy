package com.mduczmal.therapy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping(value = {"/v2/**"})
    public String hello() {
        return "index";
    }
}
