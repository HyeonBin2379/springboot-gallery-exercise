package com.ssg.gallery.account.controller;

import com.ssg.gallery.account.dto.AccountJoinRequests;
import com.ssg.gallery.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")      // 모든 http 메서드의 요청을 매핑하기 위한 어노테이션
public class AccountController {

    private final MemberService memberService;

    // 회원가입 컨트롤러
    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequests joinReq) {
        // 입력값이 하나라도 null이면 400 상태코드 반환
        if (!StringUtils.hasLength(joinReq.getName())
                || !StringUtils.hasLength(joinReq.getLoginId())
                || !StringUtils.hasLength(joinReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 입력값이 유효하면 회원정보 등록 후 200 상태코드 반환
        // MemberService 내부에서 비밀번호 암호화
        memberService.save(joinReq.getName(), joinReq.getLoginId(), joinReq.getLoginPw());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인 여부 확인
    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        // 현재 로그인한 사용자가 유효
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
