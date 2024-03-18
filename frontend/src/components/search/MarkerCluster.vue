<template>
    <MarkerCluster>
        <!--인포윈도우 마커표시<MapMarker v-if="targetLocation" :options="{position:targetLocation}" @click="selectMarker"/>-->
        <CustomMarker v-for="(plan,index) in props.plansMarker" :key="index" 
                      :options="{ position: {lat:parseFloat(plan.plan_latitude),
                                              lng:parseFloat(plan.plan_longitude) }}"
                      @click="clickPlanMarker(plan)" >
          <div style="text-align: center">
              <img 
                  class="rounded-circle" 
                  src="@/assets/mapiconlg.png" 
                  width="50" height="50" style="margin-bottom: 15px" 
                  />
          </div>
        </CustomMarker>
        <div v-if="props.status==='hotel'">
          <CustomMarker v-for="hotel in props.hotelsInfo" :key="hotel.hotel"  :options="{ position: {lat:hotel.lat,lng:hotel.lng} }" @click="clickMarker(hotel)">
            <div style="text-align: center">
                <img 
                    class="rounded-circle" 
                    src="@/assets/hotel.png" 
                    width="50" height="50" style="margin-bottom: 20px" 
                    />
            </div>
          </CustomMarker>
        </div>
        <div v-else-if="props.status==='restaurant'">
          <CustomMarker v-for="restaurant in props.restaurantsInfo" :key="restaurant.id" :options="{position: {lat:restaurant.lat,lng:restaurant.lng}}" @click="clickMarker(restaurant)">
            <div style="text-align: center">
                <img 
                    class="rounded-circle" 
                    src="@/assets/restaurant.png" 
                    width="50" height="50" style="margin-top: 8px" 
                    />
            </div>
          </CustomMarker>
        </div>
        <div v-else-if="props.status==='attraction'">
          <CustomMarker v-for="attraction in props.attractionsInfo" :key="attraction.attraction" :options="{position: {lat:attraction.lat,lng:attraction.lng}}" @click="clickMarker(attraction)">
            <div style="text-align: center">
                <img 
                    class="rounded-circle" 
                    src="@/assets/attraction.png" 
                    width="50" height="50" style="margin-bottom: 15px" 
                    />
            </div>
          </CustomMarker>
        </div>
        <CustomMarker v-for="(stopOver,index) in props.stopOver" :key="index" :options="{position: {lat:stopOver.lat,lng:stopOver.lng}}">
          <div style="text-align: center">
              <img 
                  class="rounded-circle" 
                  src="@/assets/stopover.png" 
                  width="40" height="40" style="margin-bottom: 30px" 
                  />
          </div>
        </CustomMarker>
        <CustomMarker v-if="Object.keys(props.places).length" :options="{position: props.places.geometry.location}" @click="clickMarker(props.places)">
          <div style="text-align: center">
              <img 
                  class="rounded-circle" 
                  src="@/assets/mapiconlg.png" 
                  width="50" height="50" style="margin-bottom: 15px" 
                  />
          </div>
        </CustomMarker>
    </MarkerCluster>
</template>

<script setup>
import { MarkerCluster,CustomMarker } from "vue3-google-map"
import { defineProps,defineEmits } from "vue"

const props= defineProps({
    attractionsInfo: Object,
    plansMarker: Array,
    hotelsInfo: Object,
    restaurantsInfo: Object,
    locationLatLng : Object,
    placeLatLng : Object,
    stopOver: Array,
    status: String,
    places: Object
})

const emit= defineEmits(['clickMarker','clickPlanMarker']);

const clickMarker=places=>{
    emit('clickMarker',places);
}

const clickPlanMarker=plan=>{
    emit('clickPlanMarker',plan);
}
</script>