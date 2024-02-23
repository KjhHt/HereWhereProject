<template>
    <div class="youtube-card">    
      <iframe :src="youtube.id" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen title="YouTube video player"></iframe>
    </div>
</template>

<script setup>
import { defineProps,watchEffect,ref } from 'vue';

let youtube= ref({})

const props = defineProps({
  youtube: Object,
});

watchEffect(()=>{
  console.log('유튜브',props.youtube)
  youtube= props.youtube
})
</script>

<style scoped>
.youtube-card {
  max-width: 100%;
  height: auto;
}
.youtube-card iframe {
  max-width: 100%;
  height: 200px; /* 또는 다른 높이로 조정하세요 */
}
</style>