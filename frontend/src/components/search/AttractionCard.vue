<template>
  <div>
    <!-- 관광지 카드 -->
    <div class="attraction-card" @click="showDetail(props.attraction)">
      <div class="card-body">
        <div class="card-info">
          <h6 class="card-attraction-name">{{ props.attraction.attraction }}</h6>
          <p> {{ props.attraction.address }}</p>
          <p> {{ props.attraction.rating }}점
            <span v-for="n in 5" :key="n">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                <path v-if="n <= props.attraction.rating" fill="#FFD700" d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                <g v-else-if="n - 0.5 <= props.attraction.rating" fill="#FFD700">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                  <path d="M8 0v16h8V0" fill="#fff"/>
                </g>
                <path v-else d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z" fill="#fff"/>
              </svg>
            </span>
          </p>
        </div>
        <div class="card-img">
          <img :src="props.attraction.image" @error="imgError" alt="Attraction Image">
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
    <div class="offcanvas-header d-flex justify-content-between align-items-center" style="height: 40px;">
        <h5>관광지 상세정보</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="hideDetail"></button>
    </div>
    <div class="offcanvas-body">
      <div class="card product-detail">
        <div class="product-img">
          <img :src="props.attraction.image" @error="imgError" alt="Restaurant Image">
        </div>
        <div class="attraction-name">
          <h1>{{ selectedAttraction.attraction}}</h1>
        </div>
        <div class="score-area" aria-label="consumer score">
          <p><i class="bi bi-star-fill"></i> 평점 : {{ selectedAttraction.rating }}점
            <span v-for="n in 5" :key="n">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                <path v-if="n <= props.attraction.rating" fill="#FFD700" d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.373.668C7.568.16 8.432.16 8.627.668l2.192 4.44 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746-.592L8 13.187l-4.389 2.256z"/>
                <g v-else-if="n - 0.5 <= props.attraction.rating" fill="#FFD700">
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
            <p><i class="bi bi-geo-alt-fill"></i> {{ selectedAttraction.address }}</p>
          </div>
        </div>
        <div class="card-content">
          <p><i class="bi bi-clock-fill"></i> {{ getTodayOperatingTime }}</p>
        </div>
        <div class="card-footer">
          <button class="btn btn-fill"><i class="bi bi-calendar-plus-fill"></i> Here Where <br/>일정 추가하기</button>
        </div>
      </div>
    </div>
  </div> 
  
</template>
  
<script setup>
import { defineProps, ref, computed } from 'vue';

const props = defineProps({
  attraction: Object
});

const isDetailVisible = ref(false);
const selectedAttraction = ref({});

const showDetail = (attraction) => {
  selectedAttraction.value = attraction;
  isDetailVisible.value = true;
};

const hideDetail = () => {
  isDetailVisible.value = false;
};
//해당 날짜 운영시간 출력
const days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
const getTodayOperatingTime = computed(() => {
  const today = new Date();
  const todayLabel = days[today.getDay()];

  if (selectedAttraction.value && selectedAttraction.value.operating_time) {
    const operatingTime = selectedAttraction.value.operating_time[todayLabel];
    if (operatingTime) {
      return `${todayLabel} ${operatingTime}`;
    } else {
      return `운영 시간 정보가 제공되지 않는 장소입니다`;
    }
  } else {
    return `운영 시간 정보가 없습니다.`;
  }
});
//이미지 대체 사진
function imgError(event){
  event.target.src = require("@/assets/logo.png");
}
</script>

<style scoped>
.attraction-card {
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
.attraction-card:hover {
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
  width: 60%; /* 내용과 이미지 사이의 비율을 조절 */
  padding: 10px; /* 내용 주변에 여백 추가 */
  text-align: left;
}
.card-attraction-name{
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
.attraction-name{
  margin-top: 10px;
}
.attraction-name h1 {
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