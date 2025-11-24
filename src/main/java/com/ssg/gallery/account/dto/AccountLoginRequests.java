package com.ssg.gallery.account.dto;

import lombok.Getter;

// 로그인 요청 시 사용
@Getter
public class AccountLoginRequests {

    private String loginId;
    private String loginPw;
}
