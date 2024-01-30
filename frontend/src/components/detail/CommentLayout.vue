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
})

const msgData = ref([]);

onMounted(() => {
  getComment();
});

const getComment = () => {
  axios.get('http://localhost:8080/comment', {
    params: {
      board_no: props.boardDetail[0]
    }
  })
  .then(res =>{
    msgData.value = res.data;
  })
  .catch(err => console.log(err))
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
  axios.post('http://localhost:8080/user/commentInsert',data)
  .then( res => {
    if(res.status === 200){
      getComment();
      for (const item of props.boardList) {
        if(item.board_no === props.boardDetail[0]){
          console.log(item.comment_count);
          item.comment_count++;
        }
      }
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