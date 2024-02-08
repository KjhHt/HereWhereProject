<template>
      <div class="attraction-card">
        <div class="card-body">
          <div>
            <h6>{{ props.attraction.attraction }}</h6>
            <p> {{ props.attraction.address }}</p>
            <p> {{ props.attraction.rating }}</p>
            <!--<p>편의시설: {{ props.hotel.convenience }}</p>-->
          </div>
          <img :src="props.attraction.image" alt="관광지 이미지">
        </div>
      </div>
  </template>
  
  <script setup>
  import { defineProps } from 'vue';
  const props=defineProps({
    attraction:Object
  })
console.log(props.attraction)

  </script>
  
  <style scoped>
  .attraction-card {
    border: 1px solid #ddd;
    margin: 10px;
    padding: 10px;
    width: 397px;
    height: 172.5px;
  }
  
  .card-body {
    display: flex;
    justify-content: space-between;
  }
  
  img {
    max-width: 150px;
    height: 150px;
  }
  </style>