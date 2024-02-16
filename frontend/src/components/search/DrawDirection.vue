<template>
    <Polyline :options="drivePath"></Polyline>
</template>

<script setup>
  import { Polyline } from "vue3-google-map";
  import axios from 'axios';
  import { ref,defineProps,watchEffect } from 'vue';

  const drivePath= ref({});

  const props= defineProps({
    coords: Array
  })
  watchEffect(()=> {
    if(props.coords && props.coords.length === 2){
      let start= props.coords[0]
      let end= props.coords[1]
      console.log('프롭',props.coords)
      axios.get('https://apis-navi.kakaomobility.com/v1/directions', {
        params: {
          origin: start[1]+','+start[0],
          destination: end[1]+','+end[0],
          waypoints: '',
          priority: 'RECOMMEND',
          car_fuel: 'GASOLINE',
          car_hipass: false,
          alternatives: false,
          road_details: false
        },
        headers: {
          Authorization: `KakaoAK 56184ce4bda79d91a88a15bc795ae6cf`
        }
      })
      .then((response) => {
        let roads= response.data.routes[0].sections[0].roads;
        let direction= [];
        for (let i=0; i<roads.length; i++){
          let vertexes= roads[i].vertexes;
          for (let k = 0; k < vertexes.length; k += 2) {
            direction.push({
              lng: vertexes[k],
              lat: vertexes[k + 1]
            });
          }
        }
        drivePath.value= {
          path: direction,
          geodesic: true,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 2,
        };
      })
      .catch((error) => {
        console.log(error)
      })
    }
  })

</script>