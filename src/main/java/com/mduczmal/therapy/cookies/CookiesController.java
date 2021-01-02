package com.mduczmal.therapy.cookies;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CookiesController {
    private final Cookies cookies;

    public CookiesController(Cookies cookies) {
        this.cookies = cookies;
    }

    @GetMapping(value = "/v2/cookies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Boolean> cookies() {
        return Map.of("cookies", cookies.areAccepted());
    }

    @PostMapping("/v2/cookies")
    public Map<String, Object> acceptCookies() {
        cookies.accept();
        return Map.of();
    }
}

