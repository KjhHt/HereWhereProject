<template>
  <div>
    <header class="header" :class="{ 'header-fixed': isHeaderFixed }" v-if="$route.name !== 'location'">
      <!-- 헤더 내용 -->
      <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    </header>
    <div class="map-container">

      <!-- 검색창 -->
      <div class="search-container">
          <input v-model="searchQuery" placeholder="Here Where검색" @input="handleSearchInput" />
          <button @click="왼쪽모달창 = true" class="search-button">
              <i class="fas fa-search"></i>         
          </button>
          <button v-if="왼쪽모달창" @click="왼쪽모달창 = false" class="search-button">
              <i class="fas fa-times"></i>
          </button>
      </div>

      <!-- 검색창 옆의 버튼 -->
      <div>
        <button class="accommodation-button" @click="모달창 = true"><i class="fas fa-bed"></i> 호텔</button>
        <button class="restaurant-button" @click="모달창 = true"><i class="fas fa-utensils"></i> 맛집</button>	
        <button class="landmark-button" @click="모달창 = true"><i class="fas fa-place-of-worship"></i> 명소</button>
      </div>

      <!-- 오른쪽 모달창 -->
      <div class="modal-container" v-if="모달창 == true" :class="{ 'modal-open': 모달창 }">
        <button><i class="fas fa-bed"></i>  호텔</button>
        <button><i class="fas fa-utensils"></i>  맛집</button>
        <button><i class="fas fa-place-of-worship"></i>  명소</button>       
      </div>

      <!--오른쪽 모달창 닫는 버튼-->
      <button class="side-close-button" v-if="모달창 == true" @click="모달창 = false">
          <i class="fas fa-angle-right"></i>
      </button>

      <!--오른쪽 모달창 여는 버튼-->
      <button class="side-open-button" v-if="모달창 == false" @click="모달창 = true">
          <i class="fas fa-angle-left"></i>
      </button>

      <!-- 왼쪽 모달창 -->
      <div class="leftmodal-container" v-if="왼쪽모달창 == true"></div>

      <!-- 구글맵 api -->
      <GoogleMap :api-key="apikey" style="width: 100%; height: calc(100vh - 80px)" :center="center" :zoom="15" :options="mapOptions" @map-initialized="onMapInitialized">
        <Marker :options="{ position: center }">
          <InfoWindow>
            <!-- InfoWindow 내용 -->
          </InfoWindow>
        </Marker>
      </GoogleMap>

      <SearchModal />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { GoogleMap, Marker, InfoWindow } from 'vue3-google-map';

const apikey = 'AIzaSyA16otojrK96bOUH8jhs4Fp4gC3Glt2xrY';
const center = ref({ lat: 37.4727853, lng: 127.1418984 });
const searchQuery = ref('');
const 모달창 = ref(false);
const 왼쪽모달창 = ref(false);
</script>


<style scoped>

.map-container {
  position: relative;
}
.search-container {
  position: absolute;
  top: 10px;
  left: 10px;
  z-index: 1001;
  background-color: white;
  padding: 10px;
  border-radius: 30px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
.search-container input {
  width: 290px;
  height: 30px;
  padding: 5px;
  margin-right: 5px;
  border: 1px solid white; /* Change the border color to white */
  border-radius: 5px;
}

.search-button {
  padding: 5px;
  background-color: white;
  color: #000; /* Set the color to black */
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.modal-container {
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 1000;
  background-color: white;
  padding: 10px;
  border-radius: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  bottom: 5px;
  width: 330px; /* 가로 길이를 늘림 */
  display: flex; /* Flex container로 설정 */
  flex-direction: row; /* 가로로 나란히 배열하도록 설정 */
  gap: 10px; /* 각 버튼 사이의 간격 설정 */
}

.modal-container button {
  flex: 1; /* 버튼이 나눠서 영역을 차지하도록 설정 */
  padding: 8px 0;
  background-color: hsl(200, 80%, 95%); /* 아주 밝은 하늘색으로 변경 */
  border: none;
  border-radius: 10px;
  cursor: pointer;
  height: 50px; /* 세로 길이를 줄임 */
  color: black;
  font-weight: bold;
}

.leftmodal-container {
  position: absolute;
  top: 10px;
  left: 10px;
  z-index: 1000;
  background-color: white;
  padding: 10px;
  border-radius: 30px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  bottom: 5px;
  width: 362px; /* 가로 길이를 늘림 */
  display: flex; /* Flex container로 설정 */
  flex-direction: row; /* 가로로 나란히 배열하도록 설정 */
  gap: 10px; /* 각 버튼 사이의 간격 설정 */
}
.side-close-button {
  position: absolute;
  top: 320px;
  right: 340px; /* 원하는 위치로 조절하세요 */
  z-index: 1001; /* z-index 값을 더 큰 값으로 설정 */
  padding: 20px 3px; /* 조절 가능한 패딩 값으로 변경 */
  background-color: white;
  color: black;
  border-radius: 7px 0 0 7px; /* 직사각형의 경계를 조절하는 값으로 변경 */
  cursor: pointer;
  font-weight: bold;
  
}
.side-open-button {
  position: absolute;
  top: 320px;
  right: 0px; /* 원하는 위치로 조절하세요 */
  z-index: 1001; /* z-index 값을 더 큰 값으로 설정 */
  padding: 20px 3px; /* 조절 가능한 패딩 값으로 변경 */
  background-color: white;
  color: black;
  border-radius: 7px 0 0 7px; /* 직사각형의 경계를 조절하는 값으로 변경 */
  cursor: pointer;
  font-weight: bold;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.accommodation-button {
  position: absolute;
  top: 18px;
  left: 385px; /* 원하는 위치로 조절하세요 */
  z-index: 1001; /* z-index 값을 더 큰 값으로 설정 */
  padding: 6px 15px;
  background-color: white;
  color: black;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-weight: bold;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.restaurant-button {
  position: absolute;
  top: 18px;
  left: 480px; /* 원하는 위치로 조절하세요 */
  z-index: 1001; /* z-index 값을 더 큰 값으로 설정 */
  padding: 6px 15px;
  background-color: white;
  color: black;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-weight: bold;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  
}
.landmark-button {
  position: absolute;
  top: 18px;
  left: 568px; /* 원하는 위치로 조절하세요 */
  z-index: 1001; /* z-index 값을 더 큰 값으로 설정 */
  padding: 6px 15px;
  background-color: white; /* 배경을 흰색으로 설정 */
  color: black; /* 글자를 검은색으로 설정 */
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-weight: bold;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

</style>
