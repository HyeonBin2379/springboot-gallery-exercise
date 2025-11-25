package com.ssg.gallery.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {
    // 세션: 서버가 생성될 때부터 소멸할 때까지 유지
    // 세션에 로그인한 사용자 정보를 저장
    public static void setSession(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    // 세션 값 조회 -> 사용자가 회원인지를 확인
    public static Object getSession(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    // 세션 삭제 -> 로그아웃 처리
    public static void removeSession(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }
}
