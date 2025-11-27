package com.ssg.gallery.account.controller;

import com.ssg.gallery.account.dto.AccountJoinRequests;
import com.ssg.gallery.account.dto.AccountLoginRequests;
import com.ssg.gallery.account.etc.AccountConstants;
import com.ssg.gallery.account.helper.AccountHelper;
import com.ssg.gallery.block.service.BlockService;
import com.ssg.gallery.common.util.HttpUtils;
import com.ssg.gallery.common.util.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")      // 모든 http 메서드의 요청을 매핑하기 위한 어노테이션
public class AccountController {
    
    private final AccountHelper accountHelper;
    private final BlockService blockService;

    // 회원가입 컨트롤러
    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequests joinReq) {
        // 입력값이 하나라도 null이면 400 상태코드 반환
        if (!StringUtils.hasLength(joinReq.getName()) || !StringUtils.hasLength(joinReq.getLoginId()) || !StringUtils.hasLength(joinReq.getLoginPw())) {
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
        // 입력 값이 비어 있다면
        if (!StringUtils.hasLength(loginReq.getLoginId()) || !StringUtils.hasLength(loginReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    // ② 액세스 토큰을 재발급하는 메서드. @GetMapping 애너테이션을 지정하여 HTTP GET요청을 매핑하고, 연결 경로로 /api/account/token 을 지정한다.
    //   쿠키의 리프레시 토큰을 조회하고 이 값이 유효하다면 이 토큰을 활용하여 액세스 토큰을 발급하고 리턴한다.
    @GetMapping("/api/account/token")
    public ResponseEntity<?> regenerate(HttpServletRequest req) {
        String accessToken = "";
        String refreshToken = HttpUtils.getCookieValue(req, AccountConstants.REFRESH_TOKEN_NAME);

        // 리프레시 토큰이 유효하다면
        if (StringUtils.hasLength(refreshToken) && TokenUtils.isValid(refreshToken) && !blockService.has(refreshToken)) {
            // 리프레시 토큰의 내부 값 조회
            Map<String, Object> tokenBody = TokenUtils.getBody(refreshToken);

            // 리프레시 토큰의 회원 아이디 조회
            Integer memberId = (Integer) tokenBody.get(AccountConstants.MEMBER_ID_NAME);

            // 액세스 토큰 발급
            accessToken = TokenUtils.generate(AccountConstants.ACCESS_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memberId, AccountConstants.ACCESS_TOKEN_EXP_MINUTES);
        }

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
