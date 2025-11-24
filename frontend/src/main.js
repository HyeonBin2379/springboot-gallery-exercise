import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
// 마운트 시점 변경(라우터 초기 탐색 이후)
// 1. Vue 라우터가 초기 작업을 완료한 시점으로 마운트 시점을 변경
// 2. 구현할 라우터 관련 기능이 중복 호출되는 것을 방지
router.isReady().then(() => {
    app.mount('#app');
})