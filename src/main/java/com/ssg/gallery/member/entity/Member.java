package com.ssg.gallery.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String loginPw;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    public Member() {
    }

    public Member(String name, String login_id, String login_pw) {
        this.name = name;
        this.loginId = login_id;
        this.loginPw = login_pw;
    }
}
