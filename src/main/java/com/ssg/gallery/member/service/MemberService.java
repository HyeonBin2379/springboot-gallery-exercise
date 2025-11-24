package com.ssg.gallery.member.service;

import com.ssg.gallery.member.entity.Member;

public interface MemberService {

    // 회원 데이터를 저장 -> 회원 이름, 로그인 아이디/비밀번호를 매개변수로 전달받음
    void save(String name, String login_id, String login_pw);

    // 회원데이터 조회 메서드, 매개변수로 로그인 아이디/비밀번호를 받고 리턴타입으로 회원 엔티티를 지정
    Member find(String login_id, String login_pw);

}
