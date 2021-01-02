package com.mduczmal.therapy.ad;

import org.springframework.stereotype.Service;

@Service
public abstract class AdFactory {
    public abstract Ad createAd(AdCreator creator);
}
