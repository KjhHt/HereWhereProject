<template>
    <div class="text-center" @click="goToNextQuestion">
      <img
      :src="imageSrc"
      alt="Image"
      class="mbti_image img-fluid"
      @mouseenter="scaleUp"
      @mouseleave="scaleDown"
    />
      <template v-for="(value, name, index) in summary" :key="index">
        <p class="mbti_summary mt-4" v-if="index === 0">{{ name }}</p>
      </template>
    </div>
</template>
  
<script setup>
import { defineProps, computed,defineEmits } from 'vue';
import { gsap } from 'gsap';
  
const props = defineProps({
  index: Number,
  type: String,
});
const emit = defineEmits(['nextQuestion']);

const imageSrc = computed(() => require(`@/assets/Q${props.index+1}_${props.type}.jpg`));
const summaries = [
  { A: {'조용히 산책하며 혼자 사진 찍기를 선호합니다':0}, B: {'사람들과 어울려 새로운 만남을 추구합니다':0} },
  { A: {'혼자서 미술관이나 카페를 방문하는 것을 좋아합니다':0}, B: {'현지인이나 다른 여행자와 함께 탐험하는 것을 선호합니다':0} },
  { A: {'조용한 곳에서 혼자 생각하며 계획을 세우는 것을 좋아합니다':0}, B: {'친구나 가족과 함께 아이디어를 나누며 계획을 세우는 것을 선호합니다':0} },

  { A: {'실제적인 체험과 활동을 중시합니다':1}, B: {'의미 있는 경험과 내면의 성찰을 추구합니다':1} },
  { A: {'구체적인 일정과 명소를 미리 정하는 것을 선호합니다':1}, B: {'전체적인 흐름에 맡기며 유연하게 계획하는 것을 좋아합니다':1} },
  { A: {'눈앞의 아름다운 풍경과 맛있는 음식입니다':1}, B: {'그곳에서 느낀 감정과 생각입니다':1} },
  
  { A: {'논리적이고 체계적으로 문제를 해결하려 합니다':2}, B: {'사람들의 감정과 관계를 우선하여 해결책을 찾습니다':2} },
  { A: {'그 유용성과 실용적 가치를 먼저 고려합니다':2}, B: {'그 경험이 주는 감정적 만족과 의미를 중요시합니다':2} },
  { A: {'명확한 이유와 목적이 있을 때 선택합니다':2}, B: {'내적인 감정과 가치에 따라 선택합니다':2} },
  
  { A: {'세부 일정과 계획을 미리 세웁니다':3}, B: {'유연하게 계획하며 즉흥적으로 즐깁니다':3} },
  { A: {'미리 계획된 투어나 체험입니다':3}, B: {'그 순간의 기분에 따라 결정하는 것입니다':3} },
  { A: {'남은 일정을 체크하며 계획대로 진행합니다':3}, B: {'마지막 순간까지 새로운 경험을 찾아냅니다':3} },

];

const summary = computed(() => summaries[props.index][props.type]);

const goToNextQuestion = () => emit('nextQuestion', { type: props.type, summary: summary.value });
// GSAP Animation Functions
const scaleUp = (event) => {
  gsap.to(event.target, { scale: 1.05, duration: 0.3, ease: "power1.out" });
};

const scaleDown = (event) => {
  gsap.to(event.target, { scale: 1, duration: 0.3, ease: "power1.out" });
};
</script>
  
<style scoped>

.mbti_image {
  width: 100%; /* 이미지 너비를 컨테이너에 맞춤 */
  height: 400px;
  object-fit: cover; /* 이미지 비율을 유지하면서 잘라내어 맞춤 */
}

.mbti_summary {
  font-size: 1rem;
  font-weight: bold;
}
</style>