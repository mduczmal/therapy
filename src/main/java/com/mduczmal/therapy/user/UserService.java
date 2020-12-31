package com.mduczmal.therapy.user;

import com.mduczmal.therapy.moderator.Moderator;
import com.mduczmal.therapy.therapist.Therapist;
import com.mduczmal.therapy.therapist.TherapistRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    /*
    Single Responsibility - klasa dostarcza dla kontrolera metod dotyczących obecnie zalogowanego użytkownika
    1. Ta klasa ma pojedynczą odpowiedzialność
    2. Kontroler zachowuje pojedynczą odpowiedzialność
     */
    private final TherapistRepository therapistRepository;

    public UserService(TherapistRepository therapistRepository) {
        this.therapistRepository = therapistRepository;
    }

    public UserAccount getCurrentUserAccount() {
        Object userAccount = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (userAccount instanceof UserAccount) {
            return (UserAccount) userAccount;
        }
        else {
            return null;
        }
    }

    public Therapist getCurrentTherapist() {
        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) return null;
        if (userAccount.hasRole("ROLE_THERAPIST")) {
            Therapist oldTherapist = (Therapist) userAccount.getUser();
            //db query allows to create next ad immediately after removing the old one
            //without logout and login
            return therapistRepository.findById(oldTherapist.getId()).orElse(null);
        } else {
            return null;
        }
    }

    public Moderator getCurrentModerator() {
        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) return null;
        if (userAccount.hasRole("ROLE_MODERATOR")) {
            return (Moderator) userAccount.getUser();
        } else {
            return null;
        }
    }
}
