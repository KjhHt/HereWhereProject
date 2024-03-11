<template>
    <div class="PublishLocationStyle__PublishLocationContainer-sc-1a6vsfj-0 mt24 location-view">
        <div class="location-view-for-online">
            <div class="publish-title">
                위치 추가
            </div>
            <div class="location-input " id="location">
                <i class="input-tips-left">
                    하나 이상의 위치를 선택해주세요
                </i>
                <i class="input-tips-right">
                    <svg width="14" height="14" viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                        <defs></defs>
                        <path d="M128 240c-61.856 0-112-50.144-112-112S66.144 16 128 16s112 50.144 112 112-50.144 112-112 112zm0-21.333c50.074 0 90.667-40.593 90.667-90.667S178.074 37.333 128 37.333 37.333 77.926 37.333 128 77.926 218.667 128 218.667zm10.667-117.334v64h16v21.334h-53.334v-21.334h16v-42.666h-16v-21.334h37.334zm-13.334-10.666c-7.364 0-13.333-5.97-13.333-13.334C112 69.97 117.97 64 125.333 64c7.364 0 13.334 5.97 13.334 13.333 0 7.364-5.97 13.334-13.334 13.334z" 
                        fill="#ee3b28" 
                        class="transform-group">
                        </path>
                    </svg>  
                </i>
                <GoogleAddressAutocomplete 
                    apiKey="AIzaSyB_xHRmY9pPWr9b6Big-8EmQLeGofecec0"
                    v-model="value"
                    @callback="callbackFunction" 
                    class="css-class-here"
                    placeholder="위치를 추가해주세요."
                />
                <!--<input type="text" placeholder="위치를 추가해주세요." value="">-->
            </div>
        </div>
    </div>
</template>
<script setup>
import { ref,defineEmits } from "vue"
import GoogleAddressAutocomplete from 'vue3-google-address-autocomplete'

const locationData = ref({});
const value = ref('');

const emit = defineEmits('[updateLocation]');
const callbackFunction = (e) => {
    console.log(e);
    console.log('위도 : ',e.geometry.location.lat());
    console.log('경도 : ',e.geometry.location.lng());
    const photoUrl = e.photos[0].getUrl({maxWidth: 400}); //이미지 url임~
    const name = e.name; //이름 (검색한단어와 name이 다르면 데이타지우기)
    const address = e.formatted_address; //주소
    const rating = e.rating; //별점
    const placeid = e.place_id; //placeid
    const lat = e.geometry.location.lat();
    const lng = e.geometry.location.lng();
    locationData.value['location_name']=name;
    locationData.value['location_address']=address;
    locationData.value['location_imageurl']=photoUrl;
    locationData.value['location_lat']=lat;
    locationData.value['location_lng']=lng;
    //별점은 없을때가있어서, 없으면 0으로 처리
    if(rating)
        locationData.value['location_rating']=rating;
    else
        locationData.value['location_rating']=0;
    locationData.value['location_placeid']=placeid;
    emit('updateLocation',locationData.value);
}

</script>
<style scope>
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .mt24 {
    margin-top: 24px;
}
.PublishLocationStyle__PublishLocationContainer-sc-1a6vsfj-0 .location-view-for-online {
    display: block;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .publish-title {
    font-size: 18px;
    line-height: 22px;
    color: rgb(0, 0, 0);
    display: flex;
    -webkit-box-align: center;
    align-items: center;
}
.PublishLocationStyle__PublishLocationContainer-sc-1a6vsfj-0 .location-input {
    width: 282px;
    height: 40px;
    position: relative;
}
.PublishLocationStyle__PublishLocationContainer-sc-1a6vsfj-0 .location-input .input-tips-left {
    position: absolute;
    bottom: -20px;
    left: 0px;
    font-size: 14px;
    color: rgb(238, 59, 40);
    line-height: 18px;
    display: none;
}
.PublishLocationStyle__PublishLocationContainer-sc-1a6vsfj-0 .location-input .input-tips-right {
    position: absolute;
    color: rgb(238, 59, 40);
    bottom: -22px;
    right: 0px;
    display: none;
}
.PublishLocationStyle__PublishLocationContainer-sc-1a6vsfj-0 .location-input input {
    width: 100%;
    height: 20px;
    padding: 8px 0px;
    border-bottom: 0.5px solid rgb(206, 210, 218);
}
.PublishLocationStyle__PublishLocationContainer-sc-1a6vsfj-0 input::placeholder{
    font-size: 14px;  
    color: #ced2d9;
}
input {
    background: none;
    outline: none;
    border: 0;
    font-size: inherit;
    color: inherit;
    overflow: visible;
    line-height: 1.15;
}
input[type="text" i] {
    padding-block: 1px;
    padding-inline: 2px;
}

</style>