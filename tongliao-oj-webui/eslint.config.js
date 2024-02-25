import antfu from '@antfu/eslint-config'

import NuxtEslintConfig from './.nuxt/eslint.config.mjs'

export default antfu(
  {
    unocss: true,
  },
  NuxtEslintConfig,
  {
    rules: {
      'curly': ['error', 'all'],
      'style/brace-style': ['error', '1tbs'],
      'jsonc/array-bracket-spacing': ['error', 'always'],
      'jsonc/sort-keys': 'off',
    },
  },
)
