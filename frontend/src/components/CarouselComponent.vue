<template>
  <div id="carouselExample" class="carousel slide" data-bs-ride="carousel" ref="carouselRef">
    <div class="carousel-inner">
      <div class="carousel-item" v-for="(component, index) in components" :key="index" :class="{active: index === currentIndex}">
        <component :is="component"/>
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev" @click="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next" @click="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>
</template>

<script setup>
import {ref, onMounted, defineProps, defineEmits } from 'vue';
import { Carousel as BootstrapCarousel } from 'bootstrap';
import TravelPage from '../views/Travel.vue';
import AirTicketPage from '../views/AirTicketTemp.vue';
import ExchangePage from '../views/CurrencyView.vue';

const props = defineProps(['currentIndex']);
const emit = defineEmits(['update:currentIndex']);
let components = [TravelPage, AirTicketPage, ExchangePage];

const carouselRef = ref(null);
let carouselInstance = null;

onMounted(() => {
  carouselInstance = new BootstrapCarousel(carouselRef.value, {
    interval: 86400000, 
    ride: 'carousel',
  });
});

const next = () => {
  carouselInstance.next();
  emit('update:currentIndex', (props.currentIndex + 1) % components.length);
};

const prev = () => {
  carouselInstance.prev();
  emit('update:currentIndex', (props.currentIndex - 1 + components.length) % components.length);
};

</script>

<style scoped>
.carousel-control-prev,
.carousel-control-next {
  position: fixed;
  top: 85%; /* 버튼을 화면 세로 중앙에 위치시킵니다 */
  transform: translateY(-50%); /* 버튼의 중앙이 화면 세로 중앙에 정확히 오도록 조정합니다 */
  background-color: black;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
  filter: none;
}

.carousel-control-prev:hover,
.carousel-control-next:hover {
  background-color: white;
}
.carousel-control-prev:hover .carousel-control-prev-icon,
.carousel-control-next:hover .carousel-control-next-icon {
  filter: invert(1);
}
</style>