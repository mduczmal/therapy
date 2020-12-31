package com.mduczmal.therapy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //Interface Segregation Principle - klasa korzysta z repozytorium zawierającego dane uwierzytelniania
    //Jako klient tego interfejsu nie musi się przejmować tym niezależnie czy dane należą do moderatora czy terapeuty
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public SecurityDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityDetails> osd = userRepository.findById(username);
        return osd.orElseThrow(() -> new UsernameNotFoundException("User " + " not found"));
    }
}
