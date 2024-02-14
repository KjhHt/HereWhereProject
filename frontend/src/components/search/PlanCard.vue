<template>
  <div class="restaurant-card">
    <div class="card-body">
      <div>
        <h6>{{ props.plan.name }}</h6>
        <p> {{ props.plan.formatted_address }}</p>
        <a href="#" class="btn btn-primary" @click="passArrival">경로</a>
        <!--<p>편의시설: {{ props.hotel.convenience }}</p>-->
      </div>
      <img :src="photoUrl" alt="맛집 이미지">
    </div>
  </div>
</template>
  
<script setup>
  import { defineProps, watchEffect, defineEmits, ref } from 'vue';

  const photoUrl= ref();
  const apiKey='AIzaSyA16otojrK96bOUH8jhs4Fp4gC3Glt2xrY';

  const props=defineProps({
    plan: Object
  })

  watchEffect(()=>{
    console.log('플랜',props.plan);
    setPhotoUrl(props.plan.photos)
    console.log('사진url:',photoUrl);
  });

  function setPhotoUrl(photos){
    if (photos && photos.length > 0) {
      const photo = photos[0]; 
      const photoReference = photo.photo_reference;
    
      if (photoReference) {
        photoUrl.value = `https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${photoReference}&key=${apiKey}`;
      } else if (photo.getUrl) {
        photoUrl.value = photo.getUrl({maxWidth: 400});
      } else {
        photoUrl.value = 'place_default.png'; // 기본 이미지 URL을 설정하세요
      }
    } else {
      photoUrl.value = 'place_default.png'; // 기본 이미지 URL을 설정하세요
    }
  }

  const emit= defineEmits(['passArrival'])

  const passArrival=()=>{
    emit('passArrival', props.plan)
  }

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