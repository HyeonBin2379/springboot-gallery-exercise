package com.ssg.gallery.account.etc;

// 계정 관련 상수 정의 클래스
public class AccountConstants {

    // 반복적으로 사용될 회원 아이디를 정의한 문자열
    public static final String MEMBER_ID_NAME = "member_id";

    // 액세스 토큰 이름 : 토큰기능에서 사용될 액세스 토큰의 이름 정의한 문자열 상수
    // 사용자 정보에 접근할 때 사용하는 토큰
    public static final String ACCESS_TOKEN_NAME = "accessToken"; // ①

    // 리프레시 토큰 이름 : 토큰기능에서 사용될 리프레시 토큰의 이름을 정의한 문자열 상수
    // 토큰의 유효시간을 갱신할 때 사용
    public static final String REFRESH_TOKEN_NAME = "refreshToken"; // ②

    // 액세스 토큰 유효 시간(1분) 토큰 기능에서 사용될 액세스 토큰의 유효시간을 정의한 정수형 상수
    // 보안성을 높이기 위해, 가급적이면 짧게 설정하는 것이 좋으나, 너무 짧으면 토큰을 자주 발행해야 한다는 번거로움이 존재
    public static final int ACCESS_TOKEN_EXP_MINUTES = 1; // ③

    // 리프레시 토큰 유효 시간(24시간) 리프레시 토큰의 유효시간을 정의한 정수형 상수
    public static final int REFRESH_TOKEN_EXP_MINUTES = 60 * 24; // ④
}
