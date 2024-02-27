<script setup lang="ts">
import locale from 'element-plus/es/locale/lang/zh-cn'
</script>

<template>
  <div>
    <el-config-provider :locale="locale">
      <NuxtLayout>
        <NuxtPage />
      </NuxtLayout>
    </el-config-provider>
  </div>
</template>
