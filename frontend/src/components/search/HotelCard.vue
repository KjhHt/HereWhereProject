<template>
    <div class="hotel-card">
      <div class="card-body">
        <div>
          <h6>{{ props.hotel.hotel }}</h6>
          <p>평점: {{ props.hotel.review }}</p>
          <p>가격: {{ props.hotel.price }}</p>
          <!--<p>편의시설: {{ props.hotel.convenience }}</p>-->
        </div>
        <img :src="props.hotel.img_urls" alt="호텔 이미지">
      </div>
    </div>
</template>
  
<script setup>
import { defineProps } from 'vue';
const props=defineProps({
  hotel:Object
})
console.log(props.hotel)

</script>

<style scoped>
.hotel-card {
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