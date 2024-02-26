<script setup lang="ts">
import locale from 'element-plus/es/locale/lang/zh-cn'

const { width } = useWindowSize()

if (width.value < 768) {
  useRouter().push('/err')
}

watchThrottled(
  width,
  (w) => {
    if (w < 768) {
      useRouter().push('/err')
    }
  },
  {
    throttle: 500,
  },
)

const isDark = useDark({
  onChanged(dark: boolean) {
    document.documentElement.classList.toggle('dark', dark)
  },
})
</script>

<template>
  <el-config-provider :locale="locale">
    <div m-auto max-w-6xl>
      <el-container>
        <el-header v-if="$router.currentRoute.value.name !== 'error'">
          <el-menu mode="horizontal" :ellipsis="false" :default-active="$router.currentRoute.value.path" router>
            <div inline-flex items-center justify-center px-5>
              <img w-10 src="assets/images/logo.svg">
            </div>
            <el-menu-item index="/code/contest">
              竞赛
            </el-menu-item>
            <el-menu-item index="/code/problem">
              题库
            </el-menu-item>
            <el-menu-item index="/forum">
              讨论
            </el-menu-item>
            <div class="flex-grow" />
            <div inline-flex items-center justify-center px-5>
              <el-switch v-model="isDark">
                <template #active-action>
                  <i class="i-ep-moon" />
                </template>
                <template #inactive-action>
                  <i class="i-ep-sunny" />
                </template>
              </el-switch>
            </div>
            <el-menu-item index="/system">
              {{ useTokenStore().username() }}
            </el-menu-item>
          </el-menu>
        </el-header>
        <el-main>
          <slot />
        </el-main>
        <el-footer v-if="$router.currentRoute.value.name !== 'error'">
          <div class="text-center">
            <el-link href="https://github.com/wuluknut/tongliao-oj" target="_blank">
              AGPLv3 © Wulu Knut
            </el-link>
          </div>
        </el-footer>
      </el-container>
    </div>
  </el-config-provider>
</template>
