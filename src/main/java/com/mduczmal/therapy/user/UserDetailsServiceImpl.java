package com.mduczmal.therapy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserAccountRepository userAccountRepository;
    @Autowired
    public void setUserRepository(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }
    @Override
    public UserAccount loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> osd = userAccountRepository.findById(username);
        return osd.orElseThrow(() -> new UsernameNotFoundException("User " + " not found"));
    }
}
