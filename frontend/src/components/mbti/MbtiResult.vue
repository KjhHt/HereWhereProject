<template>
    <transition name="modal" v-if="openModal">
        <div class="layerContainer" @click.self="closeModal">
            <div class="LayerContainer-sc-16ecu2q-0 iwjgOX" @click.stop>
                <div class="mbti_result_root_class">
                    <div class="mbti_PageContainer">
                        <div class="mbti_HomeContainer">
                            <div class="mbti_Question">
                                <div class="mbti_header">
                                    <h1>당신의 MBTI는 {{ result.mbti }} 입니다</h1>
                                    <div class="mbti_percentage">정확도 : {{ result.probability.toFixed(2)}}%</div>
                                </div>
                                <div class="mbti_FlexContainer">
                                    <div class="mbti_image">
                                        <img :src="result.image" alt="ISFP Image">
                                    </div>
                                    추천여행지<div class="mbti_location">{{result.destination}}</div>
                                    <div class="mbti_description">{{ animatedDescription }}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<script setup>
import { ref, defineEmits, defineProps, watch, onMounted } from 'vue';
import { gsap } from 'gsap';

const props = defineProps({
    openModal: Boolean,
    result: Object
});
const emit = defineEmits(['closeModal']);
const animatedDescription = ref("");
onMounted(() => {
  watch([() => props.openModal, () => props.result.description], ([newOpenModal, newDescription]) => {
    if (newOpenModal && newDescription) {
      animatedDescription.value = ""; // Reset the animated description
      let i = 0;
      const typingSpeed = 50; // Adjust typing speed
      const typeWriterInterval = setInterval(() => {
        if (i < newDescription.length) {
          animatedDescription.value += newDescription.charAt(i);
          i++;
        } else {
          clearInterval(typeWriterInterval);
          // Apply fade-in effect to the whole text using GSAP after typing effect is complete
          gsap.to(".mbti_description", { opacity: 1, duration: 1 });
        }
      }, typingSpeed);
    }
  }, { immediate: true });
});


const closeModal = () => {
  emit('closeModal');
};
</script>

<style scoped>
.char {
  opacity: 0;
}
.layerContainer {
    width:100%;
    height:100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(0, 0, 0, 0.3);
    position: fixed;
    inset: 0px;
    text-align: center;
    z-index: 9999;
}

.mbti_result_root_class{
    position: relative;
}
.mbti_PageContainer{
    overflow: hidden;
}
.mbti_HomeContainer{
    display: flex;
    max-width: 1200px;
    margin: 0px auto;
    padding-top: 16px;
}
.mbti_Question{
    width:100%;
    background: white; /* 흰색 배경 적용 */
    padding: 20px; /* 적당한 패딩을 주어 내용과 테두리 사이에 간격을 만듭니다 */
    border-radius: 10px; /* 모서리를 둥글게 처리합니다 */
}
.mbti_header{
    position: relative;
    display: flex;
    justify-content: center;
    flex-direction: column;
    text-align: center;
    background: white;
    margin-bottom: 16px;
}
.mbti_percentage{
    position: relative;
    right: 0;
}
.mbti_FlexContainer{
    display: flex;
    flex-direction: column;
    
}
.mbti_image{
    display: flex;
    justify-content: center;
    margin-bottom: 16px;
}
.mbti_image img{
    width: 100%;
    max-width: 600px;
    height: auto;
    border-radius: 5px;
}
.mbti_location{
    text-align: center;
    font-size: 1.2em;
    margin-bottom: 16px;
    font-weight: bold;
}
.mbti_description{
    text-align: justify;
    max-width: 600px;
    margin: 0 auto;
}
</style>