package com.ssg.gallery.account.controller;

import com.ssg.gallery.account.dto.AccountJoinRequests;
import com.ssg.gallery.account.dto.AccountLoginRequests;
import com.ssg.gallery.account.helper.AccountHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")      // 모든 http 메서드의 요청을 매핑하기 위한 어노테이션
public class AccountController {
    
    private final AccountHelper accountHelper;

    // 회원가입 컨트롤러
    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequests joinReq) {
        // 입력값이 하나라도 null이면 400 상태코드 반환
        if (joinReq.getName() == null || joinReq.getLoginId() == null || joinReq.getLoginPw() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 입력값이 유효하면 회원정보 등록 후 200 상태코드 반환
        accountHelper.join(joinReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인 컨트롤러
    // 메시지 바디 사용 + 아이디/비밀번호 노출 방지 -> POST 사용
    @PostMapping(value = "/api/account/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountLoginRequests loginReq) {
        if (loginReq.getLoginId() == null || loginReq.getLoginPw() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    // 400 status
        }
        String output = accountHelper.login(loginReq, request, response);
        if (output == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 status
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    // 로그인 여부 확인
    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(HttpServletRequest request) {
        // 현재 로그인한 사용자가 유효
        return new ResponseEntity<>(accountHelper.isLoggedIn(request), HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/api/account/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        accountHelper.logout(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
