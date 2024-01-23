<script setup>
import { ref } from 'vue';
import { useStore } from 'vuex'
import VueSidebarMenuAkahon from "vue-sidebar-menu-akahon";
const menuTitle=ref('');
const menuItems=ref([
    {link:'map',name:'나의 여행 지도',tooltip:'나의 여행 지도',icon:'bx-map-alt'},
    {link:'reservations',name:'나의 예약',tooltip:'나의 예약',icon:'bx-calendar-event'},
    {link:'info',name:'나의 정보',tooltip:'나의 정보',icon:'bx-user-circle'},
    {link:'follow',name:'팔로우',tooltip:'팔로우',icon:'bx-user-plus'}
]);
const searchPlaceholder=ref('검색')
const searchTooltip=ref('검색')
const logoTitleColor=ref('#000')
const bgColor=ref('#E7E7E7')
const iconsColor=ref('#000')
const secondaryColor=ref('#E7E7E7')
const itemsTooltipColor=ref('#000')
console.log(menuTitle);
const menuItemsTextColor=ref('#000')
const searchInputTextColor=ref('#000')
const menuItemsHoverColor=ref('#000')
const isMenuOpen=ref(false)
const store = useStore()
let index=0;
function onMenuItemClick(item){
    // console.log(item)
    index=menuItems.value.findIndex(menuItem=>menuItem.link===item)
    store.commit('setSelectSide',index)
    console.log('index:',index)
}
</script>
<template>
    <div>
        <VueSidebarMenuAkahon
        :menuTitle='menuTitle'
        :menuItems='menuItems'
        :bgColor='bgColor'
        :iconsColor='iconsColor'
        :secondaryColor='secondaryColor'
        :itemsTooltipColor='itemsTooltipColor'
        :menuItemsTextColor='menuItemsTextColor'
        :searchInputTextColor='searchInputTextColor'
        :menuItemsHoverColor='menuItemsHoverColor'
        :logoTitleColor='logoTitleColor'
        :searchPlaceholder='searchPlaceholder'
        :searchTooltip='searchTooltip'
        :isMenuOpen='isMenuOpen'
        @menuItemClcked='onMenuItemClick'/>
    </div>
</template>
<style scoped>
.page-container {
  display: flex;
  height: 100vh; 
}
.sidebar {
  position: relative;
  
}
</style>