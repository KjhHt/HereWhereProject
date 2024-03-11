<template>
    <div class="text-center" @click="goToNextQuestion">
      <img
      :src="imageSrc"
      alt="Image"
      class="mbti_image img-fluid"
      @mouseenter="scaleUp"
      @mouseleave="scaleDown"
    />
      <p class="mbti_summary mt-4">{{ summary }}</p>
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
  { A: '다양한 사람들과의 대화를 통해 추천받은 관광 명소 찾아다닌다', B: '알려지지 않은 곳을 모험가처럼 탐험한다' },
  { A: '철저한 준비와 계획을 세운다', B: '즉흥적인 결정으로 새로운 상황에 부딪힌다' },
  { A: '축제를 즐기며 많은 사람들과 정보를 나눈다', B: '주변 상황과 사람들의 감정을 관찰한다' },
  { A: '여행일기나 블로그에 기록한다', B: '사진이나 비디오로 기록한다' },
  { A: '다양한 문화를 경험하며, 그 경험을 통한 문화적, 역사적 배움과 지식의 확장', B: '자신만의 시간을 갖고 내면의 평화를 찾는 것' },
  { A: '스스로 문제를 분석하고 해결책을 찾는다', B: '현지인의 도움을 받으며 문제를 해결한다' },
  { A: "친구나 가족과의 대화를 통해 스트레스를 완화합니다.",B: "혼자 걷기나 요가 등으로 스트레스를 관리합니다."  },
  { A: '필요한 정보와 지식을 사전에 습득해야 한다', B: '즉흥적인 결정과 모험을 통해 새로운 경험과 발견을 추구한다' },
  { A: '많은 사람과 정보를 나눈다', B: '나만의 비밀로 간직한다' },
  { A: '다양한 문화와 사회에 대한 이해', B: '자기 자신에 대한 이해와 개인적 성장' }
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