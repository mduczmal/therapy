package com.mduczmal.therapy.ad;

public class TherapyAdFactory extends AdFactory {
    @Override
    public TherapyAd createAd(AdCreator creator) {
        if (creator.getAd() != null) {
            throw new IllegalStateException("Therapist " + creator.getId() + " with ad " + creator.getAd() + " wanted to create another one");
        }
        TherapyAd ad = new TherapyAd(creator.getId());
        creator.setAd(ad.getId());
        return ad;
    }
}