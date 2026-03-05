import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://172.27.12.136:8080', 
        changeOrigin: true,
        secure: false,
      },
      "/auth": {
        target: "http://172.27.12.136:8080",
        changeOrigin: true,
        secure: false,
      },
    }
  }
})