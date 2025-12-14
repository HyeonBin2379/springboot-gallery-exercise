import axios from "axios";
// ① 계정 스토어 객체 생성시 사용하는 함수 임포트

// ① Axios 인스턴스 생성
const instance = axios.create({
    withCredentials: true,
});

// 인터셉터(응답 시)
instance.interceptors.response.use((res) => {
// ② HTTP 응답 값을 받았을때 실행되는 인터셉터 메서드
//   HTTP 상태 코드가 400(Bad Request: 잘못된 요청)이라면 오류 안내 창에 출력
//                   401(Unauthorized: 권한 없음)이라면 안내 창 출력 후 메인페이지 이동
//                   500(Internal Server Error: 서버 내부 오류) 오류 안내 창 출력

    return res;
}, async (err) => {
    switch (err.response.status) {
        case 400:
            window.alert("잘못된 요청입니다.");
            break;

        // ② HTTP 응답코드가 401 이라면 액세스 토큰이 만료된 것일 수 있으므로 쿠키에 있는 리프레시 토큰으로 액세스 토큰을 다시 요청한다.
        //   쿠키는 HTTP요청시 자동으로 포함되므로, 액세스 토큰을 다시 받았다면 토큰을 교체하여 HTTP 요청을 다시 수행한다.
        //    해당 요청의 HTTP 응답 상태 코드가 이전과 동일한 401 일 수도 있으므로 방지를 위해 요청 설정(config)에 config.retried = true 설정한다.
        case 401:
            // 로그인 시도 중에 401이 뜬 거라면(아이디 비번 틀림) 페이지 이동하면 안 됨
            if (err.config.url.includes('/v1/api/account/login')) {
                // 로그인 화면에서 처리하도록 에러를 그냥 던짐
                return Promise.reject(err);
            }

            window.alert("권한이 없습니다.");
            window.location.replace("/");
            break;

        case 500:
            window.alert("오류가 있습니다. 관리자에게 문의해주세요.");
            break;
    }

    return Promise.reject(err);
});

export default {
    get(url, params) { // ③ Axios 객체의 메서드 호출하여 HTTP GET 요청
        return instance.get(url, {params});
    },
    post(url, params) { // ④ Axios 객체의 메서드 호출하여 HTTP POST 요청
        return instance.post(url, params);
    },
    put(url, params) { // ⑤ Axios 객체의 메서드 호출하여 HTTP PUT 요청
        return instance.put(url, params);
    },
    delete(url) { // ⑥ Axios 객체의 메서드 호출하여 HTTP DELETE 요청
        return instance.delete(url);
    }
};