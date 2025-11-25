package com.ssg.gallery.account.dto;

import lombok.Getter;
import lombok.Setter;

// 회원가입 요청 시 사용하는 DTO -> 이 DTO에 저장되는 데이터는 포론트의 회원가입 폼에서 입력된 내용
@Getter
@Setter
public class AccountJoinRequests {

    private String loginId;
    private String loginPw;
    private String name;
}
