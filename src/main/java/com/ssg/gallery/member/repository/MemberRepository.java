package com.ssg.gallery.member.repository;

import com.ssg.gallery.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Member 테이블의 각 행을 기본키로 구분
// JpaRepository를 상속받은 클래스는 빈으로 등록되어 JPA 컨테이너의 관리 대상이 됨
public interface MemberRepository extends JpaRepository<Member, Integer> {

    // null 처리를 위해 Optional 사용 -> 조회된 결과가 없는 경우도 안전하게 처리
    // 로그인 아이디, 패스워드로 회원 정보를 조회
    Optional<Member> findByLoginIdAndLoginPw(String loginId, String loginPw);

    // 아이디로 회원정보 조회
    Optional<Member> findByLoginId(String loginId);
}
