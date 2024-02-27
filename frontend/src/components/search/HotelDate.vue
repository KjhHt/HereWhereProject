<template>
  <div class="form-wrap">
    <form @submit.prevent="searchHotel">
      <div class="guests">
        <button type="button" id="cnt-down" class="counter-btn" @click="decreaseGuests">
          <i class="bi bi-caret-left-fill"></i>
        </button>
        <input type="text" id="guestNo" name="guests" :value="guests">
        <button type="button" id="cnt-up" class="counter-btn" @click="increaseGuests">
          <i class="bi bi-caret-right-fill"></i>
        </button>
      </div>
      <div class="dates">
        <div class="arrival">
          <label for="arrival">Check-In</label>
          <br>
          <input id="arrival" name="arrival" type="date" v-model="checkInDate" @change="setMinCheckOutDate">
        </div>
        <div class="departure">
          <label for="departure">Check-Out</label>
          <br>
          <input id="departure" name="departure" type="date" v-model="checkOutDate" :min="minCheckOutDate">
        </div>
      </div>
      <button type="submit" class="btn">Search Hotel</button>
    </form>
  </div>
</template>

<script setup>
import { ref, computed, watch, defineProps, getCurrentInstance } from 'vue';

const today = new Date();
const tomorrow = new Date(today.getTime()); // Create a new Date object for tomorrow
tomorrow.setDate(today.getDate() + 1);
let guests = ref(2); // Default value
let checkInDate = ref(today.toISOString().split('T')[0]); // Default check-in date is today
let checkOutDate = ref(tomorrow.toISOString().split('T')[0]); // Initialize check-out date as empty

const props = defineProps({
  places: Object,
  latNumber: Number,
  lngNumber: Number
});

const instance = getCurrentInstance();

const increaseGuests = () => {
  guests.value = Math.min(guests.value + 1, 6); 
};

const decreaseGuests = () => {
  guests.value = Math.max(guests.value - 1, 1); 
};

const setMinCheckOutDate = () => {
  let date = new Date(checkInDate.value);
  date.setDate(date.getDate() + 1);
  checkOutDate.value = date.toISOString().split('T')[0];
};

// Compute minimum check-out date
const minCheckOutDate = computed(() => {
  let date = new Date(checkInDate.value);
  date.setDate(date.getDate() + 1);
  return date.toISOString().split('T')[0]; 
});
watch(checkInDate, setMinCheckOutDate);

const searchHotel = () => {
  let lat, lng;

  if (props.latNumber && props.lngNumber) {
    lat = props.latNumber;
    lng = props.lngNumber;
  } 
  else if (props.places) {
    lat = props.places.geometry.location.lat();
    lng = props.places.geometry.location.lng();
  } 
  else {
    lat = 0;
    lng = 0;
  }

  const searchData = {
    guests: guests.value,
    checkInDate: checkInDate.value,
    checkOutDate: checkOutDate.value,
    lat: lat,
    lng: lng
  };
  instance.emit('search-event', searchData);
};
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700);

body {
  background: #8DFEE1;
  color: #888;
  font: 'Open Sans';
  font-size: 100%;
  overflow: hidden;
}

@media(max-width: 450px) {
  body {
    background: #EBF3F5;
    overflow: scroll;
  }
}


.form-wrap {
  
  text-align: center;
  font-size: 1em;
  width: 100%;
}

input {
  padding: 15px 10px;
  border: none;
  margin-bottom: 20px;
  border-radius: 10px;
  box-shadow: 1px 3px 20px rgba(17, 17, 17, .1);
  width: 100%;
  height: 40px;
  outline: #8DFEE1;
}

input[type=date]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  display: none;
}

#guestNo {
  width: 80px;
  margin-bottom: 10px;
  text-align: center;
  height: 45px;
}

label {
  padding-bottom: 10px;
  display: inline;
  float: left;
}

.counter-btn {
  background-color: white;
}

.counter-btn:nth-child(2) {
  float: right;
}

.dates {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* Adjust .arrival and .departure */
.arrival, .departure {
  width: calc(50% - 5px); /* Subtract margin */
  margin-right: 5px; /* Add margin between elements */
}

.btn {
  border: none;
  padding: 10px;
  margin: 15px auto;
  width: 80%;
  height: 45px;
  display: flex; /* Use Flexbox */
  justify-content: center; /* Center horizontally */
  align-items: center; /* Center vertically */
  background: #a2d4fa;
  color: #fff;
  font-size: 1.3em;
  letter-spacing: 0.1em;
  box-shadow: 1px 3px 20px rgba(17, 17, 17, .1);
  border-radius: 3px;
  outline: none;
}

.btn:hover {
  background: #fd987c;
}



p {
  flex-grow: 1;
}

i {
  flex-grow: 1;
  font-size: 2em;
  color: #888;
}

i:hover {
  color: #6c6c6c;
}

@keyframes ribbon-right {
  0% {
    transform: translate(-30px, -20px);
  }
}

@keyframes ribbon-left {
  0% {
    transform: translate(30px, -20px);
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  80% {
    opacity: 1;
  }
}

</style>

