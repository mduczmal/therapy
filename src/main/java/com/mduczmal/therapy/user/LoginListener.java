package com.mduczmal.therapy.user;

import com.mduczmal.therapy.cookies.Observer;
import com.mduczmal.therapy.cookies.Subject;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;


@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private final Subject subject;
    private final Observer observer;

    public LoginListener(Subject subject, Observer observer) {
        this.subject = subject;
        this.observer = observer;
    }

    @Override
    public void onApplicationEvent(@NonNull InteractiveAuthenticationSuccessEvent event)
    {
        subject.attach(observer);
    }
}
