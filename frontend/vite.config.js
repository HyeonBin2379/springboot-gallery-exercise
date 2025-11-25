import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    // /v1/api로 시작하는 url 경로는 서버에게 HTTP 요청을 보내도록 우회
    proxy: {
      "/v1/api": {
        target: "http://localhost:8080",
        changeOrigin: true
      }
    }
  },
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
