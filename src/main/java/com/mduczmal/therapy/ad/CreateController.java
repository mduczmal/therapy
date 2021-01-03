package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.user.UserRepository;
import com.mduczmal.therapy.user.UserService;
import com.mduczmal.therapy.user.therapist.Therapist;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class CreateController {
    private final AdRepository adRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AdFactory adFactory;

    public CreateController(AdRepository adRepository, UserService userService,
                            UserRepository userRepository, AdFactory adFactory){
        this.adRepository = adRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.adFactory = adFactory;
    }
    @PostMapping(value = "/v2/ad",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createAd(@RequestBody AdDetails adDetails) {
        Therapist therapist = userService.getCurrentTherapist();
        Ad ad = adFactory.createAd(therapist);
        ad.setDetails(adDetails);
        userRepository.save(therapist);
        adRepository.save(ad);
        return Collections.singletonMap("result", "ok!");
    }
}
