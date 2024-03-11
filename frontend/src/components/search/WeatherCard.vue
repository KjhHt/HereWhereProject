<template>
  <div class="weather-side" :style="{ backgroundImage: 'url(' + weather.weather_image + ')' }">
    <div class="weather-gradient">
      <div class="weather-info-container">
        <h2 class="date-dayname">{{ dayOfWeek }}</h2>
        <span class="date-day">{{ formattedDate }}</span><br/>
        <span class="location"><i class="bi bi-geo-alt-fill"></i>{{ weather.country }}</span>
        <div class="weather-container">
          <h1 class="weather-temp"><i class="fa" :class="weather.weather_icon" aria-hidden="true"></i>{{ weather.temp }}</h1>
          <h3 class="weather-desc">{{ weather.condition }}</h3>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

// eslint-disable-next-line no-unused-vars
const props = defineProps({ weather: Object });
const today = new Date();
const year = today.getFullYear();
const month = today.getMonth() + 1;
const date = today.getDate();
const formattedDate = `${year}년 ${month}월 ${date}일`;
const daysOfWeek = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
const dayOfWeek = daysOfWeek[today.getDay()];
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css');

.weather-side {
  background-size: cover;
  border-radius: 25px;
  transition: transform 300ms ease;
  transform: translateZ(0);
  width: 325px; /* 너비 지정 */
  height: 11rem; /* 높이 자동 조절 */
  margin: 0 auto; /* 상하 여백 지정 및 가운데 정렬 */
}

.weather-gradient {
  background: linear-gradient(135deg, rgba(114,237,242, 0.85) 10%, rgba(81,81,229, 0.85) 100%);
  border-radius: 25px;
  padding: 0.8rem;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  height: 100%;
}

.weather-info-container {
  text-align: center;
}

.date-dayname, .date-day, .location {
  color: white;
  font-size: 18px;
  margin: 5px 0;
}

.weather-container {
  margin: 20px 0;
}

.weather-temp {
  color: white;
  font-size: 38px;
  font-weight: 700;
}

.weather-desc {
  font-size: 20px;
}

@media (max-width: 600px) {
  .weather-side {
    width: 90%; /* 모바일 화면에서 너비 조절 */
  }
}
</style>