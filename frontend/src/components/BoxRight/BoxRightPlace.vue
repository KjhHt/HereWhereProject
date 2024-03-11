<template>
    <div class="cardContainer">
        <div class="cardTitle">
            해당 게시물에서 언급됨
        </div>
        <div class="scrollCardContent">
            <div class="card" @click="moveLocationView(placeData.location_placeid)">
                <div class="imageContainer">
                    <div class="lazyload-wrapper">
                        <img width="120" height="100%" :src="placeData.location_imageurl" class="image">
                    </div>             
                </div>
                <div class="content">
                    <div class="info">
                        <h2 class="placeName">{{ placeData.location_name }}</h2>
                        <div class="ratingBlock">
                            <span v-if="placeData.location_rating != 0" class="rating">
                                {{ placeData.location_rating }}
                                <span class="totalRating">
                                    /5
                                </span>
                            </span>                               
                        </div> 
                        <div class="addressBlock">
                            <span class="placeAddress">{{ placeData.location_address }}</span>
                        </div>    
                        <div class="moreInfoButton">
                            <span class="moreInfoText">
                                더 보기
                            </span>
                        </div>    
                    </div>   
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref,defineProps,watchEffect,defineEmits } from 'vue'

const props = defineProps({
    detailData: Object,
});

const emit = defineEmits(['moveLocationViewHandle']);

watchEffect(() => {
    if (props.detailData) {
        if (props.detailData['locationList'] !== undefined && props.detailData['locationList'] !== null) {
            placeData.value = props.detailData['locationList'];
        }
    }
});

const placeData = ref({});

const moveLocationView = (place_id) => {
    emit('moveLocationViewHandle',place_id);
}
</script>

<style scoped>
.cardContainer {
    position: relative;
    left: 0px;
    bottom: 0px;
    border-top: 13px solid rgb(240, 242, 245);
    width: 100%;
}
.cardContainer .cardTitle {
    padding-top: 20px;
    padding-left: 32px;
    padding-bottom: 16px;
    color: rgb(15, 41, 77);
    font-size: 20px;
    font-weight: 500;
    text-align: left;
}
.cardContainer .scrollCardContent {
    height: auto;
    max-height: 365px;
    overflow: visible;
}
.card {
    margin-left: 32px;
    display: flex;
    flex-direction: row;
    background-color: #ffffff;
    padding: 16px;
    border: 1px solid #f0f2f5;
    border-radius: 8px;
    box-shadow: 0px 8px 20px 0px rgba(15, 41, 77, 0.08);
    position: relative;
    cursor: pointer;
    margin-bottom: 20px;
}
.imageContainer {
    width: 120px;
    height: 120px;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
}
.image {
    object-fit: cover;
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center center;
    height: 100%; 
    width: 120px; 
    border-radius: 4px; 
    overflow: hidden; 
    /* position: absolute; */
}
.content {
    flex: 1;
    width: 0;
    max-width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 8px;
    margin-top: 0px;
}
.info {
    flex: 1;
}
.placeName {
    color: #0f294d;
    font-size: 18px;
    line-height: 24px;
    font-weight: 500;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-word;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    text-align: left;
}
.ratingBlock {
    color:white;
    margin-top: 4px;
    text-align: left;
}
.rating {
    background: #1136a6;
    padding: 0 4px;
    border-top-left-radius: 60px;
    border-bottom-left-radius: 60px;
    border-bottom-right-radius: 40px;
    font-size: 14px;
    font-weight: 500;
    line-height: 18px;
}
.rating > .totalRating {
    color: rgba(255,255,255,0.50);
}
.addressBlock {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-top: 4px;
}
.placeAddress {
    color: #455873;
    font-size: 14px;
    font-weight: normal;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.moreInfoButton {
    background: #3264ff;
    border-radius: 4px;
    padding: 8px 12px;
    cursor: pointer;
     position: absolute;
    right: 16px;
    bottom: 16px;
}
.moreInfoText {
    color: #ffffff;
    font-size: 16px;
    font-weight: 500;
    line-height: 22px;
}
</style>