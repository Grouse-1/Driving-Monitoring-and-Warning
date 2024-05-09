<script setup>
import {reactive, ref, watch} from 'vue'
import {addFamily, editFamily} from "@/api/CRUD/family.js";
import {addAdmin, editAdmin} from "@/api/CRUD/admin.js";
import {ElMessage} from "element-plus";
const emits = defineEmits(['update:modelValue'])
//const refresh = defineEmits(['update:dialogFresh'])

const form = reactive({
  id: null,
  name: '',
  password:'',
  auth: null,
  relationship:'',
  elderly_id:'',
  email:'',
  phone:'',
  elderly:''
})
const ruleFormRef = ref(null)//提交验证
const validatePhone = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('Please input phone number'))
  }
  if (!/^1\d{10}$/.test(value)) {
    return callback(new Error('请输入11位手机号'))
  }
  callback()
}
const rules=ref({
  name:[
    { required: true, message: '姓名不能为空', trigger: 'blur' },

  ],
  password:[
    { required: true, message: '密码不能为空', trigger: 'blur' },

  ],
  auth:[

    { required: true, message: '权限不能为空', trigger: 'blur' },
    {
      pattern: '^(0|[1-9]\\d*)$',
      message: '请输入正确的权限',
    }
  ],
  elderly: [
      { required: true, message: '老人姓名不能为空', trigger: 'blur' },
  ],
  phone: [
    { required: true, message: '联系电话不能为空', trigger: 'blur' },
    { validator: validatePhone, trigger: 'blur' },
  ],
  email:[
    {
      type: 'email',
      message: '请输入正确的邮箱地址',
      trigger: 'blur',
    },
  ]
})

const props = defineProps({
  dialogTitle:{
    type: String,
    default: '',
    required: true,
  },
  dialogTableValue: {
    type: Object,
    default: ()=>{}
  },
  initMenuList: Function
})

watch(()=>props.dialogTableValue, ()=>{
  //console.log(props.dialogTableValue)
  if(props.dialogTitle === '编辑家属信息'){
    form.id = props.dialogTableValue.id
    form.name = props.dialogTableValue.name
    form.password = props.dialogTableValue.password
    form.auth = props.dialogTableValue.auth
    form.relationship = props.dialogTableValue.relationship
    form.elderly_id = props.dialogTableValue.elderly_id
    form.email = props.dialogTableValue.email
    form.phone = props.dialogTableValue.phone
    form.elderly = props.dialogTableValue.elderly
  }
},{deep:true, immediate:true})



const handleClose=()=>{
  emits('update:modelValue',false)
  if(props.dialogTitle === '编辑家属信息'){
  }
  if(props.dialogTitle === '添加家属信息'){
    form.name = null
    form.password = null
    form.auth = null
    form.elderly = null
    form.phone = null
    form.email = null
  }
}

const handleConfirm=async()=>{
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate(async (valid) => {  // 注意：此时使用的是 ruleFormRef.value，而仅写 ruleFormRef 是拿不到值且会报错的;
    if (valid) {   // 注意：只有当所有的规则都满足后，此时的 valid 的值才为 true，才能执行下面的值;
      console.log('submit!')
      if(props.dialogTitle === '添加家属信息'){
        const res1 = await addFamily.add(form.valueOf())
        if(res1.data.toString() === '0'){
          ElMessage.error({
            message: '家属不存在',
            type: 'error',
            duration: 2000
          })
          return false
        }
        form.name = null
        form.password = null
        form.auth = null
        form.elderly = null
        form.phone = null
        form.email = null
      }
      if(props.dialogTitle === '编辑家属信息'){
        const res2 = await editFamily.edit(form)
        if(res2.data.toString() === '0'){
          ElMessage.error({
            message: '家属不存在',
            type: 'error',
            duration: 2000
          })
          return false
        }
        form.name = null
        form.password = null
        form.auth = null
        form.elderly = null
        form.phone = null
        form.email = null
      }
      props.initMenuList()
      handleClose()

    } else {
      console.log('error submit!')
      return false
    }
  })
}



</script>

<template>
  <el-dialog
      :model-value="dialogVisible"
      :title="dialogTitle"
      width="500"
      @close="handleClose"
  >
    <el-form :model="form" ref="ruleFormRef" label-width="auto" style="max-width: 600px" :rules="rules">
      <el-form-item label="账户名" prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" />
      </el-form-item>
      <el-form-item label="权限" prop="auth">
        <el-input v-model="form.auth" />
      </el-form-item>
      <el-form-item label="关联老人" prop="elderly">
        <el-input v-model="form.elderly" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped lang="scss">

</style>