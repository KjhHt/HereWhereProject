<template>
    <div class="card-container">
        <RecommendLoading :loading="isLoading" class="loading"/>
        <div v-for="(destination, index) in recommend_search" :key="index" class="card" :style="{ right: index * 140 + 'px' }">
            <img :src="destination.image" alt="Destination Image" class="destination-image" @click="imgClick(destination.destination)">
            <div class="destination-info">
                <p>{{ destination.destination }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from "axios";
import { ref, defineEmits, defineProps, watch } from "vue";
import RecommendLoading from '@/components/search/RecommendLoading.vue';
const recommend_search = ref([]);
const isLoading = ref(false)
const emit = defineEmits(['imgClick']);
const props = defineProps({
    places: Object,
    imgsearchplaces: Object,
    predict: Object
})
const imgClick = (value) => {
  emit('imgClick',value);
}
async function fetchMbtiTrip(places) {
    try {
        const data = {
            search: places.formatted_address
        };
        isLoading.value = true;
        recommend_search.value = []; 
        const response = await axios.post(process.env.VUE_APP_PYTHON_API_URL + '/recommend_by_search', data);
        recommend_search.value = response.data; // 수정된 부분
    } 
    catch (error) {
        console.error('Error fetching Mbti trip:', error);
    }
    finally {
        isLoading.value = false;
    }
}
watch(() => [props.places, props.imgsearchplaces, props.predict],([places, imgplaces, predict]) => {
    if (Object.keys(places).length !== 0) {
      fetchMbtiTrip(places);
    } 
    else if (imgplaces !== null) {
      fetchMbtiTrip(imgplaces);
    }
    else if (predict !== null) {
      fetchMbtiTrip(predict);
    }
  }
);
</script>
<style scoped>
.card-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    width: 100%;
}
.card {
    position: absolute;
    bottom: 20px;
    width: 130px;
    height: 130px;
    border: 1px solid #ccc;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    overflow: hidden;
    margin: 10px;
}
.loading {
    position: absolute;
    bottom: 20px;
    width: 400px;
    height: 100px;
    overflow: hidden;
    margin: 10px;
    right: 20px;
}
.destination-image {
    width: 100%;
    height: 100%;
    border-radius: 8px;
}
.destination-info {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    padding: 8px;
    color: white;
    border-radius: 0 0 8px 8px;
}
.destination-info p {
    margin: 0;
    font-size: 14px;
    font-weight: bold;
}
</style>