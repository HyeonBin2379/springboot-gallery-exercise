package com.ssg.gallery.member.entity;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class LoginUser extends User {

    private final String salt;
    private final Integer id;

    public LoginUser(String username, String password, String salt, Integer id,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.salt = salt;
        this.id = id;
    }
}
