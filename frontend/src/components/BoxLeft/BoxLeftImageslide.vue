<template>
    
    <div class="image-gallery-slides">
        <div v-for="(image, index) in images" :key="index"
            class="image-gallery-slide"
            :class="{ center: props.currentIndex === index + 1, right: props.currentIndex < index + 1 }"
            :style="{ transform: `translate3d(${(index - props.currentIndex + 1) * 100}%, 0, 0)`, transition: 'all 450ms ease-out 0s' }">
            <div class="image-gallery-image ">
                <div class="lazyload-wrapper " style="width:650px;height:484.59px">
                    <div class="image-flip" :class="{ rotate: rotate[index] }">
                        <div class="front">
                            <img :alt="image.alt" 
                            :src="image.src" 
                            class="bg-img" 
                            style="object-fit: cover; 
                            width: 100%; height: 100%;"   >
                        </div>
                        <div class="back">
                            <img :alt="image.alt" 
                            :src="image.ocrSrc" 
                            class="bg-img" 
                            style="object-fit: cover; 
                            width: 100%; height: 100%;"   >
                        </div>
                    </div>
                </div>
            <button class="gallery-button" v-if="isOcrImage[index] && !isTranslated[index]" @click="translateImage(index)">번역하기</button>
            <button class="gallery-button" v-if="isTranslated[index] && !rotate[index]" @click="toggleRotate(index)">번역사진</button>
            <button class="gallery-button" v-if="rotate[index]" @click="toggleRotate(index)">원본</button>
            <!-- <button class="gallery-button" @click="openModal">원본</button>     -->
            </div>
        </div>
    </div>
    <LoadingModal />
</template>
<script setup>
import axios from 'axios';
import { ref,defineProps,defineEmits,provide } from 'vue';
import LoadingModal from './LoadingModal.vue';
//import axios from 'axios';

const props = defineProps({
  images: Array,
  currentIndex: Number,
  isOcrImage:Array,
});

let rotate = ref(new Array(props.images.length).fill(false));
let isTranslated = ref(new Array(props.images.length).fill(false));
let isModalVisible = ref(false);

// const openModal = () => {
//   isModalVisible.value = true;
// };

provide('isModalVisible', isModalVisible);

const toggleRotate = (index) => {
    rotate.value[index] = !rotate.value[index];
};

//testImage.value = reader.result.split(",")[0]+','+response.data;
const emit = defineEmits(['updateOcrImage']);
const translateImage = async (index) => {
    isModalVisible.value = true;
    let base64Image = props.images[index].src.split(",")[1];
    const sliceData = props.images[index].src.split(",")[0];
    axios.post(process.env.VUE_APP_PYTHON_API_URL+'/imageOcr', {
        base64Encoded : base64Image
    })
    .then(response => {
        emit('updateOcrImage',index,response.data,sliceData);
        isTranslated.value[index] = true;
        isModalVisible.value = false;
    })
    .catch(error => {
        console.log(error);
    });
};

</script>
<style scope>

.image-gallery-image {
    position: relative;
}

.gallery-button {
    position: absolute;
    top: 10px;
    right: 10px;
    padding: 10px 15px;
    background: #000;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background .3s;
    font-size: 100%;
    line-height: 1.15;
    margin: 0;
}

.image-flip {
    position: relative;
    transform-style: preserve-3d;
    transition: transform 1s;
    height: 484.58px;
}

.front, .back {
    position: absolute;
    width: 100%;
    height: 100%;
    backface-visibility: hidden;
}

.back {
    transform: rotateY(180deg);
}

.rotate {
    transform: rotateY(180deg);
}

.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-slides {
    padding-bottom: 484.58px;
    width: 100%;
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-slides {
    padding-bottom: 523px;
}
}
@media screen and (min-width: 426px){
.image-gallery-slides {
    margin: 0 auto;
    /* height: 506px; */
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding-bottom: 536px;
    height: 0;
    width: 760px;
    line-height: 0;
    overflow: hidden;
    position: relative;
    white-space: nowrap;
}
}
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-slides .image-gallery-slide {
    width: 100%;
    bottom: 0px;
}
@media screen and (min-width: 426px){
.image-gallery-slide {
    background: #fff;
    left: 0;
    position: absolute;
    top: 0;
    bottom: 30px;
    width: 100%;
    /* height: 100%; */
}
}
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-image {
    overflow: hidden;
    position: relative;
    margin-left: 0px;
    width: 100% !important;
    height: 100% !important;
    transform: scaleX(1) !important;
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-image {
    width: 628px !important;
    height: 506px !important;
    -webkit-transform: scaleX(1.2) !important;
    transform: scaleX(1.2) !important;
    margin-left: 70px;
}
}
@media screen and (min-width: 426px){
.image-gallery-slide .image-gallery-image, .image-gallery-slide img {
    width: 100%;
    height: 100%;
}
}
.hwNqPL .bg-grey, .hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-image .bg-img {
    z-index: -1;
    margin-left: 0px;
    width: 100% !important;
    height: 100% !important;
    object-fit: fill !important;
}

.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-image img {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 650px !important;
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-image img {
    -o-object-fit: contain !important;
    object-fit: contain !important;
    width: 628px !important;
}
}

@media screen and (min-width: 426px){
.gl-cpt_imagallery_noback img {
    background-image: none !important;
}
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery img {
    background: url(data:image/png;base64,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) no-repeat center;
    background-size: cover;
}
}
.hwNqPL .bg-grey {
    background: rgba(78, 78, 78, 0.75);
}
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-image img {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 650px !important;
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-image img {
    -o-object-fit: contain !important;
    object-fit: contain !important;
    width: 628px !important;
}
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery_noback img {
    background-image: none !important;
}
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery img {
    background: url(data:image/png;base64,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) no-repeat center;
    background-size: cover;
}
}
@media screen and (min-width: 426px){
.image-gallery-description {
    font-size: 12px;
    color: #999;
    width: 100%;
    max-width: 760px;
    line-height: 16px;
    margin: 12px auto 0;
    position: absolute;
    left: 0;
    bottom: -27px;
}
}


.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-slides .image-gallery-slide {
    width: 100%;
    bottom: 0px;
}
@media screen and (min-width: 426px){
.image-gallery-slide {
    background: #fff;
    left: 0;
    position: absolute;
    top: 0;
    bottom: 30px;
    width: 100%;
    /* height: 100%; */
}
}

</style>