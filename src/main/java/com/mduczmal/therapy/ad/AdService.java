package com.mduczmal.therapy.ad;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    private final AdRepository adRepository;

    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public List<Ad> load() {
        return adRepository.findAll();
    }
}
