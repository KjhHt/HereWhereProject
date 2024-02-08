<template>
      <div class="restaurant-card">
        <div class="card-body">
          <div>
            <h6>{{ props.restaurant.restaurant }}</h6>
            <p> {{ props.restaurant.address }}</p>
            <p> {{ props.restaurant.rating }}</p>
            <p> {{ props.restaurant.tel }}</p>
            <!--<p>편의시설: {{ props.hotel.convenience }}</p>-->
          </div>
          <img :src="props.restaurant.image" alt="맛집 이미지">
        </div>
      </div>
  </template>
  
  <script setup>
  import { defineProps } from 'vue';
  const props=defineProps({
    restaurant:Object
  })
console.log(props.restaurant)

  </script>
  
  <style scoped>
  .restaurant-card {
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