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
@Table(name = "users")
public class UserAccount implements UserDetails {

    @Id
    private String username;
    private String password;
    private boolean enabled;
    @OneToMany(mappedBy = "username", fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @OneToOne
    private final User user;

    public UserAccount() {
        this.user = null;
        this.username = "";
        this.password = "";
        this.enabled = true;
        this.authorities = new ArrayList<>();
    }

    public UserAccount(User user, String username, String password, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = true;
        this.user = user;
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

    public boolean hasRole(String role) {
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals(role));
    }
    public User getUser() {
        return user;
    }

}
