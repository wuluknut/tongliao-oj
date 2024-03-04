<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  title: string
  content: string
}

const dialog = ref(false)

const keyword = ref('')

const formRef = ref<FormInstance>()

const ruleForm = reactive<RuleForm>({
  title: '',
  content: '',
})

const rules = reactive<FormRules<RuleForm>>({
  title: [
    { required: true, message: '请输入标题', trigger: 'blur' },
  ],
  content: [
    { required: true, message: '请输入内容', trigger: 'blur' },
  ],
})

async function submit(formEl: FormInstance | undefined) {
  if (!formEl) {
    return
  }
  await formEl.validate((valid) => {
    if (valid) {
      useApiDiscussion(ruleForm.title, ruleForm.content).then((res) => {
        if (res.data.value.status === 200 && ElMessage.success('发布成功！')) {
          dialog.value = false

          keyword.value = ''

          formRef.value?.resetFields()

          page(1)
        }
        res.data.value.status === 400 && ElMessage.error(res.data.value.message)
      })
    }
  })
}

const total = ref(0)

const records = ref<[{ id: number, title: string, content: string, star: number, createBy: string, createTime: string }] | undefined>(undefined)

function page(val: number) {
  useApiDiscussionList(val, keyword.value, false).then((res) => {
    if (res.data.value.status === 200) {
      total.value = res.data.value.content.totalPage
      records.value = res.data.value.content.records

      useWindowScroll().y.value = 0
    }
  })
}

function star(id: number) {
  useApiForumStar(id, false)
}

page(1)
</script>

<template>
  <div m-auto max-w-4xl>
    <div flex justify-between>
      <el-button type="success" @click="dialog = true">
        <i class="el-icon--left i-ep-edit-pen" /> 发布讨论
      </el-button>
      <div>
        <el-input v-model="keyword" placeholder="搜索">
          <template #append>
            <el-button @click="page(1)">
              <i class="i-ep-search" />
            </el-button>
          </template>
        </el-input>
      </div>
    </div>
    <div v-for="item in records" :key="item.id" mt-5>
      <el-card shadow="hover">
        <div @click="useRouter().push(`/forum/${item.id}`)">
          <h4>
            {{ item.title }}
          </h4>
          <el-text type="info" line-clamp="4">
            {{ item.content }}
          </el-text>
          <div mt-2>
            <el-text type="info">
              发起者： {{ item.createBy }}
            </el-text>
          </div>
        </div>

        <template #footer>
          <div flex justify-between>
            <el-button link @click="++item.star && star(item.id)">
              <i class="el-icon--left i-ep-star" /> {{ item.star }}
            </el-button>
            <span>
              <el-text type="info">
                {{ item.createTime.replace('T', ' ') }}
              </el-text>
            </span>
          </div>
        </template>
      </el-card>
    </div>
    <div mt-5 flex justify-end>
      <el-pagination v-model:page-count="total" small background layout="prev, pager, next" @update:current-page="page" />
    </div>
    <el-dialog v-model="dialog" width="600" center>
      <el-form ref="formRef" :model="ruleForm" :rules="rules" label-position="top">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title" placeholder="标题" maxlength="50" show-word-limit />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="ruleForm.content" placeholder="内容" type="textarea" resize="none" maxlength="5000" show-word-limit h-xs />
        </el-form-item>
        <el-form-item>
          <el-button w-full type="primary" @click="submit(formRef)">
            发布
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
