package com.mduczmal.therapy.user;

import com.mduczmal.therapy.cookies.Cookies;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;


@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private final UserService userService;
    private final Cookies cookies;


    public LoginListener(Cookies cookies, UserService userService) {
        this.cookies = cookies;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(@NonNull InteractiveAuthenticationSuccessEvent event)
    {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null && currentUser.getCookiesAccepted()) {
            cookies.accept();
        }
    }
}
