<template>
  <div class="tab-buttons nav nav-tabs">
    <a v-for="tab in tabs" :key="tab.id" @click="selectTab(tab.id)"
       :class="['nav-link', {'active': selectedTab === tab.id}]" role="button">
      {{ tab.label }}
    </a>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';


const emits = defineEmits(['selectTab']);

// 탭 데이터 정의
const tabs = [
  { id: 'day', label: '1일' },
  { id: 'week', label: '5일' },
  { id: 'month', label: '1개월' },
  { id: 'year', label: '1년' },
  { id: 'all', label: '최대' },
];

// 선택된 탭
const selectedTab = ref('day');

// 탭 선택 핸들러
function selectTab(tabId) {
  selectedTab.value = tabId;
  emits('selectTab', tabId);
}

selectTab(selectedTab.value)

</script>

<style scoped>
/* 여기에 추가적인 커스텀 스타일을 적용할 수 있습니다. */
</style>
