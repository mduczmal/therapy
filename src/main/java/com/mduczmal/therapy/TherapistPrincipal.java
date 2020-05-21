package com.mduczmal.therapy;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class TherapistPrincipal implements UserDetails {
    @Id
    private String username;
    private String password;
    private boolean enabled;
    @OneToOne
    private Therapist therapist;
    //TODO: remove eager fetching, put @Transactional in some sensible place instead
    @OneToMany(mappedBy = "username", fetch = FetchType.EAGER)
    private List<Authority> authorities;

    public TherapistPrincipal() {
        this.username = "";
        this.password = "";
        this.enabled = true;
        this.authorities = new ArrayList<>();
    }

    public TherapistPrincipal(String username, String password, List<Authority> authorities,
                              Therapist therapist) {
        therapist.setLogin(username);
        this.therapist = therapist;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = true;
    }

    public Therapist getTherapist() {
        return therapist;
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
