<template>
    <div v-if="currencyTitle" class="p-4">
      <h1 class="pl-2 m-2">
        {{ currencyName }}
        {{ currencyStandard }}
        {{ currencySymbol }}
        
      </h1>
      <h2 class="pl-2 m-2 ">
        = {{ currentCurrency }}원
      </h2>
    </div>
  </template>
  
  <script setup>
  import { defineProps, watch, ref, computed } from 'vue';
  import axios from 'axios';
  
  const props = defineProps({
    currencyTitle: Object,
  });
  
  const currentCurrency = ref('');
  
  // computed를 사용하여 currencyTitle의 유효성 검사 후 사용
  const currencyName = computed(() => props.currencyTitle ? props.currencyTitle[0] : '');
  const currencySymbol = computed(() => props.currencyTitle ? props.currencyTitle[1] : '');
  const currencyStandard = computed(() => props.currencyTitle ? props.currencyTitle[5] : '');


  watch(
    () => props.currencyTitle,
    (newValue) => {
      if (newValue && newValue[1]) {
        getCurrentCurrency(newValue[1]);
      }
    },
    { immediate: true } // 컴포넌트 마운트 시 즉시 실행
  );
  
  async function getCurrentCurrency(currencyCode) {
    try {
      const response = await axios.get(`${process.env.VUE_APP_PYTHON_API_URL}/current_currency?currencyCode=${currencyCode}`);
      currentCurrency.value = response.data;
    } catch (error) {
      console.error('API 호출 중 에러 발생:', error);
      currentCurrency.value = '정보를 불러오는데 실패했습니다.';
    }
  }
  </script>
  
  <style scoped>
  </style>
  