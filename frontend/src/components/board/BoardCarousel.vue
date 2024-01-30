<template>
  <div id="carouselExample" class="carousel slide">
    <div class="carousel-inner">
      <div class="carousel-item " v-for="(image,idx) in props.imageList" :key="image.uid" :class="[idx==0?'active':'']">
        <a @click="deleteImage(image.uid)"><i class="fa fa-times-circle delete-btn" aria-hidden="true" ></i></a>
        <img :src="image.url" class="d-block w-100 object-fit-cover" alt="...">
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev" v-if="props.imageList.length>1">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next" v-if="props.imageList.length>1">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>
</template>
<script setup>
import { defineProps,defineEmits } from 'vue';
const props=defineProps({
  imageList:{
      type:Array,
  }
})
const emit=defineEmits(['delete-image'])
function deleteImage(uid){
emit('delete-image',uid)
}
</script>
<style scoped>
.delete-btn {
position: absolute;
top: 10px; /* Adjust as needed */
right: 10px; /* Adjust as needed */
z-index: 2; /* Higher than carousel controls */
border: none;
border-radius: 50%;
padding: 5px 10px;
font-size: 25px;
font-weight: bold;
cursor: pointer;
color: gray;
}
.delete-btn:hover {
color: #FF0000; /* Red color on hover */
}
.carousel {
min-height: 400px;
position: relative; /* Ensure relative positioning for correct z-index stacking */
}
.carousel img{
height: 400px;
}
.carousel-control-prev,
.carousel-control-next {
  z-index: 1; /* Lower than delete button */
}

</style>