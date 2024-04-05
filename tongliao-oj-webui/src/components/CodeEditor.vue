<script setup lang="ts">
const props = defineProps<{
  lang?: number
  code?: string
  desc?: string
}>()

const lang = ref('')

const code = defineModel({ default: '' })

watch(() => props.code, () => {
  switch (props.lang) {
    case 54:
      lang.value = 'cpp'
      code.value = `class Solution {\npublic:\n\t${props.code} {\n\n\t}\n};\n`
      break
    case 62:
      lang.value = 'java'
      code.value = `class Solution {\n\t${props.code} {\n\n\t}\n}\n`
      break
    case 63:
      lang.value = 'javascript'
      code.value = `${props.code} {\n\n}\n`
      break
    case 71:
      lang.value = 'python'
      code.value = `class Solution(object):\n\t${props.code}\n\t\tpass`
      break
    case 82:
      lang.value = 'sql'
      code.value = `${props.code}`
      break
  }
})

const options = reactive({
  theme: useDark().value ? 'vs-dark' : 'vs',
  tabSize: 2,
  minimap: {
    enabled: false,
  },
})
</script>

<template>
  <div h-full flex flex-row>
    <div class="w-1/3" mr-2 h-full flex flex-col border border-gray-600 rounded border-solid bg-white dark:bg-black>
      <div rounded bg-gray-50 p-2 dark:bg-black>
        <el-text type="primary">
          <el-icon>
            <i class="i-ep-info-filled" />
          </el-icon>
          题目描述
        </el-text>
      </div>
      <div flex-1 overflow-auto rounded p-1>
        <div break-words v-html="props.desc" />
      </div>
    </div>
    <div class="w-2/3" h-full flex flex-col border border-gray-600 rounded border-solid bg-white dark:bg-black>
      <div rounded bg-gray-50 p-2 dark:bg-black>
        <el-text type="success">
          <el-icon>
            <i class="i-ep-cpu" />
          </el-icon>
          代码
        </el-text>
      </div>
      <div flex-1 pb-1>
        <MonacoEditor v-model="code" :lang="lang" :options="options" h-full />
      </div>
    </div>
  </div>
</template>

<style>
html,
body,
#__nuxt,
#__nuxt>div {
  margin: 0;
  height: 100%;
}
</style>
