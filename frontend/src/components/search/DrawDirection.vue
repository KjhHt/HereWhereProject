<template>
  <Polyline v-if="showPolyLine" :options="driveOptions"></Polyline>
</template>

<script setup>

import { Polyline } from "vue3-google-map";
import { ref,defineProps,watch,defineEmits } from 'vue';

const driveOptions= ref({})
let showPolyLine= ref(false)
const props= defineProps({
  coords: Object,
  showRoute: Boolean,
})

watch(()=>props.coords, coords=>{
  if(coords === null) {
    showPolyLine.value= false
    return
  }
  if(coords) {
    showPolyLine.value= true
    driveOptions.value= {
        path: coords,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2,
      }
    sendCamera(coords)
  }
},{deep:true})

watch(()=>props.showRoute, showRoute=>{
  if(!showRoute) showPolyLine.value= false
})

const emit= defineEmits(['cameraSend'])
const sendCamera= coords=>{
  emit('cameraSend',coords)
}
</script>