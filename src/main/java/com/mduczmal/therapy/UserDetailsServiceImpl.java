package com.mduczmal.therapy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public TherapistPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TherapistPrincipal> otp = userRepository.findById(username);
        if (otp.isEmpty()) throw new UsernameNotFoundException("User " + " not found");
        return otp.get();
    }
}
