<script setup lang="ts">
definePageMeta({
  layout: 'code',
})

const { isFullscreen, enter, exit } = useFullscreen()

watchThrottled(
  isFullscreen,
  (full) => {
    if (!full) {
      quit()
    }
  },
  {
    throttle: 500,
  },
)

const end = ref(true)

const code = ref('')

const index = ref(0)

const content = ref<{ id: number, title: string, declare: string, language: number, question: string }>()

const problems = ref<{ id: number, title: string, declare: string, language: number, question: string, code: string, score: number, result: number }[]>([])

const contest = ref<{ createBy: string, createTime: string, result: number }[]>([])

useApiContestInfo(useRoute().params.id as string).then((res) => {
  if (res.data.value.status === 200) {
    problems.value = res.data.value.content

    if (problems.value.length > 0) {
      end.value = false

      toggle(true)
      enter()
    }
  }
})

useApiContestScore(useRoute().params.id as string).then((res) => {
  if (res.data.value.status === 200) {
    contest.value = res.data.value.content
  }
})

function contestSubmit() {
  useApiContestSubmit(Number.parseInt(useRoute().params.id as string), problems.value?.map(item => item.score))
}

let loading: any

function submit() {
  useApiJudgeSubmit(problems.value[index.value].id, code.value).then((res) => {
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

          if (res.data.value.content.status === 3 && (problems.value[index.value].result < res.data.value.content.result || problems.value[index.value].result === undefined)) {
            problems.value[index.value].result = res.data.value.content.result
            problems.value[index.value].score = res.data.value.content.id
          }
        }
      } else {
        loading?.close()
        ElMessage.error(res.data.value.message)
      }
    })
  }, 1000)
}

function toggle(e: boolean) {
  problems.value[index.value].code = code.value

  if (e) {
    if (index.value > 0) {
      index.value--
    }
  } else {
    if (index.value < problems.value.length - 1) {
      index.value++
    }
  }

  code.value = problems.value[index.value].code

  content.value = problems.value[index.value]
}

function quit() {
  exit()
  contestSubmit()
  useRouter().push('/code/contest')
}
</script>

<template>
  <div h-full flex flex-col>
    <div v-if="!end" h-full flex flex-col>
      <div class="h-19/20">
        <code-editor v-model="code" :lang="content?.language" :code="content?.declare" :desc="content?.question" />
      </div>
      <div class="h-1/20" flex items-center justify-center p-1>
        <el-button type="primary" @click="toggle(true)">
          上一题
        </el-button>
        <el-button type="success" @click="submit()">
          提交
        </el-button>
        <el-button type="danger" @click="quit()">
          结束
        </el-button>
        <el-button type="primary" @click="toggle(false)">
          下一题
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
    <div v-if="end">
      <el-text size="large" text-center>
        <p>竞赛排名</p>
      </el-text>
      <client-only>
        <el-table :data="contest">
          <el-table-column label="参加者" prop="createBy" width="200" align="center" />
          <el-table-column label="比赛得分" prop="result" align="center" />
          <el-table-column label="提交时间" width="200" align="center">
            <template #default="scope">
              {{ scope.row.createTime.replace('T', ' ') }}
            </template>
          </el-table-column>
        </el-table>
      </client-only>
      <div m-5>
        <el-button w-full type="success" @click="useRouter().go(-1)">
          返回
        </el-button>
      </div>
    </div>
  </div>
</template>
