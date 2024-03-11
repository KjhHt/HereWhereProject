<template>
  <div v-if="loading" id="loading">
    <div class="wavy">
      <span style="--i:1;">추</span>
      <span style="--i:2;">천</span>
      <span style="--i:3;">여</span>
      <span style="--i:4;">행</span>
      <span style="--i:5;">지</span>
      <span style="--i:6;">분</span>
      <span style="--i:7;">석</span>
      <span style="--i:8;">중</span>
      <span style="--i:9;">.</span>
      <span style="--i:10;">.</span>
      <span style="--i:11;">.</span>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref, watchEffect } from 'vue';

const props = defineProps({
    loading: Boolean
});

const loadingKey = ref(0);

watchEffect(() => {
  if (props.loading) {
    loadingKey.value++;
  }
});
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  font-family: consolas;
}

body {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: #000;
}

.wavy {
  position: relative;
}

.wavy span {
  position: relative;
  display: inline-block;
  color: black;
  font-size: 2em;
  text-transform: uppercase;
  animation: animate 1s ease-in-out infinite;
  animation-delay: calc(0.1s * var(--i));
  font-weight: bold;
  line-height: 2.5em; 
}

@keyframes animate {
  0% {
    transform: translateY(0px);
  }

  20% {
    transform: translateY(-20px);
  }

  40%, 100% {
    transform: translateY(0px);
  }
}
</style>
