package com.mduczmal.therapy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {
    private final AdRepository adRepository;
    private final TherapistRepository therapistRepository;

    public InitData(AdRepository adRepository, TherapistRepository therapistRepository) {
        this.adRepository = adRepository;
        this.therapistRepository = therapistRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Therapist therapist = new Therapist("Test-therapist");
        Ad ad = new Ad(therapist);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        System.out.println("Number of therapists: " + therapistRepository.count());
    }
}
