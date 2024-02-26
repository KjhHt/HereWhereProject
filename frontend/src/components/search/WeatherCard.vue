<template>
  <div class="container-weather">
    <div class="weather-side">
      <div class="weather-gradient">
        <div class="date-container">
          <h2 class="date-dayname">{{ dayOfWeek }}</h2>
          <span class="date-day">{{ formattedDate }}</span>
          <i class="bi bi-geo-alt-fill"></i>
          <span class="location">{{ props.weather.country }}</span>
        </div>
        <div class="weather-container">
          <i class="weather-icon" data-feather="sun"></i>
          <h1 class="weather-temp">{{ props.weather.temp }}</h1>
          <h3 class="weather-desc">{{ props.weather.weather }}</h3>
        </div>
      </div>
    </div>
    <div class="info-side">
      <div class="today-info-container">
        <div class="today-info">
          <div class="temp_min">          
            <span class="value">최저 기온</span>
            <div class="clear"></div>
          </div>
          <div class="temp_max">        
            <span class="value">최고 기온</span>
            <div class="clear"></div>
          </div>
          <div class="humidity">
            <span class="value">습도</span>
            <div class="clear"></div>
          </div>
          <div class="wind">
            <span class="value">풍속</span>
            <div class="clear"></div>
          </div>
        </div>
        <div class="today-info-value">
          <div class="temp_min">          
            <span class="value">{{ props.weather.temp_min }}</span>
            <div class="clear"></div>
          </div>
          <div class="temp_max">        
            <span class="value">{{ props.weather.temp_max }}</span>
            <div class="clear"></div>
          </div>
          <div class="humidity">
            <span class="value">{{ props.weather.humidity }}</span>
            <div class="clear"></div>
          </div>
          <div class="wind">
            <span class="value">{{ props.weather.wind }}</span>
            <div class="clear"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

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
/* 여기에 스타일 코드 추가 */
.container-weather {
  border-radius: 25px;
  color: #ffffff;
  height: 230px;
  background-color: #222831;
  margin-top: 30px;
}
.weather-side {
  position: relative;
  width: 47.8%;
  height: 100%;
  border-radius: 25px;
  background-image: url("https://images.unsplash.com/photo-1559963110-71b394e7494d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80");
  transition: transform 300ms ease;
  transform: translateZ(0) scale(1.02) perspective(1000px);
  float: left;
}
.weather-side:hover {
  transform: scale(1.1) perspective(1500px) rotateY(10deg);
}
.weather-gradient {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: linear-gradient(135deg, #72EDF2 10%, #5151E5 100%);
  border-radius: 25px;
  opacity: 0.8;
}
.date-container {
  position: absolute;
  top: 25px;
  left: 50%; /* 요소의 왼쪽 모서리를 기준으로 */
  transform: translateX(-50%); /* 요소의 가로폭의 절반만큼 왼쪽으로 이동 */
  width: 100%;
}
.date-dayname {
  margin: 0;
}
.date-day {
  display: block;
}
.location {
  display: inline-block;
  margin-top: 10px;
}
.weather-container {
  position: absolute;
  bottom: 40px;
  left: 50%; /* 요소의 왼쪽 모서리를 기준으로 */
  transform: translateX(-50%); /* 요소의 가로폭의 절반만큼 왼쪽으로 이동 */
  width: 100%;
}
.weather-icon {
  height: 60px;
  width: auto;
}
.weather-temp {
  margin: 0;
  font-weight: 700;
  font-size: 38px;
}
.weather-desc {
  margin: 0;
  font-size: 20px;
}
.info-side {
  position: relative;
  float: left;
  height: 100%;
  width: 50%;
}
.today-info-container {
  display: flex;
  height: 100%;
}
.today-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 문자를 왼쪽으로 몰기 위한 수정 */
  justify-content: center;
  font-weight: bold;
  /* 기존 CSS 코드 */
  height: 100%;
  font-size: 18px;
  margin-left: 10px;
  width: 42%;
  flex: 1.2;
}
.today-info-value{
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 문자를 왼쪽으로 몰기 위한 수정 */
  justify-content: center;
  font-weight: bold;
  /* 기존 CSS 코드 */
  height: 100%;
  font-size: 18px;
  width: 50%;
  flex: 1.2;
  margin-left: 10px;
}
.today-info>div:not(:last-child) {
  margin-bottom: 10px;
}
.today-info-value>div:not(:last-child) {
  margin-bottom: 10px;
}
.today-info>div .value {
  float: right;
  margin-bottom: 10px;
}
.today-info-value>div .value {
  float: right;
  margin-bottom: 10px;
}
</style>