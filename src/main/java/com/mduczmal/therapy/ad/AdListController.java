package com.mduczmal.therapy.ad;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdListController {

    private final AdRepository adRepository;

    public AdListController(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @PostMapping(value = "/v2/ads",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ad> ads() {
        return adRepository.findAll();
    }
}
