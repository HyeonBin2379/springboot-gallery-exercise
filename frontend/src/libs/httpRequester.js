import axios from "axios";

const instance = axios.create(); // ① Axios 인스턴스 생성

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

        case 401:
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