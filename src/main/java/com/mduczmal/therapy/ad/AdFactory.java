package com.mduczmal.therapy.ad;

import com.mduczmal.therapy.user.User;
import org.springframework.stereotype.Service;

@Service
public abstract class AdFactory {
    public abstract Ad createAd(User user);
}
