package com.mduczmal.therapy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public TherapistPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
