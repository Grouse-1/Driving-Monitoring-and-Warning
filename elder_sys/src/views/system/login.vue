<template>
  <div class="container">
    <div class="box">
      <div class="login-content-left">
        <img :src="loginLeftPng"/>
        <div class="login-content-left-mask">
          <div>{{ systemTitle }}</div>
          <div>{{ systemSubTitle }}</div>
        </div>
      </div>

      <div class="box-inner">
        <h1>欢迎登录</h1>
        <el-form class="form">
          <el-input
              size="large"
              v-model="form.name"
              placeholder="用户名"
              type="text"
              maxlength="50"
          >
            <template #prepend>
              <i class="fa fa-user" aria-hidden="true"></i>
            </template>
          </el-input>
          <el-input
              size="large"
              ref="password"
              v-model="form.password"
              :type="passwordType"
              placeholder="密码"
              name="password"
              maxlength="50"
          >
            <template #prepend>
              <i class="fa fa-lock" aria-hidden="true"></i>
            </template>
            <template #append>
              <i class="fa fa-eye" :class="passwordType ? 'fa fa-eye-slash': 'fa fa-eye'"
                 @click="passwordTypeChange"></i>
            </template>
          </el-input>

          <el-form-item>
            <div class="flex flex-wrap gap-4 items-center">
              <el-select
                  v-model="form.Role"
                  placeholder="选择登录角色"
                  size="large"
                  clearable
                  style="width: 240px"
              >
                <el-option
                    v-for="item in RoleOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                />
              </el-select>
            </div>
          </el-form-item>
          <el-form-item prop="validCode">
          <el-input
              size="default"
              v-model="sidentifyMode"
              style="width: 150px;height: 65%;margin-top: 2%;margin-right:2%"
              ref="yzm"
              placeholder="验证码"
              name="yzm"
              maxlength="35"
          >
            <template #prepend>
              <i class="fa fa-check-circle-o" aria-hidden="true"></i>
            </template>
<!--            <template #append>-->
<!--              <i class="fa fa-eye" :class="passwordType ? 'fa fa-eye-slash': 'fa fa-eye'"-->
<!--                 @click="passwordTypeChange"></i>-->
<!--            </template>-->
          </el-input>
            <div class="code" @click="refreshCode">
              <validCode :identifyCode="identifyCode"></validCode>
            </div>
          </el-form-item>
          <el-button type="primary" :loading="form.loading" @click="submit" style="width: 100%;" size="large">
            登录
          </el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script lang="js">
import { systemTitle, systemSubTitle } from '@/config'
import {defineComponent, ref, reactive, onMounted} from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
//import loginLeftPng from '@/assets/login/left.jpg';
import loginLeftPng from '@/assets/login/kj.webp';
import validCode from '@/components/validCode/Sidentify.vue'

export default defineComponent({
  setup() {
    // 变量定义
    const router = useRouter()
    const route = useRoute()
    const store = useStore()
    const form = reactive({
      name: 'admin',
      password: '123456',
      loading: false,
      Role:''
    })
    let sidentifyMode = ref('') //输入框验证码
    let identifyCode = ref('') //图形验证码
    let identifyCodes = ref('1234567890abcdefjhijklinopqrsduvwxyz') //验证码出现的数字和字母
    const passwordType = ref('password')
    let response = null
    //const Role = ref('')
    const RoleOptions = [{
      value: 'admin',
      label: '管理员',
    },
      {
        value: 'family',
        label: '家属',
      },
      {
        value: 'seller',
        label: '商家',
      }]
    // 方法定义
    const doLogin = async () => {
      try {
        await store.dispatch('auth/login', { username: form.name, password: form.password, role: form.Role }).then(async response => {
          if (response.data.code === 200) {
            console.log('Login successful:', response.data);
            await router.replace('/');
            store.state.token = response.data.data.token
            //await this.router.push({path:'/'});
          } else {
            ElMessage.error({
              message: '登录失败',
              type: 'error',
              duration: 1000
            })
            form.loading = false;
            refreshCode()
            sidentifyMode.value = '';
            console.error('Login failed:', response.data);
          }
        })
        // response = await ULogin.login(form);
        // 登录成功后的处理
      } catch (error) {
        console.log('error login捕获到')
        ElMessage.error({
          message: '登录失败',
          type: 'error',
          duration: 1000})
        form.loading = false;
        refreshCode()
        sidentifyMode.value = '';

      }
    }

    const randomNum = (min, max) => {
      max = max + 1
      return Math.floor(Math.random() * (max - min) + min)
    }

    const makeCode = (o, l) => {
      for (let i = 0; i < l; i++) {
        identifyCode.value += o[randomNum(0, o.length)]
      }
    }

    const refreshCode = () => {
      identifyCode.value = ''
      makeCode(identifyCodes.value, 4)
    }

    const passwordTypeChange = () => {
      passwordType.value === '' ? passwordType.value = 'password' : passwordType.value = ''
    }

    const checkForm = () => {
      return new Promise((resolve, reject) => {
        if (form.name === '') {
          ElMessage.warning({
            message: '用户名不能为空',
            type: 'warning'
          });
          return;
        }
        if (form.password === '') {
          ElMessage.warning({
            message: '密码不能为空',
            type: 'error'
          })
          return;
        }
        if (!sidentifyMode.value) {
          ElMessage.warning({
            message: '验证码不能为空',
            type: 'error'
          })
          return;
        }
        if (sidentifyMode.value !== identifyCode.value) {
          ElMessage({ type: 'error', message: '验证码错误' })
          refreshCode()
          return
        }
        resolve(true)
      })
    }

    const submit = () => {
      checkForm()
          .then(() => {
            form.loading = true;
            // 在then中访问response对象
            doLogin()
                // .then(() => {
                //   console.log(response)
                //   if (response.data.code === 200) {
                //     router.replace('/');
                //   }else {
                //     return false
                //   }
                // });
          })
    }

    // 生命周期钩子
    onMounted(() => {
      identifyCode.value = ''
      makeCode(identifyCodes.value, 4)

    })

    // 返回
    return {
      loginLeftPng,
      systemTitle,
      systemSubTitle,
      form,
      passwordType,
      passwordTypeChange,
      submit,
      sidentifyMode,
      identifyCode,
      identifyCodes,
      refreshCode,
      response,
      RoleOptions,
    }
  },
  components: {
    validCode
  }
})
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  width: 100vw;
  height: 100vh;
  background: #fff url('@/assets/login/kjpg.png') no-repeat center;
  overflow: hidden;
  background-size: cover;
  cursor: pointer;
  user-select: none;

  .box {
    width: 1160px;
    display: flex;
    position: absolute;
    left: 50%;
    top: 50%;
    background: white;
    border-radius: 8px;
    transform: translate(-50%, -50%);
    height: 440px;
    overflow: hidden;
    box-shadow: 0 6px 20px 5px rgba(152, 152, 152, 0.1),
    0 16px 24px 2px rgba(117, 117, 117, 0.14);

    .login-content-left {
      position: relative;

      img {
        height: 440px;
      }

      .login-content-left-mask {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-image: linear-gradient(rgba(0,204,222,0.8), rgba(51,132,224,0.8));
        text-align: center;
        color: #fff;
        font-size: 1.8rem;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        letter-spacing: 2px;

        div:nth-child(1) {
          font-size: 3.5rem;
          margin-bottom: 1em;
        }
      }
    }

    .box-inner {
      width: 500px;

      h1 {
        margin-top: 35px;
        text-align: center;
        color: #181818;
      }

      .form {
        width: 80%;
        margin: 30px auto 15px;

        .el-input {
          margin-bottom: 20px;
        }

        .password-icon {
          cursor: pointer;
          color: #409eff;
        }
      }

      .fixed-top-right {
        position: absolute;
        top: 10px;
        right: 10px;
      }
    }
  }
}

@media screen and (max-width: 1260px) {
  .login-content-left {
    display: none;
  }
  .box {
    width: 500px !important;
  }
}

@media screen and (max-width: 750px) {
  .container .box, .container .box-inner {
    width: 100vw !important;
    height: 100vh;
    box-shadow: none;
    left: 0;
    top: 0;
    transform: none;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    h1 {
      margin-top: 0;
    }

    .form {
    }
  }
}
</style>
