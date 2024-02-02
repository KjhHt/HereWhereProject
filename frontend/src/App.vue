<script setup>
import { ref, computed } from 'vue'
import PanolensPage from './views/PanolensPage.vue';
import MainPage from './views/MainPage.vue';
import Header from './components/Header.vue';
import Join from './views/JoinView.vue';
import MyCalendar from './views/CalendarView.vue';
import Admin from './views/AdminView.vue';
import Location from './views/LocationView.vue';
import MyPageView from './views/MyPageView.vue';
import BoardView from './views/BoardView.vue';
import { Chat } from "@chat-ui/vue3";

// 챗봇 : @Chat-ui\vue3 -> dist -> components -> (240~250 라인) chat-ui.vue3.es.js 모듈수정해야함 
function handleSendEvent(input){
  if (input == '') return;
  const messagePerson = {
    type: 'person',
    timestamp: formatAMPM(new Date()),
    message: input,
  };
  data.value.push(messagePerson);


  setTimeout(async () => {
    const response = await fetch('https://www.boredapi.com/api/activity');
    const res = await response.json();
    const messageChatbot = {
      type: 'chatbot',
      timestamp: formatAMPM(new Date()),
      message: res.activity,
    };
    data.value.push(messageChatbot);
    }, getRandomNumber());
}

function getRandomNumber() {
  return Math.floor(Math.random() * (2000 - 1000 + 1)) + 1000;
}

function formatAMPM(date) {
  var hours = date.getHours();
  var minutes = date.getMinutes();
  var ampm = hours >= 12 ? 'PM' : 'AM';
  hours = hours % 12;
  hours = hours ? hours : 12; // the hour '0' should be '12'
  minutes = minutes < 10 ? '0' + minutes : minutes;
  var strTime = hours + ':' + minutes + ' ' + ampm;
  return strTime;
}

const data = ref([
  { message: 'Hi! How are you?', type: 'chatbot', timestamp: '3:45 PM' },
  { message: 'Hello, i\'m fine, thanks.', type: 'person', timestamp: '3:46 PM' },
  { message: 'How can i help you?', type: 'chatbot', timestamp: '3:47 PM' },
])


const page_=ref('main')

computed(showHeader)
  function showHeader() {
    return this.$route.meta.showHeader !== false;
  }
console.log(ref)

function selectPage(page){
  page_.value=page
  
}
</script>
<template>
  <PanolensPage/>
   <Header @selectPage="selectPage"/>
    <MainPage v-if="page_=='main'"/>
    <Join v-if="page_=='join'"/>
    <MyCalendar v-if="page_=='mycalendar'"/>
    <Admin v-if="page_=='admin'"/>
    <Location v-if="page_=='location'"/>
    <MyPageView v-if="page_=='mypage'"/>
    <BoardView v-if="page_=='board'"/>
    <Chat :onSend="handleSendEvent" :chat="data" />

</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0;
  padding: 0;
  height: 100vh;
}
</style>