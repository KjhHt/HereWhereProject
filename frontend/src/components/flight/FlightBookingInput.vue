<template>
    <SimpleTypeahead
        ref="typeahead"
        class="autocomplete form-control"
        :placeholder="placeholder"
        :items="airportsList"
        @select-item="selectItem"
        :item-projection="itemProjection"
        :minInputLength="1"
        :default-item="defaultItem"
    />
</template>

<script setup>
import { defineProps, defineEmits, ref, watch } from 'vue';
import SimpleTypeahead from 'vue3-simple-typeahead';
import 'vue3-simple-typeahead/dist/vue3-simple-typeahead.css';
import axios from 'axios';

const props = defineProps({
    placeholder: String,
    token: String,
    airports: Array,
    defaultItem: Object,
});

const typeahead = ref(null);
const airportsList = ref([]);
const selectedAirportCodes = ref('');
const emits = defineEmits(['selectOrigin', 'selectDestination', 'alert']);

watch(() => props.airports, newAirports => {
    if (!newAirports) return;
    airportsList.value = newAirports.map(airport => `${airport.COUNTRY}-${airport.AIRPORT_NAME}(${airport.IATA})`);
    if(props.defaultItem)
        typeahead.value._.data.input = airportsList.value.find(data => data.includes(typeahead.value._.data.input));
});

async function searchAirports(query) {
    if (!props.token) {
        console.log('token error:', props.token);
        return;
    }
    try {
        const response = await axios.get(`https://api.amadeus.com/v1/reference-data/locations`, {
            params: {
                subType: 'AIRPORT,CITY',
                keyword: query,
                // latitude: 37.4923,
                // longitude: 127.0292,
                // radius:100
            },
            headers: {
                Authorization: `Bearer ${props.token}`,
                'Accept-Language': 'ko'
            },
        });
        return response.data.data
    } catch (error) {
        console.error('Error fetching airports:', error);
    }
}

function selectItem(selectedItem) {
    if(!selectedItem) return;
    let iataCode = '';
    if( selectedItem.length === 3 ){
        iataCode = selectedItem;
    }
    else{
        iataCode = selectedItem.split('(')[1].split(')')[0];
    }
    // selectedItem에서 IATA 코드 추출
    const placeholderType = props.placeholder;
    const data=searchAirports(iataCode)
    if(data.length) {
        alert(`${placeholderType}으로 검색되는 공항 데이터가 없습니다.`)
        return
    }
    selectedAirportCodes.value = iataCode;
    if (placeholderType === '출발지') {
        emits('selectOrigin', iataCode);
    } else {
        emits('selectDestination', iataCode);
    }

}

function itemProjection(item) {
    return item.toString();
}


</script>


<style scoped>
</style>