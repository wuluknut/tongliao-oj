<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  content: string
  parentId: string
}

const dialog = ref(false)

const formRef = ref<FormInstance>()

const ruleForm = reactive<RuleForm>({
  content: '',
  parentId: useRoute().params.id as string,
})

const rules = reactive<FormRules<RuleForm>>({
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
      useApiComment(ruleForm.parentId, ruleForm.content).then((res) => {
        if (res.data.value.status === 200 && ElMessage.success('发布成功！')) {
          dialog.value = false

          formRef.value?.resetFields()

          page(1)
        }
        res.data.value.status === 400 && ElMessage.error(res.data.value.message)
      })
    }
  })
}

function goBack() {
  useRouter().go(-1)
}

const content = ref<{ id: number, title?: string, content?: string, star: number, createBy?: string, createTime?: string }>({ id: 0, star: 0 })

useApiDiscussionInfo(useRoute().params.id as string).then((res) => {
  if (res.data.value.status === 200) {
    content.value = res.data.value.content
  }
})

const total = ref(0)

const records = ref<[{ id: number, parentId: number, content: string, star: number, createBy: string, createTime: string }] | undefined>(undefined)

function page(val: number) {
  useApiCommentList(val, useRoute().params.id as string).then((res) => {
    if (res.data.value.status === 200) {
      total.value = res.data.value.content.totalPage
      records.value = res.data.value.content.records

      useWindowScroll().y.value = 0
    }
  })
}

function confirmDelete(id: number, is: boolean) {
  useApiForumDelete(id, is).then((res) => {
    if (res.data.value.status === 200 && ElMessage.success('删除成功！')) {
      is ? page(1) : useRouter().push('/forum')
    }
    res.data.value.status === 400 && ElMessage.error(res.data.value.message)
  })
}

function star(id: number, is: boolean) {
  useApiForumStar(id, is)
}

page(1)
</script>

<template>
  <div m-auto max-w-4xl>
    <el-page-header @back="goBack">
      <template #content>
        <div class="flex items-center">
          讨论内容
        </div>
      </template>
    </el-page-header>
    <div mt-5>
      <el-card shadow="never">
        <div flex justify-between>
          <h4>
            {{ content.title }}
          </h4>
          <el-popconfirm v-if="useTokenStore().username() === content.createBy" title="确认删除?" @confirm="confirmDelete(content.id, false)">
            <template #reference>
              <el-button type="danger" link>
                <i class="el-icon--left i-ep-delete" />
              </el-button>
            </template>
          </el-popconfirm>
        </div>
        <el-text>
          {{ content.content }}
        </el-text>
        <div mt-2>
          <el-text type="info">
            发起者： {{ content.createBy }}
          </el-text>
        </div>

        <template #footer>
          <div flex justify-between>
            <div flex justify-start>
              <el-button link @click="++content.star && star(content.id, false)">
                <i class="el-icon--left i-ep-star" /> {{ content.star }}
              </el-button>
            </div>
            <el-button type="success" @click="dialog = true">
              <i class="el-icon--left i-ep-edit-pen" /> 回复评论
            </el-button>
          </div>
        </template>
      </el-card>
    </div>
    <div v-for="item in records" :key="item.id" mt-5>
      <el-card shadow="never">
        <el-text>
          {{ item.content }}
        </el-text>
        <div mt-2 flex justify-between>
          <el-text type="info">
            评论者： {{ item.createBy }}
          </el-text>
          <el-popconfirm v-if="useTokenStore().username() === item.createBy" title="确认删除?" @confirm="confirmDelete(item.id, true)">
            <template #reference>
              <el-button type="danger" link>
                <i class="el-icon--left i-ep-delete" />
              </el-button>
            </template>
          </el-popconfirm>
        </div>

        <template #footer>
          <div flex justify-between>
            <el-button link @click="++item.star && star(item.id, true)">
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
