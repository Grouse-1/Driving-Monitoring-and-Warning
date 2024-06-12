<script setup>
import {onMounted, onUnmounted, reactive, ref, watch} from 'vue'
import {addElderly, editElderly} from "@/api/CRUD/elderly.js";
import {Search} from "@element-plus/icons-vue";
import {GetRfid} from "@/api/getInfo/index.js";
import {ElForm} from "element-plus";
import {addAdmin, editAdmin} from "@/api/CRUD/admin.js";
const emits = defineEmits(['update:modelValue'])
//const refresh = defineEmits(['update:dialogFresh'])

const form = reactive({
  id: null,
  name: '',
  password:'',
  auth: null,
  sex:'',
  age:null,
  balance: '',
  rfidtag: null,
  location_id:null,
  location:''
})

const latestData = ref('');
let ws = null;

const ruleFormRef = ref(null)//提交验证

const validateGender = (rule, value, callback) => {
  if (!value) {
    callback(new Error('Please select your gender'));
  } else if (value !== '男' && value !== '女') {
    callback(new Error('未知性别'));
  } else {
    callback();
  }
}

const checkAge = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('请输入年龄'))
  }
  const age = parseInt(value, 10)
  if (isNaN(age) || age <= 0 || age > 150) {
    return callback(new Error('未知年龄'))
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
  sex:[
    { required: true, message: '性别不能为空', trigger: 'blur' },
    { validator: validateGender, trigger: 'blur' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' },
    { validator: checkAge, trigger: 'blur' }
  ],
  rfidtag: [
      { required: true, message: '请扫描身份卡', trigger: 'blur' },
  ],
    location: [
      {required: false, message: '请输入地点', trigger: 'blur'}
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
  if(props.dialogTitle === '编辑老人信息'){
    form.id = props.dialogTableValue.id
    form.name = props.dialogTableValue.name
    form.password = props.dialogTableValue.password
    form.auth = props.dialogTableValue.auth
    form.sex = props.dialogTableValue.sex
    form.age = props.dialogTableValue.age
    form.balance = props.dialogTableValue.balance
    form.rfidtag = props.dialogTableValue.rfidtag
    form.location = props.dialogTableValue.location

  }
},{deep:true, immediate:true})



const handleClose=()=>{
  emits('update:modelValue',false)
  if(props.dialogTitle === '编辑老人信息'){

  }
  if(props.dialogTitle === '添加老人信息'){
    form.name = null
    form.password = null
    form.auth = null
    form.sex = null
    form.age = null
    form.balance = null
    form.rfidtag = null
    form.location = null
    latestData.value = null
  }

}

// props.initMenuList()
const handleConfirm=async()=>{
  console.log("form.name"+form.name)
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate(async (valid) => {  // 注意：此时使用的是 ruleFormRef.value，而仅写 ruleFormRef 是拿不到值且会报错的;
    if (valid) {   // 注意：只有当所有的规则都满足后，此时的 valid 的值才为 true，才能执行下面的值;
      console.log('submit!')
      if(props.dialogTitle === '添加老人信息'){
        const res1 = await addElderly.add(form.valueOf())
        form.name = null
        form.password = null
        form.auth = null
        form.sex = null
        form.age = null
        form.balance = null
        form.rfidtag = null
        form.location = null
      }
      if(props.dialogTitle === '编辑老人信息'){
        //console.log("id是"+props.dialogTableValue.id)
        //console.log("rfid是"+form.valueOf().rfidtag)
        const res2 = await editElderly.edit(form)
        form.name = null
        form.password = null
        form.auth = null
        form.sex = null
        form.age = null
        form.balance = null
        form.rfidtag = null
        form.location = null
      }
      props.initMenuList()
      handleClose()

    } else {
      console.log('error submit!')
      return false
    }
  })

}

const connectWebSocket = () => {
  // 这里的地址ws://localhost:3000需要替换为你的WebSocket服务端地址
  ws = new WebSocket('ws://localhost:7799/elder');

  ws.onopen = () => {
    console.log('WebSocket连接已打开');
  };

  ws.onmessage = (event) => {
    console.log('接收到消息:', event.data);
    latestData.value = event.data;
    //messages.value.push({ id: messages.value.length, data: event.data });
  };

  ws.onerror = (error) => {
    console.error('WebSocket发生错误:', error);
  };

  ws.onclose = () => {
    console.log('WebSocket连接已关闭');
  };
};
const getRfid = () =>{
  form.rfidtag = latestData.value
}
watch(()=>form.location, (newData)=>{
  if(newData === '菜市场'){
    form.location_id = 1
  }else if(newData === '公园'){
    form.location_id = 2
  }else if(newData === '户外'){
    form.location_id = 3
  }else {
  }
},{deep:true,immediate:true})

onMounted(() => {
  connectWebSocket();
});
onUnmounted(() => {
  if (ws) {
    ws.close();
  }
});

</script>

<template>
  <el-dialog
      :model-value="dialogVisible"
      :title="dialogTitle"
      width="500"
      @close="handleClose"
  >
    <el-form :model="form"  ref="ruleFormRef"  label-width="auto" style="max-width: 600px" :rules="rules">
      <el-form-item label="账户名" prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" />
      </el-form-item>
<!--      <el-form-item label="权限" prop="auth">-->
<!--        <el-input v-model="form.auth" />-->
<!--      </el-form-item>-->
      <el-form-item label="性别" prop="sex">
        <el-input v-model="form.sex" />
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input v-model="form.age" />
      </el-form-item>
      <el-form-item label="余额" prop="balance" v-if="dialogTitle === '编辑老人信息'">
        <el-input v-model="form.balance" />
      </el-form-item>
      <el-form-item label="序列号" prop="rfidtag">
        <el-col :span="19">
        <el-input v-model="form.rfidtag" />
        </el-col>
        <el-button type="success" @click="getRfid" style="margin-left: 24px">扫描</el-button>
      </el-form-item>
      <el-form-item label="地点" prop="location" v-if="dialogTitle === '编辑老人信息'">
        <el-input v-model="form.location" />
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