<script setup>
import { ref } from 'vue';
import VueSidebarMenuAkahon from "vue-sidebar-menu-akahon";
const menuTitle=ref('HERE WHERE');
const menuItems=ref([
    {link:'#',name:'나의 여행 지도',tooltip:'나의 여행 지도',icon:'bx-map-alt'},
    {link:'#',name:'나의 예약',tooltip:'나의 예약',icon:'bx-calendar-event'},
    {link:'#',name:'나의 정보',tooltip:'나의 정보',icon:'bx-user-circle'},
    {link:'#',name:'팔로우',tooltip:'팔로우',icon:'bx-user-plus'}
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
</script>
<template lang="">
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
        :isMenuOpen='isMenuOpen'/>
    </div>
</template>
<style scoped>
</style>