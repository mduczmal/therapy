package com.mduczmal.therapy.user;

import com.mduczmal.therapy.cookies.Observer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements Observer {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
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

    @Override
    public void update() {
        User user = getCurrentUser();
        if (user != null && !user.getCookiesAccepted()) {
            user.setCookiesAccepted();
            userRepository.save(user);
        }
    }
}
