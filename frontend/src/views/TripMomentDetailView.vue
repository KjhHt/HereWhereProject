<template>
    <div class="Travel-Detail-Container">
        <div class="PhotoDetailStyle-xpwzvz-1 hwNqPL">
            <!--좌측-->
            <div class="gl-cpt_imagallery gl-cpt_imagallery_noback">
                <div class="photo-list-warp">
                    <div class="photo-list-imgs-list">
                        <div class="image-gallery " aria-live="polite">
                            <div class="image-gallery-content">
                                <div class="image-gallery-slide-wrapper bottom">
                                    <BoxLeftButton :currentIndex="currentIndex" :totalIndex="images.length" @moveImage="handleMoveImage"/>
                                    <div class="image-gallery-swipe">
                                        <BoxLeftImageslide @updateOcrImage="updateOcrImage" :isOcrImage="isOcrImage" :images="images" :currentIndex="currentIndex"/>
                                    </div>
                                    <BoxLeftGalleryIndex :currentIndex="currentIndex" :totalIndex="images.length"/>
                                </div>
                                <div class="image-gallery-thumbnails-wrapper bottom image-gallery-overview">
                                    <BoxLeftThumnail :images="images" :currentIndex="currentIndex" :displayIndex="displayIndex" @selectImage="handleSelectImage"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    
            <!--우측-->
            <div class="BoxRight-xpwzvz-2 photo-post-container">
                <div class="scroll_contentouter">
                    <div class="scroll_content">
                        <BoxRightProfile :detailData="detailData"/>
                        <div class="info-wrapper">
                            <BoxRightTitle :detailData="detailData"/>
                            <BoxRightContent :detailData="detailData"/>
                        </div>
                        <BoxRightTime :detailData="detailData"/>
                        <div style="background-color:#DADFE6;height:1px"></div>
                        <BoxRightVotelist @openModal="handleOpenModal" @updateLikes="handleUpdateLikes" :detailData="detailData" :like_count="like_count"/>
                        <FollowModal :stompClient="stompClient" v-if="selectedLikers" :likers="selectedLikers" @closeModal="selectedLikers = null"/>
                        <BoxRightComment :detailData="detailData" :lastUpdated="lastUpdated"/>
                    </div>
                    <BoxRightInput :like_count="like_count" :detailData="detailData" @updateComment="handleUpdateComment" @updateLikes1="handleUpdateLikes"/>
                </div>
                <BoxRightPlace @moveLocationViewHandle="moveLocationViewHandle" :detailData="detailData"/>
            </div>
        </div>
    </div>
    
    </template>
<script setup>
import { ref, reactive ,defineProps , onMounted,defineEmits } from 'vue';
import BoxLeftButton from '@/components/BoxLeft/BoxLeftButton.vue'
import BoxLeftImageslide from '@/components/BoxLeft/BoxLeftImageslide.vue';
import BoxLeftGalleryIndex from '@/components/BoxLeft/BoxLeftGalleryIndex.vue'
import BoxLeftThumnail from '@/components/BoxLeft/BoxLeftThumnail.vue';


import BoxRightProfile from '@/components/BoxRight/BoxRightProfile.vue';
import BoxRightTitle from '@/components/BoxRight/BoxRightTitle.vue';
import BoxRightContent from '@/components/BoxRight/BoxRightContent.vue';
import BoxRightTime from '@/components/BoxRight/BoxRightTime.vue';
import BoxRightVotelist from '@/components/BoxRight/BoxRightVotelist.vue';
import BoxRightComment from '@/components/BoxRight/BoxRightComment.vue';
import BoxRightInput from '@/components/BoxRight/BoxRightInput.vue';
import BoxRightPlace from '@/components/BoxRight/BoxRightPlace.vue';
import FollowModal from '@/components/BoxRight/FollowModal.vue';
import axios from 'axios';
    
const props = defineProps({
    tripmonetData: Object,
    stompClient: Object,
});

const detailData = ref({});
const isOcrImage = ref([]);
const like_count = ref('');
const selectedLikers = ref(null);
const emit = defineEmits(['moveLocationViewHandler']);

async function sendImages(images) {
    try {
        const response = await axios.post(process.env.VUE_APP_PYTHON_API_URL+'/is_ocr_image', {
        base64Encoded: images
    });
        isOcrImage.value = response.data;
    } 
    catch (error) {
        console.log(error);
    }
}

onMounted(async () => {
    detailData.value = props.tripmonetData;
    like_count.value = detailData.value.like_count;
    detailData.value?.base64BoardImages.forEach((src) => {
        images.push({ src: src, alt: '이미지', ocrSrc : '' });
    });
    //이미지 텍스트 감지 여부
    await sendImages(detailData.value.base64BoardImages);
});


//이미지 넣는 변수
const images = reactive([]);
const currentIndex = ref(1);
const displayIndex = ref(0);
const lastUpdated = ref(null);

const handleOpenModal = (likers) => {
    selectedLikers.value = likers;
};

const handleSelectImage = (index) => {
    currentIndex.value = index + 1; // 인덱스는 0부터 시작하므로 1을 더합니다.
};

const handleMoveImage = (direction) => {
    currentIndex.value += direction;
    displayIndex.value = Math.floor(currentIndex.value / 5) * 5; 
};

const handleUpdateComment = (timestamp) => {
    lastUpdated.value = timestamp;
}

const handleUpdateLikes = () => {
    const vuexStore = JSON.parse(localStorage.getItem('vuex'));
    const userInfo = vuexStore.loginStore.userInfo;
    const profileimage = localStorage.getItem('profileImage');
    const no = detailData.value.board_no;
    axios.get(`${process.env.VUE_APP_API_URL}/user/like?board_no=`+no)
    .then(res=>{
        console.log(res.data);
        if(res.data){ // true일 경우 새로 누른경우
            let likeList = detailData.value.likeList;
            like_count.value++;
            detailData.value.like = true;
            likeList.unshift({
                like_id : userInfo.id,
                name : userInfo.name,
                profileimage : profileimage,
            })
        }
        else{ // false일 경우 취소한경우
            let likeList = detailData.value.likeList;
            like_count.value--;
            detailData.value.like = false;
            detailData.value.likeList = likeList.filter(like => like.like_id !== userInfo.id);
        }
    })
    .catch(err=>console.log(err))
}

const updateOcrImage = (index,data,sliceData) => {
    images[index].ocrSrc = sliceData+','+data;
}

const moveLocationViewHandle = (place_id) => {
    emit('moveLocationViewHandler',place_id);
}
</script>
<style scoped>

div{
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline;
}
span{
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline;
}

/*좌측 css */
.Travel-Detail-Container {
    max-width: 1160px;
    margin: 0px auto;
    padding: 0px;
}
.hwNqPL {
    width: 100%;
    position: relative;
    margin-top: 8px;
    display: flex;
    flex-direction: row;
    margin-bottom: 64px;
}
.hwNqPL .gl-cpt_imagallery {
    position: relative;
    top: 0px;
    left: 0px;
    margin-left: 0px;
    margin-top: 0px;
    height: auto;
    margin-bottom: 0px;
    padding-bottom: 0px;
    width: 650px;
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery {
    position: absolute;
    top: 50%;
    left: 50%;
    margin-left: -590px;
    margin-top: -373px;
    width: 1180px;
    height: 746px;
    background: #fff;
    border-radius: 2px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding-bottom: 30px;
}
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp {
    position: relative;
}
}
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list {
    transform: scale(1, 1) translateX(0px);
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list {
    -webkit-transform: scaleX(0.8) translateX(-270px);
    transform: scaleX(0.8) translateX(-270px);
}
}
@media screen and (min-width: 426px){
.image-gallery {
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    -o-user-select: none;
    user-select: none;
    -webkit-tap-highlight-color: transparent;
}
}
.hwNqPL .gl-cpt_imagallery .image-gallery-content {
    padding: 0px;
}
@media screen and (min-width: 426px){
.image-gallery-content {
    position: relative;
    line-height: 0;
    top: 0;
    padding: 0 58px;
}
}
@media screen and (min-width: 426px){
.image-gallery-slide-wrapper {
    position: relative;
}
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-thumbnails-wrapper.image-gallery-overview {
    width: 650px !important;
    background: rgba(0, 0, 0, 0.8);
}
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-thumbnails-wrapper {
    margin-left: auto !important;
    margin-right: auto !important;
    width: 762px !important;
    overflow: hidden !important;
}
}
@media screen and (min-width: 426px){
.image-gallery-thumbnails-wrapper {
    position: relative;
    margin: 0 1px;
}
}

/*우측 css */
.photo-post-container {
    position: relative;
    flex: 1 1 0%;
    left: 0px;
    height:auto;
    z-index:0
}

.scroll_contentouter{
    position: relative;
    overflow: hidden;
    max-height: 707px;
}

.photo-post-container .scroll_content {
    overflow-y: auto;
    overflow-x: hidden;
    position: relative;
    
}
.scroll_content{
    max-height: 627px; 
    overflow-y: auto;
}
.userContainer {
    line-height: 24px;
    margin-left: 28px;
    margin-top: 27px;
    margin-bottom: 10px;
    
}

.photo-post-container .info-wrapper {
    margin-left: 32px;
}

h1{
    margin: 0;
    padding: 0;
    border:0;
    vertical-align: baseline;
}

.comment-content {
    width: 100%;
    border-top: 1px solid rgb(234, 234, 234);
    box-sizing: border-box;
    padding-left: 32px;
    padding-right: 32px;
}

</style>