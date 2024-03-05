<script setup>
import { ref, computed, onMounted , watch } from 'vue'
import PanolensPage from './views/PanolensPage.vue';
import MainPage from './views/MainPage.vue';
import Header from './components/Header.vue';
import Join from './views/JoinView.vue';
import MyCalendar from './views/CalendarView.vue';
import Location from './views/LocationView.vue';
import MyPageView from './views/MyPageView.vue';
import BoardView from './views/BoardView.vue';
import TestView from './views/TestView.vue';
import TripMoment from './views/TripMoment.vue';
import TripMomentWriteView from './views/TripMomentWriteView.vue';
import TripMomentDetailView from './views/TripMomentDetailView.vue';
import TranslationView from './views/TranslationView.vue';
import FlightReserveView from './views/FlightReserveView.vue';
import ChatBot from './components/ChatBot.vue';
import axios from 'axios';

import { getStompClient } from '@/services/websocket.js'; 
import { useRoute } from 'vue-router';

const route=useRoute();
const stompClient = ref(null);
const noticeListData = ref([]);
const noticeCountData = ref(0);
const locationValue = ref('');
const locationLatLng = ref([]);
const initialSearchParameters=ref({});

watch(() => [route.query.origin, route.query.destination, route.query.adults], ([origin, destination, adults]) => {
  if (origin && destination && adults) {
    initialSearchParameters.value = {
      origin,
      destination,
      adults: parseInt(adults, 10) // 문자열을 숫자로 변환
    };
    page_.value = route.query.page_;
  }
}, {
  deep: true
});


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
        console.log('제발와라 : ',data) //이따
        if(data.follow_isRequest == 'ok'){//팔로우 첫 요청일때
          const currentTime = new Date().toLocaleString();
          noticeListData.value.splice(0, 0, {
            follow_senderid : data.follow_senderid,
            notice_content : '팔로우 요청을 보냈습니다',
            notice_createtime : currentTime,
            notice_no : data.notice_no,
          });
          console.log(noticeListData.value);
        
        }
        console.log(data);
        console.log(data.follow_senderid);//보낸사람
        console.log(data.follow_requesttime);//시간
        console.log('요청여부 : ',data.follow_isRequest); //요청여부
      });
    });
    //console.log("웹소켓 연결 및 구독 성공!");
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
      noticeListData.value = res.data.map(item => {
        if(item.profileimage === '0'){
          item.profileimage = require('@/assets/dino.jpg');
        }
        else if(!item.profileimage.startsWith('http')){
          item.profileimage = `data:${res.headers['content-type']};base64,${item.profileimage}`;
        }
        return item; // 이 부분이 추가되었습니다.
      });
      console.log('5시46분 : ',res.data);
    })
    .catch(err=>console.log(err))
  }
}
 
const page_=ref('main')

//메인페이지에서 지도 페이지로 값 넘기기
const handleImgClick = (value) =>{
  locationValue.value = value;
  page_.value = 'location';
}
const handleItemClick = (value) =>{ 
  locationLatLng.value = value;
  page_.value = 'location';
}
const handleSearchImgLocation = (value) =>{
  const latLngArray = [value.geometry.location.lat, value.geometry.location.lng];
  locationLatLng.value = latLngArray;
  page_.value = 'location';
}
const disconnectLocation= ()=> {
  locationValue.value= '',
  locationLatLng.value = [];
} 

computed(showHeader)
  function showHeader() {
    return this.$route.meta.showHeader !== false;
  }

function selectPage(page){
  if(page === 'main'){
    window.history.pushState({}, '', '/');
  }
  page_.value=page
}

const data_ = ref({});
function selectPageData(page,data){
  data_.value=data;
  page_.value=page;
}

function selectPageFlight(page){
  page_.value=page;
  initialSearchParameters.value = '';
}
const resetCount = () => {
  noticeCountData.value = 0;
}

function moveLocationViewHandler(place_id){
  console.log('app.vue에서 place_id : ',place_id);
}
</script>
<template>
  <PanolensPage/>    
    <Header @resetCount="resetCount" @selectPage="selectPage" @selectPageFlight="selectPageFlight" :noticeListData="noticeListData" :noticeCountData="noticeCountData"/>
    <MainPage v-if="page_=='main'" @imgClick="handleImgClick" @searchImgLocation="handleSearchImgLocation"/>
    <Join v-if="page_=='join'"/>
    <MyCalendar v-if="page_=='mycalendar'"/>
    <Location v-if="page_=='location'" :locationValue="locationValue" :locationLatLng="locationLatLng" @disconnect="disconnectLocation"/>
    <MyPageView v-if="page_=='mypage'" @handleItem="handleItemClick"/>
    <BoardView v-if="page_=='board'" :stompClient="stompClient" />
    <ChatBot/>
    <TestView v-if="page_=='test'"/>
    <TripMoment @selectPage="selectPage" @selectPageData="selectPageData" v-if="page_=='trip-moment'"/>
    <TripMomentWriteView @selectPage="selectPage" v-if="page_=='TripMomentWriteView'"/>
    <TripMomentDetailView @moveLocationViewHandler="moveLocationViewHandler" :stompClient="stompClient" :tripmonetData="data_" v-if="page_=='TripMomentDetailView'"/>
    <TranslationView v-if="page_=='translation'"/>
    <FlightReserveView v-if="page_=='flightreserve'" @updateIsHeaderData="updateIsHeaderData" :isHeader="isHeader" :initialSearchParameters="initialSearchParameters"/>
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