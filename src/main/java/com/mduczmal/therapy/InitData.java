package com.mduczmal.therapy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    private void addTherapist(String username, String password) {
        Therapist therapist = new Therapist(username);
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority(username, "ROLE_THERAPIST");
        authorities.add(authority);
        TherapistPrincipal therapistPrincipal = new TherapistPrincipal(therapist.getLogin(),
                passwordEncoder.encode(password), authorities, therapist);
        Ad ad = new Ad(therapist);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        userRepository.save(therapistPrincipal);
        authorityRepository.save(authority);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=1; i<10; i++) {
            addTherapist("Test" + i, "pass" + i);
            System.out.println("Number of therapists: " + therapistRepository.count());
        }
    }
}
