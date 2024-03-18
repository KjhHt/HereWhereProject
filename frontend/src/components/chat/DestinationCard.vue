<template>
  <div class="destination-card" @click="emits('moveToLocation',info.destination)">
    <img :src="info.image" alt="destination image" class="destination-image">
    <h3 class="destination-title">{{ info.destination }}</h3>
    <p class="destination-tag">태그: {{ info.tag }}</p>
    <p class="destination-category">카테고리: {{ info.category }}</p>
  </div>
</template>

<script setup>
import { defineProps,defineEmits } from 'vue';
const emits=defineEmits(['moveToLocation'])
// eslint-disable-next-line no-unused-vars
const props=defineProps({
  info: Object
});
</script>

<style scoped>

.description-clamp {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3; /* 표시하려는 줄 수 */
  overflow: hidden;
  text-overflow: ellipsis;
}

.destination-card {
  margin: 10px;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  background-color: white;
  max-width: 300px;
}

.destination-image {
  width: 100%;
  border-radius: 8px;
}

.destination-title {
  margin-top: 8px;
  color: #025CDB;
  font-size: 18px;
}

.destination-description,
.destination-tag,
.destination-category {
  font-size: 14px;
  color: #666;
}
</style>
