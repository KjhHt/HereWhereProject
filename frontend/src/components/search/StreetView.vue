<template>
    <div class="streetview-container rounded" ref="streetView" >
    </div>
</template>
<script setup>
// eslint-disable-next-line no-unused-vars
import {ref, watch, defineProps, onMounted} from 'vue';
let props=defineProps({
    map:Object
})

const streetView=ref(null)
const streetViewPanorama=ref(null)
onMounted(() => {
    watch(() => props.map?.ready, ready => {
    if (!ready || !streetView.value) return;
    props.map.map.addListener('center_changed', () => {
      const newCenter = props.map.map.getCenter();
      if (streetViewPanorama.value) {
        const newPosition = new props.map.api.LatLng(newCenter.lat(), newCenter.lng());
        streetViewPanorama.value.setPosition(newPosition);
      }
    });
    streetViewPanorama.value = new props.map.api.StreetViewPanorama(streetView.value, {
      position: { lat: props.map.map.center.lat(), lng: props.map.map.center.lng() },
      pov: { heading: 165, pitch: 0 },
      zoom: 1,
      visible: true,
      disableDefaultUI: true
    });
    props.map.map.setStreetView(streetViewPanorama.value);
    streetView.value.addEventListener('click', () => {
        toggleFullScreen(streetView.value);
        });
    });

})

watch(() => props.map?.map?.center, newCenter => {
  if (streetViewPanorama.value) {
    console.log('streetViewPanorama.value');
    console.log(streetViewPanorama.value);
    const newPosition = new props.map.LatLng(newCenter.lat(), newCenter.lng());
    streetViewPanorama.value.setPosition(newPosition);
  }
}, { deep: true });

function toggleFullScreen(element) {
  if (!document.fullscreenElement) {
    if (element.requestFullscreen) {
      element.requestFullscreen();
    } else if (element.mozRequestFullScreen) { /* Firefox */
      element.mozRequestFullScreen();
    } else if (element.webkitRequestFullscreen) { /* Chrome, Safari & Opera */
      element.webkitRequestFullscreen();
    } else if (element.msRequestFullscreen) { /* IE/Edge */
      element.msRequestFullscreen();
    }
  } 
}
</script>

<style scoped>
.streetview-container {
  position: absolute;
  top: 20px;
  width: 130px; /* 스트리트뷰 너비 */
  height: 130px; /* 스트리트뷰 높이 */
  border: 1px solid #ccc;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  overflow: hidden;
}
.streetview-container:-webkit-full-screen {
  width: 100%;
  height: 100%;
}
.streetview-container:-moz-full-screen {
  width: 100%;
  height: 100%;
}
.streetview-container:fullscreen {
  width: 100%;
  height: 100%;
}
</style>