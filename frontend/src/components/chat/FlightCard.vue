<!-- eslint-disable no-unused-vars -->
<template>
  <div class="flight-card" @click="emits('moveToFlight',info)">
    <div class="airline-logo">
      <img :src="info.airlineLogo" alt="Airline Logo">
    </div>
    <div class="flight-details">
      <p><strong>항공사:</strong> {{ info.airline }}</p>
      <p><strong>출발:</strong> {{ info.origin }} ({{ info.originCode }})</p>
      <p><strong>도착:</strong> {{ info.destination }} ({{ info.destinationCode }})</p>
      <p><strong>출발일:</strong> {{ formatDate(info.departureDate) }}</p>
      <p><strong>출발 시간:</strong> {{ formatTime(info.departureTime) }}</p>
      <p><strong>도착 시간:</strong> {{ formatTime(info.arrivalTime) }}</p>
      <p><strong>가격:</strong> {{ formatPrice(info.price) }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
// eslint-disable-next-line no-unused-vars
const props=defineProps({
  info:Object
})

// eslint-disable-next-line no-unused-vars
const emits=defineEmits(['moveToFlight'])

// eslint-disable-next-line no-unused-vars
function formatDate(date) {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(date).toLocaleDateString('ko-KR', options);
}
// eslint-disable-next-line no-unused-vars
function formatTime(time) {
  const [hours, minutes] = time.split(':');
  return `${hours}시 ${minutes}분`;
}
// eslint-disable-next-line no-unused-vars
function formatPrice(price) {
    return parseInt(price).toLocaleString('ko-KR', { style: 'currency', currency: 'KRW' });
}


</script>

<style scoped>
.flight-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 15px;
  background: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  margin-top: 10px;
}

.airline-logo img {
  max-width: 80px;
  margin-bottom: 15px;
}

.flight-details {
  width: 100%;
}

.flight-details p {
  margin: 5px 0;
  line-height: 1.4;
  text-align: center;
}
</style>
