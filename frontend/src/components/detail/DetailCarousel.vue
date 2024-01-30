<template>
    <div :id="'carouselExample' + props.boardDetail[0]" class="carousel slide"> 
      <div class="carousel-inner d-flex">
        <div class="carousel-item " v-for="(image,idx) in props.imageSrc[props.boardDetail[0]]" :key="image" :class="[idx==0?'active':'']">
          <img :src="image" class="d-block w-100 object-fit-cover" alt="...">
        </div>
      </div>
      <button class="carousel-control-prev" type="button" :data-bs-target="'#carouselExample' + props.boardDetail[0]" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" :data-bs-target="'#carouselExample' + props.boardDetail[0]" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>

</template>
<script setup>
import { defineProps } from 'vue';    
const props=defineProps({
    imageList:{
        type:Array,
    },
    boardDetail: {
        type: Array,
    },
    imageSrc : {
        type: Array,
    },
})

</script>
<style scoped>
.carousel {
  min-height: 400px;
}

.carousel-inner img {
  height: 400px;
}
</style>