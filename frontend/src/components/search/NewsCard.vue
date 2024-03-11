<template>
    <div class="container mt-4">
      <h5 class="mb-4" style="font-weight:bold; text-align: left;">News</h5>
      <div class="news-carousel">
        <transition-group name="list" tag="div">
          <div class="news-item" v-if="activeNews" :key="activeNews.id">
            <a :href="activeNews.link" class="list-group-item-action" target="_blank">
              <div class="d-flex w-100 justify-content-between align-items-center">
                <h5 class="mb-1 news-title font-weight-bold">{{ activeNews.title }}</h5>
              </div>
            </a>
          </div>
        </transition-group>
      </div>
    </div>
  </template>
  
  <script setup>
  import { defineProps, ref, onMounted, onUnmounted, computed } from 'vue';
  
  const props = defineProps({
    news: Array
  });
  
  const activeIndex = ref(0);
  
  const activeNews = computed(() => props.news[activeIndex.value]);
  
  const nextNews = () => {
    if (activeIndex.value < props.news.length - 1) {
      activeIndex.value++;
    } else {
      activeIndex.value = 0;
    }
  };
  
  let intervalId;
  
  onMounted(() => {
    intervalId = setInterval(nextNews, 2500); // Adjust the time as needed
  });
  
  onUnmounted(() => {
    clearInterval(intervalId);
  });
  
  </script>
  
  <style scoped>
  .news-carousel {
    max-height: 100px; 
    overflow: hidden;
    position: relative;
  }
  
  .list-enter-active, .list-leave-active {
    transition: opacity 0.5s;
  }
  .list-enter, .list-leave-to /* .list-leave-active for <2.1.8 */ {
    opacity: 0;
    position: absolute;
    top: 0;
    width: 100%;
  }

  .news-title {
    color: #9893EA;
    font-weight: bold;
  }
  </style>
  