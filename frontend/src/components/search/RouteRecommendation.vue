<template>
    <div class="row mb-3">
        <label for="departures" class="col-sm-2 col-form-label">
        </label>
        <div class="col-sm-2">
            <img :src="require('@/assets/icon-gps.png')" alt="아이콘" id="icon-route1">
            <input type="text" class="form-control" id="departures"
                        placeholder="출발지를 선택하거나 지도를 클릭하세요" ref="departuresRef" @keydown.enter="searchPlace" @input="searchObserve">
        </div>
    </div>
    <div class="revert" @click="swapDepartureAndArrival">
        <button class="revert_btn">
            <img :src="require('@/assets/icon-revert.png')" alt="아이콘" id="icon-revert">
        </button>
    </div>
    <div class="row mb-3">
        <label for="arrivals" class="col-sm-2 col-form-label" ></label>
        <div class="col-sm-2">
            <img :src="require('@/assets/icon-gps.png')" alt="아이콘" id="icon-route">
            <input type="text" class="form-control" id="arrivals" 
                        placeholder="도착지를 선택하세요" ref="arrivalsRef" @keydown.enter="searchPlace">
        </div>
    </div>
    <div class="btnserch">
        <a href="#" id="btn_find" class="btn btn-outline-dark" @click="searchRoute">검색</a>
    </div>
    <div class="Route_form">
        <ul>
            <li class="li">
                <button class="bus_btn" @click="setActive('bus_btn')">
                    <span><img :src="require('@/assets/icon-school-bus.png')" alt="아이콘" id="icon-road"></span>
                    <span>대중교통</span>
                </button>
            </li>
            <li class="li">
                <button class="car_btn" @click="setActive('car_btn')">
                    <span><img :src="require('@/assets/icon-car.png')" alt="아이콘" id="icon-road"></span>
                    <span>자동차</span>
                </button>
            </li>
            <li class="li">
                <button class="walk_btn" @click="setActive('walk_btn')">
                    <span><img :src="require('@/assets/icon-walk.png')" alt="아이콘" id="icon-road"></span>
                    <span>도보</span>
                </button>
            </li>
        </ul>
    </div>
    <div class="View_route">
        <ul>
            <li v-if="activeMode === 'bus_btn'">
                <div id="bus-div" class="card w-100 mb-3">
                    <div class="card-body" id="transit-card-body" v-for="(transitInfo,index) in transitList" :key="index" @click="clickTransit(transitInfo)">
                        <div class="transit-card-info" data-bs-toggle="collapse" :data-bs-target="'#transitInfo'+index" aria-expanded="false" :aria-controls="'transitInfo'+index">
                            <div id="index-circle"> {{ index+1 }} </div>
                            <span v-for="(step,index) in transitInfo.legs[0].steps" :key="index">
                                <span class="transit-space" v-if="step.travel_mode==='TRANSIT' && step.transit.line.vehicle.type.includes('BUS')">
                                    <img :src="require('@/assets/icon-bus.png')"/>
                                    <span>{{ step.transit.line.short_name.replace(/\D/g, '') }}</span>                              
                                </span>
                                <span class="transit-space" v-if="step.travel_mode==='TRANSIT' && (step.transit.line.vehicle.type.includes('SUBWAY') || step.transit.line.vehicle.type.includes('HEAVY_RAIL'))">
                                    <img :src="require('@/assets/icon-subway.png')"/>
                                    <span>{{ step.transit.line.short_name }}</span>
                                </span>
                            </span>
                            <div class="card-info-head" id="transit-card-info-head">
                                <span><strong>{{ transitInfo.legs[0].duration.text }}</strong></span>
                                <span><small>{{ transitInfo.legs[0].distance.text }}</small></span>
                                <ul class="drive-ul">
                                    <li>{{ transitInfo.legs[0].departure_time.text }} ~ {{ transitInfo.legs[0].arrival_time.text }}</li>
                                </ul>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div v-for="step in transitInfo.legs[0].steps" :key="step" class="collapse multi-collapse" :id="'transitInfo'+index">
                                    <div class="transit-info">
                                        <div class="card-info">
                                            <div class="row">
                                                <div class="col-4">
                                                    <img v-if="step.travel_mode==='TRANSIT' && step.transit.line.vehicle.type.includes('BUS')" :src="require('@/assets/icon-bus.png')" />
                                                    <img v-if="step.travel_mode==='TRANSIT' && (step.transit.line.vehicle.type.includes('SUBWAY') || step.transit.line.vehicle.type.includes('HEAVY_RAIL'))" :src="require('@/assets/icon-subway.png')" />
                                                    <img v-if="step.travel_mode === 'WALKING'" :src="require('@/assets/icon-walking.png')" />  
                                                    <span id="transitName" v-if="step.travel_mode==='TRANSIT'">{{ step.transit.line.short_name }}</span>
                                                </div>
                                                <div class="col-8">
                                                    <div class="transit-card-info-body">
                                                        <span><strong>{{ step.duration.text }}</strong> <small>{{ step.distance.text }}</small></span>
                                                        <ul class="transit-ul">
                                                            <li v-if="step.travel_mode==='TRANSIT'">
                                                                <input type="text" readonly :value="`${step.transit.departure_stop.name}역 ~ ${step.transit.arrival_stop.name}역`"/>
                                                            </li>
                                                            <li v-else>
                                                                <input type="text" readonly :value="step.instructions"/>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>     
                    </div>
                </div>
            </li>
            <li v-else-if="activeMode === 'car_btn'">
                <div class="routes-card" v-for="(driveInfo,index) in driveList" :key="index">
                    <div class="card-body" @click="clickDrive(driveInfo)">
                        <div class="card-info">
                            <div id="index-circle">{{ index+1 }}</div>
                            <div class="card-info-head">
                            <span><strong>{{ driveInfo.summary.duration }}</strong></span>
                            <span><small>{{ driveInfo.summary.distance }}</small></span>
                            <ul class="drive-ul">
                                <li>택시비{{ driveInfo.summary.fare.taxi }}원</li>
                                <li>{{ driveInfo.summary.fare.toll }}</li>
                            </ul>
                            </div>
                        </div>
                    </div>
                </div>                
            </li>
            <li v-else-if="activeMode === 'walk_btn'">
                <div class="routes-card" v-if="walkInfo.length!==0" @click="clickWalk(walkInfo)">
                    <div class="card-body">
                        <div class="card-info">
                            <div class="card-info-head">
                            <span><strong>{{ walkInfo.features[0].properties.totalTime }}</strong></span>
                            <span><small>{{ walkInfo.features[0].properties.totalDistance }}</small></span>
                            </div>
                        </div>
                    </div>
                </div>               
            </li>
            <li v-else-if="activeMode === 'searchArea'">
                <div class="search-card" v-for="searchInfo in searchList" :key="searchInfo.place_id" @click="searchClick(searchInfo)">
                    <div class="card-body">
                        <div class="card-info">
                            <img :src="require('@/assets/location2.png')" id="search-icon" />
                            <div id="search-head" class="card-info-head">
                            <p>{{ searchInfo.name }}</p>
                            </div>
                            <div id="search-body" class="card-info-body">
                            <p> {{ searchInfo.formatted_address }}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li v-if="Object.keys(flightInfo).length > 0 && Object.keys(departuresPath).length > 0 && Object.keys(arrivalsPath).length > 0 && activeMode !== 'searchArea'">
                <div id="bus-div"  class="card w-100 mb-3" v-show="showFlightCard">
                    <div class="card-body">
                        <div class="row" >
                            <div class="col">
                                <div class="flight-info" id="bestRoute">
                                    <div class="card-info">
                                        <div class="d-flex flex-column">
                                            <div class="d-flex justify-content-between flex-fill mb-3">
                                                <span>최적항공편</span>
                                                <span class="text-end"><strong>{{ parseInt(flightInfo.price.total) }}원</strong> <small>경유{{ flightInfo.itineraries[0].segments.length-1 }}회</small></span>
                                            </div>

                                            <div class="flex-fill text-end">
                                                <div class="flight-card-info-body d-flex justify-content-between mb-2">
                                                    <a class="btn btn-primary" @click="searchAirplane(flightInfo.itineraries[0].segments[0].departure.iataCode, flightInfo.itineraries[0].segments[flightInfo.itineraries[0].segments.length-1].arrival.iataCode)">자세히</a>
                                                    <ul class="flight-ul">
                                                        <li>{{ getAirport(flightInfo.itineraries[0].segments[0].departure.iataCode).name }}</li>
                                                        <li>{{ getAirport(flightInfo.itineraries[0].segments[flightInfo.itineraries[0].segments.length-1].arrival.iataCode).name }}</li>
                                                    </ul>               
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="flight-info" v-for="(step,index) in departuresPath.routes[0].legs[0].steps" :key="index">
                                    <div class="card-info">
                                        <div class="row">
                                            <div class="col-4">
                                                <img v-if="step.travel_mode==='TRANSIT' && step.transit.line.vehicle.type.includes('BUS')" :src="require('@/assets/icon-bus.png')" />
                                                <img v-if="step.travel_mode==='TRANSIT' && (step.transit.line.vehicle.type.includes('SUBWAY') || step.transit.line.vehicle.type.includes('HEAVY_RAIL'))" :src="require('@/assets/icon-subway.png')" />
                                                <img v-if="step.travel_mode === 'WALKING'" :src="require('@/assets/icon-walking.png')" />  
                                                <span id="transitName" v-if="step.travel_mode==='TRANSIT'">{{ step.transit.line.short_name }}</span>
                                            </div>
                                            <div class="col-8">
                                                <div class="flight-card-info-body">
                                                    <span><strong>{{ step.duration.text }}</strong> <small>{{ step.distance.text }}</small></span>
                                                    <ul class="flight-ul">
                                                        <li v-if="step.travel_mode==='TRANSIT'">
                                                            <input type="text" readonly :value="`${step.transit.departure_stop.name}역 ~ ${step.transit.arrival_stop.name}역`"/>
                                                        </li>
                                                        <li v-else>
                                                            <input type="text" readonly :value="step.instructions"/>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="flight-info" v-if="Object.keys(flightInfo).length > 0">
                                    <div class="card-info">
                                        <div class="row">
                                            <div class="col-4">
                                                <img :src="require('@/assets/airplane.png')"/> 
                                            </div>
                                            <div id="flightCol8" class="col-8">
                                                <div class="flight-card-info-body">
                                                    <div>{{ formatDateTime(flightInfo.itineraries[0].segments[0].departure.at) }}</div>
                                                    <div>{{ formatDateTime(flightInfo.itineraries[0].segments[flightInfo.itineraries[0].segments.length-1].arrival.at) }}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="flight-info" v-for="(step,index) in arrivalsPath.routes[0].legs[0].steps" :key="index">
                                    <div class="card-info">
                                        <div class="row">
                                            <div class="col-4">
                                                <img v-if="step.travel_mode==='TRANSIT' && step.transit.line.vehicle.type.includes('BUS')" :src="require('@/assets/icon-bus.png')" />
                                                <img v-if="step.travel_mode==='TRANSIT' && (step.transit.line.vehicle.type.includes('SUBWAY') || step.transit.line.vehicle.type.includes('HEAVY_RAIL'))" :src="require('@/assets/icon-subway.png')" />
                                                <img v-if="step.travel_mode === 'WALKING'" :src="require('@/assets/icon-walking.png')" />  
                                                <span id="transitName" v-if="step.travel_mode==='TRANSIT'">{{ step.transit.line.short_name }}</span>
                                            </div>
                                            <div class="col-8">
                                                <div class="flight-card-info-body">
                                                    <span><strong>{{ step.duration.text }}</strong> <small>{{ step.distance.text }}</small></span>
                                                    <ul class="flight-ul">
                                                        <li v-if="step.travel_mode==='TRANSIT'">
                                                            <input type="text" readonly :value="`${step.transit.departure_stop.name}역 ~ ${step.transit.arrival_stop.name}역`"/>
                                                        </li>
                                                        <li v-else>
                                                            <input type="text" readonly :value="step.instructions"/>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>   
                    </div>
                </div>
            </li>
            <li v-if="zeroResult">
                경로를 찾지 못했습니다.
            </li>
        </ul>
        <div v-if="showRoading" id="planeLoading">
            <span class="plane fa fa-plane"></span>
            <div id="wave0" class="animate">
                <span class="dot"></span>
            </div>
            <div id="wave1" class="animate">
                <span class="dot"></span>
            </div>
            <div id="wave2" class="animate">
                <span class="dot"></span>
            </div>
            <div id="wave3" class="animate">
                <span class="dot"></span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps, ref, watch, defineEmits,onMounted } from 'vue'
     //toRaw } 
import axios from 'axios';
import qs from 'qs';
import Papa from 'papaparse';
import { getDistanceInKm,nextSevenDay } from '@/composable/custom';

const departuresRef=ref(null);
const arrivalsRef=ref(null);
let activeMode = ref(null);
let searchList= ref([]);
let driveList= ref([]);
let transitList= ref([]);
let walkInfo= ref([]);
const placesService= ref();
const directionsService= ref();
const directionsRenderer= ref();
const directionsRenderer_= ref();
const geocoder= ref();
let showFlightCard= ref(false);
let routeLocation= ref({});
let showRoading= ref(false);
let searchTarget;
let transitResponse;
let driveResponse;
let walkResponse;
const csvData= ref([]);
let flightInfo= ref({});
let zeroResult= ref(false);
let departuresPath= ref({});
let arrivalsPath= ref({});
let flightList= ref({})

const props = defineProps({
    map:Object,
    routeInfo: Object,
    arrivals: Object,
    showRoute: Boolean,
});

const autoCompleteOptions={
      fields:['formatted_address','geometry','name','rating','photos'],
      strictBounds:false
    }

function swapDepartureAndArrival() {
    const temp = departuresRef.value.value;
    departuresRef.value.value = arrivalsRef.value.value;
    arrivalsRef.value.value = temp;
}

function setActive(buttonType) { 
    if(Object.keys(flightInfo.value).length > 0) return;
    zeroResult.value= false;
    document.querySelectorAll('.bus_btn, .car_btn, .walk_btn').forEach(button => {
        if (button.classList.contains(buttonType)) {
            if(button.classList.contains('active')){
                button.classList.remove('active')
                activeMode.value = null
            }
            else {
                button.classList.add('active')
                activeMode.value = buttonType
            }
        } else {
            button.classList.remove('active');
        }
    });
}

onMounted(()=>{
  Papa.parse('airports.csv', {
    download: true,
    header: true,
    complete: (results) => {
      csvData.value = results.data;
    }
  });
});

watch(() => props.map?.ready, ready => {
    if (!ready) return;
    // Google Maps Places 라이브러리가 로드되었는지 확인
    if (props.map.api && props.map.api.places) {
        placesService.value= new props.map.api.places.PlacesService(props.map.map)
        directionsService.value= new props.map.api.DirectionsService()
        directionsRenderer.value= new props.map.api.DirectionsRenderer()
        directionsRenderer_.value= new props.map.api.DirectionsRenderer()
        geocoder.value= new props.map.api.Geocoder()
        // departures 입력 필드에 대한 Autocomplete 인스턴스 생성
        new props.map.api.places.Autocomplete(departuresRef.value, autoCompleteOptions);
        // arrivals 입력 필드에 대한 Autocomplete 인스턴스 생성
        new props.map.api.places.Autocomplete(arrivalsRef.value, autoCompleteOptions);
    }
});

watch(()=>props.routeInfo, routeInfo=>{ //지도 클릭이벤트 감지해서 주소넣기
    if(departuresRef.value.value==='') {
        departuresRef.value.value= routeInfo.formatted_address
        arrivalsRef.value.placeholder= '도착지를 선택하거나 지도를 클릭하세요'
    }else if(arrivalsRef.value.value==='') arrivalsRef.value.value= routeInfo.formatted_address
})

watch(()=>props.arrivals, arrivals=>{ //부모 컴포넌트에서 경로 목적지 받아오기
    arrivalsRef.value.value= arrivals.formatted_address
})

watch(()=>props.showRoute, showRoute=>{ //그려진 경로 초기화
    if(!showRoute) {
        if(directionsRenderer.value) directionsRenderer.value.setMap(null)
        if(directionsRenderer_.value) directionsRenderer_.value.setMap(null)
        activeMode.value= null
        document.querySelectorAll('.bus_btn, .car_btn, .walk_btn').forEach(button => {
            if(button.classList.contains('active')) button.classList.remove('active')
        });
    }
})

const searchPlace=(e)=>{        //출발지, 도착지 엔터로 검색
    placesService.value.textSearch({query:e.target.value},(result,status)=>{
        if(status === 'OK'){
            searchList.value= result
            document.querySelectorAll('.bus_btn, .car_btn, .walk_btn').forEach(button => {
                if(button.classList.contains('active')) button.classList.remove('active')
            })
            zeroResult.value= false;
            activeMode.value='searchArea'   
            searchTarget= e.target
        }
    })
}

const searchClick=(searchInfo)=>{
    if(searchTarget) searchTarget.value= `${searchInfo.formatted_address}(${searchInfo.name})`
}

const searchObserve=()=>{
    if(departuresRef.value.value !== ''){
        arrivalsRef.value.placeholder= '도착지를 선택하거나 지도를 클릭하세요'
    }else arrivalsRef.value.placeholder= '도착지를 선택하세요'
}

const searchRoute=()=>{ //출발지 목적지 좌표 얻기
    if(departuresRef.value.value.trim() === '' || departuresRef.value.value.trim()==='') return
    routeLocation.value={}
    if(directionsRenderer.value) directionsRenderer.value.setMap(null)
    if(directionsRenderer_.value) directionsRenderer_.value.setMap(null)
    placesService.value.textSearch({query:departuresRef.value.value}, (result,status)=>{
        if(status === 'OK'){
            routeLocation.value.departures={lat:result[0].geometry.location.lat(), lng:result[0].geometry.location.lng()}
            placesService.value.textSearch({query:arrivalsRef.value.value}, (result,status)=>{
                if(status === 'OK'){
                    routeLocation.value.arrivals={lat:result[0].geometry.location.lat(), lng:result[0].geometry.location.lng()}
                    findRoute(routeLocation.value)
                }
            })
        }
    })
}

// 거리 표시 함수
function displayDistance(distance) {
    if(distance < 1000) return distance + 'm';
    if(distance < 10000) return (distance / 1000).toFixed(1) + 'km';
    return parseInt(distance / 1000) + 'km';
}

// 시간 표시 함수
function displayDuration(duration) {
    if(duration < 60) return '1분';
    if(duration < 3600) return parseInt(duration / 60) + '분';
    return parseInt(duration / 3600) + '시간 ' + parseInt((duration % 3600) / 60) + '분';
}

//비행시간 포맷 함수
function formatDateTime(datetimeStr) {
    const dateObj = new Date(datetimeStr);
    const month = dateObj.toLocaleString('ko-KR', { month: 'long' });
    const day = dateObj.toLocaleString('ko-KR', { day: 'numeric' });
    const time = dateObj.toLocaleString('ko-KR', { hour: 'numeric', minute: 'numeric', hour12: true });
    return `${month}${day} ${time}분`;
}

const findRoute= async(route)=> { //경로 찾기 이벤트
    flightInfo.value= {};
    zeroResult.value= false;
    activeMode.value= null
    document.querySelectorAll('.bus_btn, .car_btn, .walk_btn').forEach(button => {
        if(button.classList.contains('active')) button.classList.remove('active')
    });
    try{
        showFlightCard.value= false
        driveList.value= [];
        transitList.value= [];
        walkInfo.value= [];
        driveResponse= null;
        transitResponse= null;
        walkResponse= null;
        showRoading.value= true;
        [driveResponse, transitResponse, walkResponse]= await Promise.all([
            getDriveRoute(route),
            getTransitRoute(route),
            getWalkRoute(route)
        ]);     
        if(driveResponse){
            if(driveResponse.data.routes[0].summary){
                driveResponse.data.routes.forEach(routes=>{
                    routes.summary.distance = displayDistance(routes.summary.distance);
                    routes.summary.duration = displayDuration(routes.summary.duration);
                    if(routes.summary.fare.toll===0) delete routes.summary.fare.toll;
                    else routes.summary.fare.toll= '통행료'+routes.summary.fare.toll+'원'; 
                })
                driveList.value= driveResponse.data.routes
            }
        }
        if(transitResponse) transitList.value= transitResponse.routes
        if(walkResponse.data) {
            walkInfo.value= walkResponse.data
            let properties= walkInfo.value.features[0].properties
            properties.totalDistance= displayDistance(properties.totalDistance)
            properties.totalTime= displayDuration(properties.totalTime)
        }
        showRoading.value= false;
        document.querySelectorAll('.bus_btn, .car_btn, .walk_btn').forEach(button => {
            if(button.classList.contains('active')) button.classList.remove('active')
        });
        document.querySelector('.bus_btn').click();
    }catch(error){
        if(error==='ZERO_RESULTS'){
            getFlightPath(route)
        }else{
            console.log('error',error)
            showRoading.value= true;
        }
    }
  }

  const data = qs.stringify({
    'grant_type': 'client_credentials',
    'client_id': process.env.VUE_APP_AMADEUS_KEY,
    'client_secret': process.env.VUE_APP_AMADEUS_SECRET
  });

  const config = {
    method: 'post',
    url: 'https://test.api.amadeus.com/v1/security/oauth2/token',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    },
    data : data
  };

  async function getFlightPath(route){
    try{
        directionsRenderer.value.setMap(null);
        directionsRenderer_.value.setMap(null);
        let [departuresAirport, arrivalsAirport] = await Promise.all([
            searchAirports(route.departures.lat, route.departures.lng), // 출발지 근처 공항 찾기
            searchAirports(route.arrivals.lat, route.arrivals.lng) // 목적지 근처 공항 찾기
        ]);
        let departuresAirportRoute= {departures:{lng:route.departures.lng, lat:route.departures.lat}, //출발지와 공항 좌표
                            arrivals:{lng:parseFloat(departuresAirport.longitude_deg), lat:parseFloat(departuresAirport.latitude_deg)}}
        let arrivalsAirportRoute= {departures:{lng:parseFloat(arrivalsAirport.longitude_deg), lat:parseFloat(arrivalsAirport.latitude_deg)}, //목적지와 공항 좌표
                            arrivals:{lng:route.arrivals.lng, lat:route.arrivals.lat}}
        let credential= await axios(config); //아마데우스 토큰 생성
        flightList.value= await axios.get('https://test.api.amadeus.com/v2/shopping/flight-offers',{ //아마데우스 비행경로 찾기
            params:{
                originLocationCode: departuresAirport.iata_code,
                destinationLocationCode: arrivalsAirport.iata_code,
                departureDate: nextSevenDay(),
                adults: 1,
                max: 1,
                currencyCode: 'KRW'
            },
            headers: {
                'Authorization': `Bearer ${credential.data.access_token}`
            }
        });
        [departuresPath.value, arrivalsPath.value]= await Promise.all([ //대중교통 경로 찾기
            getTransitRoute(departuresAirportRoute),
            getTransitRoute(arrivalsAirportRoute)
        ]);
        directionsRenderer.value.setMap(props.map.map); //대중교통 경로 그리기
        directionsRenderer_.value.setMap(props.map.map);
        directionsRenderer.value.setDirections(departuresPath.value);
        directionsRenderer_.value.setDirections(arrivalsPath.value);
        let flightPath= []
        let airportInfo= []
        flightList.value.data.data[0].itineraries[0].segments.forEach((segment,index)=>{ //비행경로에서 공항코드 추출
            if(index === 0) airportInfo.push(getAirport(segment.departure.iataCode));
            airportInfo.push(getAirport(segment.arrival.iataCode));
        });
        airportInfo.forEach(info=>{
            flightPath.push({lat:parseFloat(info.latitude_deg), lng:parseFloat(info.longitude_deg), transit:'FLIGHT'});
        })
        setDraw(flightPath);
        showRoading.value= false;
        showFlightCard.value= true;
        console.log(flightList.value);
        console.log('departuresPath',departuresPath.value);
        console.log('arrivalsPath',arrivalsPath.value);
        flightInfo.value= flightList.value.data.data[0];
    }catch(error){
        if(error==='ZERO_RESULTS') zeroResult.value= true;
        showRoading.value= false;
    }
}

  async function getDriveRoute(route){ //카카오 운전 경로 API
    return new Promise((resolve, reject) => {
        axios.get('https://apis-navi.kakaomobility.com/v1/directions', {
            params: {
                origin: route.departures.lng+','+route.departures.lat,
                destination: route.arrivals.lng+','+route.arrivals.lat,
                waypoints: '',
                priority: 'RECOMMEND',
                car_fuel: 'GASOLINE',
                car_hipass: false,
                alternatives: true,
                road_details: false
            },
            headers: {
                Authorization: process.env.VUE_APP_KAKAO_AUTHORIZATION
            }
        })
        .then(response => {
            resolve(response);
        })
        .catch(error => {
            reject(error);
        })
    });
  }

  async function getTransitRoute(route){ //구글 대중교통 경로 API
    let request= {
        origin: route.departures,
        destination: route.arrivals,
        travelMode: 'TRANSIT',
        provideRouteAlternatives: true
    }
    return new Promise((resolve,reject)=> {
        directionsService.value.route(request, (response,status)=>{
            if(status === 'OK'){
                resolve(response)
            }else{
                reject(status)
            }
        })
    })
  }

  async function getWalkRoute(route) { // TMAP 도보 경로 API
    if(getDistanceInKm(route.departures.lat, route.departures.lng,  //출발지와 목적지의 직선거리가 30km이하일때만 도보경로 얻기
        route.arrivals.lat, route.arrivals.lng) > 30) return;
    let headers = { appKey: process.env.VUE_APP_TMAP_API_KEY }
    let request = {
        startX: route.departures.lng,
        startY: route.departures.lat,
        endX: route.arrivals.lng,
        endY: route.arrivals.lat,
        reqCoordType: "WGS84GEO",
        resCoordType: "WGS84GEO",
        startName: "출발지",
        endName: "도착지"
    }
    return new Promise((resolve, reject) => {
        axios.post('https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1',
            request, { headers: headers })
            .then(response => {
                resolve(response);
            })
            .catch(error => {
                console.log('T맵에러',error);
                showRoading.value = false;
                reject(error);
            });
    });
  }

const emit= defineEmits(['passRouteLocation','setDraw','passIataCode'])

const setDraw=drivePath=>{
    emit('setDraw',drivePath);
}

const clickDrive=(driveInfo)=>{
    let roads= driveInfo.sections[0].roads
    let drivePath=[]
    for (let i=0; i<roads.length; i++){
        let vertexes= roads[i].vertexes;
        for (let k = 0; k < vertexes.length; k += 2) {
            drivePath.push({
              lng: vertexes[k],
              lat: vertexes[k + 1]
            })
        }
    }
    if(directionsRenderer.value) directionsRenderer.value.setMap(null)
    if(directionsRenderer_.value) directionsRenderer_.value.setMap(null)
    setDraw(drivePath)
}

const clickWalk=(walkInfo)=>{
    let features= walkInfo.features;
    let walkPath=[];
    for(let i=0; i<features.length; i++){
        let coordinates= features[i].geometry.coordinates
        for(let k=0; k<coordinates.length; k++){
            if(coordinates[k] instanceof Array){
                walkPath.push({
                    lng: coordinates[k][0],
                    lat: coordinates[k][1]
                })
            }else{
                walkPath.push({
                    lng: coordinates[0],
                    lat: coordinates[1]
                })
            }
        }
    }
    if(directionsRenderer.value) directionsRenderer.value.setMap(null)
    if(directionsRenderer_.value) directionsRenderer_.value.setMap(null)
    setDraw(walkPath)
}

const clickTransit=(transitInfo)=>{
    transitResponse.routes= [transitInfo]
    setDraw(null)
    directionsRenderer.value.setMap(props.map.map)
    directionsRenderer.value.setDirections(transitResponse)
}

//근처공항 찾기
const searchAirports = (lat, lng) => {
  return new Promise((resolve, reject) => {
    const distance = 500;  // 검색 반경 (킬로미터 단위)
    let airports=[];
    csvData.value.forEach(row => {
        try{
        if(row.name && row.type.toLowerCase().includes("large_airport") && row.scheduled_service==="yes"){
            const radLat = lat * Math.PI / 180;  // radians
            const radLng = lng * Math.PI / 180;  // radians
            const radLatAirport = parseFloat(row.latitude_deg) * Math.PI / 180;  // radians
            const radLngAirport = parseFloat(row.longitude_deg) * Math.PI / 180;  // radians

            const a = Math.pow(Math.sin((radLat - radLatAirport) / 2), 2)
            + Math.cos(radLat) * Math.cos(radLatAirport) * Math.pow(Math.sin((radLng - radLngAirport) / 2), 2);
            const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            const d = 6371 * c;  // 지구의 반지름 (킬로미터 단위)
            if (d <= distance) {
            airports.push({...row, distance: d});  // 거리 정보를 추가하여 객체를 배열에 넣음
            }
        }
        }catch(error){
        reject(error);  // 에러가 발생하면 reject
        }
    });
    // 거리에 따라 배열을 정렬
    airports.sort((a, b) => a.distance - b.distance);
    if(airports.length > 0) {
        resolve(airports[0]);  // 가장 가까운 공항을 resolve
    } else {
        reject('No airport found');  // 공항을 찾지 못하면 reject
    }
  });
};

//공항 코드로 찾기
const getAirport=(iataCode)=>{
    return csvData.value.find(item=> item.iata_code === iataCode);
}

//
const searchAirplane=(departureIata, arrivalIata)=>{
    let iataCode= {origin: departureIata, destination: arrivalIata}
    emit('passIataCode', iataCode);
}

</script>
<style scoped>
#departures, #arrivals {
    width: 370px;
    position: sticky;
    margin-left: -80px;
    padding: 10px 10px 10px 60px;
    z-index: 1060;
}
#departures{
    /* margin-right: -400px; */
    margin-top: 50px;
}
#arrivals{
    margin-top: 50px;
}

#icon-route{
    width: 32px;
    height: 32px;
    margin-left: -170px;
    position: absolute;
    margin-top: 58px;
    z-index: 1061;
}
#icon-route1{
    width: 32px;
    height: 32px;
    margin-left: -170px;
    position: absolute;
    margin-top: 58px;
    z-index: 1061;
}
.btnserch{
    margin-left: 60px;
    position: absolute;
    top: 38px;
    width: 60px;
    height: 20px;
    z-index: 10;

}
#icon-road{
   margin-left: 3px;
   margin-top: -10px;
   width: 60px;
   height: 60px;
}
.form-control {
    margin-right: -50px;
}
.revert {
    width: 40px;
    height: 40px;
    /* margin-left: 0px; */
    position: absolute;
    margin-left: 40px;
    margin-top: 5px;
    border-radius: 5px;
}
.revert_btn{
    width: 38px;
    height: 37px;
    background-color: #ffffff;  
}
#icon-revert{
    margin-left: -12px;
    margin-top: -7px;
    width: 30px;
    height: 30px;    
}
button> span{
    display: block;
}
#btn_find{
    width: 60px;
    height: 45px;
    text-align: center;
    line-height: 30px;
    margin-left: 50px;
    margin-top: 90px;
}
.Route_form{
    width: 500px;
    height: 100px;
    /* border: 1px solid red; */
    margin-left: -223px;
    margin-top: -60px;
}
.li{
    display: inline-block;
    margin-right: -2px;
    /* height: 80px; */
    margin-top: 5px;
    padding: 60px;
}
.bus_btn,
.walk_btn,
.car_btn{
    transition: background-color 0.3s ease; /* 색상 변화에 대한 전환 효과 */
    cursor: pointer;
    height: 85px;
    width: 120px;
    background-color: white;
    border: 1px solid #ccc;
    color: #979696;
}
.bus_btn{
    position: absolute;
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
}

.car_btn{
    position: absolute;
}
.walk_btn{
    position: absolute;
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;
}
.bus_btn:hover,
.walk_btn:hover,
.car_btn:hover{
    background-color: #bcbdbe;
}
.bus_btn.active,
.walk_btn.active,
.car_btn.active {
    border-color: #28a745;
    background-color: #0df0afb5; /* 배경색의 투명한 초록색 */
}
.col-sm-2{
    width: 700px;
}
.View_route{
    width: 380px;
    height: 400px;
    border: 1px solid #ffffff;
    padding: 10px 10px 10px;
    margin-left: -87px;
    margin-top: 80px;
    max-height: 400px; /* 스크롤 가능한 영역의 최대 높이 설정 */
    overflow-y: auto;
    background-color: rgb(255, 255, 255);
    z-index: 5;
}
.View_route> ul{
    list-style-type: none;
    padding-left: 0;
}
.View_route> ul> li{
    margin-top: 0;
}
.routes-card,
.search-card {
    border: 1px solid #ddd;
    width: 340px;
    height: 100px;
    cursor: pointer;
    border-radius: 10px;
    overflow: hidden; /* 이미지가 카드를 벗어나지 않도록 */
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}
#search-icon{
    width: 32px;
    height: 32px;
    margin-left: -37px;
    position: relative;
    margin-top: 25px;
    z-index: 1061;
}
.card-info {
  width: 100%; /* 내용과 이미지 사이의 비율을 조절 */
  padding: 10px; /* 내용 주변에 여백 추가 */
  padding-left: 40px;
  text-align: left;
  overflow: hidden; /* 텍스트가 넘칠 경우 자르기 */
  white-space: nowrap; /* 텍스트가 한 줄로만 표시되도록 설정 */
  text-overflow: ellipsis; /* 텍스트가 넘칠 경우 마침표로 표시 */
}
.card-info-head {
  text-overflow: ellipsis;  /* 넘친 텍스트를 ...으로 표시 */
  white-space: nowrap;  /* 텍스트를 한 줄에 표시 */
  overflow: hidden;     /* 내용이 넘칠 경우 숨김 처리 */
  font-weight: bold;
}
.card-body {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  height: 100%;
  width: 100%;
}
#index-circle{
    display: inline-block;
    border-radius: 50%;
    width: 25px;
    height: 25px;
    text-align: center;
    background-color: rgb(69, 155, 236);
    color: white;
    margin-left: -5px;
    margin-right: 5px;
}
.routes-card .card-info{
    padding-left: 20px;
}
.drive-ul> li{
    list-style: none;
    margin: 0;
    float: right;
    font-weight: lighter;
    font-size: 15px;
    margin-right: 5px;
}
.drive-ul> li:first-child{
    margin-right: 0px;
}
.routes-card span> strong{
    font-size: 24px;
}
.routes-card span> small{
    margin-left: 10px;
    font-size: 20px;
}
.transit-space:not(:first-child){
    margin-left: 10px;
}

.transit-space> img{
    display: inline-block;
    margin-top:0;
    width: 35px;
    height: 35px;
}
.transit-space> span{
    display: inline-block;
    position: absolute;
    font-weight:bold;
    font-size:12px;
    margin-top:30px;
    margin-left:-35px;
    width: 35px;
    text-align: center;
    color: red;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
#transit-card{
    height:110px;
}
#transit-card .card-info-head{
    margin-top: 10px;
}
#transit-card-body{
    display: block;
    text-align:left;
    padding-left:20px;
    padding-top:10px;
    padding-bottom: 5px;
    border-radius:10px;
    border: 1px solid #ccc;
}
#transit-card-body strong{
    font-size: 24px;
}
#transit-card-body small{
    margin-left:10px;
    font-size: 20px;
}
.card-info> img{
    width:35px;
    height:35px;
}
.transit-info{
    width: 100%;
    height: 100px;
}
.flight-info{
    padding-left: 0;
    width:100%;
    height:70px;
    border: 1px solid #eee;
    border-radius: 5px;
}
.transit-info> .card-info{
    padding-left: 0;
    width:100%;
    height:100%;
    border-top: 1px solid #eee;
}
.flight-info img,
.transit-info>.card-info>span{
    color: red;
    font-weight: bold;
}
#transitName{
    margin-left: 5px;
}
.transit-info> .card-info
.card{
    border:none;
}
#transit-card-info-head{
    margin-top:10px;
}
.flight-ul,
.transit-ul{
    list-style:none;    
    font-size: 14px;
    padding:0;
}
.flight-ul> li,
.transit-ul> li{
    margin: 0;
}
.transit-ul input{
    border: none;
}
.flight-ul input{
    border: none;
    text-align:right;
    margin-right: 15px;
}
.flight-card-info-body,
.transit-card-info-body{
    display: inline-block;
    text-align:right;
}
.transit-card-info-body> span{
    display: inline;
}
.flight-card-info-body> span{
    display: inline;
    margin-right: 15px;
}
.flight-info img,
.transit-info img{
    display: inline-block;
    width:35px;
    height:35px;
}
.col-4{
    padding-left:10px;
    padding-right:0;
}
.col-8{
    text-align:right;
    padding: 0;
}
#flight-card-info{
    padding-left:10px;
    padding-top:0;
}
#planeLoading {
margin-left: auto;
margin-right: auto;
width: 200px;
height: 100px;
}
.plane {
font-size: 40px;
-ms-transform: rotate(0deg);
-webkit-transform: rotate(0deg);
transform: rotate(0deg);
position: relative;
margin-top: 10px;
-webkit-animation: plane 2s infinite;
-webkit-animation-timing-function: linear;
animation: plane 2s infinite;
animation-timing-function: linear;
color: #9893EA
}
@keyframes plane {
0% {
    left: -50%;
}
100% {
    left: 100%;
}
}
.animate {
display: inline-block;
}
.animate .dot {
display: block;
width:8px;
height:8px;
margin-top: 8px;
border-radius:50%;
margin-right:10px;
background: black;
}
#wave0 .dot {
animation: wave0 1s infinite ease-in-out;
animation-duration: 5s;
animation-fill-mode: forwards;
}
#wave1 .dot {
animation: wave0 1s infinite ease-in-out;
animation-duration: 4.65s;
animation-fill-mode: forwards;
}
#wave2 .dot {
animation: wave0 1s infinite ease-in-out;
animation-duration: 4.45s;
animation-fill-mode: forwards;
}
#wave3 .dot {
    animation: wave0 1s infinite ease-in-out;
    animation-duration: 4.3s;
    animation-fill-mode: forwards;
}
@keyframes wave0 {
    100%{
        transform: initial;
    }
    0%, 30%, 60% {
        -webkit-transition: all 200ms ease-in;
        -webkit-transform: scale(1.2);
        -ms-transition: all 200ms ease-in;
        -ms-transform: scale(1.2);
        -moz-transition: all 200ms ease-in;
        -moz-transform: scale(1.2);
        transition: all 200ms ease-in;
        transform: scale(1.2);
        background: #DEDEDE;
    }
}
#search-head{
    margin-top: -50px;
}
#search-body{
    margin-top: -5px;
}
#bus-div{
    border: none;
}
.flight-info> .card-info{
    padding-left: 25px;
}
#flightCol8{
    padding-right: 15px;
}
#bestRoute{
    margin-bottom: 50px;
    border-radius: 8px;
    height: 100px;
}
</style>