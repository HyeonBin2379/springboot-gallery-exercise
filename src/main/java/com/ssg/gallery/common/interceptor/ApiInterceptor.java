package com.ssg.gallery.common.interceptor;

import com.ssg.gallery.account.helper.AccountHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// ① 스프링 인터셉터: 스프링 컨테이너에서 관리하는 컴포넌트
@Component
@RequiredArgsConstructor
public class ApiInterceptor implements HandlerInterceptor {

    private final AccountHelper accountHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ② 핸들러 인터셉터 인터페이스에서 정의한 메서드 구현. preHandle메서드 추후 구현 일단 true 리턴
        //   true를 반환하면 클라이언트의 요청이 이전과 동일하게 컨트롤러로 전달된다.
        //   false를 반환하면 클라이언트의 요청은 인터셉터를 거치게 됨
        if (accountHelper.getMemberId(request) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
