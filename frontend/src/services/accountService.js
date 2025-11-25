import axios from "axios";
// 계정 서비스 구현  : 백엔드 API를 호출해서 회원의 계정 데이터를 프론트에서 처리하는 서비스

// 회원가입: HTTP POST 메서드로 회원가입 API를 호출하고, 응답값을 리턴하는 기능
export const join = (args) => {
    return axios.post("/v1/api/account/join", args).catch(e => e.response);
};

// 로그인: HTTP POST 메서드로 로그인 API를 호출하고, 응답값을 리턴하는 기능
export const login = (args) => {
    return axios.post("/v1/api/account/login", args).catch(e => e.response);
}

// 현재 로그인 중인 사용자 체크: HTTP GET 메서드로 로그인 사용자 확인 API를 호출하고 응답값을 리턴하는 기능
export const check = () => {
    // cors error에 관한 proxy 서버를 추가했으므로, 요청한 url 경로의 https://localhost:8080 부분을 삭제
    return axios.get("/v1/api/account/check").catch(e => e.response);
}

// 로그아웃: HTTP POST 메서드로 로그아웃 API를 호출하고 응답 결과를 리턴하는 기능
export const logout = () => {
    return axios.post("/v1/api/account/logout").catch(e => e.response);
}