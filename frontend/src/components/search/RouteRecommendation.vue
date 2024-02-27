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
                <div class="card w-100 mb-3">
                    <div class="card-body" id="transit-card-body" v-for="(transitInfo,index) in transitList" :key="index" @click="clickTransit(transitInfo)">
                        <div class="transit-card-info" data-bs-toggle="collapse" :data-bs-target="'#transitInfo'+index" aria-expanded="false" :aria-controls="'transitInfo'+index">
                            <div id="index-circle"> {{ index+1 }} </div>
                            <span v-for="(step,index) in transitInfo.legs[0].steps" :key="index">
                                <span class="transit-space" v-if="step.travel_mode==='TRANSIT' && step.transit.line.name.indexOf('버스') !== -1">
                                    <img :src="require('@/assets/icon-bus.png')"/>
                                    <span>{{ step.transit.line.short_name.replace(/\D/g, '') }}</span>                              
                                </span>
                                <span class="transit-space" v-if="step.travel_mode==='TRANSIT' && step.transit.line.name.indexOf('지하철') !== -1">
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
                                                    <img v-if="step.travel_mode==='TRANSIT' && step.transit.line.name.indexOf('버스') !== -1" :src="require('@/assets/icon-bus.png')" />
                                                    <img v-if="step.travel_mode==='TRANSIT' && (step.transit.line.name.indexOf('지하철') !== -1 || step.transit.line.name.indexOf('전철') !== -1)" :src="require('@/assets/icon-subway.png')" />
                                                    <img v-if="step.travel_mode === 'WALKING'" :src="require('@/assets/icon-walking.png')" />  
                                                    <span v-if="step.travel_mode==='TRANSIT'">{{ step.transit.line.short_name }}</span>
                                                    <span></span>
                                                </div>
                                                <div class="col-8">
                                                    <div class="transit-card-info-body">
                                                        <span><strong>{{ step.duration.text }}</strong> <small>{{ step.distance.text }}</small></span>
                                                        <ul class="transit-ul">
                                                            <li v-if="step.travel_mode==='TRANSIT' && 
                                                                    (step.transit.line.name.indexOf('지하철') !== -1 || step.transit.line.name.indexOf('전철') !== -1 || step.transit.line.name.indexOf('버스') !== -1)">
                                                                {{ step.transit.arrival_stop.name }}역 ~ {{ step.transit.departure_stop.name }}역
                                                            </li>
                                                            <li v-else>
                                                                {{ step.instructions }}
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
                            <div id="index-circle">1</div>
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
                            <div class="card-info-head">
                            <p>{{ searchInfo.name }}</p>
                            </div>
                            <div class="card-info-body">
                            <p> {{ searchInfo.formatted_address }}</p>
                            </div>
                        </div>
                    </div>
                </div>
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
import { defineProps, ref, watch, defineEmits } from 'vue'
import axios from 'axios'
import { getDistanceInKm } from '@/composable/custom';

const departuresRef=ref(null)
const arrivalsRef=ref(null)
//const transitDetailRef= ref(null)
let activeMode = ref(null)
let searchList= ref([])
let driveList= ref([])
let transitList= ref([])
let walkInfo= ref([])
const placesService= ref()
const directionsService= ref()
const directionsRenderer= ref()
const geocoder= ref()
let routeLocation= ref({})
let showRoading= ref(false)
let searchTarget
let transitResponse

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

watch(() => props.map?.ready, ready => {
    if (!ready) return;
    // Google Maps Places 라이브러리가 로드되었는지 확인
    if (props.map.api && props.map.api.places) {
        placesService.value= new props.map.api.places.PlacesService(props.map.map)
        directionsService.value= new props.map.api.DirectionsService()
        directionsRenderer.value= new props.map.api.DirectionsRenderer();
        geocoder.value= new props.map.api.Geocoder()
        // departures 입력 필드에 대한 Autocomplete 인스턴스 생성
        const departuresAutocomplete = new props.map.api.places.Autocomplete(departuresRef.value, autoCompleteOptions);
        // arrivals 입력 필드에 대한 Autocomplete 인스턴스 생성
        const arrivalsAutocomplete = new props.map.api.places.Autocomplete(arrivalsRef.value, autoCompleteOptions);

        departuresAutocomplete.addListener('place_changed',()=>{
            console.log('출발지:',departuresAutocomplete.getPlace())
        })
        arrivalsAutocomplete.addListener('place_changed',()=>{
            console.log('도착지:',arrivalsAutocomplete.getPlace())
        })
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

watch(()=>props.showRoute, showRoute=>{
    console.log('경로감지')
    if(!showRoute) {
        if(directionsRenderer.value) directionsRenderer.value.setMap(null)
        activeMode.value= null
        document.querySelectorAll('.bus_btn, .car_btn, .walk_btn').forEach(button => {
            if(button.classList.contains('active')) button.classList.remove('active')
        });
    }
})

const searchPlace=(e)=>{        //출발지, 도착지 엔터로 검색
    console.log('이벤트',e)
    placesService.value.textSearch({query:e.target.value},(result,status)=>{
        console.log('상태',status)
        if(status === 'OK'){
            searchList.value= result
            document.querySelectorAll('.bus_btn, .car_btn, .walk_btn').forEach(button => {
                if(button.classList.contains('active')) button.classList.remove('active')
            })
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


const findRoute= async(route)=> { //경로 찾기 이벤트
    driveList.value= []
    transitList.value= []
    walkInfo.value= []
    showRoading.value= true;
    let driveResponse= await getDriveRoute(route)
    transitResponse= await getTransitRoute(route)
    let walkResponse
    if(getDistanceInKm(route.departures.lat, route.departures.lng,  //출발지와 목적지의 직선거리가 30km이하일때만 도보경로 얻기
            route.arrivals.lat, route.arrivals.lng) < 30){
        walkResponse= await getWalkRoute(route)
    }
    if(driveResponse){
        driveResponse.data.routes.forEach(routes=>{
            routes.summary.distance = displayDistance(routes.summary.distance);
            routes.summary.duration = displayDuration(routes.summary.duration);
            if(routes.summary.fare.toll===0) delete routes.summary.fare.toll
            else routes.summary.fare.toll= '통행료'+routes.summary.fare.toll+'원'
        })
        driveList.value= driveResponse.data.routes
    }
    transitList.value= transitResponse.routes
    if(walkResponse) {
        walkInfo.value= walkResponse.data
        let properties= walkInfo.value.features[0].properties
        properties.totalDistance= displayDistance(properties.totalDistance)
        properties.totalTime= displayDuration(properties.totalTime)
        console.log('워크인포',walkInfo.value)
    }
    showRoading.value= false;
    document.querySelector('.bus_btn').click()
  }

  async function getDriveRoute(route){ //카카오 운전 경로 API
    let response = await axios.get('https://apis-navi.kakaomobility.com/v1/directions', {
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
    return response;
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

async function getWalkRoute(route){ //TMAP 도보 경로 API
    try{
        let headers= {appKey: process.env.VUE_APP_TMAP_API_KEY}
        let request= {
            startX: route.departures.lng,
            startY: route.departures.lat,
            endX: route.arrivals.lng,
            endY: route.arrivals.lat,
            reqCoordType: "WGS84GEO",
            resCoordType: "WGS84GEO",
            startName: "출발지",
            endName: "도착지"
        }
        let response= await axios.post('https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1',
                                    request, {headers: headers})
        return response
    }catch(error) {
        console.log('T맵에러',error)
    }
}

const emit= defineEmits(['passRouteLocation','setDraw'])

const setDraw=drivePath=>{
    emit('setDraw',drivePath)
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
    setDraw(drivePath)
}

const clickWalk=(walkInfo)=>{
    console.log('워크인뽀',walkInfo)
    let features= walkInfo.features
    let walkPath=[]
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
    setDraw(walkPath)
}

const clickTransit=(transitInfo)=>{
    transitResponse.routes= [transitInfo]
    setDraw(null)
    directionsRenderer.value.setMap(props.map.map)
    directionsRenderer.value.setDirections(transitResponse)
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
    margin-left: -70px;
    position: absolute;
    margin-top: 58px;
    z-index: 1061;
}
#icon-route1{
    width: 32px;
    height: 32px;
    margin-left: -70px;
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
   margin-left: 15px;
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
    position: absolute;
    margin-top: 25px;
    z-index: 1061;
}
.card-info {
  width: 100%; /* 내용과 이미지 사이의 비율을 조절 */
  padding: 10px; /* 내용 주변에 여백 추가 */
  padding-left: 40px;
  text-align: left;
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
    margin-top:-5px;
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
    border-top: 1px; 
    width: 100%;
    height: 100px;
}
.transit-info> .card-info{
    padding-left: 0;
    width:100%;
    height:100%;
    border-top: 1px solid #eee;
}
.transit-info img{
    display: inline-block;
}
.transit-info>.card-info>span{
    color: red;
    font-weight: bold;
}
.card{
    border:none;
}
#transit-card-info-head{
    margin-top:10px;
}
.transit-ul{
    list-style:none;    
    font-size: 14px;
    padding:0;
}
.transit-ul> li{
    margin: 0;
}
.transit-card-info-body{
    display: inline-block;
    text-align:right;
}
.transit-card-info-body> span{
    display: inline;
}
.transit-info img{
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
</style>