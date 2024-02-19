<!-- ParentComponent.vue -->
<template>
    <div class="feature-section px-4" v-for="(flights,continent) in flightsData" :key="continent" >
        <h2 class="left-aligned slide-in-from-right">{{continent}}</h2>
        <div class="image-list slide-in-from-left">
            <div class="image-list">
                <FlightCard :flights="flights"/>
            </div>
        </div>
        <button v-if="flights.length > flightLimits[continent]" @click="showMore(continent)">더보기</button>
    </div>
</template>
  
<script setup>
import FlightCard from './FlightCard.vue';
import { onMounted,reactive} from 'vue'
import axios from 'axios'

const flightsData=reactive({})


// 각 대륙별로 표시되는 항목의 수를 관리하는 상태
const flightLimits = reactive({
  "오세아니아": 8,
  "아시아": 8,
  "북아메리카": 8,
  "남아메리카": 8,
  "유럽": 8,
  "아프리카": 8,
  "남극": 8,
})

// "더보기" 버튼을 클릭했을 때 실행될 함수
function showMore(continent) {
  const newLimit = flightLimits[continent] + 8;
  flightLimits[continent] = flightsData[continent].length < newLimit ? flightsData[continent].length : newLimit;
}


async function groupFlightsByContinent() {
  try {
        const response = await axios.get(process.env.VUE_APP_PYTHON_API_URL+'/flights_list');
        const data = response.data;
        // 빈 대륙 제거 로직 추가
        Object.keys(data).forEach(key => {
          if (data[key].length === 0) {
            delete data[key];
          }
        });
        // 응답 데이터를 flightsData에 직접 할당
        Object.assign(flightsData, data);
    } catch (error) {
        console.error("Failed to fetch flights list:", error);
    }
}

onMounted(()=>{
  groupFlightsByContinent();
})


</script>
<style scoped>
h2 {
  font-weight: bold;
  font-size: 2em;
}

img {
  max-width: 100%;
}

.image-list {
  margin-top: 1em;
  display: grid;  /* 이미지 리스트를 그리드 레이아웃으로 설정 */
  gap: 40px;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));  /* 최소 너비 240px의 열을 가능한 많이 만들되, 열 너비가 320px보다 커지면 공간을 고르게 분배 */
}

.image-list img:last-child {
  margin-right: 0; /* 마지막 이미지의 오른쪽 마진 제거 */
}

.left-aligned {
  text-align: left;
}

.feature-section:not(:last-child) {
  margin-bottom: 100px;
}
.features{
  padding: 80px
}

</style>  