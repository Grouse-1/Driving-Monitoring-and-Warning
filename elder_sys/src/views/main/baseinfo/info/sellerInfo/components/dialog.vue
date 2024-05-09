<script setup>
import {reactive, ref, watch} from 'vue'
import {addSeller, editSeller} from "@/api/CRUD/seller.js";
import {addAdmin, editAdmin} from "@/api/CRUD/admin.js";
const emits = defineEmits(['update:modelValue'])
//const refresh = defineEmits(['update:dialogFresh'])

const form = reactive({
  id: null,
  name: '',
  password:'',
  auth: null,
  shopname:'',
  phone:''
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
  shopname:[
    { required: true, message: '姓名不能为空', trigger: 'blur' },

  ],
  phone: [
    { required: true, message: '联系电话不能为空', trigger: 'blur' },
    { validator: validatePhone, trigger: 'blur' },
  ],
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
  if(props.dialogTitle === '编辑商户信息'){
    form.id = props.dialogTableValue.id
    form.name = props.dialogTableValue.name
    form.password = props.dialogTableValue.password
    form.auth = props.dialogTableValue.auth
    form.shopname = props.dialogTableValue.shopname
    form.phone = props.dialogTableValue.phone
  }
},{deep:true, immediate:true})



const handleClose=()=>{
  emits('update:modelValue',false)
  if(props.dialogTitle === '编辑商户信息'){
  }
  if(props.dialogTitle === '添加商户信息'){
    form.name = null
    form.password = null
    form.auth = null
    form.shopname = null
    form.phone = null
  }
}

const handleConfirm=async()=>{
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate(async (valid) => {  // 注意：此时使用的是 ruleFormRef.value，而仅写 ruleFormRef 是拿不到值且会报错的;
    if (valid) {   // 注意：只有当所有的规则都满足后，此时的 valid 的值才为 true，才能执行下面的值;
      console.log('submit!')
      if(props.dialogTitle === '添加商户信息'){
        const res1 = await addSeller.add(form.valueOf())
        form.name = null
        form.password = null
        form.auth = null
        form.shopname = null
        form.phone = null
      }
      if(props.dialogTitle === '编辑商户信息'){
        //console.log("id是"+props.dialogTableValue.id)
        //console.log("id是"+form.valueOf().id)
        const res2 = await editSeller.edit(form)
        form.name = null
        form.password = null
        form.auth = null
        form.shopname = null
        form.phone = null
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
      <el-form-item label="账号" prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" />
      </el-form-item>
      <el-form-item label="权限" prop="auth">
        <el-input v-model="form.auth" />
      </el-form-item>
      <el-form-item label="商店名称" prop="shopname">
        <el-input v-model="form.shopname" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" />
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