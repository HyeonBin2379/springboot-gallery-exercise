//Login.vue 로그인 컴포넌트
<script setup>
import {reactive} from "vue";
import {login} from "@/services/accountService";
import {useRouter} from "vue-router";

// ① 계정 스토어 객체 생성 시 필요한 메서드 임포트
import {useAccountStore} from "@/stores/account";

// 반응형 상태
const state = reactive({ // ①
  form: {
    loginId: "",
    loginPw: "",
  }
});

// 라우터 객체
const router = useRouter(); // ②

// 로그인 데이터 제출
const submit = async () => { // ③
  const params = new URLSearchParams();
  params.append('loginId', state.form.loginId);
  params.append('loginPw', state.form.loginPw);

  const res = await login(params);

  switch (res.status) {
    case 200:
      await router.push("/");
      break;

    case 404:
      window.alert("입력하신 정보와 일치하는 회원이 없습니다.");
      break;
  }
};
</script>

<template>
  <div class="login">
    <div class="container"> <!-- ④ -->
      <form class="py-5 d-flex flex-column gap-3" @submit.prevent="submit"> <!-- ⑤ -->
        <h1 class="h5 mb-3">로그인</h1>
        <div class="form-floating">
          <input type="email" class="form-control" id="loginId" placeholder="이메일" v-model="state.form.loginId"> <!-- ⑥ -->
          <label for="loginId">이메일</label>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" id="loginPw" placeholder="패스워드" v-model="state.form.loginPw" autocomplete="false"> <!-- ⑥ -->
          <label for="loginPw">패스워드</label>
        </div>
        <button type="submit" class="w-100 h6 btn py-3 btn-primary">로그인</button> <!-- ⑦ -->
      </form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login > .container { // ⑧
  max-width: 576px;
}
</style>