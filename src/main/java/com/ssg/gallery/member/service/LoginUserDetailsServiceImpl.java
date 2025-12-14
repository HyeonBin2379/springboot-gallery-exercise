package com.ssg.gallery.member.service;

import com.ssg.gallery.member.entity.LoginUser;
import com.ssg.gallery.member.entity.Member;
import com.ssg.gallery.member.repository.MemberRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("로그인 시도: " + username);
        Optional<Member> loginMember = memberRepository.findByLoginId(username);
        return loginMember
                .map(member -> new LoginUser(member.getLoginId(), member.getLoginPw(), member.getLoginPwSalt(), member.getId(), Collections.emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException("사용자명이 존재하지 않습니다."));
    }
}
