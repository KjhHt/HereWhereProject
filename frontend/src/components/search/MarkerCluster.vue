<template>
    <MarkerCluster>
          <!--인포윈도우 마커표시<MapMarker v-if="targetLocation" :options="{position:targetLocation}" @click="selectMarker"/>-->
          <CustomMarker v-for="inter in props.intersInfo" :key="inter.name" 
                        :options="{ position: {lat:inter.geometry.location.lat(),
                                               lng:inter.geometry.location.lng() }}"
                        @click="clickMarker(inter)" >
            <div style="text-align: center">
                <img 
                    class="rounded-circle" 
                    src="@/assets/Cat.png" 
                    width="40" height="40" style="margin-bottom: 40px" 
                    />
            </div>
          </CustomMarker>
          <CustomMarker v-for="hotel in props.hotelsInfo" :key="hotel.hotel"  :options="{ position: {lat:hotel.lat,lng:hotel.lng} }" @click="clickMarker(hotel)">
            <div style="text-align: center">
                <img 
                    class="rounded-circle" 
                    src="@/assets/hotel.png" 
                    width="50" height="50" style="margin-bottom: 20px" 
                    />
            </div>
        </CustomMarker>
        <CustomMarker v-for="restaurant in props.restaurantsInfo" :key="restaurant.id" :options="{position: {lat:restaurant.lat,lng:restaurant.lng}}" @click="clickMarker(restaurant)">
          <div style="text-align: center">
              <img 
                  class="rounded-circle" 
                  src="@/assets/restaurant.png" 
                  width="50" height="50" style="margin-top: 8px" 
                  />
          </div>
        </CustomMarker>
        <CustomMarker v-for="attraction in props.attractionsInfo" :key="attraction.attraction" :options="{position: {lat:attraction.lat,lng:attraction.lng}}" @click="clickMarker(attraction)">
          <div style="text-align: center">
              <img 
                  class="rounded-circle" 
                  src="@/assets/attraction.png" 
                  width="50" height="50" style="margin-bottom: 15px" 
                  />
          </div>
        </CustomMarker>
        <CustomMarker :options="{position: {lat:parseFloat(props.locationLatLng[0]),lng:parseFloat(props.locationLatLng[1])}}">
          <div style="text-align: center">
              <img 
                  class="rounded-circle" 
                  src="@/assets/mapiconlg.png" 
                  width="50" height="50" style="margin-bottom: 15px" 
                  />
          </div>
        </CustomMarker>
        <CustomMarker v-if="props.placeLatLng" :options="{position: {lat:props.placeLatLng.lat,lng:props.placeLatLng.lng}}">
          <div style="text-align: center">
              <img 
                  class="rounded-circle" 
                  src="@/assets/mapiconlg.png" 
                  width="50" height="50" style="margin-bottom: 15px" 
                  />
          </div>
        </CustomMarker>
        <CustomMarker v-for="(stopOver,index) in props.stopOver" :key="index" :options="{position: {lat:stopOver.lat,lng:stopOver.lng}}">
          <div style="text-align: center">
              <img 
                  class="rounded-circle" 
                  src="@/assets/stopover.png" 
                  width="40" height="40" style="margin-bottom: 30px" 
                  />
          </div>
        </CustomMarker>
    </MarkerCluster>
</template>

<script setup>
import { MarkerCluster,CustomMarker } from "vue3-google-map"
import { defineProps,defineEmits,watch } from "vue"

const props= defineProps({
    attractionsInfo: Object,
    plansInfo: Object,
    hotelsInfo: Object,
    restaurantsInfo: Object,
    locationLatLng : Object,
    placeLatLng : Object,
    stopOver: Array
})

const emit= defineEmits(['clickMarker'])

const clickMarker=(places)=>{
    emit('clickMarker',places)
}

watch(()=>props.stopOver, stopOver=>{
  console.log('경유지', stopOver)
})

</script>