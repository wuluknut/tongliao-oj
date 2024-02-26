<script setup lang="ts">
const { width } = useWindowSize()

const isMobile = ref(width.value < 768)

watchThrottled(
  width,
  (w) => {
    if (w < 768) {
      isMobile.value = true
    } else {
      isMobile.value = false
    }
  },
  {
    throttle: 500,
  },
)
</script>

<template>
  <div>
    <div v-if="isMobile">
      <el-result icon="error" title="Not Desktop" sub-title="请使用正确的设备访问" />
    </div>
    <div v-if="!isMobile">
      <el-result icon="error" title="404 Not Found" sub-title="页面不存在">
        <template #extra>
          <a href="/">
            <el-button type="primary">返回首页</el-button>
          </a>
        </template>
      </el-result>
    </div>
  </div>
</template>
