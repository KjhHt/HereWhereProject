<template>
<div class="PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3">
  <div class="header-wrap">
    <div class="avatar_container">
      <img :src="headerData.profileimage" class="head" style="object-fit: cover;">
    </div>
    <div class="infor">
      <div class="name-container">
        <h1 class="name">{{ props.headerData.name }}</h1>
      </div>

      <div class="info-wrap">
        <div class="info-item">
          <span class="info-num">{{ headerData.boardcount }}</span>
          <span class="info-name">게시물</span>
        </div>
        <div class="info-divider"></div>
        <div class="info-item" data-tabindex="0" @click="openModal">
          <span class="info-num">{{ headerData.followcount }}</span>
          <span class="info-name">팔로워</span>
        </div>
        
      </div>             
    </div>
    <div class="follow-btn" data-isfollow="false" data-followtype="single" data-tabindex="0">
      팔로우
    </div> 
    <!-- 팔로워 모달창 -->
    <FollowModal v-if="showModal" :likers="likers" @closeModal="closeModal" />
  </div>
</div>
</template>
<script setup>
import { ref,defineProps,onMounted } from 'vue';
import FollowModal from '@/components/BoxRight/FollowModal.vue';
import axios from 'axios';

const showModal = ref(false);
const likers = ref([]);

onMounted( () => {
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const id = vuexStore.loginStore.userInfo.id;

   axios.get(`${process.env.VUE_APP_API_URL}/getMyFollowList`,{
    params : {
      id:id
    }
  })
  .then(res => {
    
    res.data.forEach(item => {
      if(item.profileImage === '0') {
        item.profileImage = require('@/assets/dino.jpg');
      }
      else if(item.profileImage && !item.profileImage.startsWith("http")) {
        item.profileImage = `data:${res.headers['content-type']};base64,${item.profileImage}`;
      }
    });

    likers.value = res.data.map(liker => ({
      name: liker.name,
      profileimage: liker.profileImage
    }));


    console.log(likers.value);
  })
  .catch(err=>console.log(err))
})

const props = defineProps({
  headerData : Object,
})

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};



</script>
<style scope>

.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 {
    background: white;
    margin-bottom: 16px;
    padding-bottom: 16px;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .header-wrap {
    padding: 16px 16px 0px;
    display: flex;
    flex-direction: row;
    align-items: flex-start;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .avatar_container {
    width: 82px;
    height: 85px;
    position: relative;
    margin-top: -4px;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .head {
    width: 68px;
    height: 68px;
    border-radius: 50%;
    position: absolute;
    left: 7px;
    top: 8px;
    overflow: hidden;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .infor {
    flex: 1 1 0%;
    margin-left: 12px;
    margin-right: 12px;
    display: flex;
    flex-direction: column;
    -webkit-box-pack: center;
    justify-content: center;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .infor .name-container {
    display: flex;
    flex-direction: row;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .infor .name-container .name {
    font-size: 32px;
    color: rgb(15, 41, 77);
    line-height: 38px;
    font-weight: bold;
    white-space: nowrap;
    flex: 0 1 auto;
    margin: 0px !important;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .infor .address {
    font-size: 12px;
    color: rgb(0, 41, 83);
    line-height: 16px;
    margin-top: 4px;
    text-align: left;
    margin-bottom: 0px;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .info-wrap {
    display: flex;
    flex-direction: row;
    -webkit-box-align: center;
    align-items: center;
    margin-top: 10px;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .info-wrap .info-item {
    display: flex;
    flex-direction: row;
    -webkit-box-align: center;
    align-items: center;
    -webkit-box-pack: start;
    justify-content: flex-start;
    position: relative;
    cursor: pointer;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .info-wrap .info-item .info-num {
    font-size: 14px;
    color: rgb(15, 41, 77);
    line-height: 22px;
    font-weight: bold;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .info-wrap .info-item .info-name {
    margin-left: 4px;
    margin-top: 2px;
    font-size: 13px;
    color: rgb(78, 99, 126);
    line-height: 16px;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .info-wrap .info-divider {
    float: left;
    width: 1px;
    height: 12px;
    background: rgb(206, 210, 217);
    margin-left: 8px;
    margin-right: 8px;
}
.PersonalHomeStyle__HeaderContainer-sc-17u1tdw-3 .follow-btn {
    background: rgb(50, 100, 255);
    border-radius: 4px;
    padding: 12px 44px;
    text-align: center;
    display: inline-block;
    cursor: pointer;
    font-size: 16px;
    color: rgb(255, 255, 255);
    line-height: 20px;
    margin-top: 35px;
}
</style>