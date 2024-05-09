<script setup >
import {onMounted, onUnmounted, reactive, ref, watch} from 'vue'
import {addPlant, editPlant} from "@/api/CRUD/plant.js";
import {ElForm} from "element-plus";
const emits = defineEmits(['update:modelValue'])

const form = reactive({
  id: null,
  name: '',
  rfid:'',
  description: '',
})

let ws = null;
const latestData = ref('');

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
  if(props.dialogTitle === '编辑植物信息'){
    form.id = props.dialogTableValue.id
    form.name = props.dialogTableValue.name
    form.rfid = props.dialogTableValue.rfid
    form.description = props.dialogTableValue.description
  }
  console.log("form.name", form.name)
},{deep:true, immediate:true})



const handleClose=()=>{
  emits('update:modelValue',false)
  if(props.dialogTitle === '编辑植物信息'){
  }
  if(props.dialogTitle === '添加植物信息'){
    form.name = null
    form.rfid = null
    form.description = null

}
}

const handleConfirm=async()=>{
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate(async (valid) => {  // 注意：此时使用的是 ruleFormRef.value，而仅写 ruleFormRef 是拿不到值且会报错的;
    if (valid) {   // 注意：只有当所有的规则都满足后，此时的 valid 的值才为 true，才能执行下面的值;
      console.log('submit!')
      if (props.dialogTitle === '添加植物信息') {
        const res1 = await addPlant.add(form.valueOf())
        form.name = null
        form.rfid = null
        form.description = null
      }
      if (props.dialogTitle === '编辑植物信息') {
        //console.log("id是"+props.dialogTableValue.id)
        //console.log("id是" + form.valueOf().id)
        const res2 = await editPlant.edit(form)
        form.name = null
        form.rfid = null
        form.description = null
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
  ws = new WebSocket('ws://localhost:7799/plant');

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
  form.rfid = latestData.value
}



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
    <el-form :model="form" ref="ruleFormRef" label-width="auto" style="max-width: 600px" :rules="rules">
      <el-form-item label="名字" prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" />
      </el-form-item>
      <el-form-item label="标签号" prop="rfid" >
        <el-col :span="19">
          <el-input v-model="form.rfid" />
        </el-col>
        <el-button type="success" @click="getRfid" style="margin-left: 24px">扫描</el-button>
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