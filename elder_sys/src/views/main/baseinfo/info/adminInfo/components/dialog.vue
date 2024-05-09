<script setup >
import {reactive, ref, watch} from 'vue'
import {addAdmin, editAdmin} from "@/api/CRUD/admin.js";
import {ElForm} from "element-plus";
const emits = defineEmits(['update:modelValue'])

const form = reactive({
  id: null,
  name: '',
  password:'',
  auth: null,
})

const ruleFormRef = ref(null)//提交验证

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
  if(props.dialogTitle === '编辑管理员'){
    form.id = props.dialogTableValue.id
    form.name = props.dialogTableValue.name
    form.password = props.dialogTableValue.password
    form.auth = props.dialogTableValue.auth
  }
  console.log("form.name", form.name)
},{deep:true, immediate:true})



const handleClose=()=>{
  emits('update:modelValue',false)
  if(props.dialogTitle === '编辑管理员'){
  }
  if(props.dialogTitle === '添加管理员'){
    form.name = null
    form.password = null
    form.auth = null

}
}

const handleConfirm=async()=>{
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate(async (valid) => {  // 注意：此时使用的是 ruleFormRef.value，而仅写 ruleFormRef 是拿不到值且会报错的;
    if (valid) {   // 注意：只有当所有的规则都满足后，此时的 valid 的值才为 true，才能执行下面的值;
      console.log('submit!')
      if (props.dialogTitle === '添加管理员') {
        const res1 = await addAdmin.add(form.valueOf())
        form.name = null
        form.password = null
        form.auth = null
      }
      if (props.dialogTitle === '编辑管理员') {
        //console.log("id是"+props.dialogTableValue.id)
        //console.log("id是" + form.valueOf().id)
        const res2 = await editAdmin.edit(form)
        form.name = null
        form.password = null
        form.auth = null
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
      <el-form-item label="密码" prop="password" v-if="dialogTitle === '添加管理员'">
        <el-input v-model="form.password" />
      </el-form-item>
      <el-form-item label="权限" prop="auth">
        <el-input v-model="form.auth" />
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