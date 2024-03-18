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
  { situation: 'Q1. 여행 중에는' },
  { situation: 'Q2. 새로운 도시를 탐험할 때' },
  { situation: 'Q3. 여행 계획을 세울 때' },
  { situation: 'Q4. 여행지에서의 활동 선택에 있어서' },
  { situation: 'Q5. 여행 계획을 세울 때' },
  { situation: 'Q6. 여행지에서 기억에 남는 것은' },
  { situation: 'Q7. 여행 중 문제에 직면했을 때' },
  { situation: 'Q8. 새로운 경험을 평가할 때' },
  { situation: 'Q9. 여행지 선택의 기준은' },
  { situation: 'Q10. 여행을 떠나기 전' },
  { situation: 'Q11. 여행 중 선호하는 활동은' },
  { situation: 'Q12. 여행의 마지막 날' },
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