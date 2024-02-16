<template>
    <CurrencyInput :currency-codes="currencyCodes" @select-code="selectCode"/>
    <CurrencyTitle :currency-title="currencyData[countryNumber]"/>
    <CurrencyChart :currencyData="currencyData" :country-number="countryNumber"/>
</template>
<script setup>
import CurrencyChart from './CurrencyChart.vue';
import CurrencyTitle from './CurrencyTitle.vue';
import CurrencyInput from './CurrencyInput.vue';
import { ref, onMounted } from 'vue'
import axios from 'axios'

const currencyData=ref([]);
const countryNumber=ref(1);
const currencyCodes = ref([]);


onMounted(()=>{
    getCurrencyList();
})

async function getCurrencyList() {
    try {
        console.log('*',process.env.VUE_APP_PYTHON_API_URL,'*');
        const response = await axios.get(process.env.VUE_APP_PYTHON_API_URL+'/currency_list');
        currencyData.value = response.data;
        if (Array.isArray(response.data) && response.data.length > 0) {
            
            // response.data가 배열이고, 요소를 포함하는 경우에만 처리
            const newSuggestions = response.data.slice(1).map(arr => {
                // 데이터 유효성 검사 및 안전한 접근을 보장
                return arr.length > 3 ? `${arr[0]}/${arr[1]}` : '';
            }).filter(suggestion => suggestion); // 빈 문자열 제거
            currencyCodes.value = newSuggestions;
        }
    } catch (error) {
        console.error('Currency list fetch error:', error);
        // 에러 처리 로직 추가 (예: 사용자에게 알림)
    }
}

function selectCode(code){
    countryNumber.value = currencyData.value
        .map((arr, idx) => (arr[1] == code ? idx : -1)) // 조건에 맞는 경우 인덱스를, 아닌 경우 -1을 반환
        .filter(idx => idx !== -1); // -1이 아닌 인덱스만 필터링

}

</script>
<style scoped>
    
</style>