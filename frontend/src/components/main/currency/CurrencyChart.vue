<template>
    <ApexChart :options="chartOptions" :series="series"></ApexChart>
</template>
  
<script setup>
import { ref, watch, defineProps } from 'vue';
import ApexChart from 'vue3-apexcharts';
import { chartOptions as defaultChartOptions,markerChange } from '@/config/currencyConfig'
  
const props = defineProps({
    currencyData: Object,
    dataType: String
});
  
const series = ref([]);
const chartOptions = ref({ ...defaultChartOptions }); // chartOptions를 ref로 정의

function updateData(timeline) {
  const now = new Date();
  let start;
  switch (timeline) {
    case 'day':
      start = new Date(now.setDate(now.getDate() - 1));
      break;
    case 'week':
      start = new Date(now.setDate(now.getDate() - 5));
      break;
    case 'month':
      start = new Date(now.setMonth(now.getMonth() - 1));
      break;
    case 'year':
      start = new Date(now.setFullYear(now.getFullYear() - 1));
      break;
    case 'all':
      start = new Date(Math.min(...series.value[0].data.map(d => d.x))); // 전체 데이터의 최소 날짜
      break;
  }
  chartOptions.value = {
    ...chartOptions.value,
    xaxis: {
      ...chartOptions.value.xaxis,
      min: start.getTime(),
      max: new Date().getTime()
    }
  };

  

}
  
watch(()=>props.dataType,selectTabId=>{
    updateData(selectTabId)
})  


watch([() => props.currencyData], ([newCurrency]) => {
  if (!newCurrency || !newCurrency['RATES']) return;
  // 시리즈 데이터를 업데이트합니다.
  
  series.value = [{
    name: newCurrency['CURRENCY_NAME'],
    data: newCurrency['RATES']['date'].reduce((acc, date, index) => {
      const parts = date.split('.');
      if (parts.length !== 3) return acc; // 날짜 포맷이 유효하지 않으면 스킵
      const [year, month, day] = parts.map(part => parseInt(part, 10));

      // 유효한 날짜와 비율 값만 처리
      if (!isNaN(year) && !isNaN(month) && !isNaN(day) && !isNaN(newCurrency['RATES']['rate'][index])) {
        const utcDate = new Date(Date.UTC(year, month - 1, day));
        acc.push({
          x: utcDate.getTime(),
          y: parseFloat(newCurrency['RATES']['rate'][index].toFixed(2))
        });
      }
      return acc;
    }, [])
  }];
  const newOptions = markerChange({ ...chartOptions.value }, series.value);
  chartOptions.value = newOptions;
  
}, { deep: true });


  </script>
  