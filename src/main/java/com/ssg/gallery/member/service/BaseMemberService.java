package com.ssg.gallery.member.service;

import com.ssg.gallery.common.util.HashUtils;
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
    public void save(String name, String loginId, String loginPw) {
        // 솔트 생성
        String loginPwSalt = HashUtils.generateSalt(16);

        // 입력한 패스워드에 솔트를 적용
        String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

        // JPA 리포지토리가 제공하는 save 메서드 사용 -> 새로운 Member 타입 엔터티를 업데이트
        memberRepository.save(new Member(name, loginId, loginPwSalted, loginPwSalt));
    }

    // 회원 데이터 조회
    @Override
    public Member find(String loginId, String loginPw) {
        // 로그인한 아이디로 회원 조회
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // 솔트 조회
            String loginPwSalt = memberOptional.get().getLoginPwSalt();

            // 입력 패스워드에 솔트 적용
            String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

            if (member.getLoginPw().equals(loginPwSalted)) {
                return member;
            }
        }
        // 회원 데이터가 존재하면 해당 회원을, 없으면 null을 리턴
        return null;
    }
}
