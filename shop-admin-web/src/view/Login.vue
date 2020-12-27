<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">后台管理系统</div>
      <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="username">
            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="password"
            v-model="loginForm.password"
            @keyup.enter.native="submitForm()"
          >
            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
        </div>
        <p class="login-tips">Tips : 用户名和密码随便填。</p>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data: function () {
      let checkusername = (rule, value, callback) => {
        let userYz = /^[\u4e00-\u9fa5_a-zA-Z0-9_]{2,6}$/;
        if (!userYz.test(value)) {
          return callback(new Error("请输入2-6为汉字或字母"));
        } else {
          callback();
        }
      }
      let checkpassword = (rule, value, callback) => {
        let passwordYZ = /^[A-Za-z0-9]{3,10}$/;
        if (!passwordYZ.test(value)) {
          return callback(new Error("请输入3-10位字母或数字"));
        } else {
          callback();
        }
      }
      return {
        loginForm: {
          username: '',
          password: '',
        },
        loginRules: {
          username: [{required: true, message: '请输入用户名', trigger: 'blur', validator: checkusername}],
          password: [{required: true, message: '请输入密码', trigger: 'blur', validator: checkpassword}]
        },
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http.post("/fox/login", this.$qs.stringify(this.loginForm)).then(result => {
              if (result.data.code != 200) {
                this.$message(result.data.msg);
              } else {
                /*存储token值,页面级缓存*/
                localStorage.setItem("token", result.data.data)
                this.$router.replace("/");
              }
            })
          } else {
            alert('error submit!!');
            return false;
          }
        });
      }
    },
  }
</script>

<style scoped>
  .login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../assets/img/哆唻.jpg);
    background-size: 100%;
  }
  .ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
  }
  .ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
  }
  .ms-content {
    padding: 30px 30px;
  }
  .login-btn {
    text-align: center;
  }
  .login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
  }
  .login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
  }
</style>
