<!-- 아래 링크 추가 -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link> -->
<template>
  <div class="backdrop" @click="close"></div>
  <div class="editor-container bg-light p-3 rounded">
    <div class="profile-container">
    <img :src="formData[1]" alt="Profile Image" class="profile-image"/>
    <span class="profile-id">{{ formData[0] }}</span>
    </div>
    <Upload @upload-click="click" @update-files="handleImage" :upload-click="click" :class="[imageList.length>0?'d-none':'']"/>
    <Carousel :key="carouselKey" :imageList="imageList" @delete-image="deleteImage" :class="[imageList.length>0?'':'d-none']"/>
      <QuillEditor :options="toolbarOptions" ref="editorRef"/>
    <a class="btn btn-secondary d-block mt-3" @click="post">게시</a>
  </div>
  
</template>
<script setup>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import { defineEmits, ref, watch ,onMounted} from 'vue';
import Carousel from './BoardCarousel.vue';
import Upload from './ImageUpload.vue';
import axios from 'axios';

// import '@vueup/vue-quill/dist/vue-quill.bubble.css';
const editorRef=ref(null)
const emit=defineEmits(['close','update'])
const fileData = ref([]);
const formData = ref([]);

function close(){
  emit('close',false);
  console.log('close');
}
const toolbarOptions=ref({
  // debug: 'info',
  modules: {
    toolbar: {
      container:[[{ 'header': [1, 2, 3, 4, 5, 6, false] }],['bold', 'italic', 'underline','image','link']],
      handlers:{
        'image':uploadClick
      }
    },
  },
  placeholder: '여행 게시물을 등록해주세요!',
  theme: 'snow',
});
const click=ref(false)
const imageList=ref([])
watch(()=>editorRef.value,editor=>{
  console.log(editor)
})

watch(()=>imageList.value.length, (length)=>{
  console.log('watch:',length)
})
const carouselKey = ref(0);
watch(imageList, () => {
  // 이미지 리스트의 길이가 변경될 때마다 carouselKey 값을 변경
  carouselKey.value++;
});

onMounted(() => {
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const name = vuexStore.loginStore.userInfo.name;
  const profileimage = vuexStore.loginStore.userInfo.profileimage;
  formData.value[0] = name;

  toolbarOptions.value.placeholder = name+'님 여행을 공유해주세요';
  console.log(name+'님 여행을 공유해주세요');
  console.log(toolbarOptions.value.placeholder);


  console.log(profileimage);
  if( profileimage === '0' ){
    formData.value[1] = require('@/assets/dino.jpg');
  }
  else{
    if(profileimage.startsWith("D:") || profileimage.startsWith("E:")){
      const pathSegments = profileimage.split('\\');
      const lastSegment = pathSegments[pathSegments.length - 1];
      axios.get(`http://localhost:8080/profile/${lastSegment}`)
      .then(res => {
        const dataURI = `data:${res.headers['content-type']};base64,${res.data}`;
        formData.value[1] = dataURI;
      })
      .catch(err => console.log(err))
    }
    else{
      formData.value[1] = profileimage;
    }
  }

});

function uploadClick(){
  click.value=!click.value
}

function handleImage(files) {
  if(imageList.value.length>0) imageList.value.splice(0)
    fileData.value.push(files);
  for (let file of files) {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      const base64String = reader.result;
      imageList.value.push({ url: base64String, uid: Date.now() + Math.random().toString() });
    };
  }
}

function deleteImage(uid){
  console.log('delete:',uid);
  imageList.value=imageList.value.filter(image=>image.uid!=uid);
}

//글등록
function post(){
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const name = vuexStore.loginStore.userInfo.name;
  const formData = new FormData();
  formData.append('writer',name);
  formData.append('content',editorRef.value.getHTML());
  Array.from(fileData.value[0]).forEach((file, index) => {
    console.log(index);
    formData.append('boardImages', file);
  });
  axios.post('http://localhost:8080/user/boardInsert', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
  .then(res => {
    console.log(res.data);
    const profileimage = vuexStore.loginStore.userInfo.profileimage;
    res.data.profileimage = profileimage;
    emit('update', res.data);
    emit('close',false);
  })
  .catch(err => console.log(err))

}



</script>
<style scoped>
.editor-container {
  min-width: 400px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 30em;
  z-index: 1000;
}

.backdrop {
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