<template>
  <div class="loginPage">
    <div class="loginForm">
      <div class="loginLogo">
        <el-image :src="require('@/assets/image/logo/logo.jpg')" class="image"></el-image>
      </div>
      <div class="loginFormInput">
        <el-form status-icon ref="ruleForm" label-width="" class="demo-ruleForm">
          <el-form-item prop="username">
            <template slot="label">
              <span style="font-weight: bold;">Tài Khoản</span>
            </template>
            <el-input v-model="username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <template slot="label">
              <span style="font-weight: bold;">Mật Khẩu</span>
            </template>
            <el-input type="password" v-model="password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%;" type="primary" @click="login()">Đăng nhập</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'

@Component({
  name: 'LoginPage',
  components: {}
})
export default class extends Vue {
  private username = '';
  private password = '';

  private login() {
    this.$store.dispatch('auth/login', {
      username: this.username,
      password: this.password
    }).then(
      () => {
        this.$router.push({
          name: 'ADProductPage'
        })
      },
      // error => {
      //   this.loading = false;
      //   this.message =
      //     (error.response && error.response.data && error.response.data.message) ||
      //     error.message ||
      //     error.toString();
      // }
    );
  }
}
</script>

<style lang='scss' scoped>
.loginPage {
  background-color: #C1B1A9;
  min-width: 100vh;
  min-height: 100vh;

  .loginLogo {
    position: absolute;
    left: 50%;
    transform: translate(-50%, -135%);
    padding: 10px;
    width: 130px;
    height: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    box-shadow: 0 0 10px #ddd;
    background-color: #fff;

    .image {
      width: 100%;
      height: 100%;
      border-radius: 9999px;
      background-color: #eeeeee;
    }
  }

  .loginForm {
    position: absolute;
    top: 60%;
    left: 50%;
    width: 25%;
    background-color: #fff;
    border-radius: 3px;
    transform: translate(-50%, -60%);
    padding: 30px;
  }
}
</style>
  