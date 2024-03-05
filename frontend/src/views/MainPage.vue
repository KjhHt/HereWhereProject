<template>
  <div id="main-page">
    <div class="cInnerContent">
      <h1 class="header-section gs_reveal text-center">
        <ImageCompare @searchImgLocation = 'handleSearchImgLocation'/>
      </h1>
      <div class="menu">
        <div  @click="changeComponent(0)" class="menu-item" :class="{active: currentIndex === 0}" style="text-decoration: none;">
          <div class="menu-icon">
            <img src="@/assets/map.png" alt="map-icon">
          </div>
          <div class="menu-text">추천 여행지</div>
        </div>

        <div  @click="changeComponent(1)" class="menu-item" :class="{active: currentIndex === 1}" style="text-decoration: none;">
            <div class="menu-icon">
              <img src="@/assets/airplane.png" alt="airplane-icon">
            </div>
            <div class="menu-text">항공권</div>
          </div>

          <div  @click="changeComponent(2)" class="menu-item" :class="{active: currentIndex === 2}" style="text-decoration: none;">
          <div class="menu-icon">
            <img src="@/assets/exchange.png" alt="exchange-icon">
          </div>
          <div class="menu-text">환율</div>
        </div>
      </div>
    </div>
      <div class="features">
        <CarouselComponent 
          :current-index="currentIndex" 
          @update:currentIndex="currentIndex = $event"
          @imgClick="handleImgClick"
          />
      </div>
    
  </div>
</template>

<script setup>
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { ref,onMounted,defineEmits } from 'vue';
import CarouselComponent from '@/components/CarouselComponent.vue';
import ImageCompare from "@/components/main/ImageCompare.vue";

const emit = defineEmits(['imgClick','searchImgLocation']);

const handleSearchImgLocation = (location) => {
  emit('searchImgLocation', location);
};

const handleImgClick = (value) => {
  console.log('밸류',value)
  emit('imgClick', value);
}

let currentIndex = ref(0);
const changeComponent = (index) => {
  currentIndex.value = index;
};
gsap.registerPlugin(ScrollTrigger);
onMounted(initGSAP);
function initGSAP() {
  function animateFrom(elem, x, y) {
    elem.style.transform = `translate(${x}px, ${y}px)`;
    elem.style.opacity = "0";
    gsap.fromTo(elem, { x: x, y: y, autoAlpha: 0 }, {
      duration: 1.25,
      x: 0,
      y: 0,
      autoAlpha: 1,
      ease: "expo",
      overwrite: "auto"
    });
  }

  function hide(elem) {
    gsap.set(elem, { autoAlpha: 0 });
  }

  gsap.utils.toArray(".gs_reveal").forEach((elem) => {
    hide(elem); // assure that the element is hidden when scrolled into view
    ScrollTrigger.create({
      trigger: elem,
      markers: false,
      onEnter: () => { animateFrom(elem, 0, 100) },
      onEnterBack: () => { animateFrom(elem, 0, -100) },
      onLeave: () => { hide(elem) } // assure that the element is hidden when scrolled into view
    });
  });
}

</script>

<style scoped>
.preview-image {
  max-height: 300px;
  margin-bottom: 20px;
  border-radius: 5px; 
}

body {
  font-weight: 300;
}


.cInnerContent {
  max-width: 1240px;
  margin-left: auto;
  margin-right: auto;
}

img {
  max-width: 100%;
}

.header-section {
  margin: 0px auto;
  padding: 50px; /* 추가로 내용과의 간격을 주기 위한 패딩 설정 (원하는 값으로 조절) */
  background-image: url('@/assets/female_travel.WEBP'); /* 이미지 경로 설정 (상대 경로 또는 모듈 경로 사용) */
  background-size: cover; /* 배경 이미지 크기를 커버로 설정 */
  background-position: center; /* 배경 이미지 위치를 가운데로 설정 */
  position: relative; /* 배경 이미지를 포함한 상대적인 위치 설정 */
  color: #ffffff;
  margin-bottom: 40px;
  max-height: 567px; /* Maximum height for the header section */
  overflow: hidden; /* Hide overflowing content */
}

.gs_reveal {
  opacity: 0;
  visibility: hidden;
  will-change: transform, opacity;
}

.features{
  padding: 80px
}


.menu {
  display: flex;
  justify-content: space-between;
  

}

.menu-item {
  width: 33.33%;
  text-align: center;
  padding: 15px 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  
}
.menu-item img {
  width: 20px;
  height: 20px;
}

.menu-item:last-child {
  border-right: none;
}
.menu-text {
  margin-top: 5px;
  font-size: 14px; /* 텍스트 사이즈 조절 */
  color: #333;
}

.menu-item.active {
  border-bottom: 2px solid black;
}


</style>