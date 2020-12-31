package com.mduczmal.therapy;

import com.mduczmal.therapy.ad.AdFactory;
import com.mduczmal.therapy.ad.TherapyAd;
import com.mduczmal.therapy.user.User;
import com.mduczmal.therapy.user.therapist.Therapist;

public class TherapyAdFactory extends AdFactory {
    @Override
    public TherapyAd createAd(User user) {
        if (!(user instanceof Therapist)) {
            throw new IllegalArgumentException("Expected Therapist got: " + user.getClass());
        }
        Therapist t = (Therapist) user;
        if (t.getAd() != null) {
            throw new IllegalStateException("Therapist " + t.getId() + " with ad " + t.getAd() + " wanted to create another one");
        }
        TherapyAd ad = new TherapyAd(user.getId());
        t.setAd(ad.getId());
        return ad;
    }
}