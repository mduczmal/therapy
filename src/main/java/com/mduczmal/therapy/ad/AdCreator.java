package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.UniversallyIdentifiable;

import java.util.UUID;

public interface AdCreator extends UniversallyIdentifiable {
    UUID getAd();
    void setAd(UUID ad);
    void removeAd();
}
