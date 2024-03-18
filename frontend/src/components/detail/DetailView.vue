<!-- 아래 링크 추가 -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link> -->
<template>
    <div class="Detailbackdrop" @click="Detailclose"></div>
    <div class="row w-75">
      <div class="editor-container bg-light p-3 rounded-top">
        <div class="profile-container">
          <img :src="props.boardDetail[2]" alt="Profile Image" class="profile-image"/>
          <span class="profile-id">{{props.boardDetail[1]}}</span>
        </div>
        <Carousel :key="carouselKey" :imageList="imageList" @delete-image="deleteImage" :boardDetail="props.boardDetail" :imageSrc="imageSrc"/>
        <!-- <a class="btn btn-secondary d-block mt-3" @click="post">게시</a> -->
      </div>
        <CommentLayout  class="comment-layout bg-light d-flex rounded-bottom" :boardList="props.boardList" :boardDetail="props.boardDetail" :formatTimeAgo="props.formatTimeAgo"/>
    </div>
</template>
<script setup>
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import { defineEmits, ref, watch ,defineProps} from 'vue';
import Carousel from './DetailCarousel.vue';
import CommentLayout from '@/components/detail/CommentLayout.vue'
// import '@vueup/vue-quill/dist/vue-quill.bubble.css';
const emit=defineEmits(['Detailclose'])

const props = defineProps({
  boardDetail: {
    type: Array,
  },
  imageSrc : {
    type: Array,
  },
  boardList : {
    type: Array,
  },
  formatTimeAgo : {
    type : Function,
  },
})

function Detailclose(){
  emit('Detailclose',false);
}

const imageList=ref([])

watch(()=>imageList.value.length, (length)=>{
  console.log('watch:',length)
})
const carouselKey = ref(0);
watch(imageList, () => {
  // 이미지 리스트의 길이가 변경될 때마다 carouselKey 값을 변경
  carouselKey.value++;
});





</script>
<style scoped>
.row {
  position: fixed;
  z-index: 2000;
  margin: auto;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
}
.editor-container,.comment-layout {
  z-index: 1000;
}

.Detailbackdrop {
  position: fixed;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  background-color: rgba(0,0,0,0.7);
  z-index: 999;
}

.profile-container {
  display: flex;
  align-items: center;
  margin-bottom: 1em;
}
.profile-image {
  width: 50px; /* 이미지 크기 */
  height: 50px; /* 이미지 크기 */
  border-radius: 50%; /* 원형 이미지 */
  margin-right: 10px; /* 이미지와 아이디 사이 간격 */
}
.profile-id {
  font-weight: bold;
}
</style>