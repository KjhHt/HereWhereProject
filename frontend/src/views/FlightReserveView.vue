<template>
  
  <div class="container">
    <FlightBookingForm 
    :airports="airports" 
    :token="amadeusToken" 
    :initialSearchParameters="initialSearchParameters"
    :mainClickData="mainClickData"
    @search-flight="onSearchFlightByDate"
    @handleSelectChange="handleSelectChange"/>
    <FlightBookingList 
    :flights="flights" 
    :airlines="airlines"
    :airports="airports"
    :loading="loading"
    :travelClass="travelClass"
    />
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted, defineProps } from 'vue';
import FlightBookingForm from '@/components/flight/FlightBookingForm.vue'
import FlightBookingList from '@/components/flight/FlightBookingList.vue';

const loading=ref(false);
//api 토큰
const amadeusToken=ref('');
//공항 목록 - 파이썬 서버로 요청
const airports=ref([]);
//onSearchFlight()로 받아온 항공권 목록
const flights=ref([]);
//항공사 목록 - 파이썬 서버로 요청
const airlines=ref({})
//항공좌석 설정
let travelClass = ref('ECONOMY');

const props=defineProps({
  initialSearchParameters:Object,
})

onMounted(async () => {
  loading.value=true
  await getAirportsList();
  amadeusToken.value=await getAmadeusAccessToken();
  if(props.initialSearchParameters) 
    await onSearchFlight(props.initialSearchParameters);
  else
    loading.value=false;
  
})


async function getAmadeusAccessToken() {
  const clientId = process.env.VUE_APP_AMADEUS_CLIENT_ID; 
  const clientSecret = process.env.VUE_APP_AMADEUS_CLIENT_SECRET;
  try {
    const response = await axios.post('https://api.amadeus.com/v1/security/oauth2/token', `grant_type=client_credentials&client_id=${clientId}&client_secret=${clientSecret}`, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });

    return response.data.access_token;
  } catch (error) {
    console.error('Error getting access token:', error);
    return null;
  }
}

async function getAirportsList(){
  try {
    const response=await axios.get(`${process.env.VUE_APP_PYTHON_API_URL}/airports_list`)
    airports.value=response.data[0]
    airlines.value=response.data[1]
  } catch (error) {
    console.error('getAirPortsList Error:',error)    
  }
}


/*
async function getCheapestDate(){
  const response = await axios.get(`https://api.amadeus.com/v1/shopping/flight-dates`, {
      headers: {
        Authorization: `Bearer ${amadeusToken.value}`,
      },
      params: {
        origin: 'ICN',
        destination: 'MAD',
      }
    });

    console.log('getCheapestDate:',response.data);
}
*/

function handleSelectChange(data){
  console.log('data : ',data);
  travelClass.value = data;
}

async function onSearchFlightByDate(flightSearchParameters) {
  try {
    // getCheapestDate()
    const response = await axios.get(`https://api.amadeus.com/v2/shopping/flight-offers`, {
      headers: {
        Authorization: `Bearer ${amadeusToken.value}`,
      },
      params: {
        originLocationCode: flightSearchParameters.origin, //출발지 공항코드
        destinationLocationCode: flightSearchParameters.destination, //도착지 공항코드
        departureDate: flightSearchParameters.departureDate, //출발일
        adults: flightSearchParameters.adults, //성인 몇명인지
        currencyCode:'KRW', //한화
        travelClass: travelClass.value
      }
    });

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

    flights.value = processedFlights;
  } catch (error) {
    flights.value = [];
    console.error('onSearchFlight Error:', error);
  } finally {
    loading.value=false
  }
}

async function onSearchFlight(flightSearchParameters) {
  loading.value=true
  const dates = [];
  // 오늘 날짜부터 7일간의 날짜 배열 생성
  if(!flightSearchParameters.departureDate){
    for (let i = 0; i < 7; i++) {
    const date = new Date();
    date.setDate(date.getDate() + i);
    dates.push(date.toISOString().split('T')[0]); // YYYY-MM-DD 형식
    }
  }else{
    dates.push(flightSearchParameters.departureDate)
  }
  
  try {
    // getCheapestDate() // 필요한 경우 호출

    const requests = dates.map(departureDate => axios.get(`https://api.amadeus.com/v2/shopping/flight-offers`, {
      headers: {
        Authorization: `Bearer ${amadeusToken.value}`,
      },
      params: {
        originLocationCode: flightSearchParameters.origin,
        destinationLocationCode: flightSearchParameters.destination,
        departureDate: departureDate,
        adults: flightSearchParameters.adults,
        currencyCode: 'KRW', // 한국원화
        travelClass: travelClass.value 
        // 좌석별로 검색되도록 수정여기서 변수값바꾸자,
        // 위에있는 검색함수에도 똑같이 넣어줘야할듯!!
        /*
          ECONOMY - 이코노미 클래스
          PREMIUM_ECONOMY - 프리미엄 이코노미 클래스
          BUSINESS - 비즈니스 클래스
          FIRST - 퍼스트 클래스
        */
      }
    }));

    // Promise.all을 사용하여 모든 날짜에 대한 요청 동시에 처리
    const responses = await Promise.all(requests);
    const allFlights = responses.flatMap(response => response.data.data);

    const processedFlights = allFlights.map(flight => {
      const stopsInfo = flight.itineraries[0].segments.slice(1)
        .map(segment => segment.departure.iataCode);
      const flightClass = flight.travelerPricings[0].fareDetailsBySegment[0].cabin;
      return {
        id: flight.id,
        airlineLogo: `https://pic.tripcdn.com/airline_logo/3x/${flight.itineraries[0].segments[0].carrierCode.toLowerCase()}.webp`,
        airline: flight.itineraries[0].segments[0].carrierCode,
        departureTime: flight.itineraries[0].segments[0].departure.at,
        arrivalTime: flight.itineraries[0].segments[0].arrival.at,
        duration: flight.itineraries[0].duration,
        price: flight.price.total,
        stops: stopsInfo,
        departureAirport: flight.itineraries[0].segments[0].departure.iataCode, // Add departure airport
        destinationAirport: flight.itineraries[0].segments.slice(-1)[0].arrival.iataCode, // Add destination airport
        flightClass
      };
    }).sort((a, b) => parseFloat(a.price) - parseFloat(b.price)); // 가격 순으로 정렬

    // 최저가 항공권만 필터링
    const cheapestFlights = processedFlights.reduce((acc, flight) => {
      const existing = acc.find(f => f.departureTime.split('T')[0] === flight.departureTime.split('T')[0]);
      if (!existing || parseFloat(existing.price) > parseFloat(flight.price)) {
        acc = acc.filter(f => f.departureTime.split('T')[0] !== flight.departureTime.split('T')[0]);
        acc.push(flight);
      }
      return acc;
    }, []);

    flights.value = cheapestFlights;
  } catch (error) {
    flights.value = [];
    console.error('onSearchFlight Error:', error);
  } finally {
    loading.value=false
  }
  
}


</script>
<style scoped>
.container {
  max-width: 1200px; /* 최대 너비 설정 */
  margin: auto; /* 가운데 정렬 */
  padding: 16px; /* 내부 여백 추가 */
  display: flex; /* Flexbox 레이아웃 적용 */
  flex-direction: column; /* 자식 요소들을 세로로 정렬 */
  align-items: center; /* 자식 요소들을 가운데 정렬 */
}
</style>
