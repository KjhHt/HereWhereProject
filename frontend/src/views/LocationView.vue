<template>
  <div>
    <!-- 헤더 내용 -->
      <!-- <header class="header" :class="{ 'header-fixed': isHeaderFixed }" v-if="$route.name !== 'location'">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
      </header> -->
      <div class="map-container">
        <div class="custom-hamburger-menu">
          <Hamburgermenu :showPlan="showPlan"/>
        </div>

        <!--검색 오프-->
      <div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel" style="width: 430px;  height: calc(100vh - 81px); bottom: 0; top: auto; border: none;">
        <div class="offcanvas-header">
          <h5 class="offcanvas-title" id="offcanvasScrollingLabel"></h5>
          <!-- 2.21일 수정 -->
          <div>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"  ref="leftOffButton"><i class="bi bi-chevron-double-left"></i></button> 
          </div>
        </div>
        <div class="offcanvas-body">
          <div class="mt-3">
              <SearchCard v-for="search in searchList" :key="search.name" :search="search" @searchCard="searchClick" />
          </div>
          <div class="search-container">
            <input placeholder="Here Where검색, 이미지검색" ref="searchRef"/>
            <button type="button" class="search-button">
                <i class="fas fa-search"></i>
            </button>
            <SearchImage @searchLocation="searchLocation"/>
          </div>
          <div>
            <Weather :weather="weatherData"/>
            
          </div>
        <div class="youtubeseach-box">
          <h5 v-if="youtubeData.length>0" class="youtubesearch"><i class="bi bi-youtube"></i> YouTube</h5>
          <div id="youtubeCarousel" class="carousel slide" data-bs-ride="carousel" data-bs-interval="2000">
            <div class="carousel-inner">
              <div v-for="(youtube, index) in youtubeData" :key="youtube.id" :class="{ 'carousel-item': true, 'active': index === 0 }">
                <YoutubeCard :youtube="youtube" />
              </div>
            </div>
            <button
              class="carousel-control-prev"
              type="button"
              data-bs-target="#youtubeCarousel"
              data-bs-slide="prev"
              v-if="youtubeData.length > 0"
            >
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button
              class="carousel-control-next"
              type="button"
              data-bs-target="#youtubeCarousel"
              data-bs-slide="next"
              v-if="youtubeData.length > 0"
            >
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
            </div>
          </div>
          <News :news="newsData"/>
        </div>    
      </div>

    <!--여행플랜 오프-->
    <div class="offcanvas offcanvas-start"
          data-bs-scroll="true"
          data-bs-backdrop="false"
          tabindex="-1"
          id="offcanvasPlan"
          aria-labelledby="offcanvasPlan"
          style="width: 410px; height: calc(100vh - 81px); bottom: 0; top: auto; border: none;">
      <!-- 2.21일 수정 -->
      <div class="offcanvas-header d-flex justify-content-between align-items-center" style="height: 50px;">
            <div>
              <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="hideDetail"><i class="bi bi-chevron-double-left"></i></button> 
            </div>
            <h5 style="margin-top: 11px; margin-left: -13px;">Here Where Travel Plan</h5>
            <div style="width: same-as-button;"></div>
          </div>
      <div class="offcanvas-body">
        <PlanCard v-for="plan in plansInfo" :key="plan.id" :plan="plan" @passArrival="arrivalRoute"/>
      </div>
    </div>
    <!--음식점 오프-->
    <div class="offcanvas offcanvas-start"
          data-bs-scroll="true"
          data-bs-backdrop="false"
          tabindex="-1"
          id="offcanvasRestaurant"
          aria-labelledby="offcanvasRestaurantLabel"
          style="width: 410px; height: calc(100vh - 81px); bottom: 0; top: auto; border: none;">
          <!-- 2.21일 수정 -->
          <div class="offcanvas-header d-flex justify-content-between align-items-center" style="height: 50px;">
            <div>
              <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="hideDetail"><i class="bi bi-chevron-double-left"></i></button> 
            </div>
            <h5 style="margin-top: 11px; margin-left: -13px;">Here Where Restaurant List</h5>
            <div style="width: same-as-button;"></div>
          </div>
        <div class="offcanvas-body">
          <LoadingOverlay :loading="loadingrestaurant" />
          <RestaurantCard v-for="restaurant in restaurantsInfo" :key="restaurant.id" :restaurant="restaurant"/>
        </div>
      </div>
      <!--호텔 오프-->
      <div class="offcanvas offcanvas-start"
          data-bs-scroll="true"
          data-bs-backdrop="false"
          tabindex="-1"
          id="offcanvasHotel"
          aria-labelledby="offcanvasHotelLabel"
          style="width: 410px;  height: calc(100vh - 81px); bottom: 0; top: auto; border: none;">
          <!-- 2.21일 수정 -->
          <div class="offcanvas-header d-flex justify-content-between align-items-center" style="height: 50px;">
            <div>
              <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="hideDetail"><i class="bi bi-chevron-double-left"></i></button> 
            </div>
            <h5 style="margin-top: 11px; margin-left: -13px;">Here Where Hotel List</h5>
            <div style="width: same-as-button;"></div>
          </div>
        <div class="offcanvas-body">
          <HotelDate :places="places" @search-event="handleSearchEvent"/>
          <LoadingOverlay :loading="loadinghotel" />
          <LoadingOverlay :loading="loadingdetail" />
          <HotelCard v-for="hotel in hotelsInfo" :key="hotel.id" :hotel="hotel"/>      
        </div>
      </div>
      
      <!--관광지 오프-->
      <div class="offcanvas offcanvas-start"
          data-bs-scroll="true"
          data-bs-backdrop="false"
          tabindex="-1"
          id="offcanvasAttraction"
          aria-labelledby="offcanvasAttractionLabel"
          style="width: 410px;  height: calc(100vh - 81px); bottom: 0; top: auto; border: none;">
          <!-- 2.21일 수정 -->
          <div class="offcanvas-header d-flex justify-content-between align-items-center" style="height: 50px;">
            <div>
              <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="hideDetail"><i class="bi bi-chevron-double-left"></i></button> 
            </div>
            <h5 style="margin-top: 11px; margin-left: -13px;">Here Where Attraction List</h5>
            <div style="width: same-as-button;"></div>
          </div>
        <div class="offcanvas-body">
          <LoadingOverlay :loading="loadingattraction" />
          <AttractionCard v-for="attraction in attractionsInfo" :key="attraction" :attraction="attraction"/>
        </div>
      </div>

      <!--경로 오프-->
      <div :class="{ 'offcanvas offcanvas-bottom': true, 'show': showRoute }" tabindex="-1" id="offcanvasBottom" aria-labelledby="offcanvasBottomLabel" style="height:40vh">
        <div class="offcanvas-header">
          <h5 class="offcanvas-title" id="offcanvasBottomLabel">경로지정</h5>
          <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="routeToggle"></button>
        </div>
        <div class="offcanvas-body small">
          <div class="row mb-3">
            <label for="departures" class="col-sm-2 col-form-label">출발지</label>
            <div class="col-sm-6">
              <input type="text" class="form-control" id="departures" ref="departuresRef" placeholder="출발지를 선택하거나 지도를 클릭하세요" @input="e=>routeHandler(e,arrivalsRef)">
            </div>
          </div>
          <div class="row mb-3">
            <label for="arrivals" class="col-sm-2 col-form-label">도착지</label>
            <div class="col-sm-6">
              <input type="text" class="form-control" id="arrivals" ref="arrivalsRef" placeholder="목적지를 선택하세요">
            </div>
          </div>
          <a href="#" class="btn btn-primary" @click="setDraw">검색</a>
        </div>
      </div>
    
    <GoogleMap 
      :api-key="apiKey" 
      :center="mapOptions.center"
      :zoom="mapOptions.zoom"
      :tilt="mapOptions.tilt"
      :map-id="mapOptions.mapId"
      :disable-default-ui="mapOptions.disableDefaultUI"
      ref="mapRef"
      style="width:100vw; height:calc(100vh - 86px);"
      @click="e=>clickHandler(e)"
      >
      <DrawDirection :coords="routeCoords" @cameraSend="cameraCenter" />
      
        <CustomMarker :options="{ position: mapOptions.center, anchorPoint: 'BOTTOM_CENTER' }">
            <div style="text-align: center">
                <!-- <div style="font-size: 1.125rem">id</div> -->
                <img 
                    id="client-image" 
                    class="rounded-circle" 
                    :src="profileImage" 
                    width="40" height="40" style="margin-top: 8px" 
                    @load="customMarkerLoaded"
                    ref="customMarkerRef"/>
            </div>
        </CustomMarker>
        <InfoWindow
            :options="windowOptions"
            :model-value="infoWindow"       
            ref="infoRef">
            <div class="card" style="width: 18rem; height: 24rem;">
              <img :src="locationInfo.placeImage" class="card-img-top img-fluid" :alt=locationInfo.placeName>
              <div class="card-body">
                <h5 class="card-title">{{locationInfo.placeName}}</h5>
                <p class="card-text">{{ locationInfo.placeAddress }}</p>
                <p class="card-text">{{ locationInfo.placeRating }}</p>
                <a href="#" class="btn btn-primary" @click="setPlansInfo">Pick</a>
              </div>
            </div>
        </InfoWindow>
        <MarkerCluster>
          <!--인포윈도우 마커표시<MapMarker v-if="targetLocation" :options="{position:targetLocation}" @click="selectMarker"/>-->
          <CustomMarker v-for="plan in plansInfo" :key="plan.name" 
                        :options="{ position: {lat:plan.geometry.location.lat(),
                                               lng:plan.geometry.location.lng() }}"
                        @click="()=>searchLocation(plan)" >
            <div style="text-align: center">
                <img 
                    class="rounded-circle" 
                    src="@/assets/Cat.png" 
                    width="40" height="40" style="margin-bottom: 40px" 
                    />
            </div>
          </CustomMarker>
          <CustomMarker v-for="hotel in hotelsInfo" :key="hotel.hotel"  :options="{ position: {lat:hotel.lat,lng:hotel.lng} }">
            <div style="text-align: center">
                <img 
                    class="rounded-circle" 
                    src="@/assets/hotel.png" 
                    width="40" height="40" style="margin-top: 8px" 
                    />
            </div>
        </CustomMarker>
        </MarkerCluster>
      </GoogleMap>
    <StreetView :map="mapRef" :style="{right:streetViewRight}"/>
  </div>
</div>
</template>
    
  <script setup>
  import {
      GoogleMap,
      //Marker as MapMarker,
      MarkerCluster,
      InfoWindow,
      CustomMarker,
  } from "vue3-google-map";
  import { ThreeJSOverlayView } from '@googlemaps/three';
  // import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
  import * as THREE from 'three';
  import { ref, watch, computed, onMounted, defineProps } from "vue";
  // import { useRoute } from 'vue-router';
  import { geoLocation } from '@/composable/geoLocation'
  import gsap from 'gsap'
  import axios from "axios";
  import StreetView from '@/components/search/StreetView.vue'
  import SearchImage from '@/components/search/SearchImage.vue'
  import RestaurantCard from '@/components/search/RestaurantCard.vue'
  import AttractionCard from '@/components/search/AttractionCard.vue';
  import Hamburgermenu from '@/components/search/HamburgerMenu.vue'
  import DrawDirection from "@/components/search/DrawDirection.vue"; //경로(polyline) 그리기
  import { createObserver,routeHandler,getDistanceInKm } from "@/composable/custom"; //검색기능의 AutoComplete CSS속성을 동적으로 구현 js, 경로검색 placeholder토글
  import PlanCard from "@/components/search/PlanCard.vue";
  import HotelCard from '@/components/search/HotelCard.vue'
  import HotelDate from '@/components/search/HotelDate.vue';
  import SearchCard from '@/components/search/SearchCard.vue'
  import YoutubeCard from "@/components/search/YoutubeCard.vue";
  import Weather from "@/components/search/WeatherCard.vue";
  import News from "@/components/search/NewsCard.vue";
  import LoadingOverlay from '@/components/search/LoadingModal.vue';
  
  const streetViewRight=ref('20px')
  let showRoute= ref(false);
  let arrivals= ref([]);
  const departuresRef= ref(null);
  const arrivalsRef= ref(null);
  const planRef= ref(false);
  const showPlan= ref(false);
  const leftOffButton= ref(null);
  const routeCoords=ref([]);
  let searchList= ref([]);
  const weatherData=ref([]);
  const newsData=ref([]);

  const locationInfo=ref({
    placeName:'',
    placeAddress:'',
    placeRating:0,
    placeImage:''
  })
  const profileImage = ref('');
  //20일 추가
  const props = defineProps({
    locationValue: Object,
  });

  // 22일 나중에 수정
  onMounted(()=>{
    searchRef.value.value=props.locationValue
    profileImage.value=localStorage.getItem('profileImage');
    createObserver();
  });

  function updateInfoWindow(places){
    let photoUrl;
    console.log('photos',places.photos) //검색 결과 이미지 배열

    if (places.photos && places.photos.length > 0) {
      const photo = places.photos[0]; 
      const photoReference = photo.photo_reference;
    
      if (photoReference) {
        photoUrl = `https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${photoReference}&key=${apiKey}`;
      } else if (photo.getUrl) {
        photoUrl = photo.getUrl({maxWidth: 400});
      } else {
        photoUrl = 'place_default.png'; // 기본 이미지 URL을 설정하세요
      }
    } else {
      photoUrl = 'place_default.png'; // 기본 이미지 URL을 설정하세요
    }
    
      locationInfo.value.placeName=places.name
      locationInfo.value.placeAddress=places['formatted_address']
      locationInfo.value.placeRating=places.rating
      locationInfo.value.placeImage=photoUrl
  }

  const hotelsInfo=ref([])
  const restaurantsInfo=ref([])
  const attractionsInfo=ref([])
  const plansInfo= ref([])
  const youtubeData=ref([])
  const loadinghotel = ref(false);
  const loadingrestaurant = ref(false);
  const loadingattraction = ref(false);
  const loadingdetail = ref(false)
  

  function setPlansInfo(){
    showPlan.value= false;
    if(plansInfo.value.length !== 0){
      for(let i=0; i<plansInfo.value.length; i++){
        if(plansInfo.value[i].name === places.name) return;
      }
    }
    plansInfo.value.push(places)
    infoRef.value.close();
    showPlan.value= !showPlan.value;
  }
  //22일 로딩창 구현 수정
  // async function getYoutubeData(address){
  //   const response= await axios.get(process.env.VUE_APP_PYTHON_API_URL+'/youtube',{params:{address}})
  //   youtubeData.value=response.data
  // }

  async function getNearbyHotels(lat, lng, number, check_in, check_out) {
    try {
      loadinghotel.value = true;
      hotelsInfo.value = []
      const response = await axios.get(process.env.VUE_APP_PYTHON_API_URL + '/booking', { params: { lat, lng, number, check_in, check_out } });
      hotelsInfo.value = response.data;
    } catch (error) {
      console.error("An error occurred while fetching hotel data:", error);
    } finally {
      loadinghotel.value = false;
    }
  }

  async function getNearbyHotelsdetail(lat, lng, number, check_in, check_out) {
      const response = await axios.get(process.env.VUE_APP_PYTHON_API_URL + '/booking', { params: { lat, lng, number, check_in, check_out } });
      hotelsInfo.value = response.data;
  }

async function getNearbyRestaurants(lat, lng) {
  try {
    loadingrestaurant.value = true;
    restaurantsInfo.value = []
    const response = await axios.get(process.env.VUE_APP_PYTHON_API_URL + '/restaurant', { params: { lat, lng } });
    restaurantsInfo.value = response.data;
  } catch (error) {
    console.error("An error occurred while fetching restaurant data:", error);
  } finally {
    loadingrestaurant.value = false;
  }
}

async function getNearbyAttractions(lat, lng) {
  try {
    loadingattraction.value = true;
    attractionsInfo.value = []
    const response = await axios.get(process.env.VUE_APP_PYTHON_API_URL + '/attraction', { params: { lat, lng } });
    attractionsInfo.value = response.data;
  } catch (error) {
    console.error("An error occurred while fetching attraction data:", error);
  } finally {
    loadingattraction.value = false;
  }
}

async function getWeather(lat,lng){
  const response= await axios.get(process.env.VUE_APP_PYTHON_API_URL+'/weather',{params:{lat,lng}})
  weatherData.value=response.data
}

async function getNews(lat,lng){
  const response= await axios.get(process.env.VUE_APP_PYTHON_API_URL+'/news',{params:{lat,lng}})
  newsData.value=response.data
  console.log(newsData.value)
}
  
  function searchLocation(places){
    const location=places.geometry.location
    getNearbyRestaurants(places.geometry.location.lat(),places.geometry.location.lng())
    getNearbyAttractions(places.geometry.location.lat(),places.geometry.location.lng())
    getNearbyHotels(places.geometry.location.lat(),places.geometry.location.lng(),2,"2024-04-01","2024-04-02")
    // getYoutubeData(places.name)
    moveToPosition(location)
    updateInfoWindow(places)
    getWeather(places.geometry.location.lat(),places.geometry.location.lng())
    getNews(places.geometry.location.lat(),places.geometry.location.lng())
  }
    
  const customMarkerRef=ref(null);
  function customMarkerLoaded(){
     if(customMarkerRef.value){
      gsap.to(customMarkerRef.value, {
          duration: 1, // 애니메이션 지속 시간
          yoyo: true, // 애니메이션을 역방향으로 반복
          repeat: -1, // 무한 반복
          ease: "power1.inOut", // 이징 효과
          y: "-=20", // y축으로 20px 만큼 이동
      });
     }
  }

  const {coords} = geoLocation()
  const currPos = computed(()=>({
      lat: parseFloat(coords.value.latitude),
      lng: parseFloat(coords.value.longitude)
  }))

  const apiKey=process.env.VUE_APP_GOOGLE_API_KEY; /* 여기에 해당 apikey설정할 것!!!! */
  
  const mapOptions=ref({
      tilt: 67.5,
      heading: 90,
      zoom: 17,
      center: currPos,
      mapId: 'c1b04704f8464294',
      disableDefaultUI: true,
      gestureHandling: 'auto',
      keyboardShortcuts: false,
      clickableIcons: false,
      
    });
  const locations = ref([]);

  //검색한 목적지 좌표
  const targetLocation=ref(null)
  
  
  const infoRef=ref(null);
  const mapRef=ref(null);
  const infoWindow=ref(false);
  const windowOptions=ref({});
  const searchRef=ref(null);

  let map;
  let overlay=null;
  let scene,renderer,camera;
  watch(()=>mapRef.value?.ready,(ready)=>{
      if(!ready) return;
      map=mapRef.value.map;
      initMap(map);
  });
  
  watch(()=>infoRef.value?.ready,(ready)=>{
      if(!ready) return;
      infoRef.value.infoWindow.addListener('closeclick',()=>{
          console.log('closeclick')
      })
  });  
  
  const selectedMarkerIndex=ref(null);
    
  let targetZoom = 18; // 최종적으로 도달하고자 하는 zoom 수준
  let targetHeading = 160; // 최종적으로 도달하고자 하는 헤딩 값
  let targetTilt = 47.5; // 최종적으로 도달하고자 하는 틸트 값
  let savedPosition;//,startPosition,startTime=null;
  let currentZoom,currentHeading,currentTilt;
  // let duration=3000;
   
  function animateMap() {
    if (!savedPosition) return;
  
    const currentPosition = { lat: map.getCenter().lat(), lng: map.getCenter().lng() };
    const diffLat = Math.abs(currentPosition.lat - savedPosition.lat);
    const diffLng = Math.abs(currentPosition.lng - savedPosition.lng);
    // const currentDistance = calculateDistance(currentPosition, savedPosition);
  
    // 이동, 회전, 틸트 속도 조절 (값은 예시로, 상황에 맞게 조절)
    const moveSpeed = 0.02;
    const tiltSpeed = 0.03;
    const headingSpeed = 0.03;
    const zoomSpeed = 0.03;
  
    // 현재 줌, 헤딩, 틸트 값 업데이트
    currentZoom = map.getZoom() + (targetZoom - map.getZoom()) * zoomSpeed;
    currentHeading = map.getHeading() + (targetHeading - map.getHeading()) * headingSpeed;
    currentTilt = map.getTilt() + (targetTilt - map.getTilt()) * tiltSpeed;
  
    // 위치 업데이트
    const newLat = currentPosition.lat + (savedPosition.lat - currentPosition.lat) * moveSpeed;
    const newLng = currentPosition.lng + (savedPosition.lng - currentPosition.lng) * moveSpeed;
  
    map.setCenter({ lat: newLat, lng: newLng });
  
    // 카메라 이동
    map.moveCamera({
      zoom: currentZoom,
      heading: currentHeading,
      tilt: currentTilt
    });
  
    // 모든 조건이 충족될 때까지 재귀 호출
    if (diffLat > 0.0001 || diffLng > 0.0001 || Math.abs(currentZoom - targetZoom) > 0.1 || Math.abs(currentHeading - targetHeading) > 1 || Math.abs(currentTilt - targetTilt) > 1) {
      requestAnimationFrame(animateMap);
    } else {
      // 애니메이션 완료 후 인포 윈도우 위치 업데이트
      windowOptions.value.position = { lat: savedPosition.lat-0.00025, lng: savedPosition.lng+0.00012 };
      console.log('인포윈도우',infoRef.value)
      infoRef.value.infoWindow.setPosition(windowOptions.value.position);
      infoRef.value.infoWindow.open(mapRef.value.map);
    }
  }
  
  function getPositionValue(value) {
    if (typeof value === 'function') {
      // value가 함수인 경우, 함수를 호출합니다.
      return value();
    } else {
      // value가 함수가 아닌 경우, 직접적인 값을 반환합니다.
      return value;
    }
  }
  
  function moveToPosition(targetPosition){
    savedPosition = {
      lat: parseFloat(getPositionValue(targetPosition.lat)),
      lng: parseFloat(getPositionValue(targetPosition.lng))
    };
    targetLocation.value=savedPosition
  
    animateMap()

  }
  
  const geocoder=ref(null)

  // eslint-disable-next-line no-unused-vars
  async function calculateNearestPoints(lat,lng) {
    const delta = 0.01; // 좌표를 변경할 간격 (약 1km)
    const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]]; // 북, 동, 남, 서 방향
    const originalLatLng = new mapRef.value.api.LatLng(lat, lng);
    const points = [];
  
    // 원래 위치와 주변 위치들을 포인트 배열에 추가
    points.push(`${originalLatLng.lat()},${originalLatLng.lng()}`);
    directions.forEach(dir => {
      const newLat = originalLatLng.lat() + dir[0] * delta;
      const newLng = originalLatLng.lng() + dir[1] * delta;
      points.push(`${newLat},${newLng}`);
    });
  
    return points
  }
  
  // InfoWindow의 위치를 업데이트하는 함수
  function updateInfoWindowPosition(targetPosition) {
    // 현재 지도의 heading 값을 가져옵니다.
    // eslint-disable-next-line no-unused-vars
    const currentHeading = map.getHeading();
  
    // heading에 따라 InfoWindow의 위치를 조정하는 로직
    // 예: heading 값에 따라 위도(latitude)나 경도(longitude)를 약간 조정
    const adjustedLat = targetPosition.lat(); // 조정된 위도
    const adjustedLng = targetPosition.lng(); // 조정된 경도
  
    // InfoWindow의 위치를 업데이트합니다.
    windowOptions.value.position = { lat: adjustedLat, lng: adjustedLng };
    infoRef.value.infoWindow.setPosition(windowOptions.value.position);
  }
  
  // 지도의 heading 값이 변경될 때마다 InfoWindow의 위치를 업데이트합니다.
  // eslint-disable-next-line no-unused-vars
  watch(() => mapOptions.value.heading, (_newHeading) => {
    if (selectedMarkerIndex.value !== null) {
      const selectedMarker = locations.value[selectedMarkerIndex.value];
      updateInfoWindowPosition({ lat: selectedMarker.lat + 0.0005, lng: selectedMarker.lng });
    }
  });

  
  
  const matrixService=ref(null);
  const directionService=ref(null);
  const directionRenderer=ref(null);
  const placesService=ref(null);
  let places={}

  function initMap(googleMap) {
    console.log('mapref.value.api:',mapRef.value.api);
    const autoCompleteOptions={
      fields:['formatted_address','geometry','name','rating','photos'],
      strictBounds:false
    }
    
    matrixService.value=new mapRef.value.api.DistanceMatrixService()
    placesService.value=new mapRef.value.api.places.PlacesService(googleMap)
    directionService.value=new mapRef.value.api.DirectionsService()
    directionRenderer.value=new mapRef.value.api.DirectionsRenderer()
    directionRenderer.value.setMap(googleMap)
    geocoder.value=new mapRef.value.api.Geocoder()
    const autoComplete=new mapRef.value.api.places.Autocomplete(searchRef.value,autoCompleteOptions);
    new mapRef.value.api.places.Autocomplete(departuresRef.value,autoCompleteOptions);
    new mapRef.value.api.places.Autocomplete(arrivalsRef.value,autoCompleteOptions);
  
    // 지도의 경계(뷰포트) 속성을 자동 완성 개체에 바인딩합니다,
    // 자동 완료 요청이 현재 맵 경계를 사용하도록 설정합니다
    // 요청에 bounds 옵션이 있습니다.
    autoComplete.bindTo("bounds", map);
    console.log(autoComplete)
    autoComplete.addListener('place_changed',()=>{
      places=autoComplete.getPlace();
      console.log('플레이스',places)
      if(Object.keys(places).length > 1) {
        leftOffButton.value.click();
        searchLocation(places)
      }
      searchList.value= [];
      let request= {query: places.name};
      placesService.value.textSearch(request,(result,status)=>{
        if(status === 'OK') {
        searchList.value= result;
      }
      return;
      });
    }); 
    
    
    
  
    scene = new THREE.Scene();
    const ambientLight = new THREE.AmbientLight(0xffffff, 0.75);
    scene.add(ambientLight);
  
    const directionalLight = new THREE.DirectionalLight(0xffffff, 0.25);
    directionalLight.position.set(0, 10, 50);
    scene.add(directionalLight);
  
    camera = new THREE.PerspectiveCamera(90, window.innerWidth / window.innerHeight, 0.1, 1000);
  
    // 카메라 위치 설정
    camera.position.set(0, 10, 50); // 예시 좌표
    // 카메라 각도 설정
    camera.rotation.x = -Math.PI / 4; // X축 회전 (Pitch)
    camera.rotation.y = Math.PI / 4; // Y축 회전 (Yaw)
    camera.rotation.z = 0; // Z축 회전 (Roll)
  
    
    // ThreeJSOverlayView 설정
    overlay=new ThreeJSOverlayView({
      googleMap,
      scene,
      anchor: { ...mapOptions.value.center, altitude: 50 },
      THREE,
      camera,
    });
  
  
  
    overlay.onAdd=()=>{
      console.log('add')
    }
    
    overlay.onContextRestored=({gl})=>{
      renderer=new THREE.WebGLRenderer({
        canvas:gl.canvas,
        context:gl,
        ...gl.getContextAttributes()
      });
      renderer.autoClear=false;
    }
  
    // eslint-disable-next-line no-unused-vars
    overlay.onDraw = ({gl, transformer}) => {
    // Update camera matrix to ensure the model is georeferenced correctly on the map.
      const matrix = transformer.fromLatLngAltitude({
          lat: mapOptions.value.center.value.lat,
          lng: mapOptions.value.center.value.lng,
          altitude: 50,
      });
      camera.projectionMatrix = new THREE.Matrix4().fromArray(matrix);
      
      // Request a redraw and render the scene.
      overlay.requestRedraw();
      renderer.render(scene, camera);
  
      // Always reset the GL state.
      renderer.resetState();
    }

    //22일 매인 페이지에서 로케이션으로 값 및 검색 자동으로 추가 
    if (props.locationValue) {  
      console.log('플레이스',placesService.value)
      if(searchRef.value) placesService.value.textSearch({query:props.locationValue},(result,status)=>{
        if(status==='OK') places=result[0]
        console.log(places.geometry.location.lat())
        // const location = places.geometry.location
        getNearbyRestaurants(places.geometry.location.lat(),places.geometry.location.lng())
        getNearbyAttractions(places.geometry.location.lat(),places.geometry.location.lng())
        getNearbyHotels(places.geometry.location.lat(),places.geometry.location.lng(),2,"2024-04-01","2024-04-03")
        //getYoutubeData(places.name)
        map.setCenter({ lat: places.geometry.location.lat(), lng: places.geometry.location.lng() });
        // moveToPosition(location)
        updateInfoWindow(places)
        getWeather(places.geometry.location.lat(),places.geometry.location.lng())
        getNews(places.geometry.location.lat(),places.geometry.location.lng())        
      })
    }
  }

  /*경로 설정 */
  function routeToggle(){
    showRoute.value = false;
  }

  const arrivalRoute=arrival=>{
    let latLng= [coords.value.latitude,coords.value.longitude]
    console.log('전달받은 도착지:',arrival);
    departuresRef.value.value='';
    arrivalsRef.value.value=arrival.formatted_address
    planRef.value.click();
    arrivals.value.push([arrival.geometry.location.lat(), arrival.geometry.location.lng()]);
    arrivals.value.push(latLng);
    showRoute.value= true;
  }

  function setDraw(){
    routeCoords.value=[]
    geocoder.value.geocode({address: departuresRef.value.value})
    .then(response=>{
      if(response.results[0])
      routeCoords.value.push([response.results[0].geometry.location.lat(), response.results[0].geometry.location.lng()])
      geocoder.value.geocode({address: arrivalsRef.value.value})
      .then(result=>{
        if(result.results[0])
        routeCoords.value.push([result.results[0].geometry.location.lat(), result.results[0].geometry.location.lng()])
        routeToggle()
      }).catch(error=>console.log(error))
    }).catch(error=>console.log(error))
  }

  const cameraCenter=(data)=>{
    let distance= getDistanceInKm(data[0].lat,data[0].lng,data[data.length-1].lat,data[data.length-1].lng);
    console.log('디스탄스',distance);
    if(distance<100) map.moveCamera({zoom:12})
    mapRef.value.map.setCenter(data[parseInt(data.length/2)]);
  }

  const searchClick= searchInfo=> {
    leftOffButton.value.click();
    places= searchInfo.value;
    searchLocation(places);
  }

  const clickHandler=(e)=>{
    e.stop();
    console.log('클릭이벤트:',e)
    if(e.placeId!==undefined && showRoute.value!==true){
      let service= new mapRef.value.api.places.PlacesService(mapRef.value.map);
      let request= {
        placeId:e.placeId
      }
      service.getDetails(request, (place, status)=>{
        if(status !== 'OK') {console.log('스테이터스',status);return;}
        showPlan.value = false;
        var closeInfo= document.querySelector('.gm-ui-hover-effect');
              
        if(closeInfo !== null) {
          closeInfo.click();
        }
        let closeButton= document.querySelectorAll('.btn-close');
        if(closeButton.forEach(x=>{
          x.click();
        }))
        console.log('클로즈버튼',closeButton);

        places= place;
        searchLocation(place);
      });
    }
    if(showRoute.value === false) return
    if(departuresRef.value.value===''){
      geocoder.value.geocode({location:e.latLng})
      .then(result=>departuresRef.value.value= result.results[0].formatted_address)
    }else if(departuresRef.value.value!=='' && arrivalsRef.value.value !== '') return
    else geocoder.value.geocode({location:e.latLng})
      .then(result=>arrivalsRef.value.value= result.results[0].formatted_address)
  }
  //21일 추가 호텔 검색 로딩
  const handleSearchEvent = (searchData) => {
    loadingdetail.value = true;
    hotelsInfo.value = []; 
    getNearbyHotelsdetail(searchData.lat, searchData.lng, searchData.guests, searchData.checkInDate, searchData.checkOutDate)
    .then(() => {
      loadingdetail.value = false;
    });
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
    border: 2px solid black;
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
  .custom-hamburger-menu{
    position: absolute;
    top: 10px;
    left: 0px; /* 원하는 위치로 조절하세요 */
    z-index: 1001; /* z-index 값을 더 큰 값으로 설정 */
    
    width: 65px;
    height: 50px;
  }
  .btn-close {
    background: none;
  }
  .pac-container {
    z-index: 9999px;
    top: 50px;
    left: 100px;
  }
  .carousel-control-prev,
.carousel-control-next {
  background-color: #000; /* 배경색 */
  border-radius: 50%; /* 모서리 둥글게 */
  width: 50px; /* 너비 */
  height: 50px; /* 높이 */
  opacity: 0.6; /* 투명도 */
  margin-top: 70px;
}
.carousel-control-prev-icon,
.carousel-control-next-icon {
  display: inline-block;
  width: 20px; /* 아이콘 너비 */
  height: 20px; /* 아이콘 높이 */
  background: no-repeat 50%/100% 100%;
}
.carousel-control-prev-icon {
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='%23fff' viewBox='0 0 16 16'%3e%3cpath d='M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z'%3e%3c/path%3e%3c/svg%3e");
}
.carousel-control-next-icon {
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='%23fff' viewBox='0 0 16 16'%3e%3cpath d='M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z'%3e%3c/path%3e%3c/svg%3e");
}
.youtubesearch {
  color: red; /* 글자 색상 */
  font-size: 24px; /* 글자 크기 */
  font-family: 'Arial', sans-serif; /* 폰트 */
  text-align: center; /* 중앙 정렬 */
  margin-bottom: 20px; /* 아래쪽 마진 */
  text-align: center;
  margin-top: 20px;
  font-weight: bold;
}

  </style> 