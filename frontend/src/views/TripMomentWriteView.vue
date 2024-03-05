<template>
  <div id="__next">
      <div class="travel_guide_root_class">
        <div class="PublishIndexStyle__PublishMainContainer-sc-1q48sbw-0">
          <div class="PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1">
            <TripMomentSubtitle/>
            <TripMomentPicture @updateImages="updateImages" @updateLabels="updateLabels"/>
            <TripMomentAddtitle @updateTitle="updateTitle"/>
            <TripMomentContent @updateContent="updateContent"/>
            <TripMomentLocation @updateLocation="updateLocation"/>
            <TripMomentAddtag @updateTag="updateLabels"></TripMomentAddtag>
            <TripMomentHashtag @updateTags="updateTags" :newTags="newTags" @deleteTag="deleteTag"/>
            <TripMomentSubmit @insertData="insertData"/>
          </div>
        </div>
      </div>
</div>
</template>
<script setup>
import TripMomentSubtitle from '@/components/TripMoment/TripMomentSubtitle.vue'
import TripMomentPicture from '@/components/TripMoment/TripMomentPicture.vue'
import TripMomentAddtitle from '@/components/TripMoment/TripMomentAddtitle.vue'
import TripMomentContent from '@/components/TripMoment/TripMomentContent.vue'
import TripMomentSubmit from '@/components/TripMoment/TripMomentSubmit.vue'
import TripMomentLocation from '@/components/TripMoment/TripMomentLocation.vue'
import TripMomentHashtag from '@/components/TripMoment/TripMomentHashtag.vue'
import TripMomentAddtag from '@/components/TripMoment/TripMomentAddtag.vue'

import { ref,defineEmits } from 'vue';
import axios from 'axios';

const emit = defineEmits(['selectPage']);
// 값 종합 =>들어가는부분!
const images = ref([]);
const title = ref('');
const content = ref('');
const tags = ref('');
const location = ref({});
//insert하고 반환된 게시판데이터 프로필,이미지 base64처리 후 보내주자
// const boardInsertReturnData = ref({});


//이미지 객체 감지 후 추천태그 추가 변수
const newTags = ref([]);
//이미지 파일들
const updateImages = (newImages) => {
  images.value = newImages;
};
//제목
const updateTitle = (newTitle) => {
  title.value = newTitle;
};
//내용
const updateContent = (newContent) => {
  content.value = newContent;
};
//지역정보
const updateLocation = (newLocation) => {
  location.value = newLocation;
};
//태그들
const updateTags = (newTags) => {
  tags.value = newTags;
};
// //태그 추가
// const updateTag = (data) => {

// };

//이미지 객체감지 추천태그 추가
const updateLabels = (tags) => {
  // 감지안된건 제외한다.
  if(tags.labels.length != 0){
    tags.labels.forEach(tag => {
      newTags.value.push(tag);
    });
  }
  newTags.value = newTags.value.filter((tag, index) => {
    return newTags.value.indexOf(tag) === index;
  });
}

const deleteTag = (tag) => {
  newTags.value = newTags.value.filter(value => value !== tag);
}
// 서버에 데이타 입력하는부분
const insertData = () => {
  if (!images.value.length) { // images가 비어 있음 
    alert('이미지를 등록해주세요 !');
    return;
  } 
  else if (!title.value) { // title이 비어 있음
    alert('제목을 등록해주세요 !');
    return;
  } 
  else if (!content.value) { // content가 비어 있음
    alert('내용을 등록해주세요 !');
    return;
  } 
  else if (!Object.keys(location.value).length) { // location이 비어 있음
    location.value['isLocation'] = 'no';
  } 
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const name = vuexStore.loginStore.userInfo.name;
  const profileimage = vuexStore.loginStore.userInfo.profileimage;
  let formData = new FormData();
  formData.append('name', name);
  formData.append('profileimage', profileimage);
  formData.append('title', title.value);
  formData.append('content', content.value);
  formData.append('tags', tags.value);

  images.value.forEach((image) => {
      formData.append('boardImages', image.file);
  });
  formData.append('dto', JSON.stringify(location.value));
  axios.post('/user/boardInsert', formData, {
      headers: {
          'Content-Type': 'multipart/form-data'
      }
  })
  .then(function (response) {
    // response 등록된값들 혹시 몰라서 다시 반환하게 해놓음
    // 사용할거면 프로필이미지랑 게시판이미지base64코드만 붙이면됨.
      console.log(response.data);
      alert('게시글이 등록되었습니다!');
      emit('selectPage','trip-moment');
  })
  .catch(function (error) {
      console.log(error);
  });
}



</script>
<style scope>
#__next {
    height: 100%;
}
.travel_guide_root_class {
    position: relative;
}
.PublishIndexStyle__PublishMainContainer-sc-1q48sbw-0 {
    max-width: 1200px;
    margin: 0px auto;
    padding: 0px 30px;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 {
    padding: 0px 10px;
}



</style>