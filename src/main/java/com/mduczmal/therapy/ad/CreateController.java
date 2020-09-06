package com.mduczmal.therapy.ad;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class CreateController {
    @PostMapping(value = "/v2/ad",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createAd(@RequestBody AdDetails adDetails) {
        System.out.println(adDetails);
        return Collections.singletonMap("result", "ok!");
    }
}
