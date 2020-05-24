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
    public SecurityDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityDetails> osd = userRepository.findById(username);
        if (osd.isEmpty()) throw new UsernameNotFoundException("User " + " not found");
        return osd.get();
    }
}
