<template>
  <div class="container mt-3">
    <!-- Form Wrapper -->
    <div class="form-wrapper">
      <!-- Flight Booking Inputs -->
      <div class="booking-inputs">
        <flight-booking-input 
        class="booking-input" 
        :placeholder="placeholder[0]"
        :airports="airports" 
        :token="token"
        :defaultItem="initialSearchParameters.origin"
        @selectOrigin="updateOrgin"/>

        <flight-booking-input 
        class="booking-input" 
        :placeholder="placeholder[1]" 
        :airports="airports" 
        :token="token"
        :defaultItem="initialSearchParameters.destination"
        @selectDestination="updateDestination"/>
      </div>

      <!-- Date Selector -->
      <div class="date-selector">
        <input class="date-input" type="date" v-model="flightBook.departureDate">
      </div>

      <!-- Search Button -->
      <div class="search-button">
        <button @click="searchFlights"><i class="bi bi-search"></i></button>
      </div>

      <select class="form-control select-box" @change="handleSelectChange">  
        <option value="ECONOMY">ECONOMY</option>
        <option value="PREMIUM_ECONOMY">PREMIUM_ECONOMY</option>
        <option value="BUSINESS">BUSINESS</option>
        <option value="FIRST">FIRST</option>
      </select>
    </div>
  </div>
</template>



<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';

import FlightBookingInput from '@/components/flight/FlightBookingInput.vue';

// eslint-disable-next-line no-unused-vars
const props=defineProps({
  airports:Array,
  token:String,
  initialSearchParameters:Object,
})


const airportsList = ref([]);

watch(() => props.airports, newAirports => {
    if (!newAirports) return;
    airportsList.value = newAirports.map(airport => `${airport.COUNTRY}-${airport.AIRPORT_NAME}(${airport.IATA})`);
});

const emits=defineEmits(['searchFlight','handleSelectChange'])

const placeholder=ref(['출발지','도착지'])

const flightBook=ref({
  origin:'',
  destination:'',
  departureDate:'',
  adults: 1,
  // classType:'economy', //
  // journeyType:'round-trip'  //round-trip , one-way
})

watch(()=>flightBook.value,newFlightBook=>{
  const origin=newFlightBook.origin
  const destination=newFlightBook.destination
  if(origin==destination) alert('출발지와 도착지가 동일할 수 없습니다.')
},{deep:true})

/*
function incrementPassengers() {
  flightBook.value.passengers += 1;
}

function decrementPassengers() {
  if (flightBook.value.passengers > 1) {
    flightBook.value.passengers -= 1;
  }
}
*/
//좌석 선택
function handleSelectChange(event){
  emits('handleSelectChange',event.target.value);
}

function updateOrgin(code){
  flightBook.value.origin=code  
}

function updateDestination(code) {
  flightBook.value.destination=code
}

function searchFlights() {
  let empty=''
  if(!flightBook.value.origin||flightBook.value.origin.length==0) empty='출발지'
  else if(!flightBook.value.destination||flightBook.value.destination.length==0) empty='도착지'
  else if(!flightBook.value.departureDate||flightBook.value.departureDate.length==0) empty='날짜'
  
  if(empty) {
    alert(`${empty}를 선택해주세요`)
    return
  }
  emits('searchFlight',flightBook.value)
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: auto;
  display: flex;
  justify-content: center;
}

.form-wrapper {
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  gap: 16px;
  width: 100%;
}

.booking-inputs {
  display: flex;
  align-items: center;
  gap: 8px;
}

.booking-input,
.date-input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  height: 40px;
  box-sizing: border-box;
  transition: border-color 0.3s;
  font-size: 1rem; /* 반응형 글씨 크기 */
}

.booking-input:focus,
.date-input:focus {
  border-color: #007bff;
}

.exchange-button {
  padding: 0 10px;
  border: none;
  background: none;
  cursor: pointer;
  color: #007bff;
  transition: transform 0.3s;
  font-size: 1.5rem;
}

.exchange-button:hover {
  transform: scale(1.1);
}

.search-button button {
  padding: 10px 30px;
  background: #9893EA;
  color: #fff;
  border: none;
  border-radius: 20px;
  height: 40px;
  box-sizing: border-box;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  font-size: 1.25rem; /* 반응형 글씨 크기 */
  display: flex;
  justify-content: center;
  align-items: center; /* Add this line to align items vertically in the center */
}

.search-button button:hover {
  background-color: #7771e9;
  transform: translateY(-2px);
}

.select-box {
  width: 190px;
  margin-left: auto;
}

@media (max-width: 768px) {
  .form-wrapper {
    flex-direction: column;
    gap: 20px;
  }

  .booking-input,
  .date-input,
  .search-button button {
    width: 100%;
    font-size: 0.875rem;
  }

  .exchange-button {
    display: none;
  }
}
</style>





