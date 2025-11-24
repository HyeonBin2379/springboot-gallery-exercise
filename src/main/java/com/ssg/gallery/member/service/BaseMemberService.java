package com.ssg.gallery.member.service;

import com.ssg.gallery.member.entity.Member;
import com.ssg.gallery.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void save(String name, String login_id, String login_pw) {
        // JPA 리포지토리가 제공하는 save 메서드 사용 -> 새로운 Member 타입 엔터티를 업데이트
        memberRepository.save(new Member(name, login_id, login_pw));
    }

    @Override
    public Member find(String login_id, String login_pw) {
        Optional<Member> member = memberRepository.findByLoginIdAndLoginPw(login_id, login_pw);
        // 회원 데이터가 존재하면 해당 회원을, 없으면 null을 리턴
        return member.orElse(null);
    }
}
