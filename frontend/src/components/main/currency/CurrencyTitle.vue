<template>
  <div v-if="currencyTitle" class="p-4">
    <div class="d-flex align-items-center justify-content-start mb-2">
      <!-- 달러 입력 필드 -->
      <input type="text" v-model="currencyStandard" class="form-control me-2" @input="updateKRW"/>
      <CurrencyInput :currency-codes="formattedCurrencyCodes" @select-code="selectCode"/>
    </div>
    <h1 class="text-center mt-1 mb-2">=</h1>
    <div class="d-flex align-items-center justify-content-start">
      <!-- 원화 입력 필드 -->
      <input type="text" v-model="currencyInKRW" class="form-control me-2" @input="updateFrx" placeholder="금액 (KRW)" />
      <h2 class="pl-2 m-2 ">
        원
      </h2>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref, watch, defineEmits } from 'vue';
import CurrencyInput from '@/components/main/currency/CurrencyInput.vue';
import { formattedCurrencyCodes } from '@/config/currencyConfig';

const props = defineProps({
  currencyTitle: Object,
});

const emits=defineEmits(['selectCode'])
function selectCode(code){
  emits('selectCode',code)
}

const currencyStandard = ref(1); // 기본 통화(USD) 금액
const currencyInKRW = ref(0); // 대상 통화(KRW) 금액
const currencyRate = ref(1); // 환율을 저장할 반응형 참조

// props.currencyTitle가 변경될 때마다 실행될 함수
watch(() => props.currencyTitle, (newCurrencyTitle) => {
  // 새로운 currencyTitle 값이 유효하고, 'RATES'와 'rate'가 존재하며, 길이가 0보다 클 경우
  if (newCurrencyTitle && newCurrencyTitle['RATES'] && newCurrencyTitle['RATES']['rate'].length > 0) {
    // rate 배열의 마지막 요소를 파싱하여 currencyRate 업데이트
    currencyRate.value = parseFloat(newCurrencyTitle['RATES']['rate'].at(-1).toFixed(2));
    currencyInKRW.value=currencyRate.value
    updateFrx()
  } else {
    // 그렇지 않은 경우 기본값으로 설정
    currencyRate.value = 1;
  }
}, { deep: true }); // deep 옵션으로 중첩된 객체의 변경도 감지


// USD 금액 변경 시 KRW 업데이트
function updateKRW() {
  const result = parseFloat(currencyStandard.value) * currencyRate.value;
  currencyInKRW.value = result.toFixed(2); // 소수점 둘째 자리까지
}

// KRW 금액 변경 시 USD 업데이트
function updateFrx() {
  const result = parseFloat(currencyInKRW.value) / currencyRate.value;
  currencyStandard.value = result.toFixed(2); // 소수점 둘째 자리까지
}
</script>
