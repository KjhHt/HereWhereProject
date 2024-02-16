<template>
    <div id="chart">
      <ApexChart type="area" height="350" :options="chartOptions" :series="series"></ApexChart>
    </div>
  </template>
  
<script setup>
import { ref, watch, defineProps } from 'vue';
import ApexChart from 'vue3-apexcharts';
const props=defineProps({
    currencyData:Object,
    countryNumber:Number
});
const series = ref([{
    name: 'series-1',
    data: [30, 40, 35, 50, 49, 60, 70, 91]
}]);

const chartOptions = ref({});

watch([() => props.currencyData, () => props.countryNumber], ([newCurrency, newCountryNumber]) => {
    if (!newCurrency || newCountryNumber === undefined) return; // 새로운 데이터가 없거나 countryNumber가 정의되지 않은 경우 early return

    const newCategories = newCurrency[0].slice(7);
    chartOptions.value = {
        chart: {
            id: 'vuechart-example'
        },
        xaxis: {
            categories: newCategories
        }
    };
    series.value[0] = {
        name: newCurrency[newCountryNumber][1],
        data: newCurrency[newCountryNumber].slice(7).map(item => parseFloat(item.replace(/,/g, ''))) // 문자열 데이터를 숫자로 변환
    };
}, { deep: true }); // deep 옵션으로 내부 변화까지 감지하도록 설정


</script>
  