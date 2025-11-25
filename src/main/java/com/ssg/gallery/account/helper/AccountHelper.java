package com.ssg.gallery.account.helper;

import com.ssg.gallery.account.dto.AccountJoinRequests;
import com.ssg.gallery.account.dto.AccountLoginRequests;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 계정 관련 보조 기능 제공 - 로그인, 회원가입 관련
public interface AccountHelper {
    // 회원가입 처리 -> 응답 객체를 받아서 내부적으로 처리
    void join(AccountJoinRequests joinReq);

    // 로그인: 로그인한 사용자의 아이디, 비밀번호를 세션에 추가한 후 리다이렉트
    String login(AccountLoginRequests loginReq, HttpServletRequest request, HttpServletResponse response);

    // 회원 아이디 조회: 세션에 저장된 사용자 정보를 조회
    Integer getMemberId(HttpServletRequest request);

    // 로그인 여부 확인: 세션에 저장된 사용자 정보를 조회
    Boolean isLoggedIn(HttpServletRequest request);

    // 로그아웃: 세션에 저장된 사용자 정보를 삭제한 후 리다이렉트
    void logout(HttpServletRequest request, HttpServletResponse response);
}
