import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://192.168.0.50:8080', 
        changeOrigin: true,
        secure: false,
      },
      "/auth": {
        target: "http://192.168.0.50:8080",
        changeOrigin: true,
        secure: false,
      },
    }
  }
})