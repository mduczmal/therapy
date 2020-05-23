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

    private Ad addSimpleAd(Therapist therapist, int num) {
        Ad ad = therapist.createAd().orElseThrow(() ->
                new IllegalStateException("Therapist has ad during initialization"));
        AdDetails details = ad.getDetails();
        details.setPrice("Pierwsza wizyta", 150);
        details.setPrice("Sesja indywidualna", 100);
        details.setName("Imię" + num);
        details.setSurname("Nazwisko" + num);
        details.setAddress("ul. Ulica 1/1 00-000 Miasto");
        details.setOnlineSessions(true);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        return ad;
    }

    private Ad addComplexAd(Therapist therapist, int num) {
        Ad ad = therapist.createAd().orElseThrow(() ->
                new IllegalStateException("Therapist has ad during initialization"));
        AdDetails details = ad.getDetails();
        details.setPrice("Pierwsza wizyta", 200);
        details.setPrice("Sesja indywidualna", 130);
        details.setName("Imię" + num);
        details.setSurname("Nazwisko" + num);
        details.setAddress("ul. Ulica 1/1 00-000 Miasto");
        details.setTelephoneNumber("123456789");
        details.setEmail("example@email.com");
        details.setTherapyApproach("poznawczo-behawioralny");
        details.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sit amet luctus venenatis lectus magna fringilla urna porttitor rhoncus. Massa tincidunt nunc pulvinar sapien et ligula. Est ultricies integer quis auctor elit sed vulputate mi. Sapien pellentesque habitant morbi tristique senectus et netus. Nisl vel pretium lectus quam id leo. Ut morbi tincidunt augue interdum. Mi quis hendrerit dolor magna eget. Diam maecenas sed enim ut sem viverra aliquet eget sit. Pretium quam vulputate dignissim suspendisse in. Consequat nisl vel pretium lectus quam id leo. Ac turpis egestas maecenas pharetra. Morbi tristique senectus et netus et malesuada fames ac turpis.");
        details.setTherapyCenter("Centrum terapeutyczne X");
        details.setTraining("Certyfikat 1\nCertyfikat 2\nSzkolenie 1");
        details.setSupervision(true);
        details.setOnlineSessions(false);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        return ad;
    }


    @Override
    public void run(String... args) {
        for (int i=1; i<7; i++) {
            Therapist therapist = addTherapist("Test" + i, "pass" + i);
            if (i>2 && i<5) {
                addSimpleAd(therapist, i);
            }
            else if (i>4) {
                addComplexAd(therapist, i);
            }
            System.out.println("Number of therapists: " + therapistRepository.count());
        }
    }
}
