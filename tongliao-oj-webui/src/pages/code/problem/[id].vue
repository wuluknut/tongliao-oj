<script setup lang="ts">
definePageMeta({
  layout: 'code',
})

const code = ref('')

const content = ref<{ id: number, title: string, declare: string, language: number, question: string }>()

useApiProblemInfo(useRoute().params.id as string).then((res) => {
  if (res.data.value.status === 200) {
    content.value = res.data.value.content
  }
})

let loading: any

function submit() {
  useApiJudgeSubmit(Number.parseInt(useRoute().params.id as string), code.value).then((res) => {
    if (res.data.value.status === 200) {
      loading = ElLoading.service({
        lock: true,
        text: '执行中。。。',
      })

      query(res.data.value.message)
    }
  })
}

const dialog = ref(false)

const score = ref<{ status: number, time: string, memory: string, result: number }>()

function query(toekn: string) {
  setTimeout(() => {
    useApiJudgeQuery(toekn).then((res) => {
      if (res.data.value.status === 200) {
        if (res.data.value.content.status < 3) {
          query(toekn)
        } else {
          loading?.close()
          dialog.value = true
          score.value = res.data.value.content
        }
      } else {
        loading?.close()
        ElMessage.error(res.data.value.message)
      }
    })
  }, 1000)
}
</script>

<template>
  <div h-full flex flex-col>
    <div class="h-19/20">
      <code-editor v-model="code" :lang="content?.language" :code="content?.declare" :desc="content?.question" />
    </div>
    <div class="h-1/20" flex items-center justify-center p-1>
      <el-button type="success" @click="submit()">
        提交
      </el-button>
      <el-button type="danger" @click="useRouter().push('/code/problem')">
        结束
      </el-button>
    </div>
    <el-dialog v-model="dialog" width="300" center>
      <div text-center>
        <el-text size="large" :type="score?.status === 3 ? 'success' : 'danger'">
          <p>状态：{{ score?.status === 3 ? '通过' : (score?.status === 5 ? '执行超时' : '未通过') }}</p>
        </el-text>
        <el-text>
          <el-tag type="primary">
            耗时：{{ score?.time }} 秒
          </el-tag>
          <span mx-1 />
          <el-tag type="primary">
            内存：{{ score?.memory }}
          </el-tag>
        </el-text>
        <el-text>
          <p>得分：{{ score?.result }}</p>
        </el-text>
      </div>
    </el-dialog>
  </div>
</template>
