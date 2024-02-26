<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  username: string
  password: string
}

const formRef = ref<FormInstance>()

const ruleForm = reactive<RuleForm>({
  username: '',
  password: '',
})

const rules = reactive<FormRules<RuleForm>>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
  ],
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
      useApiLogin(ruleForm.username, ruleForm.password).then((res) => {
        res.data.value.status === 200 && ElMessage.success('登录成功！') && useRouter().push('/forum')
      })
    }
  })
}
</script>

<template>
  <div>
    <el-card m-auto w-sm>
      <el-form ref="formRef" :model="ruleForm" :rules="rules" label-position="top">
        <div text-center>
          <h3>登录</h3>
        </div>
        <el-form-item label="用户名称" prop="username">
          <el-input v-model="ruleForm.username" placeholder="用户名称" />
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="ruleForm.password" placeholder="用户密码" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button w-full type="primary" @click="submit(formRef)">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
