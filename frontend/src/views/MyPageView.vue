<template> 
  <div class="travel_guide_root_class">
    <div class="PersonalHomeStyle__PageContainer-sc-17u1tdw-0">
      <div class="PersonalHomeStyle__PersonalHomeContainer-sc-17u1tdw-1">
        <CircularMenu/>
        <div class="no-nav-main ">
          <MyPageHeader/>
          <div class="PersonalHomeStyle__FlexContainer-sc-17u1tdw-2">
          <PurchaseHistory :show-modal="showModal" :reservation="reservation" :fetch-reservation="fetchReservation" :close-modal="closeModal" @handleItemClick="handleItemClick"/>
          <MyPageTablist/>
          <MyPhotoList/>
          </div>
      </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { defineEmits} from 'vue';
import CircularMenu from '@/components/CircularMenu.vue';
import MyPhotoList from '@/components/MyPage/MyPagePhotoList.vue'
import MyPageHeader from '@/components/MyPage/MyPageHeader.vue'
import MyPageTablist from '@/components/MyPage/MyPageTablist.vue'
import PurchaseHistory from '@/components/search/PurchaseHistory.vue';

const emit = defineEmits('handleItem')

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