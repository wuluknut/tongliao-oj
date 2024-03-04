<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  password: string
  newPassword: string
}

const user = ref<{ username: string, nickname: string, phone: string } | null>(null)

useApiUser().then((res) => {
  user.value = res.data.value.content
})

const dialog = ref(false)

const formRef = ref<FormInstance>()

const ruleForm = reactive<RuleForm>({
  password: '',
  newPassword: '',
})

const rules = reactive<FormRules<RuleForm>>({
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],
})

async function submit(formEl: FormInstance | undefined) {
  if (!formEl) {
    return
  }
  await formEl.validate((valid) => {
    if (valid) {
      useApiPassword(ruleForm.password, ruleForm.newPassword).then((res) => {
        res.data.value.status === 200 && ElMessage.success(res.data.value.message) && (dialog.value = false)
        res.data.value.status === 400 && ElMessage.error(res.data.value.message)
      })
    }
  })
}

function logout() {
  useApiLogout().then(() => {
    ElMessage.success('退出成功！')
  })
}

const active = ref('forum')

const total = ref(0)

const tableData = ref([{
  title: 'xxxx',
  createTime: 'xxxx-xx-xx',
}])

function page(val: number) {
  useApiDiscussionList(val, '', true).then((res) => {
    if (res.data.value.status === 200) {
      total.value = res.data.value.content.totalPage
      tableData.value = res.data.value.content.records

      useWindowScroll().y.value = 0
    }
  })
}

page(1)
</script>

<template>
  <div>
    <el-card m-auto w-2xl>
      <template #header>
        <div flex items-center justify-between>
          <span>{{ user?.username }}</span>
          <el-button type="success" link>
            正常
          </el-button>
        </div>
      </template>
      <div>
        <p>姓名：{{ user?.nickname }}</p>
        <p>手机：{{ user?.phone }}</p>
      </div>

      <template #footer>
        <div flex items-center justify-end>
          <el-button type="primary" @click="dialog = true">
            修改密码
          </el-button>
          <el-button type="danger" @click="logout">
            退出登录
          </el-button>
        </div>
      </template>
    </el-card>
    <div m-auto max-w-4xl>
      <div mt-5>
        <el-tabs v-model="active" type="border-card">
          <el-tab-pane label="我的讨论" name="forum">
            <client-only>
              <el-table :data="tableData">
                <el-table-column label="标题">
                  <template #default="scope">
                    <el-link :underline="false" @click="useRouter().push(`/forum/${scope.row.id}`)">
                      {{ scope.row.title }}
                    </el-link>
                  </template>
                </el-table-column>
                <el-table-column label="发布时间" width="200" align="center">
                  <template #default="scope">
                    {{ scope.row.createTime.replace('T', ' ') }}
                  </template>
                </el-table-column>
              </el-table>
            </client-only>
            <div mt-5 flex justify-end>
              <el-pagination v-model:page-count="total" small background layout="prev, pager, next" @update:current-page="page" />
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <el-dialog v-model="dialog" width="400" center>
      <el-form ref="formRef" :model="ruleForm" :rules="rules" label-position="top">
        <el-form-item label="旧密码" prop="password">
          <el-input v-model="ruleForm.password" placeholder="旧密码" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input v-model="ruleForm.newPassword" placeholder="新密码" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button w-full type="primary" @click="submit(formRef)">
            确认修改
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
