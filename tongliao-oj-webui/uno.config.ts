import { defineConfig, presetAttributify, presetIcons, presetUno, presetWebFonts, transformerDirectives, transformerVariantGroup } from 'unocss'

export default defineConfig({
  presets: [
    presetUno(),
    presetIcons({
      scale: 1.2,
    }),
    presetWebFonts({
      provider: 'bunny',
      fonts: {
        sans: [{ name: 'Lato', weights: ['400', '700'] }],
        mono: [{ name: 'Fira Code', weights: ['400'] }],
      },
    }),
    presetAttributify(),
  ],
  transformers: [
    transformerDirectives(),
    transformerVariantGroup(),
  ],
})
