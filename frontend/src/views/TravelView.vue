<template>
  <div class="feature-section" v-if="mbti_trip.length > 0">
    <h2 class="left-aligned slide-in-from-right">{{userMbti}} 여행 추천 TOP10</h2>
    <div class="image-list slide-in-from-left">
      <div v-for="(trip, index) in mbti_trip" :key="index" class="image-item">
      <img :src="trip.image" class="rounded-img" @click="imgClick(trip.destination)"/>
        <div class="image-info">
          <div class="info-top">
            <span class="place-name">{{ trip.destination }}</span>         
          </div>   
          <div class="info-bottom">
            <span class="place-tag">{{ formatTags(trip.tag) }}</span>                
          </div>          
        </div>
      </div>
    </div>
  </div>
  <div class="feature-section">
    <h2 class="left-aligned slide-in-from-right">국내 인기 여행지 TOP10</h2>
    <div class="image-list slide-in-from-left">
      <div v-for="(trip, index) in korea_trip" :key="index" class="image-item">
        <img :src="trip.image" class="rounded-img" @click="imgClick(trip.location)"/>
        <div class="image-info">
          <div class="info-top">
            <span class="place-name">{{ trip.location }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="feature-section">
    <h2 class="left-aligned slide-in-from-right">해외 인기 여행지 TOP10</h2>
    <div class="image-list slide-in-from-left">
      <div v-for="(trip, index) in overseas_trip" :key="index" class="image-item">
      <img :src="trip.image" class="rounded-img" @click="imgClick(trip.location)"/>
        <div class="image-info">
          <div class="info-top">
            <span class="place-name">{{ trip.location }}</span>                   
          </div>        
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
// import { gsap } from 'gsap';
import axios from "axios";
// import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { onMounted, ref, defineEmits } from 'vue';

const mbti_trip = ref([]);
const korea_trip = ref([]);
const overseas_trip = ref([]);
const vuexStore = JSON.parse(localStorage.getItem('vuex'));
const userMbti = vuexStore.loginStore.userInfo.mbti;

async function fetchMbtiTrip() {
  try {
    const data = {
      search: userMbti
    };
    const response = await axios.post(process.env.VUE_APP_PYTHON_API_URL + '/recommend_by_mbti', data);
    mbti_trip.value = response.data;
    console.log('mbti_trip', mbti_trip);
  } catch (error) {
    console.error('Error fetching Mbti trip:', error);
  }
}

function formatTags(tags) {
  return tags.split(',').map(tag => `#${tag.trim()}`).join(' ');
}

async function fetchKoreaTrip() {
  try {
    const response = await axios.get(process.env.VUE_APP_PYTHON_API_URL + '/korea');
    korea_trip.value = response.data;
  } 
  catch (error) {
    console.error('Error fetching Korea trip:', error);
  }
}

async function fetchOverseasTrip() {
  try {
    const response = await axios.get(process.env.VUE_APP_PYTHON_API_URL + '/overseas');
    overseas_trip.value = response.data;
  } 
  catch (error) {
   console.error('Error fetching Overseas trip:', error);
  }
}

onMounted(() => {
  // gsap.registerPlugin(ScrollTrigger);
  // initGSAP();
  fetchKoreaTrip();
  fetchOverseasTrip();
  fetchMbtiTrip();
});

const emit = defineEmits(['imgClick']);
const imgClick = (value) => {
  emit('imgClick',value);
}

// const animateFrom = (elem, x, y) => {
// elem.style.transform = `translate(${x}px, ${y}px)`;
// elem.style.opacity = "0";
// gsap.fromTo(elem, { x: x, y: y, autoAlpha: 0 }, {
// duration: 1.25,
// x: 0,
// y: 0,
// autoAlpha: 1,
// ease: "expo",
// overwrite: "auto"
// });
// }

// const hide = (elem) => {
// gsap.set(elem, { autoAlpha: 0 });
// }

// const initGSAP = () => {
// gsap.registerPlugin(ScrollTrigger);

// const animateReveal = (elem, offsetY) => {
// hide(elem);
// ScrollTrigger.create({
// trigger: elem,
// markers: false,
// onEnter: () => animateFrom(elem, 0, offsetY),
// onEnterBack: () => animateFrom(elem, 0, -offsetY),
// onLeave: () => hide(elem)
// });
// };

// const animateSlide = (elem, offsetX) => {
// hide(elem);
// ScrollTrigger.create({
// trigger: elem,
// markers: false,
// onEnter: () => animateFrom(elem, offsetX, 0),
// onEnterBack: () => animateFrom(elem, -offsetX, 0),
// onLeave: () => hide(elem)
// });
// };

// gsap.utils.toArray(".gs_reveal").forEach((elem) => animateReveal(elem, 100));
// gsap.utils.toArray(".slide-in-from-left").forEach((elem) => animateSlide(elem, -100));
// gsap.utils.toArray(".slide-in-from-right").forEach((elem) => animateSlide(elem, 100));
// };
</script>

<style scoped>
body {
font-weight: 300;
}


img {
max-width: 100%;
}

.image-list {
display: flex; /* 이미지를 가로로 나열 */
gap: 40px;
}

.rounded-img {
width: 100%;  /* 이미지 폭을 부모 요소의 100%로 설정 */
height: auto;  /* 이미지 높이를 자동으로 설정 */
border-radius: 10px;  /* 이미지 모서리를 둥글게 설정 */
margin-bottom: 10px;
}

.image-list {
display: grid;  /* 이미지 리스트를 그리드 레이아웃으로 설정 */
gap: 40px;
grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));  /* 최소 너비 320px의 열을 가능한 많이 만들되, 열 너비가 320px보다 커지면 공간을 고르게 분배 */
}

.image-list img:last-child {
margin-right: 0; /* 마지막 이미지의 오른쪽 마진 제거 */
}

.left-aligned {
text-align: left;
}

.feature-section:not(:last-child) {
margin-bottom: 100px;
}

.image-item {
display: flex; /* 이미지와 이미지 정보를 세로로 배치 */
flex-direction: column; /* 요소들을 세로로 배치 */
}

.info-top {
display: flex; /* 장소 이름과 별점을 한 줄에 표시 */
justify-content: space-between; /* 장소 이름과 별점 사이에 공간 삽입 */
}

.info-bottom {
display: flex; /* 장소 이름과 별점을 한 줄에 표시 */
justify-content: space-between; /* 장소 이름과 별점 사이에 공간 삽입 */
text-align: left;
}

.address {
text-align: left; /* 주소 왼쪽 정렬 */
color:gray
}

.place-name {
font-weight: bold;
}

.place-tag {
font-weight: bold;
font-size: 15px;
}


</style>