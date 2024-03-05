<template>
  <div>
    <!-- 헤더 내용 -->
      <!-- <header class="header" :class="{ 'header-fixed': isHeaderFixed }" v-if="$route.name !== 'location'">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
      </header> -->
      <div class="map-container">
        <div class="custom-hamburger-menu">
          <Hamburgermenu :showInter="showInter"/>
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
            <SearchImage @searchImgLocation="searchImgLocation" @clearPlaces="clearPlaces"/>
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

    <!--관심여행 오프-->
    <div class="offcanvas offcanvas-start"
          data-bs-scroll="true"
          data-bs-backdrop="false"
          tabindex="-1"
          id="offcanvasInter"
          aria-labelledby="offcanvasInter"
          style="width: 410px; height: calc(100vh - 81px); bottom: 0; top: auto; border: none;">
      <!-- 2.21일 수정 -->
      <div class="offcanvas-header d-flex justify-content-between align-items-center" style="height: 50px;">
            <div>
              <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" ref="planRef" @click="hideDetail"><i class="bi bi-chevron-double-left"></i></button> 
            </div>
            <h5 style="margin-top: 11px; margin-left: -13px;">Here Where Travel Plan</h5>
            <div style="width: same-as-button;"></div>
          </div>
      <div class="offcanvas-body">
        <PlanCard v-show="showPlan" v-for="plan in plansInfo" :key="plan.id" :plan="plan" @passArrival="arrivalRoute"/>
        <div class="noplan-div" v-show="!showPlan">
          <strong>여행계획이 없습니다.</strong>
          <a href="#" class="btn btn-light">여행일정 만들기</a>
        </div>
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
          <HotelDate :places="places" :imgplaces="imgsearchplaces" :latNumber="latNumber" :lngNumber="lngNumber" @search-event="handleSearchEvent" />
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
      <div :class="{ 'offcanvas offcanvas-start': true, 'show': showRoute }" tabindex="-1" id="offcanvasBottom" aria-labelledby="offcanvasBottomLabel" style="height:100vh; width:410px;">
        <div class="offcanvas-header">
          <button type="button" id="btn_close" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" @click="routeToggle"><i class="bi bi-chevron-double-left"></i></button>
        </div>
        <div class="offcanvas-form" id="modal_form">
          <div class="offcanvas-start">
            <RouteRecommendation @setDraw="drivePath=>setDraw(drivePath)" 
                  @passRouteLocation="e=>routeCoords=e" :routeInfo="clickInfo" 
                  :arrivals="arrivalsValue" :map="mapRef" :showRoute="showRoute"
            />
          </div>
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
      style="width:100vw; height:calc(100vh - 81px);"
      @click="e=>clickHandler(e)"
      >
      <DrawDirection :coords="routeCoords" :showRoute="showRoute" @cameraSend="data=>cameraCenter(data)" />
      
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
            <div class="card" style="width: 18rem;">
              <img :src="locationInfo.placeImage" class="card-img-top img-fluid" :alt=locationInfo.placeName style="height: 250px">
              <div class="card-body">
                <h5 class="card-title">{{locationInfo.placeName}}</h5>
                <p class="card-text">{{ locationInfo.placeAddress }}</p>
                <p class="card-text">{{ locationInfo.placeRating }}</p>
                <a href="#" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#scheduleDateModal">일정추가</a> &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#" class="btn btn-light" @click="arrivalRoute(places)">경로찾기</a>
              </div>
            </div>
        </InfoWindow>
        <ScheduleDate @addSchedule="setIntersInfo"/>
        <MarkerCluster :plansInfo="plansInfo" :hotelsInfo="hotelsInfo" :stopOver="stopOver"
                          :restaurantsInfo="restaurantsInfo" :attractionsInfo="attractionsInfo" 
                            :locationLatLng = "locationLatLng" :placeLatLng="placeLatLng"
                              @clickMarker="(info)=>clickCustomMarker(info)"/>
      </GoogleMap>
    <StreetView :map="mapRef" :style="{right:streetViewRight}"/>
  </div>
</div>
</template>
    
  <script setup>
  import {
      GoogleMap,
      //Marker as MapMarker,
      InfoWindow,
      CustomMarker,
  } from "vue3-google-map";
  import { ThreeJSOverlayView } from '@googlemaps/three';
  // import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
  import * as THREE from 'three';
  import { ref, watch, computed, onMounted, defineProps, defineEmits, onUnmounted } from "vue";
  import { geoLocation } from '@/composable/geoLocation'
  import gsap from 'gsap'
  import axios from "axios";
  import StreetView from '@/components/search/StreetView.vue'
  import SearchImage from '@/components/search/SearchImage.vue'
  import RestaurantCard from '@/components/search/RestaurantCard.vue'
  import AttractionCard from '@/components/search/AttractionCard.vue';
  import Hamburgermenu from '@/components/search/HamburgerMenu.vue'
  import RouteRecommendation from "@/components/search/RouteRecommendation.vue";
  import DrawDirection from "@/components/search/DrawDirection.vue"; //경로(polyline) 그리기
  import { createObserver,getDistanceInKm } from "@/composable/custom"; //검색기능의 AutoComplete CSS속성을 동적으로 구현 js, 경로검색 placeholder토글
  import PlanCard from "@/components/search/PlanCard.vue";
  import HotelCard from '@/components/search/HotelCard.vue'
  import HotelDate from '@/components/search/HotelDate.vue';
  import SearchCard from '@/components/search/SearchCard.vue'
  import YoutubeCard from "@/components/search/YoutubeCard.vue";
  import Weather from "@/components/search/WeatherCard.vue";
  import News from "@/components/search/NewsCard.vue";
  import MarkerCluster from "@/components/search/MarkerCluster.vue";
  import LoadingOverlay from '@/components/search/LoadingModal.vue';
  import ScheduleDate from '@/components/search/ScheduleDate.vue'
  
  const streetViewRight=ref('20px')
  let showRoute= ref(false);
  const planRef= ref(false);
  const showInter= ref(false);
  const leftOffButton= ref(null);
  const routeCoords=ref({});
  let searchList= ref([]);
  const weatherData=ref([]);
  const newsData=ref([]);
  let clickInfo= ref({})
  let placeLatLng = ref(null);
  let stopOver= ref([])
  let showPlan= ref(false)

  const locationInfo=ref({
    placeName:'',
    placeAddress:'',
    placeRating:0,
    placeImage:''
  })
  const profileImage = ref('');

  const props = defineProps({
    locationValue: String,
    locationLatLng : Object
  });

  onMounted(()=>{
    searchRef.value.value=props.locationValue
    profileImage.value=localStorage.getItem('profileImage');
    createObserver();
  });

  const emit= defineEmits(['disconnect'])
  const disconnect=()=>{
    emit('disconnect')
  }
  onUnmounted(()=>{
    disconnect()
  })

  function getNextDay(date = new Date()) {
    if (!(date instanceof Date)) {
        date = new Date(date); // 문자열을 Date 객체로 변환
    }
    const nextDay = new Date(date);
    nextDay.setDate(date.getDate() + 1);
    return nextDay.toISOString().split('T')[0];
  }
  const nextDay = getNextDay();
  const followingDay = getNextDay(nextDay);

  function updateInfoWindow(places){
    let photoUrl;
    if("restaurant" in places){
      locationInfo.value.placeName= places.restaurant
      locationInfo.value.placeAddress= places.address
      locationInfo.value.placeRating= places.rating
      if(places.image !== '이미지를 제공하지 않는 음식점 입니다')
        locationInfo.value.placeImage= places.image
      else
        locationInfo.value.placeImage= 'place_default.png';
    }else if("hotel" in places){
      locationInfo.value.placeName= places.hotel
      locationInfo.value.placeAddress= places.address
      locationInfo.value.placeRating= places.review
      locationInfo.value.placeImage= places.img_urls
    }else if("attraction" in places){
      locationInfo.value.placeName= places.attraction
      locationInfo.value.placeAddress= places.address
      locationInfo.value.placeRating= places.rating
      locationInfo.value.placeImage= places.image
    }else{
      if (places.photos && places.photos.length > 0) {
        const photo = places.photos[0]; 
        const photoReference = photo.photo_reference;
      
        if (photoReference) {
          photoUrl = `https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference=${photoReference}&key=${apiKey}`;
        } else if (photo.getUrl) {
          photoUrl = photo.getUrl({maxWidth: 300});
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

  function setIntersInfo(){
    showInter.value= false;
    if(plansInfo.value.length !== 0){
      for(let i=0; i<plansInfo.value.length; i++){
        if(plansInfo.value[i].name === places.name) {
          infoRef.value.close()
          showInter.value= !showInter.value
          return;
        }
      }
    }
    plansInfo.value.push(places)
    infoRef.value.close()
    showInter.value= !showInter.value
  }
 
  async function getYoutubeData(address){
    const response= await axios.get(process.env.VUE_APP_PYTHON_API_URL+'/youtube',{params:{address}})
    youtubeData.value=response.data
  }
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
  }

  let imgsearchplaces = ref(null)

  function searchImgLocation(imgplaces){
    imgsearchplaces.value = imgplaces
    let location= imgplaces.geometry.location;
    getNearbyRestaurants(imgplaces.geometry.location.lat,imgplaces.geometry.location.lng)
    getNearbyAttractions(imgplaces.geometry.location.lat,imgplaces.geometry.location.lng)
    getNearbyHotels(imgplaces.geometry.location.lat,imgplaces.geometry.location.lng,2,nextDay,followingDay)
    getYoutubeData(imgplaces.name)
    getWeather(imgplaces.geometry.location.lat,imgplaces.geometry.location.lng)
    getNews(imgplaces.geometry.location.lat,imgplaces.geometry.location.lng)
    moveToPosition(location)
    updateInfoWindow(imgplaces)
  }
  
  function searchLocation(places){
    let location= places.geometry.location;
    getNearbyRestaurants(places.geometry.location.lat(),places.geometry.location.lng())
    getNearbyAttractions(places.geometry.location.lat(),places.geometry.location.lng())
    getNearbyHotels(places.geometry.location.lat(),places.geometry.location.lng(),2,nextDay,followingDay)
    getYoutubeData(places.name)
    getWeather(places.geometry.location.lat(),places.geometry.location.lng())
    getNews(places.geometry.location.lat(),places.geometry.location.lng())
    moveToPosition(location)
    updateInfoWindow(places)
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
  const currPos = computed(() => {
    return props.locationLatLng.length === 0
      ? {
          lat: parseFloat(coords.value.latitude),
          lng: parseFloat(coords.value.longitude),
        }
      : {
          lat: parseFloat(props.locationLatLng[0]),
          lng: parseFloat(props.locationLatLng[1]),
        };
  });
  
  const apiKey= process.env.VUE_APP_GOOGLE_API_KEY; /* 여기에 해당 apikey설정할 것!!!! */
  
  const mapOptions=ref({
      tilt: 67.5,
      heading: 90,
      zoom: 17,
      center: currPos,
      mapId: 'c1b04704f8464294',
      disableDefaultUI: true,
      gestureHandling: 'auto',
      keyboardShortcuts: false,
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
  watch(() => mapOptions.value.heading, (newHeading) => {
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
  let latNumber= ref(null)
  let lngNumber= ref(null)

  const clearPlaces = () => {
    places = {}; // Clear places
  };

  function initMap(googleMap) {
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
  
    // 지도의 경계(뷰포트) 속성을 자동 완성 개체에 바인딩합니다,
    // 자동 완료 요청이 현재 맵 경계를 사용하도록 설정합니다
    // 요청에 bounds 옵션이 있습니다.
    autoComplete.bindTo("bounds", map);
    autoComplete.addListener('place_changed',()=>{
      places=autoComplete.getPlace();
      if(Object.keys(places).length > 1) {
        leftOffButton.value.click();
        searchLocation(places)
      }else{
        searchList.value= [];
        let request= {query: places.name};
        placesService.value.textSearch(request,(result,status)=>{
          if(status === 'OK') {
            searchList.value= result;
          }
          return;
        });
      }
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

    const getPlaceData = (place) => {
      const lat = place.geometry.location.lat();
      const lng = place.geometry.location.lng();
      placeLatLng.value = { lat, lng }; // 위도와 경도를 저장합니다.
      getNearbyRestaurants(lat, lng);
      getNearbyAttractions(lat, lng);
      getNearbyHotels(lat, lng, 2, nextDay,followingDay);
      map.setCenter({ lat, lng });
      updateInfoWindow(place);
      getWeather(lat, lng);
      getNews(lat, lng);
    }
    
    if (props.locationValue && searchRef.value) {
      placesService.value.textSearch({query:props.locationValue + '시청'}, (result, status) => {
            if (status === 'OK') {
              places = result[0]
              getPlaceData(places);
        } 
      });
    }
    else if (props.locationLatLng && props.locationLatLng.length >=1 ) {
      latNumber.value = parseFloat(props.locationLatLng[0]);
      lngNumber.value = parseFloat(props.locationLatLng[1]);
      const dataLatLng = {
        lat: latNumber.value,
        lng: lngNumber.value,
      };
      map.setCenter(dataLatLng);
      getNearbyRestaurants(latNumber.value, lngNumber.value);
      getNearbyAttractions(latNumber.value, lngNumber.value);
      getNearbyHotels(latNumber.value, lngNumber.value, 2, nextDay,followingDay);
      getWeather(latNumber.value, lngNumber.value);
      getNews(latNumber.value, lngNumber.value);
    }
    else {
      latNumber.value = parseFloat(37.4923615);
      lngNumber.value = parseFloat(127.0292881);
      const dataLatLng = {
        lat: latNumber.value,
        lng: lngNumber.value,
      };
      map.setCenter(dataLatLng);
      getNearbyRestaurants(latNumber.value, lngNumber.value);
      getNearbyAttractions(latNumber.value, lngNumber.value);
      getNearbyHotels(latNumber.value, lngNumber.value, 2, nextDay,followingDay);
      getWeather(latNumber.value, lngNumber.value);
      getNews(latNumber.value, lngNumber.value);
    }
  }

  /*경로 설정 */
  const arrivalsValue= ref({})

  function routeToggle(){
    showRoute.value = false;
  }

  const arrivalRoute=arrival=>{
    geocoder.value.geocode({location:arrival.geometry.location})
      .then(result=>{
        arrivalsValue.value= result.results[0]
        infoRef.value.close()
        showRoute.value= true
      })
  }
  
  const setDraw=(drivePath)=>{
    stopOver.value= []
    routeCoords.value= drivePath
    if(drivePath && 'transit' in drivePath[0]){
      drivePath.forEach((path,index)=>{
        if(index > 0 && index < drivePath.length-1) stopOver.value.push(path)
      })
    }
  }

  const cameraCenter=(data)=>{
    let distance= getDistanceInKm(data[0].lat,data[0].lng,data[data.length-1].lat,data[data.length-1].lng);
    if(distance<1) map.moveCamera({zoom:17})
    else if(distance<5) map.moveCamera({zoom:15})
    else if(distance<20) map.moveCamera({zoom:12})
    else if(distance<100) map.moveCamera({zoom:8})
    mapRef.value.map.setCenter(data[parseInt(data.length/2)]);
  }

  const searchClick= searchInfo=> {
    leftOffButton.value.click();
    places= searchInfo.value;
    searchLocation(places);
  }

  const clickHandler=(e)=>{
    infoRef.value.close()
    e.stop()
    if(showRoute.value===true) {
      geocoder.value.geocode({location:{lat:e.latLng.lat(), lng:e.latLng.lng()}})
      .then(result=>{
        clickInfo.value= result.results[0]
        return
      })
    }
    if(e.placeId && !showRoute.value){
      let request= {
        placeId:e.placeId
      }
      placesService.value.getDetails(request, (place, status)=>{
        if(status !== 'OK') return
        showInter.value = false
        let closeInfo= document.querySelector('.gm-ui-hover-effect')
              
        if(closeInfo !== null) {
          closeInfo.click()
        }
        let closeButton= document.querySelectorAll('.btn-close')
        if(closeButton.forEach(x=>{
          x.click()
        }));
        places= place
        searchLocation(place)
        return;
      });
    }
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

  function clickCustomMarker(info){
    if("attraction" in info || "restaurant" in info || "hotel" in info){
      geocoder.value.geocode({location:{lat:info.lat,lng:info.lng}})
      .then(result=>{
        places= result.results[0]
        searchLocation(places)
      })
    }else {
      places= info
      searchLocation(places)
    }
  }

  </script>

  <style scoped>
  /********************************************************************경로 지정 */
  #modal_form {
    width: 100%;
    height: 100%;
    display: inline-block;
    padding: 0 100px 10px;
    position: relative;
    top: -30px;

  }
  #btn_close {
    /* display: inline; */
    margin-left: 350px;
    margin-top: 80px;
    z-index: 1100;
    padding: 8px 16px;
  }
  /********************************************************************경로 지정 */
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
.noplan-div{
  display: grid;
  align-items: center;
  justify-content: center;
  height: 100%;
  padding-bottom: 100px;
  color: #808080;
  align-content: center;
}
  </style> 