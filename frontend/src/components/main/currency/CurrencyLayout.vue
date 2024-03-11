<template>
    <CurrencyTitle :currency-title="currencyData" @select-code="selectCode"/>
    <CurrencySelectTab @select-tab="selectTab" :data-type="dataType"/>
    <CurrencyChart :currencyData="currencyData" :data-type="dataType"/>
    
</template>
<script setup>
import CurrencyChart from './CurrencyChart.vue';
import CurrencyTitle from './CurrencyTitle.vue';
import CurrencySelectTab from './CurrencySelectTab.vue';
import { formattedCurrencyCodes } from '@/config/currencyConfig';
import { ref } from 'vue'
import axios from 'axios'

const currencyData=ref({});
const countryNumber=ref(1);
const dataType=ref('all');

const currentCurrency=ref('USD');

let isFetching = false;

async function getCurrencyList() {
    if (isFetching) return;
    isFetching = true;
    try {
        const response = await axios.get(`${process.env.VUE_APP_PYTHON_API_URL}/currency_list?currencyCode=${currentCurrency.value}`);
        currencyData.value = response.data;
        dataType.value='day'
    } catch (error) {
        console.error('Currency list fetch error:', error);
    } finally {
        isFetching = false;
    }
}


function selectCode(code){
    countryNumber.value = formattedCurrencyCodes
                            .map((ccode,idx)=>ccode==code?idx:-1)
                            .filter(idx=>idx!=-1)[0]
    currentCurrency.value=code
    getCurrencyList()
}

function selectTab(tabId){
    dataType.value=tabId
}

</script>
<style scoped>
    
</style>