<template>
  <div class="photo">
    <img :src="imageUrl" :alt="alt" loading="lazy">
  </div>
</template>

<script>
export default {
  props: ['imageUrl', 'alt'],
}
</script>

<style scoped>
.photo {
  position: relative;
  width: 100%;
  height: 0;
  padding-bottom: 100%; 
  overflow: hidden;
}

img {
  position: absolute;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>