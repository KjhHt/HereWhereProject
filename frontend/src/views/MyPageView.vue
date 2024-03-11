<template> 
  <div class="travel_guide_root_class">
    <div class="PersonalHomeStyle__PageContainer-sc-17u1tdw-0">
      <div class="PersonalHomeStyle__PersonalHomeContainer-sc-17u1tdw-1">
        <CircularMenu/>
        <div class="no-nav-main ">
          <MyPageHeader :headerData="headerData"/>
          <div class="PersonalHomeStyle__FlexContainer-sc-17u1tdw-2">
          <PurchaseHistory :show-modal="showModal" :reservation="reservation" :fetch-reservation="fetchReservation" :close-modal="closeModal" @handleItemClick="handleItemClick"/>
          <MyPageTablist v-if="!loadingModal"/>
          <MypageLoadingModal v-if="loadingModal"></MypageLoadingModal>
          <MyPhotoList @moveDetailView="handleMoveDetailView" v-if="!loadingModal" :boardData="boardData"/>
          </div>
      </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { defineEmits,defineProps,onMounted,ref } from 'vue';
import CircularMenu from '@/components/CircularMenu.vue';
import MyPhotoList from '@/components/MyPage/MyPagePhotoList.vue'
import MyPageHeader from '@/components/MyPage/MyPageHeader.vue'
import MyPageTablist from '@/components/MyPage/MyPageTablist.vue'
import PurchaseHistory from '@/components/search/PurchaseHistory.vue';
import MypageLoadingModal from '@/components/MyPage/MypageLoadingModal.vue';

import axios from 'axios';

const emit = defineEmits('handleItem')
const headerData = ref({});
const boardData = ref([]);
const loadingModal = ref(false);

const props = defineProps({
  mypageIdData: String,
});

const handleMoveDetailView = (data) => {
  console.log('data : ',data);
  emit('selectPageData','TripMomentDetailView',data);
}

onMounted(async () => {
  const id = props.mypageIdData;
  console.log('뭐임? : ',id)
  loadingModal.value = true;
  await axios.get(`${process.env.VUE_APP_API_URL}/getMypageHeader`,{
    params: { id }
  })
  .then(res=>{
    if(res.data.profileimage === '0') {
      res.data.profileimage = require('@/assets/dino.jpg');
    }
    else if(!res.data.profileimage.startsWith("http")) {
      res.data.profileimage = `data:${res.headers['content-type']};base64,${res.data.profileimage}`;
    }
    headerData.value=res.data;
  })
  .catch(err=>console.log(err))

  await axios.get(`${process.env.VUE_APP_API_URL}/myBoard`,{
    params: { id }
  })
  .then(res=>{
  const newBoardList = res.data.map(item => {
    let profileimage = item.profileimage;
    if(profileimage === '0') {
      profileimage = require('@/assets/dino.jpg');
    }
    else if(!profileimage.startsWith("http")) {
      profileimage = `data:${res.headers['content-type']};base64,${profileimage}`;
    }

    let base64BoardImages = item.base64BoardImages.map(value => {
      return `data:${res.headers['content-type']};base64,${value}`;
    });

    // 좋아요 리스트 프로필 이미지 처리
    if(item.likeList && item.likeList.length > 0){
      item.likeList = item.likeList.map(likeItem => {
        if(likeItem.profileimage === '0')
          likeItem.profileimage = require('@/assets/dino.jpg');
        else if(!likeItem.profileimage.startsWith('http')){
          likeItem.profileimage = `data:${res.headers['content-type']};base64,${likeItem.profileimage}`;
        }
        return likeItem;
      });
    }

    return {...item, profileimage, base64BoardImages};
  })
  boardData.value = [...boardData.value, ...newBoardList];
  loadingModal.value = false;
})
  .catch(err=>console.log(err))
});

const handleItemClick = (value) =>{
  emit('handleItem',value)
}

</script>
<style scope>
.travel_guide_root_class {
    position: relative;
}
.PersonalHomeStyle__PageContainer-sc-17u1tdw-0 {
    background: rgb(245, 245, 249);
}
.PersonalHomeStyle__PersonalHomeContainer-sc-17u1tdw-1 {
    display: flex;
    max-width: 1200px;
    margin: 0px auto;
    padding-top: 16px;
}
.PersonalHomeStyle__PersonalHomeContainer-sc-17u1tdw-1 .no-nav-main {
    width: 100%;
}
.PersonalHomeStyle__FlexContainer-sc-17u1tdw-2 {
    display: flex;
    flex-direction: column;
    margin-bottom: 200px;
}


</style>