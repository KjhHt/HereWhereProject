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
    <div class="offcanvas-header d-flex justify-content-between align-items-center" style="height: 40px;">
        <h5>호텔 상세정보</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="hideDetail"></button>
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
          <button class="btn btn-fill"><i class="bi bi-calendar-plus-fill"></i> 예약하러 가기</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script setup>
import { defineProps, ref } from 'vue';
const props=defineProps({
  hotel:Object
})
console.log(props.hotel)

const isDetailVisible = ref(false);
const selectedHotel = ref({});

const showDetail = (hotel) => {
  selectedHotel.value = hotel;
  isDetailVisible.value = true;
};

const hideDetail = () => {
  isDetailVisible.value = false;
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
.offcanvas {
    /* 다른 스타일들 */

    transition: visibility 0s, opacity 0.3s;
    opacity: 0;
    visibility: hidden;
}

.offcanvas.show {
    opacity: 1;
    visibility: visible;
}

.hide-animation {
    transition: visibility 0s, opacity 0.3s;
    opacity: 0;
    visibility: hidden;
}
/* default */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Montserrat', sans-serif;
  font-size: 16px;
  letter-spacing: 0.014em;
  line-height: 170%;
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

button {
  background: none;
  border: none;
  outline: none
}

/* card style */
.card {
  width : 300px;
  height : fit-content;
  border-radius: 10px;
  padding: 20px;
  background-color: #fff;
  margin : 3px;
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

.btn-line {
  border: 2px solid #224AFF;
  color: #224AFF;
}

.btn-fill {
  color: #fff;
  background: rgb(105,132,255);
  background: linear-gradient(180deg, rgba(105,132,255,1) 0%, rgba(34,74,255,1) 100%);
}

/* remove radio style */
.remove-radio-style>input{
  visibility:hidden;
  width: 0;
  height: 0;
  position: absolute;
  left: -9999px;
}

.remove-radio-style>label{
  cursor: pointer;
}

/* custom radio */

.blue-radio>label{
  display: block;
  font-size: 12px;
  line-height: 15.2px;
  width: 30px;
  height: 30px;
  padding : 7.4px 0 ;
  text-align: center;
  border: 1.3px solid #000;
  border-radius: 3px;
  font-weight: medium;
}

.remove-radio-style>input:checked + label{
  color: #fff;
  border: 0;
  background: rgb(105,132,255);
  background: linear-gradient(180deg, rgba(105,132,255,1) 0%, rgba(34,74,255,1) 100%);
}

/* card detail */
.product-detail {
  width: 90%;
  
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

.score {
  font-style: normal;
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