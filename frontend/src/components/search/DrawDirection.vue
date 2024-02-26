<template>
  <Polyline :options="driveOptions"></Polyline>
</template>

<script setup>

import { Polyline } from "vue3-google-map";
import { ref,defineProps,watch,defineEmits } from 'vue';

const driveOptions= ref({});
const props= defineProps({
  coords: Object
});

watch(()=>props.coords, coords=>{
  if(coords) {
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

const emit= defineEmits(['cameraSend'])
const sendCamera= coords=>{
  emit('cameraSend',coords)
}
</script>