import {defineStore} from 'pinia'

// 계정 데이터를 저장하기 위한 스토어 - 세션 기능을 대리하는 저장소
// pinia의 defineStore() 메서드를 호출 -> 계정 스토어를 정의
export const useAccountStore = defineStore(
    // 스토어의 고유 식별자 ID
    "account",
    {
        // 상태 정보 프로퍼티: 회원의 로그인 상태를 표현 -> 백엔드까지 가서 확인하지 않고도 상태 정보 확인 가능
        state: () => ({
            checked: false,
            loggedIn: false,
        }),
        actions: {
            // 로그인 관련 상태정보를 변경
            // 사용자의 로그인 체크 여부 변경
            setChecked(val) {
                this.checked = val;
            },
            // 사용자의 로그인 여부를 수정
            setLoggedIn(val) {
                this.loggedIn = val;
            },
        }
    });