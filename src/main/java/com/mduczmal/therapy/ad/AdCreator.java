package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.Identifiable;

import java.util.UUID;

public interface AdCreator extends Identifiable {
    UUID getAd();
    void setAd(UUID ad);
    void removeAd();
}
