package com.ssg.gallery.member.service;

import com.ssg.gallery.member.entity.Member;
import com.ssg.gallery.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Integer save(String name, String loginId, String loginPw) {
        validateDuplicateMember(loginId);

        String encodedPassword = passwordEncoder.encode(loginPw);

        // JPA 리포지토리가 제공하는 save 메서드 사용 -> 새로운 Member 타입 엔터티를 업데이트
        Member member = new Member(name, loginId, encodedPassword, null);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(String loginId) {
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
