<template>
  <input type="file" accept="image/*" @change="uploadImage" ref="inputFile" class="d-none"/>
  <button class="search-button">
    <!-- 고침 -->
    <i @click="handleClick" class="fa fa-image" style="margin-right: 10px;"></i>
  </button>
</template>
<script setup>
import { ref,defineEmits } from 'vue';
import axios from 'axios'
const emit = defineEmits(['searchImgLocation','clearPlaces','loadingState']);
const inputFile=ref(null);
const isLoading = ref(false);
// 고침
const handleClick = () => {
  clickInput(); // clickInput 함수 호출
  clearPlaces(); // clearPlaces 함수 호출
};
function clickInput(){
  inputFile.value.click()
}
const clearPlaces = () => {
  emit('clearPlaces');
};
function uploadImage(event) {
  const files = event.target.files;
  if (files.length > 0) {
    isLoading.value = true; // 로딩 시작
    emit('loadingState', isLoading.value);
    const formData = new FormData();
    formData.append('image', files[0]);
    axios.post(process.env.VUE_APP_PYTHON_API_URL+'/searchImage', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(response =>{
      console.log(response)
      const imgplaces=response.data.results[0]
      isLoading.value = false; // 로딩 종료
      emit('loadingState', isLoading.value);
      emit('searchImgLocation',imgplaces)
    })
    .catch(error => {
      isLoading.value = false; // 로딩 종료
      emit('loadingState', isLoading.value);
      console.error('Error:', error);
    })
  }
}
</script>
<style scoped>
.search-button{
  padding: 5px;
  background-color: white;
  color: #000; /* Set the color to black */
  border: none;
  border-radius: 3px;
  cursor: pointer;
}
</style>