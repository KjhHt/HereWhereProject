<template>
  <div class="hotel-card" @click="showDetail(props.hotel)">
    <div class="card-body">
      <div class="card-info">
        <h6 class="card-hotel-name">{{ props.hotel.hotel }}</h6>
        <p> {{ props.hotel.address }}</p>
        <p> {{ (props.hotel.review / 2).toFixed(1) }}점
            <span v-for="n in 5" :key="n">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                <path v-if="n <= (props.hotel.review / 2).toFixed(1)" fill="#FFD700" d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                <g v-else-if="n - 0.5 <= (props.hotel.review / 2).toFixed(1)" fill="#FFD700">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                  <path d="M8 0v16h8V0" fill="#fff"/>
                </g>
                <path v-else d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z" fill="#fff"/>
              </svg>
            </span>
          </p>
        <p> {{ props.hotel.price }}</p>
        <!--<p>편의시설: {{ props.hotel.convenience }}</p>-->
      </div>
      <div class="card-img">
        <img :src="props.hotel.img_urls" alt="호텔 이미지">
      </div>
    </div>
  </div>

  <!-- 상세 정보 오프캔버스 -->
  <div class="offcanvas offcanvas-start" :class="{ 'show': isDetailVisible, 'hide-animation': !isDetailVisible }"  
        data-bs-scroll="true" 
        data-bs-backdrop="false" 
        tabindex="-1" 
        style="width: 410px; height: calc(100vh - 81px); bottom: 0; top: auto; border: none;">
    <!-- 2.21일 수정 -->
    <div class="offcanvas-header d-flex justify-content-between align-items-center" style="height: 50px;">
      <div>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="hideDetail"><i class="bi bi-chevron-double-left"></i></button> 
      </div>
      <h5 style="margin-top: 11px; margin-left: -13px;">Here Where Hotel Details</h5>
      <div style="width: same-as-button;"></div>
    </div>
    <div class="offcanvas-body">
      <div class="card product-detail">
        <div class="product-img">
          <img :src="props.hotel.img_urls" alt="호텔 이미지">
        </div>
        <div class="hotel-name">
          <h1>{{ selectedHotel.hotel }}</h1>
        </div>
        <div class="score-area" aria-label="consumer score">
          <p><i class="bi bi-star-fill"></i> 평점 : {{ (selectedHotel.review / 2).toFixed(1) }}점
            <span v-for="n in 5" :key="n">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                <path v-if="n <= (props.hotel.review / 2).toFixed(1)" fill="#FFD700" d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                <g v-else-if="n - 0.5 <= (props.hotel.review / 2).toFixed(1)" fill="#FFD700">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                  <path d="M8 0v16h8V0" fill="#fff"/>
                </g>
                <path v-else d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z" fill="#fff"/>
              </svg>
            </span>
          </p>      
        </div>
        <div class="card-content">
          <p><i class="bi bi-geo-alt-fill"></i> {{ selectedHotel.address }}</p>
        </div>
        <div class="card-content-price">   
          <p>HERE WHERE 단독 최저가</p>
        </div>
        <div class="card-content-price-price">   
          <p>{{ selectedHotel.price }}</p>
        </div>
        <div class="card-footer">
          <button class="btn btn-line"><i class="bi bi-cart-plus-fill"></i> 장바구니에 담기</button>
          <button class="btn btn-fill" @click="payHandler()"><i class="bi bi-calendar-plus-fill"></i> 예약하러 가기</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script setup>
import { defineProps, ref } from 'vue';
import { Bootpay } from '@bootpay/client-js';
import axios from 'axios';


// 부모 컴포넌트로부터 전달받은 props 정의
const props = defineProps({
  hotel: Object
});

function generateRandomNumber(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

// 상태 변수 선언
const isDetailVisible = ref(false);
const selectedHotel = ref({});
const insertReservationData = ref({});

// 호텔 상세 정보 표시 함수
const showDetail = (hotel) => {
  selectedHotel.value = hotel;
  isDetailVisible.value = true;
};

// 호텔 상세 정보 숨기는 함수
const hideDetail = () => {
  isDetailVisible.value = false;
};

// 결제 처리 함수
const payHandler = async () => {
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const userInfo = vuexStore.loginStore.userInfo;
  const id = userInfo.id;
  const name = userInfo.name;
  const phoneNumber = userInfo.phonenumber;
  const order_id = '0000'+generateRandomNumber(1111,9999);
  const items_name = '호텔 예약';
  // 쉼표(,) 제거 후 숫자로 변환
  const price = parseInt(selectedHotel.value.price.replace(/,/g, ''));
  try {
    const response = await Bootpay.requestPayment({
      "application_id": process.env.VUE_APP_BOOTPAY_API_KEY,
      "price": price,
       
      "order_name": selectedHotel.value.hotel,
      "order_id": order_id,
      "tax_free": 0,
      "user": {
        "id": id,
        "username": name,
        "phone": phoneNumber
      },
      "items": [
        {
          "id": order_id,
          "name": items_name,
          "qty": 1,
          "price": price
        }
      ],
      "extra": {
        "open_type": "iframe",
        "card_quota": "0,2,3",
        "escrow": false
      }
    });

    // 결제 처리 결과 확인
    paycheck(response);
  } catch (error) {
    console.log(error.message); // 오류 처리
  }
};

// 결제 완료 후 동작 처리 함수
const paycheck = (response) => {
  console.log('paycheck : ',response);
  const status_locale = response.data.status_locale;
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const id = vuexStore.loginStore.userInfo.id;
  const lat = selectedHotel.value.lat;
  const lng = selectedHotel.value.lng;
  // 결제 완료 후의 동작 추가
  if (status_locale === '결제완료') {
    // 결제가 성공적으로 완료된 경우
    insertReservationData.value['reservation_lat']=lat;
    insertReservationData.value['reservation_lng']=lng;
    insertReservationData.value['reservation_pricename']=response.data.order_name;
    insertReservationData.value['reservation_price']=response.data.price;
    insertReservationData.value['reservation_receipturl']=response.data.receipt_url;
    insertReservationData.value['id']=id;
    alert('결제가 완료되었습니다.');
    payListInsert();
  } else {
    // 결제가 실패한 경우
    alert('결제에 실패하였습니다.');
  }
};

// 결제 정보를 서버에 전송하는 함수
const payListInsert = async () => {
  try {
    const response = await axios.post(process.env.VUE_APP_API_URL+'/insertReservation',insertReservationData.value)
    console.log('성공');
    console.log(response.data);
  } catch (error) {
    console.log('실패');
  }
};
</script>

<style scoped>

.hotel-card {
  border: 1px solid #ddd;
  width: 375px;
  height: 190px;
  cursor: pointer;
  margin-bottom: 8px;
  border-radius: 10px;
  overflow: hidden; /* 이미지가 카드를 벗어나지 않도록 */
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.hotel-card:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 10px rgba(0,0,0,0.2);
}

.card-body {
  display: flex;
  justify-content: space-between;
  height: 100%;
  width: 100%;
}

.card-info {
  width: 60%; /* 내용과 이미지 사이의 비율을 조절 */
  padding: 10px; /* 내용 주변에 여백 추가 */
  text-align: left;
}
.card-hotel-name{
  font-weight: bold;
}

*::selection {
  background-color: #4364F7;
  color: #fff;
}

body {
  background-color: #eeeeee;
  display: flex;
  width: 100%;
  flex-direction: clumn;
  justify-content: center;
  align-items: center;
}


li {
  list-style: none;
}

.card {
  width : 300px;
  height : fit-content;
  border-radius: 10px;
  padding: 20px;
  background-color: #fff;

}

.card-content {
  margin-top: 20px;
}
.card-content p{
  font-size: 18px;
}
.card-content ~ .card-footer{
  margin-top: 20px;
}

/* custom button */

.btn {
  border-radius: 15px;
  padding: 12px 36px ;
  font-weight: bold;
  width: 100%;
  font-size: 18px;
  cursor: pointer;
}
.btn-close {
    background: none;
  }
.btn-close::before, .btn-close::after {
  content: none;
}
.btn-close i {
  display: inline;
}

.btn-line {
  border: 2px solid #224AFF;
  color: #224AFF;
}

.btn-fill {
  color: #fff;
  background: rgb(105,132,255);
  background: linear-gradient(180deg, rgba(105,132,255,1) 0%, rgba(34,74,255,1) 100%);
}
/* <!-- 2.21일 수정 --> */
.product-detail {
  width: 99%;
}
.hotel-name{
  margin-top: 10px;
}
.hotel-name h1 {
  font-size: 20px;
  font-weight: bold;
}
.card-img{
  width: 40%; /* 내용과 이미지 사이의 비율을 조절 */
}

.card-img img{
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 카드를 꽉 채우면서 비율을 유지 */
  border-radius: 10px;
  padding: 5px;
}
.product-img {
  width: 100%;
  height: 210px;
  overflow: hidden; /* 이미지를 부모 요소의 영역 안에 유지하기 위해 */
}

.product-img img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지를 가로세로 비율을 유지하면서 부모 요소에 꽉 차게 설정 */
  border-radius: 10px;
}

.product-detail h1 {
  margin-bottom: 9px;
}

.score-area {
  text-align: left;
}

#choose-size {
  margin-bottom: 12px;
}

#choose-size li{
  display: inline-block;
  margin-right: 15px;
}

.card-footer > button:first-child{
  margin-bottom: 10px;
}
.card-content {
  text-align: left; /* 왼쪽 정렬 설정 */
}
.card-content-price{
  margin-top: 10px;
  font-weight: bold;
  text-align: right;
}
.card-content-price-price p{
  font-weight: bold;
  text-align: right;
  font-size: 30px;
}

</style>