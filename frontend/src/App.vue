<script setup>
import { ref, computed, onMounted } from 'vue'
import PanolensPage from './views/PanolensPage.vue';
import MainPage from './views/MainPage.vue';
import Header from './components/Header.vue';
import Join from './views/JoinView.vue';
import MyCalendar from './views/CalendarView.vue';
import Admin from './views/AdminView.vue';
import Location from './views/LocationView.vue';
import MyPageView from './views/MyPageView.vue';
import BoardView from './views/BoardView.vue';
import TestView from './views/TestView.vue';
import TripMoment from './views/TripMoment.vue';
import { Chat } from "@chat-ui/vue3";
//import { getStompClient } from '@/services/websocket.js'; 
//import axios from 'axios';

const stompClient = ref(null);
const noticeListData = ref([]);
const noticeCountData = ref(0);
const locationValue = ref('');

onMounted(() => {
  //const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  /*
  const userInfo = vuexStore.loginStore.userInfo;
  if (userInfo != null && userInfo.id != null) {
    stompClient.value = getStompClient();  // 웹소켓 연결을 가져옵니다.
    stompClient.value.connect({}, () => {
      stompClient.value.subscribe(`/user/${userInfo.id}/queue/notification`, function(notification) {
        // 알림 메시지를 받으면 실행할 코드...
        noticeCountData.value++;
        const data = JSON.parse(notification.body);
        if(data.follow_isRequest == 'ok'){//팔로우 첫 요청일때
          const currentTime = new Date().toLocaleString();
          const lastNotice = noticeListData.value[noticeListData.value.length - 1];
          const newNoticeNo = lastNotice ? lastNotice.notice_no + 1 : 1;
          noticeListData.value.splice(0, 0, {
            id : data.follow_senderid,
            notice_content : '팔로우 요청을 보냈습니다',
            notice_createtime : currentTime,
            notice_no : newNoticeNo,
          });
          console.log(noticeListData.value);
        
        }
        console.log(data);
        console.log(data.follow_senderid);//보낸사람
        console.log(data.follow_requesttime);//시간
        console.log('요청여부 : ',data.follow_isRequest); //요청여부
      });
    });
    console.log("웹소켓 연결 및 구독 성공!");
    noticeList(userInfo.id);
  }*/
});

/*
function noticeList(){
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const userInfo = vuexStore.loginStore.userInfo;
  if (userInfo != null && userInfo.id != null) {
    axios.get(process.env.VUE_APP_API_URL+'/noticeList')
    .then(res=>{
      console.log(res);
      noticeListData.value = res.data;
      console.log(noticeListData.value);
    })
    .catch(err=>console.log(err))
  }
}*/

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

//메인페이지에서 지도 페이지로 값 넘기기
const handleImgClick = (value) =>{
  console.log('app.vue에서 : ',locationValue.value);
  locationValue.value = value;
  console.log('app.vue에서 : ',locationValue.value);
  page_.value = 'location';
}

const page_=ref('main')

computed(showHeader)
  function showHeader() {
    return this.$route.meta.showHeader !== false;
  }
console.log(ref)

function selectPage(page){
  page_.value=page
}

const disconnectLocation= ()=> {
  locationValue.value= ''
}

</script>
<template>
  <PanolensPage/>    
   <Header @selectPage="selectPage" :noticeListData="noticeListData" :noticeCountData="noticeCountData"/>
    <MainPage v-if="page_=='main'" @imgClick="handleImgClick"/>
    <Join v-if="page_=='join'"/>
    <MyCalendar v-if="page_=='mycalendar'"/>
    <Admin v-if="page_=='admin'"/>
    <Location v-if="page_=='location'" :locationValue="locationValue" @disconnect="disconnectLocation"/>
    <MyPageView v-if="page_=='mypage'"/>
    <BoardView v-if="page_=='board'" :stompClient="stompClient" />
    <Chat :onSend="handleSendEvent" :chat="data" />
    <TestView v-if="page_=='test'"/>
    <TripMoment v-if="page_=='trip-moment'"/>
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