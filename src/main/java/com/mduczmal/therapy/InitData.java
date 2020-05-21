package com.mduczmal.therapy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final AdRepository adRepository;
    private final TherapistRepository therapistRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public InitData(AdRepository adRepository, TherapistRepository therapistRepository,
                    UserRepository userRepository, AuthorityRepository authorityRepository,
                    PasswordEncoder passwordEncoder) {
        this.adRepository = adRepository;
        this.therapistRepository = therapistRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Therapist addTherapist(String username, String password) {
        Therapist therapist = new Therapist(username);
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority(username, "ROLE_THERAPIST");
        authorities.add(authority);
        TherapistPrincipal therapistPrincipal = new TherapistPrincipal(therapist.getLogin(),
                passwordEncoder.encode(password), authorities, therapist);
        therapistRepository.save(therapist);
        userRepository.save(therapistPrincipal);
        authorityRepository.save(authority);
        return therapist;
    }

    private Ad addSimpleAd(Therapist therapist) {
        Ad ad = new Ad(therapist);
        AdDetails details = ad.getDetails();
        details.setPrice("Pierwsza wizyta", 150);
        details.setPrice("Sesja indywidualna", 100);
        details.setName("Pan/Pani " + therapist.getLogin());
        details.setSurname("Nazwisko");
        details.setAddress("ul. Ulica 1/1 00-000 Miasto");
        details.setOnlineSessions(true);
        adRepository.save(ad);
        return ad;
    }


    @Override
    public void run(String... args) throws Exception {
        for (int i=1; i<3; i++) {
            Therapist therapist = addTherapist("Test" + i, "pass" + i);
            addSimpleAd(therapist);
            System.out.println("Number of therapists: " + therapistRepository.count());
        }
    }
}
