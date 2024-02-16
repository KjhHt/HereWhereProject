<template>
    <div class="chat">
      <CommentList :msgData="msgData" class="comment_list"/>
      <CommentForm @submitMessage="sendMessage" class="comment_form"/>
    </div>
  </template>
  
<script setup>
import { ref,onMounted,defineProps } from 'vue';
import CommentList from "@/components/detail/CommentList";
import CommentForm from "@/components/detail/CommentForm";
import axios from 'axios';

const props = defineProps({
  boardDetail: {
    type: Array,
  },
  boardList : {
    type: Array,
  },
  formatTimeAgo : {
    type : Function,
  },
})

const msgData = ref([]);

onMounted(() => {
  getComment();
});

const getComment = async () => {
  try {
    
    const res = await axios.get(`${process.env.VUE_APP_API_URL}/comment`, {
      params: {
        board_no: props.boardDetail[0]
      }
    });
    
    for(const key in res.data){
      console.log('확인 : ',res.data[key].profileimage);
      let profileimage = res.data[key].profileimage;
      if( profileimage === '0' ){
        res.data[key].profileimage = require('@/assets/dino.jpg');
      }
      else{
        if(profileimage.startsWith("D:") || profileimage.startsWith("E:") ){
          const pathSegments = profileimage.split('\\');
          const lastSegment = pathSegments[pathSegments.length - 1];
          const imgResponse = await axios.get(`${process.env.VUE_APP_API_URL}/profile/${lastSegment}`);
          const dataURI = `data:${imgResponse.headers['content-type']};base64,${imgResponse.data}`;
          res.data[key].profileimage = dataURI;
        }
        else{
          res.data[key].profileimage = profileimage;
        }
      }
      const createtime = props.formatTimeAgo(res.data[key].comment_createtime);
      res.data[key].comment_createtime = createtime;
    }
    msgData.value = res.data;
  } catch (err) {
    console.log(err);
  }
}

// 메시지 전송 함수
const sendMessage = (msg) => {
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const name = vuexStore.loginStore.userInfo.name;
  const data = {
    board_no : props.boardDetail[0],
    comment_content : msg,
    comment_writer : name
  }
  
  axios.post(`${process.env.VUE_APP_API_URL}/user/commentInsert`,data)
  .then( res => {
    if(res.status === 200){
      getComment();
      for (const item of props.boardList) {
        if(item.board_no === props.boardDetail[0]){
          console.log(item.comment_count);
          item.comment_count++;
        }
      }
      const text = document.querySelector('.form__input');
      text.value = '';

    }
  })
  .catch(err => console.log(err))
  scrollToBottom();
};

// 스크롤을 아래로 이동시키는 함수
const scrollToBottom = () => {
  setTimeout(() => {
    const element = document.getElementById("chat__body");
    element.scrollTop = element.scrollHeight;
  }, 0);
};
</script>

  
<style scoped>
.chat {
  display: flex;
  flex-direction: column;
  padding-bottom: 1em;
}

.comment_list{
  flex: 9; /* 전체의 90% 차지 */
}

.comment_form {
  flex:1;
}
</style>