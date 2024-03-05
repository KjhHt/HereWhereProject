<template>
  <div class="container my-4">
    <LoadingModal :loading="loading"/>
    <div v-if="flights.length" class="flight-results">
      <div v-for="(flight, idx) in flights" :key="flight.id" class="flight-card mb-3 p-3 shadow-sm">
        <!-- 날짜 한 번만 표시 -->
        <div class="flight-date mb-2 p-2 text-start">출발일자 : {{ formatDate(flight.departureTime) }}</div>
        <div class="flight-card-header d-flex justify-content-between align-items-center mb-2">
          <div class="flight-info mb-2">
            <img :src="flight.airlineLogo" alt="" class="airline-logo mr-2">
            <span class="airline-name ml-2">{{ airlines[flight.airline] }}</span>
          </div>
          <div class="badges position-absolute top-0 end-0" style="transform: translateY(-10px);">
            <span v-if="idx==0" class="badge badge-danger">최저가</span>
            <span v-if="flight.stops.length<1" class="badge badge-success ms-1">직항</span>
            <span v-if="!flight.stops.length<1" class="badge badge-warning ms-1">경유</span>
          </div>
          <div class="flight-tags">
            <!--
            <div class="flight-stops-info mb-2">
              <div v-if="flight.stops.length === 0" class="flight-tags">
              <span class="badge ml-1 badge-info">직항</span>
              </div>
            <div v-else class="d-flex align-items-center">
            <span v-for="(stop, index) in flight.stops" :key="index" class="mx-1">
              <i class="bi bi-geo-alt-fill"></i>{{ getCountryByIata(stop) }}
            </span>
            </div>
            </div>
            -->
            <div class="flight-path">
              <span class="flight-point departure"><i class="bi bi-geo-alt-fill"></i>{{ getCountryByIata(flight.departureAirport) }}</span>
              <span class="flight-stops">
                <i class="bi bi-dash-lg"></i>
                <span v-for="(stop, index) in flight.stops" :key="index" class="flight-point stop">
                  <i class="bi bi-record-fill"></i> {{ getCountryByIata(stop) }}
                </span>
                <i class="bi bi-dash-lg"></i>
              </span>
              <span class="flight-point destination"><i class="bi bi-geo-alt-fill"></i>{{ getCountryByIata(flight.destinationAirport) }}</span>
            </div>
          </div>
        </div>
        <div class="flight-body">
          <div class="flight-times d-flex justify-content-between">
            <div>
              <!-- 시간만 표시 -->
              <span class="flight-departure">{{ formatTime(flight.departureTime) }}</span>
              <span class="mx-2"><i class="bi bi-forward-fill"></i></span>
              <span class="flight-arrival">{{ formatTime(flight.arrivalTime) }}</span>
            </div>
          </div>
          <div class="flight-stops my-2 flight-price">
            <a @click="payment(flight)" role="button" class="btn btn-primary">{{ parseInt(flight.price).toLocaleString('ko-KR') }}원</a>
          </div>
        </div>
        <div class="flight-duration text-start">
          <i class="bi bi-clock"></i>
          {{ formatDuration(flight.duration) }}
        </div>
        
      </div>
    </div>
    <div v-if="searchData" class="text-muted">검색 결과가 없습니다.</div>
  </div>
</template>

<script setup>
import { ref,defineProps, watch } from 'vue';
import { gsap } from 'gsap';
import { Bootpay } from '@bootpay/client-js'

import LoadingModal from '@/components/search/LoadingModal.vue';
// eslint-disable-next-line no-unused-vars
const searchData = ref(false);

const props=defineProps({
  flights:Array,
  airlines:Object,
  airports:Array,
  loading:Boolean,
  travelClass:Object,
})
function formatDate(dateTime) {
    const date = new Date(dateTime);
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${month}월${day}일`;
}

function formatTime(dateTime) {
    const date = new Date(dateTime);
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
}


function formatDuration(duration) {
    const match = duration.match(/PT(\d+H)?(\d+M)?/);
    const hours = match[1] ? parseInt(match[1], 10) : 0;
    const minutes = match[2] ? parseInt(match[2], 10) : 0;
    return `${hours}시간 ${minutes}분`;
}

function getCountryByIata(iataCode) {
  const airport = props.airports.find((airport) => airport.IATA === iataCode);
  return airport ? airport.AIRPORT_NAME : iataCode;
}

watch(()=>props.flights,newFlights=>{
  if(!newFlights) return
  gsap.from('.flight-card', {
    duration: 0.5, // 애니메이션 지속 시간
    y: 100, // 시작 Y 위치 (아래에서 위로 100px)
    opacity: 0, // 시작 투명도
    stagger: 0.2, // 각 항목의 애니메이션 시작 지연 시간
    ease: 'power1.out' // Easing 함수
  });
  if(newFlights.length === 0){ // 검색결과 없을때만 나오도록 변경
    searchData.value = true;
  }

})
// 좌석 랜덤함수
function generateRandomSeat() {
  const letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K'];
  const letter = letters[Math.floor(Math.random() * letters.length)];
  const number = Math.floor(Math.random() * 51) + 10;
  return letter + number.toString();
}
// 숫자 랜덤함수
function generateRandomNumber(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

// 결제 함수 
const paycheck = (data) => {
  alert('결제가 완료되었습니다.')
  console.log('paycheck : ', data);
};

async function payment(data){
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const userInfo = vuexStore.loginStore.userInfo;
  const id = userInfo.id;
  const name = userInfo.name;
  const phoneNumber = userInfo.phonenumber;
  const seat = props.travelClass;
  const seatNumber = generateRandomSeat();
  const order_name = getCountryByIata(data.destinationAirport) + '( ' + seat + '[좌석번호 : ' + seatNumber + '] )';
  const order_id = '0000'+generateRandomNumber(1111,9999);
  try{
    await Bootpay.requestPayment({
      "application_id": process.env.VUE_APP_BOOTPAY_API_SECRET,
      "price": data.price,               
      "order_name": order_name,   
      "order_id": order_id, 
      "tax_free": 0,
      "user": {
        "id": id,
        "username": name,
        "phone": phoneNumber,
      },
      "items": [
        {
          "id": order_id,
          "name": order_name,
          "qty": 1,
          "price": data.price
        }
      ],
      "extra": {
        "open_type": "iframe",
        "card_quota": "0,2,3",
        "escrow": false
      }
    })
    .then(response => {
      paycheck(response);
    })
  }
  catch(err){
    console.log('결제에러 : ',err);
  }
}
</script>

<style scoped>

.position-relative {
  position: relative;
}

.position-absolute {
  position: absolute;
}

.top-0 {
  top: 0;
}

.end-0 {
  right: 0; /* Bootstrap 5에서 'end'는 'right'에 해당합니다 */
}


.container, .flight-results {
  max-width: 1200px;
  width: 100%;
}

/* FlightBookingForm과 일치하는 컨테이너 스타일 */
.flight-results {
  margin: auto; /* 중앙 정렬 */
  width: 100%;
}


.flight-card {
  background-color: #fff;
  border-radius: 5px;
}

.flight-card-header {
  position: relative;
  border-bottom: 1px solid #eee;
}

.airline-logo {
  width: 40px;
  height: auto;
  margin-right: 5px;
}

.flight-info, .flight-tags {
  display: flex;
  align-items: center;
}

.flight-body, .flight-footer {
  display: flex;
  justify-content: space-between;
}

.flight-duration, .flight-price {
  font-weight: bold;
}

.badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.25em 0.6em; /* 패딩을 조정하여 텍스트의 상하 간격을 줄입니다 */
  font-size: var(--fc-small-font-size); /* 폰트 사이즈를 공통 변수를 사용하여 조정합니다 */
  font-weight: bold; /* 폰트 굵기 */
  line-height: 1.2; /* 라인 높이를 조정하여 텍스트의 높이를 맞춥니다 */
  text-align: center; /* 텍스트 중앙 정렬 */
  white-space: nowrap; /* 텍스트가 한 줄로 표시되도록 설정 */
  vertical-align: middle; /* 수직 중앙 정렬 */
  border-radius: 0.375rem; /* 테두리 반경 */
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
    border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out; /* 전환 효과 */
  border: 1px solid transparent; /* 테두리를 투명으로 설정하여 높이에 영향을 주지 않도록 합니다 */
}

.badge-danger {
  color: var(--fc-button-text-color); /* 텍스트 색상 */
  background-color: var(--bs-danger); /* 배경 색상 */
  border-color: var(--bs-danger); /* 테두리 색상 */
}

.badge-success {
  color: var(--fc-button-text-color); /* 텍스트 색상 */
  background-color: var(--bs-success); /* 배경 색상 */
  border-color: var(--bs-success); /* 테두리 색상 */
}
.badge-warning {
  color: var(--fc-button-text-color); /* 텍스트 색상 */
  background-color: var(--bs-warning); /* 배경 색상 */
  border-color: var(--bs-warning); /* 테두리 색상 */
}


.badge-info {
  color: var(--fc-button-text-color); /* 텍스트 색상 */
  background-color: var(--bs-info); /* 배경 색상 */
  border-color: var(--bs-info); /* 테두리 색상 */
}




/* 기존 스타일에 추가 */
.flight-stops-info {
  font-size: 0.9em; /* 텍스트 크기 조정 */
  color: #555; /* 텍스트 색상 조정 */
  display: flex;
  align-items: center;
}

.flight-stops-info i {
  color: #17a2b8; /* 아이콘 색상 */
  margin-right: 5px; /* 아이콘과 텍스트 사이의 간격 */
}

/* 직항 및 경유지 아이콘 스타일 */
.flight-path {
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-top: 10px;
}

.flight-point {
  display: inline-flex;
  align-items: center;
  font-size: 0.9rem;
}

.departure, .destination {
  font-weight: bold;
}

.stop {
  margin: 0 5px;
}

.bi-dash-lg {
  font-size: 0.8rem;
  color: #adb5bd; /* 경로 선 색상 */
}

.bi-record-fill {
  font-size: 0.5rem;
  color: #f0ad4e; /* 경유지 아이콘 색상 */
}

.bi-geo-alt-fill {
  font-size: 1rem;
  color: #17a2b8; /* 출발지 및 도착지 아이콘 색상 */
}

</style>
