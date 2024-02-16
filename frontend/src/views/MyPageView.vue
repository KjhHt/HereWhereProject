<!-- eslint-disable no-unused-vars -->
<script setup>
import { ref, computed} from 'vue';
import { useStore } from 'vuex'
import CircularMenu from '@/components/CircularMenu.vue';
import TheSearchForm from '@/components/TheSearchForm.vue';
// import OverlayView from '@/views/mypage/OverlayView.vue';
const store=useStore()
const menu=computed(()=> store.state.menuStore.menu);
console.log('menu:',menu.value)
</script>
<template>
    <div class="page-container">
          <CircularMenu />
            <!-- <OverlayView v-if="menu==0"/> -->
          <TheSearchForm/>
    </div>
</template>
<style scoped>
.page-container {
  display: flex;
}
</style>