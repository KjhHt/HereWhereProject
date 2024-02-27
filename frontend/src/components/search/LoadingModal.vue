<template>
  <div v-if="props.loading" id="planeLoading">
    <span class="plane fa fa-plane"></span>
    <div id="wave0" class="animate">
      <span class="dot"></span>
    </div>
    <div id="wave1" class="animate">
      <span class="dot"></span>
    </div>
    <div id="wave2" class="animate">
      <span class="dot"></span>
    </div>
    <div id="wave3" class="animate">
      <span class="dot"></span>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
loading: Boolean,
});
</script>

<style scoped>
#planeLoading {
margin-left: auto;
margin-right: auto;
width: 200px;
height: 100px;
margin-top: 40px;
}
.plane {
font-size: 40px;
-ms-transform: rotate(0deg);
-webkit-transform: rotate(0deg);
transform: rotate(0deg);
position: relative;
margin-top: 10px;
-webkit-animation: plane 2s infinite;
-webkit-animation-timing-function: linear;
animation: plane 2s infinite;
animation-timing-function: linear;
color: #9893ea
}
@keyframes plane {
0% {
    left: -50%; 
}
100% { 
    left: 100%; 
}
}
.animate {
display: inline-block;
}
.animate .dot {
display: block;
width:8px;
height:8px;
margin-top: 8px;
border-radius:50%;
margin-right:10px;
background: black;
}
#wave0 .dot {
animation: wave0 1s infinite ease-in-out;
animation-duration: 5s;
animation-fill-mode: forwards;
}
#wave1 .dot {
animation: wave0 1s infinite ease-in-out;
animation-duration: 4.65s;
animation-fill-mode: forwards;
}
#wave2 .dot {
animation: wave0 1s infinite ease-in-out;
animation-duration: 4.45s;
animation-fill-mode: forwards;
}
#wave3 .dot {
    animation: wave0 1s infinite ease-in-out;
    animation-duration: 4.3s;
    animation-fill-mode: forwards;
}
@keyframes wave0 {
100%{
    transform: initial;
}
0%, 30%, 60% {
    -webkit-transition: all 200ms ease-in;
    -webkit-transform: scale(1.2);
    -ms-transition: all 200ms ease-in;
    -ms-transform: scale(1.2);   
    -moz-transition: all 200ms ease-in;
    -moz-transform: scale(1.2);
    transition: all 200ms ease-in;
    transform: scale(1.2);
    background: #dedede;
}
}
</style>