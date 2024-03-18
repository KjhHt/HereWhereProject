<template>
  <div class="app-container">
    <MbtiModal :modal="modal"/>
    <MbtiResult :openModal="resultModal" @closeModal="closeResult" :result="result"/>
    <div class="mbti_class_root_class container mt-5">
      <div class="mbti_PageContainer">
        <div class="mbti_HomeContainer">
          <div class="mbti_Question">
            <div name="slide" @before-enter="beforeEnter" @enter="enter">
              <div :key="currentQuestionIndex">
                <MbtiQuestion :index="currentQuestionIndex" />
                <div class="mbti_ImageContainer d-flex flex-wrap justify-content-around">
                  <div class="mbti_ImageItem" v-for="(option, index) in ['A', 'B']" :key="`option-${index}-${currentQuestionIndex}`">
                    <MbtiItem :index="currentQuestionIndex" :type="option" @nextQuestion="handleAnswer"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

  
<script setup>
import { ref } from 'vue';
import { gsap } from 'gsap';
import MbtiModal from '@/components/mbti/MbtiModal.vue';
import MbtiQuestion from '@/components/mbti/MbtiQuestion.vue';
import MbtiItem from '@/components/mbti/MbtiItem.vue';
import MbtiResult from '@/components/mbti/MbtiResult.vue';
import axios from 'axios'
const modal=ref(false);
const resultModal=ref(false);
const currentQuestionIndex = ref(0);
const answers = ref([]);
const result = ref({});


let combinedObject = ref({});
async function request_mbti(answers) {
  console.log('request_mbti');
  console.log(answers);
  combinedObject.value = answers.reduce((accumulator, currentObject) => {
    let key = Object.keys(currentObject)[0];
    accumulator[key] = currentObject[key];
    return accumulator;
  }, {});
  console.log('확인작업 : ',combinedObject.value);

  try {
    const response = await axios.post(`${process.env.VUE_APP_PYTHON_API_URL}/predict_mbti`, { 'answers': combinedObject.value });
    console.log(response.data);
    return response; // 이 부분이 추가되어야 합니다.
  } catch (error) {
    console.error('request_mbti_error:', error);
    throw error; // 여기서 에러를 다시 던집니다.
  }
}

const handleAnswer = async (data) => {
    try {
        answers.value.push(data.summary);
        if (currentQuestionIndex.value < 11) { 
          nextQuestion();
            
        } else {
          modal.value=true
            const response=await request_mbti(answers.value)
            result.value=response.data
            setTimeout(()=>{
              modal.value=false
              resultModal.value=true
            },3000)
        }
    } catch (error) {
        console.error('mbti_predict_error',error);
        throw error;
    }
    
}

const closeResult= () => {
    resultModal.value=false
}

const nextQuestion = () => {
  currentQuestionIndex.value++;
};

function beforeEnter(el) {
  gsap.set(el, {
    opacity: 0,
    x: 100,
  });
}

function enter(el, done) {
  gsap.to(el, {
    opacity: 1,
    x: 0,
    duration: 1,
    onComplete: done,
  });
}
</script>

<style scoped>
.app-container {
  height: calc(100vh - 61px); /* 헤더 높이를 60px로 가정 */
  overflow: auto; /* 컨텐츠가 넘칠 경우 스크롤바 생성 */
}

.mbti_class_root_class {
  display: flex;
  flex-direction: column;
  align-items: center;
  /* min-height를 제거하고 height를 사용하여 컨테이너 높이 조정 */
  height: 100%;
}

.mbti_ImageContainer {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  justify-content: center;
}

.mbti_ImageItem {
  flex: 1;
  width: 400px;
  min-width: 300px;
  max-width: 600px;
  display: flex;
  justify-content: center;
}
</style>
