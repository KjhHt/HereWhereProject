<template>
  <div id="__next">
      <div class="travel_guide_root_class">
        <div class="PublishIndexStyle__PublishMainContainer-sc-1q48sbw-0">
          <div class="PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1" :class="{'blur-background': isGeneratingImage}">
            <TripMomentSubtitle/>
            <TripMomentPicture :generateBase64Image="generateBase64Image" @updateImages="updateImages" @updateLabels="updateLabels"/>
            <TripMomentAddtitle @updateTitle="updateTitle"/>
            <TripMomentContent @updateContent="updateContent"/>
            <TripMomentLocation @updateLocation="updateLocation"/>
            <TripMomentAddtag @updateTag="updateLabels"></TripMomentAddtag>
            <TripMomentHashtag @updateTags="updateTags" :newTags="newTags" @deleteTag="deleteTag"/>
            <button class="btn btn-secondary float-start" @click="generateImage">해시태그 이미지 생성 서비스</button>
            <TripMomentSubmit @insertData="insertData"/>                
          </div>
          <div class="loadingbox">
              <MypageLoadingModal v-if="isGeneratingImage"/>
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
import MypageLoadingModal from '@/components/MyPage/MypageLoadingModal.vue';

import { ref,defineEmits,onMounted } from 'vue';
import axios from 'axios';
import Papa from 'papaparse'

const csvData=ref([]);
const isGeneratingImage = ref(false);

onMounted(()=>{
  Papa.parse('airports.csv', {
    download: true,
    header: true,
    complete: (results) => {
      csvData.value = results.data;
    }
  })
})

const emit = defineEmits(['selectPage']);
// 값 종합 =>들어가는부분!
const images = ref([]);
const title = ref('');
const content = ref('');
const tags = ref('');
const location = ref({});


//이미지 객체 감지 후 추천태그 추가 변수
const newTags = ref([]);

const tagList = ref([]);
const generateBase64Image = ref('');
const generateImage = async () => {
  try {
    // 요청할 데이터 설정
    isGeneratingImage.value = true;
    const splittedTags = tags.value.split(',');
    splittedTags.forEach(tag => {
        tagList.value.push(tag);
    });

    const requestData = {
        text_array: tagList.value
    };

    // POST 요청 보내기
    const response = await axios.post(process.env.VUE_APP_PYTHON_API_URL+'/generator', requestData);

    // 응답 확인
    generateBase64Image.value = `data:${response.headers['content-type']};base64,${response.data.image}`;
    tagList.value = [];
    isGeneratingImage.value = false;
  } catch (error) {
    tagList.value = [];
    console.error('이미지 생성 요청 에러:', error);
    isGeneratingImage.value = false;
  }
};


const searchAirports = (lat, lng) => {
  return new Promise((resolve, reject) => {
    const distance = 500;  // 검색 반경 (킬로미터 단위)
    let airports=[];
    csvData.value.forEach(row => {
        try{
        if(row.name && row.type.toLowerCase().includes("large_airport") && row.scheduled_service==="yes"){
            const radLat = lat * Math.PI / 180;  // radians
            const radLng = lng * Math.PI / 180;  // radians
            const radLatAirport = parseFloat(row.latitude_deg) * Math.PI / 180;  // radians
            const radLngAirport = parseFloat(row.longitude_deg) * Math.PI / 180;  // radians
            const a = Math.pow(Math.sin((radLat - radLatAirport) / 2), 2)
            + Math.cos(radLat) * Math.cos(radLatAirport) * Math.pow(Math.sin((radLng - radLngAirport) / 2), 2);
            const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            const d = 6371 * c;  // 지구의 반지름 (킬로미터 단위)
            if (d <= distance) {
            airports.push({...row, distance: d});  // 거리 정보를 추가하여 객체를 배열에 넣음
            }
        }
        }catch(error){
        reject(error);  // 에러가 발생하면 reject
        }
    });
    // 거리에 따라 배열을 정렬
    airports.sort((a, b) => a.distance - b.distance);
    if(airports.length > 0) {
        resolve(airports[0]);  // 가장 가까운 공항을 resolve
    } else {
        reject('No airport found');  // 공항을 찾지 못하면 reject
    }
  });
};
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
const insertData = async () => {
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

  console.log('location.value searchAirports : ',location.value.location_lat);
  let airportLocation = await searchAirports( location.value.location_lat , location.value.location_lng );
  console.log(airportLocation);
  console.log('공항코드 : ',airportLocation['iata_code']);
  location.value['location_iatacode']=airportLocation['iata_code'];

  formData.append('dto', JSON.stringify(location.value));
  console.log('확인해보자확인 : ',formData);
  await axios.post('/user/boardInsert', formData, {
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
.loadingbox {
  position: absolute;
  top: 30%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.blur-background {
    filter: blur(5px); /* 흐림 효과의 정도를 조절하려면 px 값을 조정하세요 */
}

</style>