<template>
    <div class="wrapper">
      <div class="menu-btn" @click="toggleActive">
        <div class="btn" :class="{ active: isActive }">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>
  
      <div class="icons-wrapper">
        <div class="icons">
          <div v-for="(icon, index) in icons" :key="index" class="icon" :class="{ show: isActive }">
            <i :class="`fa ${icon.class}`" :title="icon.title"></i>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
import { ref } from 'vue';

const isActive = ref(false);
const icons = [
  { class: 'fa-user-circle', title: '나의 정보' },
  { class: 'fa-user-plus', title: '팔로우' },
  { class: 'fa-search', title: '검색' }
];

const toggleActive = () => {
  isActive.value = !isActive.value;
};
</script>
  
  <style scoped>
.wrapper {
  width: 10%;
  height: 100vh;
  position: relative;
}
.menu-btn {
  background: #ffffff; 
  border: 1px solid #000000; 
  border-radius: 50%;
  width: 80px;
  height: 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: background .3s;
  z-index: 2;
  box-shadow: 0px 1px 5px rgba(0, 0, 0, .3);
}
.menu-btn:hover,
.active {
  background: #70aada;
}
.menu-btn, .icons-wrapper {
  position: absolute;
  top: 10%;
  left: 50%;
  transform: translate(-50%, -50%);
  transition-delay: .1s;
  
}
.menu-btn span {
  display: block;
  width: 30px;
  height: 3px;
  background: #333;
  margin-bottom: 5px;
  transition: background .3s, transform .3s;
}
.menu-btn span:last-child {
  margin-bottom: 0;
}
.active span:nth-child(1) {
  transform: rotate(45deg) translate(5px, 5px);
}
.active span:nth-child(2) {
  opacity: 0;
}
.active span:nth-child(3) {
  transform: rotate(-45deg) translate(6px, -5px);
}

.icons {
  position: relative;
}
.icon {
  position: absolute;
  top: calc(50% - 28px);
  left: calc(50% - 28px);
  background: #ffffff; /* 배경색을 흰색으로 변경 */
  border: 1px solid #000000; /* 원형 테두리에 검은선 추가 */
  width: 56px;
  height: 56px;
  border-radius: 50%;
  display: flex;
  cursor: pointer;
  transform: translate(0px, 0px);
  z-index: 1;
}
.icon:hover {
  background: #70aada;
}
.icon:hover .fa {
  color: #272C2C;
}
.fa {
  margin: auto;
  color: #000000; /* 아이콘 색상을 검정색으로 변경 */
  font-size: 30px;
}
.icon:nth-child(1) {
  transition: background .3s, transform .3s .10s;
}
.icon:nth-child(2) {
  transition: background .3s, transform .3s .15s;
}
.icon:nth-child(3) {
  transition: background .3s, transform .3s .20s;
}
.icon:nth-child(4) {
  transition: background .3s, transform .3s .25s;
}
.icon:nth-child(5) {
  transition: background .3s, transform .3s .30s;
}


.show:nth-child(0) {
  transform: translate(0px, 0px);
}
.show:nth-child(1) {
  transform: translate(0px, 100px);
}
.show:nth-child(2) {
  transform: translate(0px, 200px);
}
.show:nth-child(3) {
  transform: translate(0px, 300px);
}
.show:nth-child(4) {
  transform: translate(0px, 400px);
}
.show:nth-child(5) {
  transform: translate(0px, 500px);
}

  </style>