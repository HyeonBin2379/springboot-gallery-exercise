<script setup>
import Header from "@/components/Header.vue"
import Footer from "@/components/Footer.vue";

// 레이아웃에 계정 기능 적용
import {useAccountStore} from "@/stores/account";
import {watch} from "vue";
import {useRoute} from "vue-router";
import {check} from "@/services/accountService"

// 계정 스토어(저장소) -> 프론트엔드에서의 세션 역할
const accountStore = useAccountStore();

// 라우트 객체
const route = useRoute();

// 로그인 처리 여부를 확인
const checkAccount = async () => {
  // await: check()의 결과값을 받아와야 다음 작업 진행 가능
  const res = await check();

  if (res.status === 200) {
    accountStore.setChecked(true);
    accountStore.setLoggedIn(res.data === true);
  } else {
    accountStore.setChecked(false);
  }
}

// 커스텀 생성 훅: 뷰 애플리케이션 생성 시 계정 로그인 여부 확인(비동기 처리)
(async function onCreated() {
  // checkAccount()의 실행이 완료되어야 뷰를 실행
  await checkAccount();
})();

// 라우트 경로가 변경될 때마다 로그인 여부 확인
watch(() => route.path, () => {
  checkAccount();
})
</script>

<!-- 로그인 체크 여부 확인 후 출력 -->
<template>
  <template v-if="accountStore.checked">
    <Header/>
    <main>
      <!--  라우터 뷰  -->
      <router-view></router-view>
    </main>
    <Footer/>
  </template>
</template>