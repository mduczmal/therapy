package com.mduczmal.therapy.user;

import com.mduczmal.therapy.cookies.Observer;
import com.mduczmal.therapy.user.moderator.Moderator;
import com.mduczmal.therapy.user.therapist.Therapist;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements Observer {
    /*
    Single Responsibility - klasa dostarcza dla kontrolera metod dotyczących obecnie zalogowanego użytkownika
    1. Ta klasa ma pojedynczą odpowiedzialność
    2. Kontroler zachowuje pojedynczą odpowiedzialność
     */
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;

    public UserService(UserAccountRepository userAccountRepository, UserRepository userRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userRepository = userRepository;
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

    public User getCurrentUser() {
        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) return null;
        else return userAccount.getUser();
    }

    public Therapist getCurrentTherapist() {
        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) return null;
        if (userAccount.hasRole("ROLE_THERAPIST")) {
            //db query allows to create next ad immediately after removing the old one
            //without logout and login
            Optional<UserAccount> updated = userAccountRepository.findById(userAccount.getUsername());
            return updated.map(account -> (Therapist) account.getUser()).orElse(null);
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

    @Override
    public void update() {
        User user = getCurrentUser();
        if (user != null && !user.getCookiesAccepted()) {
            user.setCookiesAccepted();
            userRepository.save(user);
        }
    }
}
