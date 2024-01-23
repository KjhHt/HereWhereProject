<!-- eslint-disable no-unused-vars -->
<script setup>
import { ref, computed} from 'vue';
import { useStore } from 'vuex'
import SideBar from '@/components/SideBar.vue'
import TheSearchForm from '@/views/mypage/TheSearchForm.vue';
// import OverlayView from '@/views/mypage/OverlayView.vue';
const store=useStore()
const menu=computed(()=> store.state.menuStore.menu);
console.log('menu:',menu.value)
</script>
<template>
    <div class="page-container">
        <SideBar/>
        <!-- <OverlayView v-if="menu==0"/> -->
        <TheSearchForm v-if="menu==2"/>
    </div>
</template>
<style>
.page-container {
  display: flex;
  height: 100vh;
}
</style>