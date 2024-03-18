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
import MbtiView from './views/MbtiView.vue';
// import ChatBot from './components/ChatBot.vue';
import HereRobot from './components/HereRobot.vue';
import axios from 'axios';

import { getStompClient } from '@/services/websocket.js'; 
import { useRoute } from 'vue-router';

const route=useRoute();
const stompClient = ref(null);
const noticeListData = ref([]);
const noticeCountData = ref(0);
const locationValue = ref('');
const locationLatLng = ref([]);
const predictimg = ref(null);
const initialSearchParameters=ref({});
let placeId= ref('');

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

onMounted(async () => {
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const userInfo = vuexStore.loginStore.userInfo;
  if (userInfo != null && userInfo.id != null) {
    stompClient.value = getStompClient();
    stompClient.value.connect({}, () => {
      stompClient.value.subscribe(`/user/${userInfo.id}/queue/notification`, async function(notification) {
        noticeCountData.value++;
        const data = JSON.parse(notification.body);
        if(data.follow_isRequest == 'ok'){
          const currentTime = new Date();
          const profileImagePath = await processProfileImagePath(data.profileImage);
          noticeListData.value.splice(0, 0, {
            follow_senderid : data.follow_senderid,
            notice_content : '팔로우 요청을 보냈습니다',
            notice_createtime : currentTime,
            notice_no : data.notice_no,
            profileimage : profileImagePath
          });
        }
        console.log(data);
        console.log(data.follow_senderid);
        console.log(data.follow_requesttime);
        console.log('요청여부 : ',data.follow_isRequest);
      });
    });
    noticeList(userInfo.id);

    panolens.value = false;
  }
});

const processProfileImagePath = async (profileimage) => {
  if(profileimage === '0') {
    return require('@/assets/dino.jpg');
  }
  else if(profileimage.startsWith("D:") || profileimage.startsWith("E:")) {
    const pathSegments = profileimage.split('\\');
    const lastSegment = pathSegments[pathSegments.length - 1];
    const res = await axios.get(`${process.env.VUE_APP_API_URL}/profile/${lastSegment}`);
    return `data:${res.headers['content-type']};base64,${res.data}`;
  }
  else{
    return profileimage;
  }
};


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
        return item; 
      });
    })
    .catch(err=>console.log(err))
  }
}
 
const page_=ref('main')
const panolens = ref(true);
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
  const predict = value;
  const latLngArray = [value.geometry.location.lat, value.geometry.location.lng];
  predictimg.value = predict;
  locationLatLng.value = latLngArray;
  page_.value = 'location';
}
const disconnectLocation= ()=> {
  locationValue.value= '',
  locationLatLng.value = [];
  placeId.value= '';
} 

computed(showHeader)
  function showHeader() {
    return this.$route.meta.showHeader !== false;
  }



function selectPage(page){
  if(page === 'main'){
    window.history.pushState({}, '', '/');
    panolens.value = false;
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
  placeId.value= place_id;
  page_.value= 'location';
}

const mypageIdData = ref('');
const selectMyPage = (page)=> {
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const userInfo = vuexStore.loginStore.userInfo;
  if (userInfo != null && userInfo.id != null) {
    const id = userInfo.id;
    mypageIdData.value = id;
  }
  page_.value=page;
}
const setIata= iata=>{
  initialSearchParameters.value={
    ...iata,
    adults: parseInt(1, 10)
  }
  page_.value= 'flightreserve'
}
</script>
<template>
  <PanolensPage v-if="panolens" />
    <Header @resetCount="resetCount" @selectPage="selectPage" @selectMyPage="selectMyPage" @selectPageFlight="selectPageFlight" :noticeListData="noticeListData" :noticeCountData="noticeCountData" :isMainPage="page_=== 'main'"/>
    <MainPage v-if="page_=='main'" @imgClick="handleImgClick" @searchImgLocation="handleSearchImgLocation"/>
    <Join v-if="page_=='join'" @selectPage="selectPage"/>
    <MyCalendar v-if="page_=='mycalendar'"/>
    <Location v-if="page_=='location'" @passIataCode="setIata" :appPlaceId="placeId" :locationValue="locationValue" :locationLatLng="locationLatLng" :predictimg="predictimg" @disconnect="disconnectLocation"/>
    <HereRobot @moveToLocation="handleImgClick" @moveToFlight="setIata" @moveToLocationLatLng="handleItemClick"/>
    <MyPageView v-if="page_=='mypage'" @selectPageData="selectPageData" :mypageIdData="mypageIdData" @handleItem="handleItemClick"/>
    <BoardView v-if="page_=='board'" :stompClient="stompClient" />
    <TestView v-if="page_=='test'"/>
    <TripMoment @selectPage="selectPage" @selectPageData="selectPageData" v-if="page_=='trip-moment'"/>
    <TripMomentWriteView @selectPage="selectPage" v-if="page_=='TripMomentWriteView'"/>
    <TripMomentDetailView @selectPageStartFlight="setIata" @moveLocationViewHandler="moveLocationViewHandler" :stompClient="stompClient" :tripmonetData="data_" v-if="page_=='TripMomentDetailView'"/>
    <TranslationView v-if="page_=='translation'"/>
    <FlightReserveView v-if="page_=='flightreserve'" @updateIsHeaderData="updateIsHeaderData" :isHeader="isHeader" :initialSearchParameters="initialSearchParameters"/>
    <MbtiView v-if="page_=='mbti'"/>
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
  height: 90vh;
}
.modal-backdrop{
  background: none;
}
.modal-open{
  padding-right: 0px !important
}
</style>