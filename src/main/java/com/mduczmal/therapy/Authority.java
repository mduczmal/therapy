package com.mduczmal.therapy;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String authority;
    public Authority() {}
    public Authority(String username, String authority){
        this.username = username;
        this.authority = authority;
    }
    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(authority);
    }
}
