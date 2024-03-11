<template>
  <div>
    <!-- 레스토랑 카드 -->
    <div class="restaurant-card" @click="showDetail(props.restaurant)">
      <div class="card-body">
        <div class="card-info">
          <h6 class="card-restaurant-name">{{ props.restaurant.restaurant }}</h6>
          <p> {{ props.restaurant.address }}</p>
          <p> {{ props.restaurant.rating }}점
            <span v-for="n in 5" :key="n">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                <path v-if="n <= props.restaurant.rating" fill="#FFD700" d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                <g v-else-if="n - 0.5 <= props.restaurant.rating" fill="#FFD700">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                  <path d="M8 0v16h8V0" fill="#fff"/>
                </g>
                <path v-else d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z" fill="#fff"/>
              </svg>
            </span>
          </p>
        </div>
        <div class="card-img">
          <img :src="props.restaurant.image" @error="imgError" alt="Restaurant Image">
        </div>
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
      <h5 style="margin-top: 11px; margin-left: -13px;">Here Where Restaurant Details</h5>
      <div style="width: same-as-button;"></div>
    </div>
    <div class="offcanvas-body">
      <div class="card product-detail">
        <div class="product-img">
          <img :src="props.restaurant.image" @error="imgError" alt="Restaurant Image">
        </div>
        <div class="restaurant-name">
          <h1>{{ selectedRestaurant.restaurant }}</h1>
        </div>
        <div class="score-area" aria-label="consumer score">
          <p><i class="bi bi-star-fill"></i> 평점 : {{ selectedRestaurant.rating }}점
            <span v-for="n in 5" :key="n">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                <path v-if="n <= props.restaurant.rating" fill="#FFD700" d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                <g v-else-if="n - 0.5 <= props.restaurant.rating" fill="#FFD700">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                  <path d="M8 0v16h8V0" fill="#fff"/>
                </g>
                <path v-else d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z" fill="#fff"/>
              </svg>
            </span>
          </p>      
        </div>
        <div class="card-content">
          <div class="card-content">
            <p><i class="bi bi-geo-alt-fill"></i> {{ selectedRestaurant.address }}</p>
          </div>
        </div>
        <div class="card-content">
          <p><i class="bi bi-telephone-fill"></i>  {{ selectedRestaurant.tel }}</p>
        </div>
        <div class="card-content">
          <p><i class="bi bi-clock-fill"></i> {{ getTodayOperatingTime }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref, computed } from 'vue';

const props = defineProps({
  restaurant: Object
});

const isDetailVisible = ref(false);
const selectedRestaurant = ref({});


const showDetail = (restaurant) => {
  selectedRestaurant.value = restaurant;
  isDetailVisible.value = true;
};

const hideDetail = () => {
  isDetailVisible.value = false;
};

const days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
const getTodayOperatingTime = computed(() => {
  const today = new Date();
  const todayLabel = days[today.getDay()];

  if (selectedRestaurant.value && selectedRestaurant.value.operating_time) {
    const operatingTime = selectedRestaurant.value.operating_time[todayLabel];
    if (operatingTime) {
      return `${todayLabel} ${operatingTime}`;
    } else {
      return `운영 시간 정보가 제공되지 않는 장소입니다`;
    }
  } else {
    return `운영 시간 정보가 없습니다.`;
  }
});

function imgError(event){
  event.target.src = require("@/assets/place_default.png");
}
</script>

<style scoped>
.restaurant-card {
  border: 1px solid #ddd;
  width: 375px;
  height: 190px;
  cursor: pointer;
  margin-bottom: 13px;
  border-radius: 10px;
  overflow: hidden; /* 이미지가 카드를 벗어나지 않도록 */
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.restaurant-card:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 10px rgba(0,0,0,0.2);
}
.card {
  width : 300px;
  height : fit-content;
  border-radius: 10px;
  padding: 20px;
  background-color: #fff;
  margin : 3px;
}
.product-detail {
  width: 100%;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.card-body {
  display: flex;
  justify-content: space-between;
  height: 100%;
  width: 100%;
}
.card-info {
  width: calc(100% - 40px); /* 내용과 이미지 사이의 비율을 조절 */
  padding: 10px; /* 내용 주변에 여백 추가 */
  text-align: left;
  overflow: hidden; /* 텍스트가 넘칠 경우 자르기 */
  white-space: nowrap; /* 텍스트가 한 줄로만 표시되도록 설정 */
  text-overflow: ellipsis; /* 텍스트가 넘칠 경우 마침표로 표시 */
}
.card-restaurant-name{
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
img {
  width: 172.5px;
  height: 172.5px;
}

.offcanvas.show {
    opacity: 1;
    visibility: visible;
}
/* 2.21일 수정 */
.btn-close {
    background: none;
  }
.hide-animation {
    transition: visibility 0s, opacity 0.3s;
    opacity: 0;
    visibility: hidden;
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
.restaurant-name{
  margin-top: 10px;
}
.restaurant-name h1 {
  font-size: 20px;
  font-weight: bold;
}
.score-area {
  text-align: left;
  margin-top: 10px;
}
.card-content {
  margin-top: 10px;
  text-align: left;
}
.card-content p{
  font-size: 18px;
  display: flex;
  align-items: center;
}
.card-content i {
  margin-right: 5px; /* 적절한 간격으로 조정하세요 */
}
.card-content-time {
  text-align: left;
  padding-left: 20px; /* 적절한 크기로 조정하세요 */
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
</style>

