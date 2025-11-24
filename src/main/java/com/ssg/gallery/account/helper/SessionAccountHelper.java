package com.ssg.gallery.account.helper;

import com.ssg.gallery.account.dto.AccountJoinRequests;
import com.ssg.gallery.account.dto.AccountLoginRequests;
import com.ssg.gallery.account.etc.AccountConstants;
import com.ssg.gallery.common.util.HttpUtils;
import com.ssg.gallery.member.entity.Member;
import com.ssg.gallery.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionAccountHelper implements AccountHelper {

    private final MemberService memberService;

    @Override
    public void join(AccountJoinRequests joinReq) {
        memberService.save(joinReq.getName(), joinReq.getLoginId(), joinReq.getLoginPw());
    }

    @Override
    public String login(AccountLoginRequests loginReq, HttpServletRequest request, HttpServletResponse response) {
        Member member = memberService.find(loginReq.getLoginId(), loginReq.getLoginPw());
        // 해당하는 회원이 없을 경우
        if (member == null) {
            return null;
        }
        // 로그인 성공 시 로그인 세션에 현재 사용자 정보를 저장 -> 세션에 저장된 정보는 로그아웃 시 삭제
        // 설정한 시간 동안에는 세션을 통해 로그인 요청에 관한 정보를 유지
        HttpUtils.setSession(request, AccountConstants.MEMBER_ID_NAME, member.getId());
        return member.getLoginId();
    }

    @Override
    public Integer getMemberId(HttpServletRequest request) {
        Object memberId = HttpUtils.getSession(request, AccountConstants.MEMBER_ID_NAME);
        // 현재 로그인한 회원이 존재함.
        if (memberId != null) {
            return (int) memberId;
        }
        // 회원이 존재하지 않음
        return null;
    }

    @Override
    public Boolean isLoggedIn(HttpServletRequest request) {
        Integer memberId = getMemberId(request);
        return memberId != null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // 로그아웃 시 로그인 세션에 저장된 현재 사용자 정보 삭제
        HttpUtils.removeSession(request, AccountConstants.MEMBER_ID_NAME);
    }
}
