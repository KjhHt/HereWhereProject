<template>
  <nav>
    <svg class="ham hamRotate ham4" viewBox="0 0 100 100" width="50" @click="toggleClass" id="menu">
      <path class="line top" d="m 70,33 h -40 c 0,0 -8.5,-0.149796 -8.5,8.5 0,8.649796 8.5,8.5 8.5,8.5 h 20 v -20" />
      <path class="line middle" d="m 70,50 h -40" />
      <path class="line bottom" d="m 30,67 h 40 c 0,0 8.5,0.149796 8.5,-8.5 0,-8.649796 -8.5,-8.5 -8.5,-8.5 h -20 v 20" />
    </svg>
    <!-- 2.21일 수정 -->
    <ul :style="{ height: menuOpened ? '260px' : '0px', transition: 'height 0.3s', padding: menuOpened ? '0px' : '0px' }">
      <li>
        <a href="#" 
        type="button" 
        data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasScrolling" 
        aria-controls="offcanvasScrolling" 
        class="search-button nav-icon" 
        @click="closeMenu">
          <i class="bi bi-search" style="font-size: 27px;"></i>
          <path fill-rule="evenodd" d="M15.817.113A.5.5 0 0 1 16 .5v14a.5.5 0 0 1-.402.49l-5 1a.502.502 0 0 1-.196 0L5.5 15.01l-4.902.98A.5.5 0 0 1 0 15.5v-14a.5.5 0 0 1 .402-.49l5-1a.5.5 0 0 1 .196 0L10.5.99l4.902-.98a.5.5 0 0 1 .415.103zM10 1.91l-4-.8v12.98l4 .8V1.91zm1 12.98 4-.8V1.11l-4 .8v12.98zm-6-.8V1.11l-4 .8v12.98l4-.8z"/>
        </a>
      </li>
      <li>
        <a href="#" 
        type="button" 
        data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasPlan" 
        aria-controls="offcanvasPlan"
        ref="planRef"
        class="search-button nav-icon"
        @click="closeMenu">
          <i class="bi bi-calendar-plus-fill" style="font-size: 27px;"></i>
          <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
        </a>
      </li>
      <li>
        <a href="#" 
        type="button" 
        data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasHotel" 
        aria-controls="offcanvasHotel" 
        class="search-button nav-icon"
        @click="closeMenu('hotel')">
          <i class="fas fa-bed" style="font-size: 25px;"></i>
          <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
        </a>
      </li>
      <li>
        <a href="#" 
        type="button" 
        data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasAttraction" 
        aria-controls="offcanvasAttraction"
        class="search-button nav-icon"
        @click="closeMenu('attraction')">
          <i class="fas fa-camera-retro" style="font-size: 27px;"></i>
          <path fill-rule="evenodd" d="M15.817.113A.5.5 0 0 1 16 .5v14a.5.5 0 0 1-.402.49l-5 1a.502.502 0 0 1-.196 0L5.5 15.01l-4.902.98A.5.5 0 0 1 0 15.5v-14a.5.5 0 0 1 .402-.49l5-1a.5.5 0 0 1 .196 0L10.5.99l4.902-.98a.5.5 0 0 1 .415.103zM10 1.91l-4-.8v12.98l4 .8V1.91zm1 12.98 4-.8V1.11l-4 .8v12.98zm-6-.8V1.11l-4 .8v12.98l4-.8z"/>
        </a>
      </li>
      <li>
        <a href="#" 
        type="button" 
        data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasRestaurant" 
        aria-controls="offcanvasRestaurant" 
        class="search-button nav-icon"
        @click="closeMenu('restaurant')">
          <i class="fas fa-utensils" style="font-size: 27px;"></i>
          <path fill-rule="evenodd" d="M15.817.113A.5.5 0 0 1 16 .5v14a.5.5 0 0 1-.402.49l-5 1a.502.502 0 0 1-.196 0L5.5 15.01l-4.902.98A.5.5 0 0 1 0 15.5v-14a.5.5 0 0 1 .402-.49l5-1a.5.5 0 0 1 .196 0L10.5.99l4.902-.98a.5.5 0 0 1 .415.103zM10 1.91l-4-.8v12.98l4 .8V1.91zm1 12.98 4-.8V1.11l-4 .8v12.98zm-6-.8V1.11l-4 .8v12.98l4-.8z"/>
        </a>
      </li>
    </ul>
  </nav>
</template>
  
<script setup>
import { ref,defineProps,watchEffect,defineEmits } from 'vue';

const planRef= ref(null);

const menuOpened = ref(false);

const emit= defineEmits(['setMarkerStatus']);

const toggleClass = () => {
  menuOpened.value = !menuOpened.value;
};

const closeMenu = (status) => {
  menuOpened.value = false;
  if(status) emit('setMarkerStatus',status);
};

const props= defineProps({
  showPlan: Boolean
})

watchEffect(()=>{
  if(props.showPlan) {
    planRef.value.click();
  }
})
</script>
  
<style scoped>
nav {
    font-family: "raleway", sans-serif;
    min-width: 50px;
    margin: auto;
    text-align: center;
}
ul {
    overflow: hidden;
    float: right;
    width: 100%;
    border-radius: 10px;
}
li {
  float: right;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-top: 0px;
  margin-bottom: 0px;
}
li a {
  text-decoration: none;
  color: #000000;
}
li a:hover {
  text-decoration: none;
}
h2 {
  text-align: center;
}
span {
  height: 5px;
  background: black;
  margin-bottom: 5px;
  display: block;
  transition: all 500ms ease-in-out;
}
.ham4 .top {
  stroke-dasharray: 40 121;
}
.ham4 .bottom {
  stroke-dasharray: 40 121;
}
.ham4.active .top {
  stroke-dashoffset: -68px;
}
.ham4.active .bottom {
  stroke-dashoffset: -68px;
}
.ham {
  cursor: pointer;
  -webkit-tap-highlight-color: transparent;
  transition: transform 400ms;
  -moz-user-select: none;
  -webkit-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
.hamRotate.active {
  transform: rotate(45deg);
}
.line {
  fill:none;
  transition: stroke-dasharray 400ms, stroke-dashoffset 400ms;
  stroke:#000;
  stroke-width:5.5;
  stroke-linecap:round;
}
/* <!-- 2.21일 수정 --> */
.nav-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 50px;
  height: 50px;
  color: #000;
  background-color: #ffffff;
  border: 1px solid #000; /* 모든 테두리에 선 추가 */
}

.nav-icon:not(:first-child) {
  border-top: none; /* 첫 번째 요소가 아닌 경우 상단 선 제거 */
}

.nav-icon:last-child {
  border-bottom: 1px solid #000; /* 마지막 요소인 경우 하단 선 추가 */
}
</style>