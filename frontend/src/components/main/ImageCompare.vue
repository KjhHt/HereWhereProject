<template>
  <div class="container">
    <div class="upload">
      <div class="upload-files">
        <header>
          <p>
            <span class="up">HereWhere</span><br>
            <span class="load">이미지 예측 서비스</span>
          </p>
        </header>
        <div class="body" id="drop" @drop.prevent="handleImageDrop" @dragover.prevent>
          <div v-if="!previewImage" class="placeholder-text">
            <p><i class="fa fa-cloud-upload" aria-hidden="true"></i> 이미지를 올려주세요</p>
          </div>
          <img :src="previewImage" v-else alt="File Preview" style="width: 80%; height: 200px;">
          <input type="file" multiple="multiple" ref="fileInput" @change="handleImageChange" style="display: none;" />
        </div>
        <footer>
          <button class="importar" @click="browseFiles">이미지 선택하기</button>
        </footer>
      </div>
    </div>
    <div class="upload">
      <div class="upload-files">
        <header>
          <p>
            <span class="up">예측결과</span><br>
            <span class="load" v-if="analysisResult">{{ analysisResult.name }}</span>
          </p>
        </header>
        <div class="body" id="drop" @drop.prevent="handleImageDrop" @dragover.prevent>
          <div v-if="!previewImage" class="placeholder-text">
          </div>
          <LoadingOverlay :loading="loadingdetail"/>
          <img :src="`https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${analysisResult.photos[0].photo_reference}&key=${apiKey}`" v-if="analysisResult" alt="File Preview" style="width: 80%; height: 200px;">
          <input type="file" multiple="multiple" ref="fileInput" @change="handleImageChange" style="display: none;" />
        </div>
        <footer>
          <!-- <p class="footer-text" v-if="analysisResult">{{ analysisResult.formatted_address }}</p> -->
          <button class="importar" @click="emitSearchImgLocation" v-if="analysisResult">지도에서 확인하기</button>
        </footer>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import axios from 'axios';
import LoadingOverlay from '@/components/search/LoadingModal.vue';

const loadingdetail = ref(false)
const apiKey = process.env.VUE_APP_GOOGLE_API_KEY;
const previewImage = ref('');
const analysisResult = ref('');
const emit = defineEmits(['searchImgLocation']);

const browseFiles = () => {
  const fileInput = document.querySelector('input[type="file"]');
  fileInput.click();
}

const handleImageChange = (event) => {
  const files = event.target.files;
  if (files.length > 0) {
    const imageUrl = URL.createObjectURL(files[0]);
    previewImage.value = imageUrl;
    analysisResult.value = ''; // 결과 사진 초기화
    uploadImage(files[0]);
  }
}

const handleImageDrop = (event) => {
  const files = event.dataTransfer.files;
  if (files.length > 0) {
    const imageUrl = URL.createObjectURL(files[0]);
    previewImage.value = imageUrl;
    analysisResult.value = ''; // 결과 사진 초기화
    uploadImage(files[0]);
  }
}

const uploadImage = (file) => {
  loadingdetail.value = true;
  const formData = new FormData();
  formData.append('image', file);

  axios.post(process.env.VUE_APP_PYTHON_API_URL+'/searchImage', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
  .then(response => { 
    loadingdetail.value = false;
    analysisResult.value = response.data.results[0];
  })
  .catch(error => console.error('Error:', error));
}

const emitSearchImgLocation = () => {
  emit('searchImgLocation', analysisResult.value);
}
</script>


<style scoped>
.placeholder-text {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px; /* 이미지의 높이와 동일하게 설정 */
  border: 2px dashed #ccc;
  border-radius: 5px;
  width: 80%;
}

.placeholder-text p {
  font-size: 16px;
  color: #777;
}
.upload {
    position: relative;
    width: 400px;
    min-height: 445px;
    box-sizing: border-box;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    padding-bottom: 20px;
    background: #fff;
    animation: fadeup 0.5s 0.5s ease both;
    transform: translateY(20px);
    opacity: 0;
}
.result {
    position: relative;
    width: 400px;
    min-height: 445px;
    box-sizing: border-box;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    padding-bottom: 20px;
    background: #fff;
    animation: fadeup 0.5s 0.5s ease both;
    transform: translateY(20px);
    opacity: 0;
}

.upload-files header {
    background: #9893EA;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    text-align: center;
    padding: 20px 0;
    height: 111px;
}

.upload-files header p {
    color: #fff;
    font-size: 30px;
    margin: 0;
}

.upload-files header p i {
    transform: translateY(20px);
    opacity: 0;
    font-size: 30px;
    animation: fadeup 0.5s 1s ease both;
}

.upload-files header p .up {
    transform: translateX(-20px);
    display: inline-block;
    opacity: 0;
    animation: faderight 0.5s 1.5s ease both;
}

.upload-files header p .load {
    font-weight: bold;
    display: inline-block;
    transform: translateX(-20px);
    opacity: 0;
    animation: faderight 1s 1.5s ease both;
}

.upload-files .body {
    display: flex; /* Add flexbox */
    justify-content: center; /* Center horizontally */
    align-items: center; /* Center vertically */
    text-align: center;
    padding: 20px 0;
    padding-bottom: 10px;
}

.upload-files .body.hidden {
    display: none;
}

.upload-files .body input {
    visibility: hidden;
}

.upload-files .body p {
    font-size: 25px;
    padding-top: 15px;
    line-height: 1.4;
}

.upload-files .body p b,
.upload-files .body p a {
    color: #4db6ac;
}

.upload-files .body.active {
    border: dashed 2px #4db6ac;
}

.upload-files .body.active i {
    box-shadow: 0 0 0 -3px #fff, 0 0 0 lightgray, 0 0 0 -3px #fff, 0 0 0 lightgray;
    animation: file 0.5s ease both;
}

/* 나머지 스타일들은 이곳에 추가하세요 */

/* 애니메이션 키 프레임들 */
@keyframes fadeup {
    to {
        transform: translateY(0);
        opacity: 1;
    }
}

@keyframes faderight {
    to {
        transform: translateX(0);
        opacity: 1;
    }
}

@keyframes file {
    50% {
        box-shadow: -8px 8px 0 -3px #fff, -8px 8px 0 lightgray, -8px 8px 0 -3px #fff, -8px 8px 0 lightgray;
    }
    75%, 100% {
        box-shadow: -8px 8px 0 -3px #fff, -8px 8px 0 lightgray, -16px 16px 0 -3px #fff, -16px 16px 0 lightgray;
    }
}

.importar {
    background-color: #9893EA;
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin-bottom: 10px;
}

.importar:hover {
    background-color: #303080;
}
.result-box {
  margin-top: 20px;
}

.result {
  display: flex;
  flex-wrap: wrap;
}

.box {
  width: calc(33.33% - 20px);
  margin-right: 20px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
}
.container {
  display: flex;
  justify-content: space-between;
  width: 70%;
}

.upload,
.result {
  width: calc(50% - 10px);
}
.footer-text{
  font-size: 14px; /* 원하는 작은 글꼴 크기 */
  color: #000; /* 검은색 */
}

</style>

