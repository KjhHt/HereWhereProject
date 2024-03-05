<template>
    <div class="image-gallery-index">
        <span class="image-gallery-index-current">{{ currentIndex }}</span>
        <span class="image-gallery-index-separator"> / </span>
        <span class="image-gallery-index-total">{{ totalIndex }}</span>
    </div>
</template>

<script setup>
import { defineProps } from 'vue';

defineProps({
  currentIndex: Number,
  totalIndex: Number
});
</script>
<style scope>
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-index {
    text-align: center;
    position: absolute;
    bottom: 20px;
    width: 100%;
    transform: scale(1);
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-index {
    color: #151515;
    line-height: 1;
    padding: 0 0 6px;
    font-size: 16px !important;
    -webkit-transform: scaleX(1.25);
    transform: scaleX(1.25);
}
}
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-index span {
    color: rgb(255, 255, 255);
}
@media screen and (min-width: 426px){
.image-gallery-index-current {
    color: #3264ff;
}
}
</style>