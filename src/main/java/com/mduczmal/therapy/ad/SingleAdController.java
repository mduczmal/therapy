package com.mduczmal.therapy.ad;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SingleAdController {
    private final AdRepository adRepository;

    public SingleAdController(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @GetMapping(value = "/v2/ad",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Ad ad(@RequestParam UUID id) {
        return adRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}