package com.mduczmal.therapy.ad;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    /*
    Single Responsibility - klasa dostarcza kontrolerowi metodę zwracającą listę ogłoszeń z bazy danych
    1. Ta klasa ma pojedynczą odpowiedzialność
    2. Kontroler zachowuje pojedynczą odpowiedzialność
     */
    private final AdRepository adRepository;

    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public List<Ad> load() {
        return adRepository.findAll();
    }
}
