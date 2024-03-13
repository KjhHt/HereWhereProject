<template>
  <div class="PublishImageStyle__PublishImageContainer-sc-v627mh-0 mt24">
            <div class="publish-title">
              사진 업로드
            </div>
            <p class="sub-title">
              여행 특징이 담긴 사진을 업로드해주세요.
            </p>
            <form id="uploadform" action="https://nephele.tripcdn.com/image/v1/api/upload?channel=tripugc&amp;public=1&amp;partial=1" method="POST" class="photos" enctype="application/x-www-form-urlencoded">
              <div class="add li h5" v-for="(image, index) in images" :key="index">
              <div class="cell">
                <div class="image-container" >
                  <img :src="image.url" alt="" class="preview-image">
                    <div class="delete-wrap" @click.stop="deleteImage(index)">
                        <svg width="13" height="13" viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                            <defs></defs>
                            <path d="M202.667 38.249l15.084 15.084-74.67 74.665 74.67 74.669-15.084 15.084-74.67-74.67-74.664 74.67-15.084-15.084 74.666-74.67-74.666-74.664L53.333 38.25l74.665 74.666 74.669-74.666z" fill="#ffffff" class="icon-delete"></path>
                        </svg>
                    </div>
                </div>
                <div class="icon-upload">
                  <svg viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                    <defs>
                    </defs>
                    <path d="M98.72 58.667l-17.777 32h-43.61v106.666h181.334V90.667h-43.61l-17.778-32H98.721zM68.39 69.333l17.778-32h83.664l17.778 32H240v149.334H16V69.333h52.39zm59.61 112c-23.564 0-42.667-19.102-42.667-42.666C85.333 115.103 104.436 96 128 96s42.667 19.103 42.667 42.667-19.103 42.666-42.667 42.666zM128 160c11.782 0 21.333-9.551 21.333-21.333 0-11.783-9.551-21.334-21.333-21.334-11.782 0-21.333 9.551-21.333 21.334 0 11.782 9.551 21.333 21.333 21.333z" 
                    fill="#8592A6" 
                    class="transform-group">
                    </path>
                  </svg>
               
                </div>
                <p class="current-total" >
                  {{ index + 1 }} / 10
                </p>
                <input multiple name="image-tg" 
                type="file" 
                accept="image/jpg,image/JPG,image/jpeg,image/JPEG,image/png,image/PNG,image/gif,image/GIF" 
                class="image-upload" @change="previewImage(index)">
              </div>
            </div>
            <div class="add li online" v-if="images.length < 10">
              <div class="cell">
                <div class="icon-upload">
                  <svg viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                    <defs>
                    </defs>
                    <path d="M98.72 58.667l-17.777 32h-43.61v106.666h181.334V90.667h-43.61l-17.778-32H98.721zM68.39 69.333l17.778-32h83.664l17.778 32H240v149.334H16V69.333h52.39zm59.61 112c-23.564 0-42.667-19.102-42.667-42.666C85.333 115.103 104.436 96 128 96s42.667 19.103 42.667 42.667-19.103 42.666-42.667 42.666zM128 160c11.782 0 21.333-9.551 21.333-21.333 0-11.783-9.551-21.334-21.333-21.334-11.782 0-21.333 9.551-21.333 21.334 0 11.782 9.551 21.333 21.333 21.333z" 
                    fill="#8592A6" 
                    class="transform-group">
                    </path>
                  </svg>
                </div>
                <p class="current-total">
                  {{ images.length  }} / 10
                </p>
                <input multiple name="image-tg" 
                type="file" 
                accept="image/jpg,image/JPG,image/jpeg,image/JPEG,image/png,image/PNG,image/gif,image/GIF" 
                class="image-upload" @change="addImage">
              </div>
            </div>
            </form>
          </div>
</template>
<script setup>
import { ref,defineEmits, watch,defineProps } from 'vue';
import axios from 'axios';

let images = ref([]);

const emit = defineEmits(['updateImages','updateLabels'])

const props = defineProps({
  generateBase64Image: Object,
});

watch(() => props.generateBase64Image, (newVal) => {
  if (newVal) {
    const createFile = convertBase64ToFile(props.generateBase64Image,'createImage.jpg');
    console.log(createFile);
    addImagePlus(createFile);
  }
}, {
  deep: true 
});

function convertBase64ToFile(base64, filename) {
    // Base64 인코딩된 문자열에서 데이터 부분만 분리합니다.
    const base64Data = base64.split(',')[1];
    // Base64 데이터를 byte 배열로 변환합니다.
    const byteCharacters = atob(base64Data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    // Blob 객체를 생성합니다. MIME 타입은 상황에 맞게 조정할 수 있습니다.
    const blob = new Blob([byteArray], {type: 'image/jpeg'});
    // Blob 객체를 File 객체로 변환합니다.
    const file = new File([blob], filename, {type: 'image/jpeg'});
    
    return file;
}

const addImagePlus = (file) => {
  const reader = new FileReader();
  reader.onload = async (e) => {
    images.value.push({ url: e.target.result, file });
    emit('updateImages', images.value);  // 이벤트 발생
  };
  reader.readAsDataURL(file);  // 이 부분을 onload 이벤트 핸들러 바깥으로 이동
}

const deleteImage = (index) => {
  images.value.splice(index, 1);
  emit('updateImages', images.value);
};

const addImage = async (event) => {
  for (const file of event.target.files) {
    const reader = new FileReader();
    reader.onload = async (e) => {
      images.value.push({ url: e.target.result, file });
      emit('updateImages', images.value);  // 이벤트 발생
      try {
        const response = await axios.post(process.env.VUE_APP_PYTHON_API_URL+'/image_detecton', {
          image: e.target.result
        });
        console.log(response.data);
        //라벨 객체감지 부모 전달
        emit('updateLabels', response.data);
      } catch (error) {
        console.log(error);
      }
    };
    reader.readAsDataURL(file);
  }
  event.target.value = null;  // 초기화
};

const previewImage = (index) => (event) => {
const reader = new FileReader();
reader.onload = (e) => {
  images.value[index].url = e.target.result;
  images.value[index].file = event.target.files[0];
  event.target.value = null;
};
reader.readAsDataURL(event.target.files[0]);
};
</script>
<style scope>
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .mt24 {
  margin-top: 24px;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .publish-title {
  font-size: 18px;
  line-height: 22px;
  color: rgb(0, 0, 0);
  display: flex;
  -webkit-box-align: center;
  align-items: center;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .sub-title {
  margin-top: 8px;
  font-size: 14px;
  color: rgb(153, 153, 153);
  line-height: 16px;
  margin-bottom: 16px;
  text-align: left;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .photos {
  display: flex;
  flex-wrap: wrap;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .li {
  position: relative;
  overflow: hidden;
  width: 131px;
  height: 131px;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .h5 {
  display: none;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .add {
  display: flex;
  background: rgb(255, 255, 255);
  text-align: center;
  cursor: pointer;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  border: 2px dashed rgb(218, 223, 230);
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .cell {
  display: table-cell;
  vertical-align: middle;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .icon-upload {
  width: 46px;
  height: 38px;
  margin: 0px auto;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .icon-upload svg {
  width: 100%;
  height: 100%;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .current-total {
  margin-top: 16px;
  font-size: 16px;
  color: rgb(204, 204, 204);
  text-align: center;
  line-height: 16px;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .image-upload {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  opacity: 0;
}
input {
  background: none;
  outline: none;
  border: 0;
}
input, textarea {
  font-size: inherit;
  color: inherit;
  outline: none;
  border: 0;
  margin: 0;
  padding: 0;
}
button, input {
  overflow: visible;
}
button, input, optgroup, select, textarea {
  font-size: 100%;
  line-height: 1.15;
  margin: 0;
}
.PublishImageStyle__PublishImageContainer-sc-v627mh-0 .online {
  display: flex;
}
.image-container {
position: relative;
width: 127px;
height: 127px;
margin-top: 87px;
z-index: 1;
}

.preview-image {
width: 100%;
height: 100%;
object-fit: cover;
pointer-events: none;
}

.delete-wrap {
position: absolute;
top: 0;
right: 0;
background-color: rgba(0, 0, 0, 0.5);
border-radius: 0;
cursor: pointer;
width: 30px; 
height: 30px; 
align-items: center;
justify-content: center;
z-index: 2;
}

.delete-wrap svg {
fill: #fff;
}
</style>