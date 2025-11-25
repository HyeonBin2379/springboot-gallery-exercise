<script setup>
import {useAccountStore} from "@/stores/account";
import {logout} from "@/services/accountService";
import {useRouter} from "vue-router";

// 계정 스토어: 사용자의 로그인 상태 정보 확인용
const accountStore = useAccountStore();

// 라우터 객체: 로그아웃 시 홈 뷰로의 라우팅 수행용
const router = useRouter();

// 로그아웃: logout()의 실행이 완료되어야 다음 작업 진행 가능
const logoutAccount = async () => {
  const res = await logout();

  if (res.status === 200) {
    // 로그아웃 시 accountStore에 저장된 계정을 로그아웃 상태로 변경하고 홈 뷰로 라우팅
    accountStore.setLoggedIn(false);
    await router.push("/");
  }
}
</script>

<template>
  <header>
    <div class="navbar navbar-dark bg-dark text-white shadow-sm">
      <div class="container">
        <router-link to="/" class="navbar-brand">
          <strong>Gallery</strong>
        </router-link>
        <div class="menus d-flex gap-3">
          <template v-if="!accountStore.loggedIn">  <!-- ④ -->
            <router-link to="/login">로그인</router-link>
            <router-link to="/join">회원가입</router-link>
          </template>
          <template v-else>
            <a @click="logoutAccount()">로그아웃</a>
            <router-link to="/orders">주문 내역</router-link>
            <router-link to="/cart">장바구니</router-link>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>

<style lang="scss">
header {
  .menus {
    a {
      cursor: pointer;
      color: #fff;
      text-decoration: none;
    }
  }
}
</style>