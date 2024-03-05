<template>
<div class="User-xpwzvz-3 eXXXKQ userContainer">
    <div class="container">
        <img 
        :alt="profileImage" 
        :src="profileImage" 
        class="img" 
        style="object-fit: cover;
        background-image: url(&quot;https://ak-d.tripcdn.com/images/0551112000ct8bu4hAEDF.png&quot;);
        background-size: contain; 
        background-repeat: no-repeat; 
        background-position: center center;">
    </div>
    <span class="nickname">{{ nickname }}</span>
</div>
</template>
<script setup>
import { ref , watchEffect , defineProps } from 'vue';

const profileImage = ref('');
const nickname = ref('');

const props = defineProps({
    detailData: Object,
});

watchEffect(() => {
  if (props.detailData) {
    nickname.value = props.detailData['board_writer'];
    profileImage.value = props.detailData['profileimage'];
  }
});


</script>
<style scope>
.userContainer {
    line-height: 24px;
    margin-left: 28px;
    margin-top: 27px;
    margin-bottom: 10px;
    clear: both;
    overflow: hidden;
    -webkit-box-align: center;
    align-items: center;
    -webkit-box-pack: start;
    justify-content: flex-start;
    display: flex;
    flex-direction: row;
    width:100%
}

.userContainer .container {
    cursor: pointer;
    width: 48px;
    height: 50px;
    position: relative;
    margin-right: 4px;
    flex-shrink: 0;
}

.userContainer .img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    overflow: hidden;
    position: absolute;
    left: 4px;
    top: 5px;
    background-color: rgb(245, 247, 250);
    
}

.userContainer .nickname {
    font-size: 18px;
    color: rgb(15, 41, 77);
    cursor: pointer;
    display: block;;
    -webkit-line-clamp: 1;
    white-space: nowrap;
    width: 100%;
    text-align: left;
}
</style>