<template>
    <div class="schedule-card" @click="emits('moveToLocation',info)">
      <img :src="info.imageUrl" class="place-image" alt="Place Image">
      <div class="place-info">
        <h4>{{ info.placeName }}</h4>
        <p><strong>일자:</strong> {{ info.dateTime }}</p>
      </div>
    </div>
  </template>
  
<script setup>
import { defineProps,defineEmits } from 'vue';
// eslint-disable-next-line no-unused-vars
const props=defineProps({
    info:Object
})
const emits=defineEmits(['moveToLocation'])

</script>
<style scoped>
.schedule-card {
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
  padding: 10px;
  background: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  margin: 10px;
  width: 100%; /* Use 100% to fill the width of the parent container */
  max-width: 300px; /* Use max-width for a maximum size */
}

.place-image {
  width: 100%;
  height: 120px;
  border-radius: 8px;
  margin-bottom: 10px; /* Add some space between the image and the text */
}

.place-info {
  text-align: center;
}

.place-info h4 {
  font-size: 1.1em;
  margin-bottom: 5px;
}

.place-info p {
  font-size: 0.9em;
  margin-bottom: 5px;
}
</style>
  