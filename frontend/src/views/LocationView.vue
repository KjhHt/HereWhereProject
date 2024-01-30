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
              <i class="fa fa-image" style="margin-right: 10px;"></i>
              <i class="fas fa-search"></i>         
          </button>
          <button v-if="왼쪽모달창" @click="왼쪽모달창 = false" class="search-button">
              <i class="fas fa-times"></i>
          </button>
      </div>

      <!-- 검색창 옆의 버튼 -->
      <div>
        <button 
          class="accommodation-button" 
          :class="{ 'selected': 모달창 === 'accommodation' }"
          @click="toggleModal('accommodation')"
        >
        <i class="fas fa-bed"></i> 호텔
      </button>
        <button
          class="restaurant-button" 
          :class="{ 'selected': 모달창 === 'restaurant' }"
          @click="toggleModal('restaurant')"
        >
         <i class="fas fa-utensils"></i> 맛집
        </button> 
        <button 
        class="landmark-button" 
        :class="{ 'selected': 모달창 === 'landmark' }"
        @click="toggleModal('landmark')"
        >
        <i class="fas fa-place-of-worship"></i> 명소</button>
      </div>

      <!-- 오른쪽 모달창 -->
      <div class="modal-container" v-show="모달창">
        <button 
        :class="{ 'selected': 모달창 === 'accommodation' }"
        @click="toggleModal('accommodation')"
        >
        <i class="fas fa-bed"></i>  호텔</button>
        <button 
        :class="{ 'selected': 모달창 === 'restaurant' }"
        @click="toggleModal('restaurant')"
        >
        <i class="fas fa-utensils"></i>  맛집</button>
        <button 
        :class="{ 'selected': 모달창 === 'landmark' }"
        @click="toggleModal('landmark')"
        >
        <i class="fas fa-place-of-worship"></i>  명소</button>       
      </div>

      <!--오른쪽 모달창 닫는 버튼-->
      <button class="side-close-button" v-if="모달창" @click="모달창 = false">
          <i class="fas fa-angle-right"></i>
      </button>

      <!--오른쪽 모달창 여는 버튼-->
      <button class="side-open-button" v-if="모달창 == false" @click="모달창 = true">
          <i class="fas fa-angle-left"></i>
      </button>

      
      <!-- 왼쪽 모달창 -->
    <div class="leftmodal-container" v-if="왼쪽모달창 == true">
      <!-- 장소 정보를 나타낼 내용 -->
      <div class="place-info">
        <div class="place-image">
          <!-- 장소 이미지 -->
          <img :src="placeInfo.image" alt="Place Image">
        </div>
        <div class="place-details">
          <!-- 장소 이름 -->
          <h2>{{ placeInfo.name }}</h2>
          <!-- 장소 별점 -->
          <div class="rating">
            <span v-for="star in Array.from({ length: Math.floor(placeInfo.rating) })" :key="star">★</span>
          </div>
          <!-- 장소 주소 -->
          <p>{{ placeInfo.address }}</p>
        </div>

        <!-- 예약 폼 -->
        <div class="reservation-form">
          <h4 style="align-self: flex-start;">예약폼</h4>
          
          <div class="form-row">
            <label for="startDate">시작</label>
            <input
              type="date"
              id="startDate"
              v-model="reservation.startDate"
              :min="staticMinDate"
              @input="() => autoResizeTextArea('startDate')"
            />
          </div>

          <div class="form-row">
            <label for="endDate">종료</label>
            <input
              type="date"
              id="endDate"
              v-model="reservation.endDate"
              :min="reservation.startDate" 
              @input="() => autoResizeTextArea('endDate')"
            />
          </div>

          

          <div class="form-row">
            <label for="guests">인원</label>
            <button @click="decrementGuests">-</button>
            <span>{{ reservation.guests }}</span>
            <button @click="incrementGuests">+</button>
          </div>


          <div class="form-row">
            <label for="memo" class="label-memo">메모</label>
            <textarea
              id="memo"
              v-model="reservation.memo"
              @input="autoResizeTextArea"
              style="resize: none; overflow-y: auto; white-space: pre-wrap; "
            ></textarea>
          </div>

          <button @click="confirmReservation" class="search-button" style="align-self: flex-end;">검색</button>
        </div>
        
        <div class="scrollable-list">
          <div v-for="(place, index) in placesList" :key="index" class="list-item">
            {{ place.name }}
          </div>
        </div>

      </div>
    </div>
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

const staticMinDate = new Date().toISOString().split('T')[0]; // 오늘 날짜를 동적으로 설정

const apikey = 'AIzaSyA16otojrK96bOUH8jhs4Fp4gC3Glt2xrY';
const center = ref({ lat: 37.4727853, lng: 127.1418984 });
const searchQuery = ref('');
const 모달창 = ref(false);
const 왼쪽모달창 = ref(false);

// 장소 정보 (임시)
const placeInfo = ref({
  name: '장소 이름',
  image: 'https://via.placeholder.com/150', // 임의의 이미지 URL
  rating: 4.5, // 임의의 평점
  address: '장소 주소',
});


const toggleModal = (type) => {
  if (모달창.value === type) {
    모달창.value = false;
  } else {
    모달창.value = type;
  }
};

const reservation = ref({
  startDate: '',
  endDate: '',
  memo: '',
  guests: 1,
});


const placesList = ref([]);


const confirmReservation = () => {
  // 확인 버튼을 눌렀을 때 실행되는 로직
  placesList.value.push({ name: `호텔 ${placesList.value.length + 1}` });

  // 기존 입력값 초기화
  reservation.value.startDate = '';
  reservation.value.endDate = '';
  reservation.value.memo = '';
  reservation.value.guests = 1;
};

const autoResizeTextArea = () => {
  const textArea = document.getElementById('memo');
  textArea.style.height = 'auto';
  textArea.style.height = textArea.scrollHeight + 'px';
};

const incrementGuests = () =>{
  if (reservation.value.guests < 6) {
    reservation.value.guests++;
  }
};

const decrementGuests = () => {
  if (reservation.value.guests > 1) {
    reservation.value.guests--;
  }
};

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
  
  border-radius: 30px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  bottom: 5px;
  width: 362px; /* 가로 길이를 늘림 */
  display: flex; /* Flex container로 설정 */
  flex-direction: column; /* 세로로 나란히 배열하도록 변경 */
  gap: 10px; /* 각 버튼 사이의 간격 설정 */
  flex-grow: 1; /* 남은 공간을 균등하게 분배 */
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
.modal-container button.selected,
.accommodation-button.selected,
.restaurant-button.selected,
.landmark-button.selected {
  filter: invert(1); /* 선택된 버튼에 대한 흑백 반전 효과 추가 */
}

.place-info {
  display: flex;
  flex-direction: column; /* 세로로 나란히 배열하도록 변경 */
  height: 100%;
  border-radius: 30px;
  background-color: white;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.place-image {
  
  flex: 2; /* 이미지 영역이 더 많은 공간을 차지하도록 설정 */
  display: flex; /* 내부 컨테이너를 flex로 변경 */
  justify-content: center; /* 내부 컨테이너를 수평 중앙 정렬 */
  align-items: center; /* 내부 컨테이너를 수직 중앙 정렬 */ 
}
.place-image img {
  width: 100%; /* 이미지가 부모 요소에 꽉 차도록 변경 */
  max-height: 100%;
  object-fit: cover;
  border-radius: 8px;
}

.place-details {
  padding : 16px 24px 0; 
  flex: 1;
  display: flex;
  flex-direction: column; /* 세로로 나란히 배열하도록 변경 */
  align-items: flex-start; /* 왼쪽 정렬 설정 */
 
}

/* 구분선 스타일 */
.place-details::after {
  content: '';
  display: block;
  width: 100%; /* 가로로 꽉 차게 설정합니다. */
  height: 1px; /* 높이는 구분선의 두께입니다. 필요에 따라 조절하세요. */
  background-color: #ddd; /* 구분선의 색상을 설정하세요. */
  margin: 10px 0; /* 위아래 여백을 추가해 간격을 조절하세요. */
}

.rating {
  color: #f39c12;
}

.rating span {
  font-size: 20px;
  margin-right: 2px;
}


.reservation-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin : 16px;
  margin-bottom: 20px;
}

.form-row {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
}


.scrollable-list {
  height: 150px; /* 원하는 높이로 조절하세요 */
  overflow-y: auto;
  border: 1px solid #ddd; /* 스크롤 가능한 영역에 경계를 줄 수 있습니다. */
}

.list-item {
  border-bottom: 1px solid #ddd; /* 목록 항목 사이에 구분선을 추가합니다. */
  padding: 8px;
}

#startDate,
#endDate,
#time,
#memo {
  border: 2px solid black; /* 검은색 선 추가 */
  border-radius: 10px; /* 끝을 뭉특하게 만듦 */
  padding: 8px; /* 내부 여백 추가 */
  font-size: 16px; /* 글자 크기 설정 */
}

#memo {
  max-height: 50px;
  resize: none; /* 사용자가 수동으로 크기를 조절하지 못하도록 함 */
  overflow-y: auto; /* 초과된 내용을 숨기도록 함 */
  width: calc(100% - 20px); /* 왼쪽 모달창의 패딩을 고려하여 폭을 조절 */
}

.label-memo {
  white-space: nowrap; /* 줄바꿈 방지 */
}

.place-image {
  height: calc(850px * 1 / 6 ); /* 전체 높이의 1/6 */
}

.place-details,
.reservation-form,
.scrollable-list {
  height: calc(850px * 2 / 6); /* 전체 높이의 2/6 */
  flex-grow: 1; /* 이 섹션들 사이에서 남은 공간을 균등하게 분배 */
  
}
</style>