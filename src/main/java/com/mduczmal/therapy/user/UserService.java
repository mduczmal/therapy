package com.mduczmal.therapy.user;

import com.mduczmal.therapy.therapist.Therapist;
import com.mduczmal.therapy.therapist.TherapistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    @Autowired
    TherapistRepository therapistRepository;

    public SecurityDetails getCurrentUser() {
        Object user = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (user instanceof SecurityDetails) {
            return (SecurityDetails) user;
        }
        else {
            return null;
        }
    }

    public Therapist getCurrentTherapist() {
        SecurityDetails currentUser = getCurrentUser();
        if (currentUser == null) return null;
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
        if (authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_THERAPIST"))) {
            Therapist oldTherapist = ((UserTherapist) currentUser).getTherapist();
            //db query allows to create next ad immediately after removing the old one
            //without logout and login
            return therapistRepository.findById(oldTherapist.getId()).orElse(null);
        } else {
            return null;
        }
    }

    public UserModerator getCurrentModerator() {
        SecurityDetails currentUser = getCurrentUser();
        if (currentUser == null) return null;
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
        if (authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_MODERATOR"))) {
            return (UserModerator) currentUser;
        } else {
            return null;
        }
    }
}
