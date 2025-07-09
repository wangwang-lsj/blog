<template>
  <div class="wrapper">
    <div style="margin: 200px auto;width: 350px;height: 350px;background-color: #fff;border-radius: 10px;padding: 20px">
      <div style="text-align: center; font-size: 30px;margin: 20px auto;font-family: 'Microsoft YaHei UI'">注册</div>

      <el-form :model="user" :rules="rules" ref="formRef">
        <el-form-item label="" prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 5px 0" :prefix-icon="User"
            v-model="user.username" />
        </el-form-item>
        <el-form-item label="" prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" show-password :prefix-icon="Lock"
            v-model="user.password" />
        </el-form-item>
        <el-form-item label="" prop="confirmPassword">
          <el-input placeholder="请再次输入密码" size="medium" style="margin: 5px 0" show-password :prefix-icon="Lock"
            v-model="user.confirmPassword" @keyup.enter="handleRegister" />
        </el-form-item>
        <div style="margin: 10px 0; text-align: right">
          <el-button type="warning" size="small" @click="$router.push('/login')">返回登录</el-button>
          <el-button type="primary" size="small" @click="handleRegister">注册</el-button>
        </div>
      </el-form>

    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormItemRule } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import userApi from '@/api/userApi.ts'

const router = useRouter()

// 表单引用
const formRef = ref()

// 表单数据
const user = reactive({
  username: '',
  password: '',
  confirmPassword: ''
})

// 校验方法
const checkUsername = (rule: FormItemRule, value: string, callback: (error?: string | Error) => void) => {
  if (!value) {
    return callback(new Error('请输入用户名！'))
  }
  setTimeout(() => {
    if (value.length < 5 || value.length > 12) {
      callback(new Error('长度在 5 到 12 个字符'))
    } else {
      callback()
    }
  }, 500)
}

const checkPassword = (rule: FormItemRule, value: string, callback: (error?: string | Error) => void) => {
  if (!value) {
    return callback(new Error('请输入密码！'))
  }
  setTimeout(() => {
    if (value.length < 5 || value.length > 12) {
      callback(new Error('长度在 5 到 12 个字符'))
    } else {
      callback()
    }
  }, 500)
}

const checkConfirmPassword = (rule: FormItemRule, value: string, callback: (error?: string | Error) => void) => {
  if (!value) {
    return callback(new Error('请确认密码！'))
  } else if (value !== user.password) {
    callback(new Error('两次密码不一致!'))
  } else {
    callback()
  }
}

// 校验规则
const rules = {
  username: [
    { validator: checkUsername, trigger: 'blur' }
  ],
  password: [
    { validator: checkPassword, trigger: 'blur' }
  ],
  confirmPassword: [
    { validator: checkConfirmPassword, trigger: 'blur' }
  ]
}

// 注册处理
const handleRegister = async () => {
  const valid = await formRef.value.validate()
  if (valid) {
    const res = await userApi.register(user)
    if (res.data.code === '200') {
      ElMessage.success('注册成功')
      router.push('/login')
    } else {
      ElMessage.error(res.data.msg)
    }
  }
}
</script>

<style scoped lang="scss">
.wrapper {
  height: 100vh;
  overflow: hidden;
  background-image: linear-gradient(to top right, #FC4668, #3F5EFB);
}
</style>
