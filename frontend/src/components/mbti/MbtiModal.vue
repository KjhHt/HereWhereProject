<template>
    <div class="container" v-if="modal">
      <div class="loading-modal" >
        <div class="load-triangle" :class="[modal?'animated':'']"></div>
        <div class="load-logo" :class="[modal?'animated':'']">
          <div class="load-logo-top" :class="[modal?'animated':'']">
            <p :class="[modal?'animated':'']">MBTI</p>
          </div>
          <div class="load-logo-bottom" :class="[modal?'animated':'']">
            <p :class="[modal?'animated':'']">분석중</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
<script setup>
import { defineProps } from 'vue';

// eslint-disable-next-line no-unused-vars
const props=defineProps({
    modal:Boolean
})


</script>
  
<style scoped>
.container {
  position: absolute;
  height: 100%;
  width: 100vw;
  top: 0;
  left: 0;
  z-index: 1000;
}
.loading-modal {
  height: 100vh;
  width: 100vw;
  background-color: #000080;
  display: flex;
  justify-content: center;
}
.loading-modal.hidden {
  display: none;
}
@keyframes triangle {
  0% { transform: rotate(0); opacity: 0; }
  35% { transform: rotate(45deg); opacity: 0.2; }
  50% { transform: rotate(135deg); opacity: 0.4; }
  65% { transform: rotate(225deg); opacity: 0.2; }
  100% { transform: rotate(360deg); opacity: 0; }
}
.load-triangle {
  position: fixed;
  top: calc(50% - 180px);
  left: calc(50% - 200px);
	border-left: 200px solid transparent;
	border-right: 200px solid transparent;
	border-bottom: 300px solid #FFFF73;
  z-index: 3;
  opacity: 0;
}
.load-triangle.animated {
  animation: 3s triangle 1s ease-in-out forwards;
  transform-origin: 50% 50%;
}
.load-logo {
  width: 100%;
  font-size: 72px;
  font-family: Verdana;
  line-height: 4rem;
  z-index: 2;
}
.load-logo div {
  height: 50vh;
  background-color: #2d2dff;
  text-align: center;
  position: absolute;
}
@keyframes curtainUp {
  0% { transform: translateY(-100%); }
  100% { transform: translateY(0%); }
}
@keyframes moveFromRight {
  0% { transform: translateX(100%); }
  100% { transform: translateX(0%); }
}
.load-logo-top {
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: flex-end;
  flex-direction: column;
  font-weight: 700;
  color: white;
}
.load-logo-top.animated {
  animation: 1s curtainUp cubic-bezier(1,0,.85,.26);
}
.load-logo-top p {
  margin-bottom: 1.2rem;
  transform: translateX(100%);
}
.load-logo-top p.animated {
  animation: 1s moveFromRight 1.2s forwards;
}
@keyframes curtainDown {
  0% { transform: translateY(100%); }
  100% { transform: translateY(0%); }
}
@keyframes machinaLight {
  0% { text-shadow: 0 1px 1px; }
  35% { text-shadow: 0 1px 0px; }
  50% { text-shadow: 0 1px 1px; }
  75% { text-shadow: 0 -30px 600px; }
  100% { text-shadow: 0 1px 1px; }
}
@keyframes moveFromLeft {
  0% { transform: translateX(-100%); }
  100% { transform: translateX(0%); }
}
.load-logo-bottom {
  bottom: 0;
  left: 0;
  right: 0;
  color: rgba(255, 255, 115, 0.6);
  letter-spacing: 0.1rem;
  font-weight: 700;
}
.load-logo-bottom p {
  text-shadow: 0 1px 1px;
  transform: translateX(-100%);
}
.load-logo-bottom.animated {
  animation: 1s curtainDown cubic-bezier(1,0,.85,.26);
}
.load-logo-bottom p.animated {
  animation: 2s machinaLight 1s cubic-bezier(1,0,.85,.26) forwards, 1s moveFromLeft 1.2s forwards;
}
  </style>
  
