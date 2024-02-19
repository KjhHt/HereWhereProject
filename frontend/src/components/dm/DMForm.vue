<template>
    <div id="container">
        <DMAside 
          v-if="!loading" 
          @updateChatList="handleUpdateChatList"
          :followList="followList"
          >
        </DMAside>
        <DMMain 
          ref="dmMain"
          v-if="!loading" 
          :followList="followList"
          :chatList="chatList"
          :followIndex="followIndex"
          :addMessage="addMessage"
          >
        </DMMain>
    </div>
</template>

<script setup>
import { ref , onMounted} from 'vue';
import DMAside from './DMAside.vue'
import DMMain from './DMMain.vue'
import axios from 'axios';

const followList = ref([]);
const followIndex = ref(0);
const chatList = ref([]);
const loading = ref(true);
const dmMain = ref(null);

function addMessage(message) {
  console.log('왔음');
  chatList.value.push(message);
  console.log(chatList.value);
}

const handleUpdateChatList = async (user,index) => {
  console.log('updateChatList 이벤트를 받았습니다.', user.id);

  // 웹소켓 연결을 끊습니다.
  await dmMain.value.disconnect();

  followIndex.value = index;
  getChatList(user.id);

  // 새로운 웹소켓 연결을 시작합니다.
  await dmMain.value.connect();

}

const getChatList = async (data) => {
  try {
    
    const follow = await axios.get(`${process.env.VUE_APP_API_URL}/user/followList`);
    followList.value = follow.data;
    
    let paramData = followList.value[0].id;
    if(data != 1){
        paramData = data;
    }
    
    const chatlist = await axios.get(`${process.env.VUE_APP_API_URL}/user/chatList`,{
      params:{
        rid : paramData
      }
    })
    chatList.value = chatlist.data;
    console.log(chatList.value);
  } 
  catch (error) {
    console.error(error);
  }
  finally{
    loading.value = false;
  }
}

onMounted(() => {
  getChatList(1);
})


</script>

<style scoped>
*{
    box-sizing:border-box;
}
body{
	background-color:#abd9e9;
	font-family:Arial;
}
#container{
	width:750px;
	height:800px;
	background:#eff3f7;
	margin:0 auto;
	font-size:0;
	border-radius:5px;
	overflow:hidden;
}
h2,h3{
	margin:0;
}


</style>