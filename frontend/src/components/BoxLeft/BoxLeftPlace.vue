<template>
    <div class="cardContainer">
        <div class="cardTitle">
            실시간 항공권 근황
        </div>
        <div class="scrollCardContent">
            <div class="card">
                <div class="imageContainer">
                    <div class="lazyload-wrapper">
                        <img width="120" height="100%" :src="imgUrl" class="image">
                    </div>             
                </div>
                <div class="content">
                    <div class="info">
                        <div class="city-title">
                            <div style="display: flex; align-items: center;">
                            <span>서울</span>
                            <img :src="iconUrl" alt="Round Trip Icon" style="width: 12px; height: 12px; margin: 0 5px;">
                            <span>도쿄</span>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 4px; display: flex;">
                            <div class="text">
                                <p>
                                    왕복
                                    <span class="gap-line"></span>
                                    <span style="margin-left: 5px;">
                                        3월 7일 - 3월 9일
                                    </span>
                                </p> 
                            </div>
                        </div>
                        <div style="display:flex;flex-direction:row;margin-top:8px;">
                            <div style="display:flex;flex:1;flex-direction:column;align-items:flex-start;margin-right:4px;justify-content:flex-start">
                                <div style="display:flex;align-items:flex-end;margin-bottom:4px">
                                    <div style="flex:1">
                                    </div>
                                </div>
                                <div style="display:flex;flex-direction:row;justify-content:flex-end;align-items:flex-end;flex-wrap:wrap">
                                    <div style="color:#3264ff;font-size:12px;font-weight:bold;line-height:16px">
                                        <span style="font-size: 20px;line-height: 22px">116,691</span>
                                        <span>원</span>
                                    </div>
                                </div>   
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
    </div>
</template>

<script setup>
import { ref } from 'vue'




const imgUrl = ref('https://ak-d.tripcdn.com/images/01058120005r0hvyk9F44_C_600_400_R5.jpg')
const iconUrl = ref(require('@/assets/round_trip_icon.png'))
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
    padding-top: 26px;
    padding-left: 32px;
    padding-bottom: 30px;
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
    margin-top:0px;
}
.info {
    flex: 1;
}
.city-title {
    line-height: 22px;
    color: #0f294d;
    text-transform: capitalize;
    margin-bottom: 8px;
    font-weight: 500;
    word-break: break-word;
    font-size: 16px;
}
.fh-bundle-recommend-card-list-icon {
    font-family: "fh-bundle-card" !important;
    font-size: 16px;
    font-style: normal;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
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


.iconUrl{
    width: 20px;
    height: 20px;
}

.text {
    font-size: 14px;
    color: #455873;
    flex: 1;
    box-sizing: border-box;
}
.text p {
    margin: 0;
    line-height: 18px;
    text-align: left;
}
.text .gap-line {
    width: 1px;
    height: 8px;
    display: inline-block;
    background: #9b9898;
    vertical-align: middle;
}
</style>