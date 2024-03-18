<template>
    <SearchBar searchData
        @keywordChanged="updateKeyword"
        @search="search"
    />

    <div v-show="!loadingModal" class="tripLastMenu"> 
        <div class="tagChattingTitle">
            <h2 :class="{ clicked: selectedMenu === 'wordCloud' }" @click="showWordCloud">인기 태그</h2>
            <h2 :class="{ clicked: selectedMenu === 'chat' }" @click="showChat">실시간 채팅</h2>
        </div>
        <div class="wordCloutContainer" v-show="showWordCloudContainer">
            <img class="wordCloud" :src="wordcloudBase64"/>
        </div>
        <div class="chatContainer" v-show="showChatContainer">
            <TripMomentChatting :myCountry="myCountry"/>
        </div>
    </div>

    <div class="index_trip_moment_con">
        <TripMomentTitle @moveJoinView="$emit('selectPage', 'TripMomentWriteView')"/>
        <TripCard 
            :loadingModal="loadingModal"
            :averageProbabilities="averageProbabilities"
            :boardList="boardList" 
            :no_BoardList="no_BoardList"
            @requestMoreData="loadMoreData"
            @moveDetailView="handleMoveDetailView"
        />
    </div>

    <!-- <button 
        style="display: none;" 
        id="selectPage" 
        @click="$emit('selectPage','TripMomentJoinView')">
    </button> -->

</template>
  
<script setup>
import { ref , onMounted , defineEmits } from 'vue';
import { gsap } from 'gsap';
import SearchBar from '@/components/SearchBar.vue';
import TripMomentTitle from '@/components/TripMomentTitle.vue'
// import TripMomentWorldCloudTitle from '@/components/TripMomentWordCloudTitle.vue'
// import TripMomentChattingTitle from '@/components/TripMomentChattingTitle.vue'
import TripMomentChatting from '@/components/TripMoment/TripMomentChatting.vue'
import TripCard from '@/components/TripCard.vue'
import axios from 'axios';

const num = ref(0);
const boardList = ref([]);
const no_BoardList = ref(false);
const keyword = ref('');
const loadingModal = ref(false);
const wordcloudBase64 = ref('');
const myCountry = ref('');
const averageProbabilities = ref('');

const showWordCloudContainer = ref(true);
const showChatContainer = ref(false);

//api 토큰
const amadeusToken=ref('');

const emit = defineEmits(['selectPageData'])

const animateContainer = (containerToShow, containerToHide) => {
  gsap.to(containerToHide, { autoAlpha: 0, duration: 0.5, onComplete: () => {
    showWordCloudContainer.value = containerToShow === 'wordCloud';
    showChatContainer.value = containerToShow === 'chat';
    gsap.fromTo(containerToShow === 'wordCloud' ? '.wordCloutContainer' : '.chatContainer', 
                { autoAlpha: 0 }, 
                { autoAlpha: 1, duration: 0.5 });
  }});
};

const selectedMenu = ref('wordCloud');

const showWordCloud = () => {
  if (!showWordCloudContainer.value) {
    animateContainer('wordCloud', '.chatContainer');
    selectedMenu.value = 'wordCloud';
  }
};

const showChat = () => {
  if (!showChatContainer.value) {
    animateContainer('chat', '.wordCloutContainer');
    selectedMenu.value = 'chat';
  }
};

const handleMoveDetailView = async (data) => {
  boardList.value = [];
  loadingModal.value = true;

  let iatacode = data.locationList.location_iatacode;
  await onSearchFlightByDate(iatacode); 
  data.flightList = flightList.value;
  
  loadingModal.value = false;

  emit('selectPageData','TripMomentDetailView',data);
    
}

let commentProbabilitiesList = []; 

// getBoardList 함수 정의
const getBoardList = (keyword) => {
    // 게시판 데이터 호출
    axios.get(process.env.VUE_APP_API_URL+'/board', {
        params: {
            num: num.value, // num 변수가 정의되어 있어야 합니다.
            keyword: keyword,
        }
    })
    .then(res => {
        const newBoardList = res.data.map(item => {
            // 프로필 이미지 처리
            if(item.profileimage === '0')
                item.profileimage = require('@/assets/dino.jpg');
            else if(!item.profileimage.startsWith('http')){
                item.profileimage = `data:${res.headers['content-type']};base64,${item.profileimage}`;
            }
            // 게시판 이미지들 처리
            item.base64BoardImages = item.base64BoardImages.map(value => {
                return `data:${res.headers['content-type']};base64,${value}`;
            });
            // 좋아요 리스트 프로필 이미지 처리
            if(item.likeList && item.likeList.length > 0){
                item.likeList = item.likeList.map(likeItem => {
                    if(likeItem.profileimage === '0')
                        likeItem.profileimage = require('@/assets/dino.jpg');
                    else if(!likeItem.profileimage.startsWith('http')){
                        likeItem.profileimage = `data:${res.headers['content-type']};base64,${likeItem.profileimage}`;
                    }
                    return likeItem;
                });
            }
            return item;
        });
        // 새로운 게시물에 대한 comment_probability 리스트 업데이트
        const newCommentProbabilities = newBoardList.map(post => {
            return post.commentList.map(comment => {
                // '부정'인 경우, 100에서 comment_probability 값을 빼서 반환
                if(comment.comment_sentiment === '부정') {
                    return (100 - comment.comment_probability).toFixed(2);
                }
                // 그 외의 경우, comment_probability 값을 그대로 반환
                return comment.comment_probability;
            });
        });
        // 기존 리스트에 새로운 게시물의 comment_probability 리스트를 추가
        commentProbabilitiesList = [...commentProbabilitiesList, ...newCommentProbabilities];
        // 각 게시물의 comment_probability 평균 계산 함수
        const calculateAverageCommentProbability = () => {
            const averages = commentProbabilitiesList.map(commentProbabilities => {
                const sum = commentProbabilities.reduce((acc, probability) => acc + parseFloat(probability), 0);
                const average = sum / commentProbabilities.length;
                return average.toFixed(2);
            });
            return averages;
        }
        // 각 게시물의 comment_probability 평균 계산
        averageProbabilities.value = calculateAverageCommentProbability();
        // 기존 게시물 리스트 업데이트
        boardList.value = [...boardList.value, ...newBoardList];
        if(newBoardList.length === 0) {
            no_BoardList.value = true; // 더 이상 추가할 게시물이 없을 경우
        }
        
        loadingModal.value = false; // 로딩 모달 해제
    })
    .catch(err => console.log(err));
}

let flightList = ref([]);

async function onSearchFlightByDate(iatacode) {
  try {
    const response = await axios.get(`https://api.amadeus.com/v2/shopping/flight-offers`, {
      headers: {
        Authorization: `Bearer ${amadeusToken.value}`,
      },
      params: {
        originLocationCode: 'ICN', //출발지 공항코드
        destinationLocationCode: iatacode, //도착지 공항코드
        departureDate: '2024-03-16', //출발일
        adults: 1, //성인 몇명인지
        currencyCode:'KRW', //한화
      }
    });


    if(response.data.data && response.data.data.length > 0){
          // 받은 데이터를 처리
        const processedFlights = response.data.data.map(flight => {
        const stopsInfo = flight.itineraries[0].segments.slice(1) // 첫 번째 세그먼트는 출발지를 나타내므로 제외
          .map(segment => segment.departure.iataCode); // 각 세그먼트의 출발 공항 코드
        const flightClass = flight.travelerPricings[0].fareDetailsBySegment[0].cabin;
        return {
          id: flight.id,
          airlineLogo:`https://pic.tripcdn.com/airline_logo/3x/${flight.itineraries[0].segments[0].carrierCode.toLowerCase()}.webp`,
          airline: flight.itineraries[0].segments[0].carrierCode,
          departureTime: flight.itineraries[0].segments[0].departure.at,
          arrivalTime: flight.itineraries[0].segments[0].arrival.at,
          duration: flight.itineraries[0].duration,
          price: flight.price.total,
          stops: stopsInfo, // 경유지 정보 추가
          departureAirport: flight.itineraries[0].segments[0].departure.iataCode, // Add departure airport
          destinationAirport: flight.itineraries[0].segments.slice(-1)[0].arrival.iataCode, // Add destination airport
          flightClass
        };
      }).sort((a, b) => parseFloat(a.price) - parseFloat(b.price)); // 가격 순으로 정렬
      
        flightList.value.push(getCountryByIata(processedFlights[0].departureAirport));
        flightList.value.push(getCountryByIata(processedFlights[0].destinationAirport));
        flightList.value.push(processedFlights[0].stops.length);
        flightList.value.push(processedFlights[0].airlineLogo);
        flightList.value.push(processedFlights[0].price);
        flightList.value.push(processedFlights[0].arrivalTime);
        flightList.value.push(processedFlights[0].departureTime);
      
    }
  } catch (error) {
    console.error('onSearchFlight Error:', error);
  } 
}

function getCountryByIata(iataCode) {
  const airport = airports.value.find((airport) => airport.IATA === iataCode);
  return airport ? airport.AIRPORT_NAME : iataCode;
}

const airlines=ref({})
const airports=ref([]);

async function getAirportsList(){
  try {
    const response=await axios.get(`${process.env.VUE_APP_PYTHON_API_URL}/airports_list`)
    airports.value=response.data[0]
    airlines.value=response.data[1]
  } catch (error) {
    console.error('getAirPortsList Error:',error)    
  }
}









const loadMoreData = () => {
    num.value += 8;
    getBoardList(keyword.value);
}

const updateKeyword = (newKeyword) => {
  keyword.value = newKeyword;  // 검색어를 갱신
};

const search = () => {
    num.value = 0;  // 페이지 번호 초기화
    boardList.value = [];  // 게시글 목록 초기화
    no_BoardList.value = false; // 게시글옵저버 초기화
    loadingModal.value = true;
    getBoardList(keyword.value);  // 게시글 목록을 새로 불러
}

const wordCloudImage = () => {
    axios.get(process.env.VUE_APP_PYTHON_API_URL+'/wordcloud')
    .then(res => {
        wordcloudBase64.value = res.data.wordcloud_image;
    })
    .catch(err => console.log(err))

}

// 위치 정보를 가져오는 함수
function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else {
    console.log("Geolocation is not supported by this browser.");
  }
}

// 위치 정보를 출력하는 함수
function showPosition(position) {
  getCountry(position.coords.latitude,position.coords.longitude);
}

function getCountry(latitude, longitude) {
  // Google Maps Geocoding API URL / 
  const url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&language=en&key=${process.env.VUE_APP_GEOCODING_API_KEY}`;
  // axios 요청
  axios.get(url)
    .then(response => {
        const addressComponents = response.data.results[0].address_components;
        let country = null;
        // 국가명 추출
        for (let i = 0; i < addressComponents.length; i++) {
            if (addressComponents[i].types.indexOf("country") > -1) {
            country = addressComponents[i].long_name;
            break;
            }
        }
        // 국가명 출력
        if (country) {
            myCountry.value = country;
        } else {
            console.log("Country not found");
        }
    })
    .catch(error => {
      console.log(error);
    });
}

async function getAmadeusAccessToken() {
  const clientId = process.env.VUE_APP_AMADEUS_CLIENT_ID; 
  const clientSecret = process.env.VUE_APP_AMADEUS_CLIENT_SECRET;
  try {
    const response = await axios.post('https://api.amadeus.com/v1/security/oauth2/token', `grant_type=client_credentials&client_id=${clientId}&client_secret=${clientSecret}`, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
    return await response.data.access_token;
  } catch (error) {
    console.error('Error getting access token:', error);
    return null;
  }
}


 

onMounted(async () => {
    loadingModal.value = true;
    await getAirportsList();
    amadeusToken.value = await getAmadeusAccessToken();
    getBoardList('');
    wordCloudImage();
    getLocation();
});

</script>

<style scoped>
.tagChattingTitle h2 {
    display: inline-block;
    cursor: pointer;
    margin: 0 10px;
    padding-bottom: 5px;
}

.clicked {
    border-bottom: 2px solid black;
}

.tripLastMenu {
    /* display: flex;  Flexbox를 사용하여 요소를 수평으로 배치합니다. */
    /* justify-content: space-between;  요소를 좌우에 배치합니다. */
    /* align-items: center;  요소를 수직으로 가운데 정렬합니다. */
    max-width: 1160px;
    margin: 0 auto;
    margin-top: 50px;
    max-height: 500px;
    min-height: 500px;
}

.wordCloud {
    /* 이미지에 대한 스타일을 지정합니다. */
    /* width: 500px;  이미지 너비를 최대 50%로 지정합니다. */
    height: 400px; /* 이미지 높이를 자동으로 조정합니다. */
    margin-top: 25px;
}

.chatContainer {
    /* 대화 컨테이너에 대한 스타일을 지정합니다. */
    flex-grow: 1; /* 남은 공간을 모두 차지하도록 합니다. */
    margin-left: 30px; /* 좌측 여백을 추가합니다. */
    height: 500px;
}



.gl-cpt-search-big-contain {
    max-width: 1160px;
    height: 148px;
    background: #fff;
    border: 1px solid #f0f2f5;
    -webkit-box-shadow: 0 4px 16px 0 rgba(69,88,115,.2);
    box-shadow: 0 4px 16px 0 rgba(69,88,115,.2);
    margin: 0 auto;
    position: relative;
    z-index: 9;
    padding: 0 24px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border-radius: 2px;
}
div{
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline; 
}
.index_trip_moment_con{
    position: relative;
    background-color: #fff;
    max-width: 1160px;
    margin: 0px auto;
}
.index_trip_moment_con .bottom_desc {
    height: 40px;
    line-height: 20px;
    margin-bottom: 12px;
    font-size: 14px;
    word-break: break-all;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
}

</style>