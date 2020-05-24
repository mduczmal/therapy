package com.mduczmal.therapy.ad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdService {
    /*
    Single Responsibility - klasa dostarcza kontrolerowi metodę zwracającą listę ogłoszeń z bazy danych
    1. Ta klasa ma pojedynczą odpowiedzialność
    2. Kontroler zachowuje pojedynczą odpowiedzialność
     */
    @Autowired
    AdRepository adRepository;
    public List<Ad> load() {
        return StreamSupport.stream(adRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
