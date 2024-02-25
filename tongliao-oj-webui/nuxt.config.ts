// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  modules: [
    '@element-plus/nuxt',
    '@pinia/nuxt',
    '@unocss/nuxt',
    '@vueuse/nuxt',
    'nuxt-module-eslint-config',
  ],

  app: {
    head: {
      title: 'Tongliao OJ',
    },
  },

  css: [
    '@unocss/reset/normalize.css',
    '~/assets/css-vars.css',
  ],

  ssr: false,

  router: {
    options: {
      hashMode: true,
    },
  },

  srcDir: 'src/',

  serverDir: 'server/',

  dir: {
    public: 'static/',
  },

  imports: {
    dirs: ['apis'],
  },

  vite: {
    server: {
      proxy: {
        '/api': {
          target: 'http://localhost:8000',
          changeOrigin: true,
          rewrite: path => path.replace(/^\/api/, ''),
        },
      },
    },
  },

  elementPlus: {
    themes: ['dark'],
  },

  eslintConfig: {
    setup: false,
  },

  typescript: {
    strict: true,
    typeCheck: true,
    shim: false,
  },

  devtools: {
    enabled: true,
  },
})
