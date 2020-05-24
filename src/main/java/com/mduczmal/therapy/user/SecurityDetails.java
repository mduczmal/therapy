package com.mduczmal.therapy.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public abstract class SecurityDetails implements UserDetails {
    /*
    Open-closed principle - w serwisie już istnieją 2 rodzaje użytkowników, którzy mogą się logować:
    moderatorzy i terapuci. Jeśli zajdzie taka potrzeba, można rozszerzyć funkcjonalność systemu
    o kolejny rodzaj użytkownika dziedzicząc po klasie SecurityDetails bez modyfikowania istniejącego kodu.
     */
    @Id
    private String username;
    private String password;
    private boolean enabled;
    //TODO: remove eager fetching, put @Transactional in some sensible place instead
    @OneToMany(mappedBy = "username", fetch = FetchType.EAGER)
    private List<Authority> authorities;

    public SecurityDetails() {
        this.username = "";
        this.password = "";
        this.enabled = true;
        this.authorities = new ArrayList<>();
    }

    public SecurityDetails(String username, String password, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
