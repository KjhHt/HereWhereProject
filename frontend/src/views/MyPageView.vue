<!-- eslint-disable no-unused-vars -->
<script setup>
import { ref, computed, defineEmits} from 'vue';
import { useStore } from 'vuex'
import CircularMenu from '@/components/CircularMenu.vue';
import TheSearchForm from '@/components/TheSearchForm.vue';
// import OverlayView from '@/views/mypage/OverlayView.vue';
import PurchaseHistory from '@/components/search/PurchaseHistory.vue';
const store=useStore()
const menu=computed(()=> store.state.menuStore.menu);
console.log('menu:',menu.value)
const emit = defineEmits('handleItem')


const handleItemClick = (value) =>{
  console.log('제발좀',value);
  emit('handleItem',value)
}

</script>
<template>
    <PurchaseHistory :show-modal="showModal" :reservation="reservation" :fetch-reservation="fetchReservation" :close-modal="closeModal" @handleItemClick="handleItemClick"/>
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