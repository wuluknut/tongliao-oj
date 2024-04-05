<script setup lang="ts">
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
</script>

<template>
  <div h-full bg-gray-100 dark:bg-black:100>
    <el-container h-full>
      <el-main>
        <slot />
      </el-main>
    </el-container>
  </div>
</template>
