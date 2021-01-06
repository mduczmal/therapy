package com.mduczmal.therapy.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
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
    @Override
    public String getAuthority() {
        return authority;
    }
}
