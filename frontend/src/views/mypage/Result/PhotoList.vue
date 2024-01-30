<template>
  <div class="wrapper">
    <div class="photos">
      <PhotoItem
        v-for="photo in photos"
        :key="photo.id"
        :alt="photo.alt_description"
        :image-url="photo.urls.thumb"
      />
    </div>
  </div>
</template>

<script>
import PhotoItem from './PhotoItem.vue';

export default {
  components: { PhotoItem },
  props: ['photos'],
};
</script>

<style scoped>
.wrapper {
  height: 600px;
  overflow-y: auto;
}

.photos {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.photos img {
  width: 100%;
  height: 100%;
  display: block;  
}
</style>
