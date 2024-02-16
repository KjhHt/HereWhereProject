<template>
    <div>
        <h1 @click="test">테스트 페이지</h1>

        <input type="text" v-model="title" id="title" placeholder="Title"/>
        <input type="text" v-model="content" id="content" placeholder="Content"/>
        <button @click="pushTest">등록알림보내기</button>

    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const title = ref('');
const content = ref('');

const pushTest = async () => {
    // id 부분은 내가 보내고자 하는사람의 아이디 <-
    
    const response = await axios.post(`${process.env.VUE_APP_API_URL}/pushSend`, {
      id : 'ttt',
      title: title.value,
      message: content.value
    });
    console.log('확인 : ',response);
};

</script>

<style>
    
</style>