<template>
  <div class="container">
    <div style="display: flex; background-color: #fff; width: 42%; overflow: hidden; border-radius: 5px;">
      <div style="flex: 1">
        <img src="@/assets/imgs/reg.png" alt="" style="width: 100% ">
      </div>
      <div style="width: 342px; padding: 12px; background-color: white; border-radius: 5px;">
        <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">欢迎注册</div>
        <el-form :model="form" :rules="rules" ref="formRef" style="margin-top: 10px;">
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" style="margin-top: 15px;" placeholder="请输入账号" v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock"  style="margin-top: 10px;" placeholder="请输入密码" show-password  v-model="form.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass">
            <el-input prefix-icon="el-icon-lock"  style="margin-top: 10px;" placeholder="请确认密码" show-password  v-model="form.confirmPass"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%; background-color: #1edac1; margin-top: 15px; border-color: #333; color: white" @click="register">注 册</el-button>
          </el-form-item>
          <div style="display: flex; align-items: center">
            <div style="flex: 1"></div>
            <div style="flex: 1; text-align: right;margin-top: 5px;">
              已有账号？请 <a href="/login">登录</a>
            </div>
          </div>
        </el-form>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ]
      }
    }
  },
  created() {

  },
  methods: {
    register() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/')  // 跳转登录页面
              this.$message.success('注册成功')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  /*background-image: url("@/assets/imgs/bg1.jpg");*/
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
  /*渐变*/
  background-image: linear-gradient(#00f6ad, #04b8de);
}
a {
  color: #2a60c9;
}
</style>