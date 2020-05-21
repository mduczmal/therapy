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

    public InitData(AdRepository adRepository, TherapistRepository therapistRepository,
                    UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.adRepository = adRepository;
        this.therapistRepository = therapistRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Therapist therapist = new Therapist("Test1");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        TherapistPrincipal therapistPrincipal = new TherapistPrincipal(therapist.getLogin(),
                passwordEncoder.encode("hello"), authorities, therapist);
        Ad ad = new Ad(therapist);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        userRepository.save(therapistPrincipal);
        System.out.println("Number of therapists: " + therapistRepository.count());
    }
}
