<template>
<div class="mbti_question_header text-center">
  <h2>{{ questionData[index].situation }}</h2>
</div>
</template>

<script setup>
import { defineProps } from 'vue';

defineProps({
  index: {
    type: Number,
    default: 0,
  },
});

const questionData = [
  { situation: 'Q1. 새로운 도시를 여행할 때 당신은 어떤 활동을 선호합니까?' },
  { situation: 'Q2. 여행 계획을 세울 때 당신의 접근 방식은 무엇인가요?' },
  { situation: 'Q3. 여행 중에 흥미로운 축제나 이벤트가 있다면 당신의 반응은?' },
  { situation: 'Q4. 여행에서 경험한 감정을 기록하는 방법은 무엇인가요?' },
  { situation: 'Q5. 여행을 가게 되었을 때 당신이 가장 기대하는 것은?' },
  { situation: 'Q6. 여행지에서 마주친 어려움에 당신은 어떻게 대처합니까?' },
  { situation: 'Q7. 여행지에서 스트레스를 받았을 때 당신의 대처 방법은?' },
  { situation: 'Q8. 여행을 통해서 얻고 싶은것들은 무엇인가요?' },
  { situation: 'Q9. 여행 중에 발견한 숨겨진 명소를 당신은 어떻게 하나요?' },
  { situation: 'Q10. 여행의 목적을 정할 때 당신은 어떤 것에 가장 큰 가치를 두나요?' },
];
</script>
<style scope>
.mbti_question_header{
  display: flex;
  justify-content: center;
  align-items: center;
  background: white;
  margin-bottom: 16px;
}

h2 {
  font-size: 30px;
}
</style>