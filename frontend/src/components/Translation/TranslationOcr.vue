<template>
  <div class="scene">
    <div class="book-wrap">
      <div class="left-side">
        <div class="book-cover-left"></div>
        <div class="layer1">
          <div class="page-left"></div>
        </div>
        <div class="layer2">
          <div class="page-left"></div>
        </div>
        <div class="layer3">
          <div class="page-left"></div>
        </div>
        <div class="layer4">
          <div class="page-left"></div>
        </div>
        <div class="layer-text">
          <div class="page-left-2">
            <div class="corner"></div>
            <div class="corner2"></div>
            <div class="corner-fold"></div>
            <div class="page-text w-richtext">
              <h3><strong>Translation Note</strong></h3>
              <div class="add">
                <textarea class="textbox" placeholder="번역하고 싶은 글을 적어보세요" v-model="inputText"></textarea>
              </div>
              <button class="btn_button" type="button" @click="submit">번역하기</button>
              <button class="btn_button" type="button" @click="swapText">반전</button>
            </div>
          </div>
        </div>
      </div>
      <div class="center"></div>
      <div class="right-side">
        <div class="book-cover-right"></div>
        <div class="layer1">
          <div class="page-right"></div>
        </div>
        <div class="layer2 right">
          <div class="page-right"></div>
        </div>
        <div class="layer3 right">
          <div class="page-right"></div>
        </div>
        <!-- :src="alflqhrl" -->
        <div class="layer-text right">
          <div class="page-right-2">
            <div class="page-text w-richtext">
              <h3><strong>Translation Result</strong></h3>
              <div class="typing-text" ref="typingTextContainer">
                <select class="form-select form-select-lg mb-3" id="select_form" aria-label="Large select example" v-model="selectedLang" @change="changeLanguage">
                  <option v-for="(lang, index) in languages" :key="index" :value="lang.code">{{ lang.name }}</option>
                </select>
                <textarea class="textbox1" ></textarea>
                <div class="loading" v-if="loading" >
                  <span></span>   <!--1. span은 하나의 원이다. -->
                  <span></span>
                  <span></span>
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
  import axios from 'axios';

  const inputText = ref('');  // 입력된 텍스트
  const translatedText = ref('');  // 번역 결과
  const selectedLang = ref('EN'); // 선택된 언어의 코드, 기본값은 'EN'
  const loading = ref(false);



  const languages = [
{ name: '영어', code: 'EN' },{ name: '일본어', code: 'JA' },{ name: '한국어', code: 'KO' },{ name: '아랍어', code: 'AR' },
{ name: '중국어 (간체)', code: 'ZH' },{ name: '프랑스어', code: 'FR' },
{ name: '독일어', code: 'DE' },{ name: '이탈리아어', code: 'IT' },{ name: '포르투갈어', code: 'PT' },{ name: '러시아어', code: 'RU' },
{ name: '스페인어', code: 'ES' },{ name: '태국어', code: 'TH' },{ name: '터키어', code: 'TR' },{ name: '베트남어', code: 'VI' },
{ name: '네덜란드어', code: 'NL' },{ name: '그리스어', code: 'EL' },{ name: '폴란드어', code: 'PL' },
{ name: '스웨덴어', code: 'SV' },{ name: '체코어', code: 'CS' },{ name: '덴마크어', code: 'DA' },
{ name: '핀란드어', code: 'FI' },{ name: '헝가리어', code: 'HU' },
{ name: '슬로바키아어', code: 'SK' },{ name: '루마니아어', code: 'RO' },{ name: '인도네시아어', code: 'ID' },
{ name: '우크라이나어', code: 'UK' },
{ name: '슬로베니아어', code: 'SL' },{ name: '리투아니아어', code: 'LT' },
];




const changeLanguage = () => {
  console.log('Selected Language:', selectedLang.value);
};
  

const submit = async () => {
  const textArea = document.querySelector('.typing-text textarea');
  textArea.value = '';
  loading.value = true;

  try {
    
    let response = await axios.post(process.env.VUE_APP_PYTHON_API_URL+'/translate', {
      text: inputText.value,
      target_lang: selectedLang.value // 일본어로 번역하려는 경우
    }, {
      headers: {
        'Content-Type': 'application/json'
      }
    });

    console.log('API 응답:', response); // 응답 전체 확인
    console.log('API 응답 데이터:', response.data); // 응답 데이터 확인

  if (response.status === 200) {
    if (response) {
      const translatedTextData = response.data;
      translatedText.value =translatedTextData
      
      
      typeWriterAnimation();
      console.log('번역된 텍스트:', translatedText.value); // 번역된 텍스트 확인
      // 타이핑 애니메이션 효과 적용
      
    } else {
      console.error('API 응답에서 번역된 텍스트가 없습니다.');
      translatedText.value = '번역된 텍스트가 없습니다.';
    }
  } else {
    console.error('API 요청이 실패하였습니다.');
    translatedText.value = '번역에 실패했습니다.';
  }
} catch (error) {
  console.error('API 요청이 실패하였습니다.', error);
  translatedText.value = '번역에 실패했습니다.';
}finally{
    loading.value = false;
  }
};
const swapText = () => {
const temp = inputText.value;
inputText.value = translatedText.value;
translatedText.value = temp;
typeWriterAnimation();
};
const typeWriterAnimation = () => {
let i = 0;
const typingSpeed = 100; // 타이핑 속도 (ms)
const textArea = document.querySelector('.typing-text textarea');

    textArea.value = translatedText.value.substring(0, i); // 번역된 텍스트 설정


const typeWriterInterval = setInterval(() => {
  if (i < translatedText.value.length) {
    textArea.value += translatedText.value.charAt(i);
    i++;
  } else {
    clearInterval(typeWriterInterval);
  }
}, typingSpeed);
};

</script>

<style scoped>
.loading{
  margin-top: -290px;
  margin-left: -190px;
  z-index: 10;
}

.loading span {
    display: inline-block; /* span 내부요소들을 한줄로 세우기 */
    width: 7px;
    height: 7px;
    background-color: gray;
    border-radius: 50%;    /* span을 동그랗게 */
    animation: loading 1s 0s linear infinite;
    /* 이벤트명  반복시간  딜레이시간  이벤트처리부드럽게  이벤트무한반복*/
    margin-top: 0px;
    display: inline-block;
    margin-left: 10px;
  }
  
  .loading span:nth-child(1) {  /*loading의 자식 중 첫번째 span*/
    /*nth-child : 형제 사이에서의 순서*/
    animation-delay: 0s;
    background-color: rgb(63, 63, 63);
  }
  
  .loading span:nth-child(2) {
    animation-delay: 0.2s;
    background-color: rgb(163, 163, 163);
  }
  
  .loading span:nth-child(3) {
    animation-delay: 0.4s;
    background-color: rgb(114, 114, 114);
  }
@keyframes loading {        /*loading이라는 keyframe 애니메이션*/
    0%,                      /* 0, 50, 100은 구간*/
    100% {
      opacity: 0;            /* 안보였다가 */
      transform: scale(0.5); /*transform의 scale로 요소를 확대 또는 축소할 수 있음*/   
    }
    50% {
      opacity: 1;             /* 보였다가 */
      transform: scale(1.2); /* 1.2배 */
    }
  }

div {
  cursor: url('data:image/png;base64,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'),auto;
}

.btn_button{
  margin-left: 10px;
  border-radius: 5px;
  margin-top: 30px;
}

.add {
  height: 100%;
}
.textbox1{
  position: relative;
  width: 100%;
  top: -80px;
  min-height: 200px; /* 최소 높이 지정 */
  max-height: 1500px; /* 최대 높이 지정 */
  resize: none;
  overflow-y: auto; /* 내용이 넘칠 경우 스크롤바 표시 */

}
.textbox {
  position: relative;
  width: 100%;

  min-height: 200px; /* 최소 높이 지정 */
  max-height: 1500px; /* 최대 높이 지정 */
  resize: none;
  overflow-y: auto; /* 내용이 넘칠 경우 스크롤바 표시 */

}

@media (max-width: 768px) {
 
}


.textbox.typing-animation {
border: none;
overflow: hidden; /* 텍스트가 넘칠 경우 숨김 */
white-space: nowrap; /* 텍스트가 줄바꿈되지 않도록 설정 */
animation: typing 2s steps(30, end); /* 타이핑 애니메이션 적용 */
}

@keyframes typing {
from {
  width: 0;
}
to {
  width: 100%;
}
}

#select_form{
  position: relative;
  display: block;
  margin-left: 100%;
  right: 117px;
  margin-top: 25px;
  bottom: 100px;
  width: 150px;
  z-index: 10;
  border-color: #fff;
  background-color: transparent;
}

.w-block {
  display: block;
}
.w-inline-block {
  max-width: 100%;
  display: inline-block;
}
.w-webflow-badge,
.w-webflow-badge * {
  position: static;
  left: auto;
  top: auto;
  right: auto;
  bottom: auto;
  z-index: auto;
  display: block;
  visibility: visible;
  overflow: visible;
  overflow-x: visible;
  overflow-y: visible;
  box-sizing: border-box;
  width: auto;
  height: auto;
  max-height: none;
  max-width: none;
  min-height: 0;
  min-width: 0;
  margin: 0;
  padding: 0;
  float: none;
  clear: none;
  border: 0 none transparent;
  border-radius: 0;
  background: none;
  background-image: none;
  background-position: 0% 0%;
  background-size: auto auto;
  background-repeat: repeat;
  background-origin: padding-box;
  background-clip: border-box;
  background-attachment: scroll;
  background-color: transparent;
  box-shadow: none;
  opacity: 1.0;
  transform: none;
  transition: none;
  direction: ltr;
  font-family: inherit;
  font-weight: inherit;
  color: inherit;
  font-size: inherit;
  line-height: inherit;
  font-style: inherit;
  font-variant: inherit;
  text-align: inherit;
  letter-spacing: inherit;
  text-decoration: inherit;
  text-indent: 0;
  text-transform: inherit;
  list-style-type: disc;
  text-shadow: none;
  font-smoothing: auto;
  vertical-align: baseline;
  cursor: inherit;
  white-space: inherit;
  word-break: normal;
  word-spacing: normal;
  word-wrap: normal;
}
.w-webflow-badge {
  position: fixed !important;
  display: inline-block !important;
  visibility: visible !important;
  opacity: 1 !important;
  z-index: 2147483647 !important;
  top: auto !important;
  right: 12px !important;
  bottom: 12px !important;
  left: auto !important;
  color: #AAADB0 !important;
  background-color: #fff !important;
  border-radius: 3px !important;
  padding: 6px 8px 6px 6px !important;
  font-size: 12px !important;
  opacity: 1.0 !important;
  line-height: 14px !important;
  text-decoration: none !important;
  transform: none !important;
  margin: 0 !important;
  width: auto !important;
  height: auto !important;
  overflow: visible !important;
  white-space: nowrap;
  box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.1), 0px 1px 3px rgba(0, 0, 0, 0.1);
}
.w-webflow-badge > img {
  display: inline-block !important;
  visibility: visible !important;
  opacity: 1 !important;
  vertical-align: middle !important;
}

.w-richtext:before,
.w-richtext:after {
  content: " ";
  display: table;
}
.w-richtext:after {
  clear: both;
}
.w-richtext[contenteditable="true"]:before,
.w-richtext[contenteditable="true"]:after {
  white-space: initial;
}

/*-----------------------*/

h3 {
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 24px;
  line-height: 30px;
  font-weight: bold;
}

h6 {
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 12px;
  line-height: 18px;
  font-weight: bold;
}

p {
  margin-top: 0;
  margin-bottom: 10px;
}

a {
  text-decoration: underline;
}

.scene {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  width: 100%;
  height: 54vw;
  -webkit-box-pack: center;
  -webkit-justify-content: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: stretch;
  -webkit-align-items: stretch;
  -ms-flex-align: stretch;
  align-items: stretch;
  background-color: #fff;
  -webkit-perspective: 4000px;
  perspective: 4000px;
  -webkit-perspective-origin: 50% 0%;
  perspective-origin: 50% 0%;
}

.book-wrap {
  position: relative;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  width: 57vw;
  margin-top: 8vw;
  margin-bottom: 8vw;
  padding-right: 1%;
  padding-left: 1%;
  -webkit-box-pack: center;
  -webkit-justify-content: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-perspective: 4000px;
  perspective: 4000px;
  -webkit-perspective-origin: 50% 50%;
  perspective-origin: 50% 50%;
  -webkit-transform: translate3d(0px, 5%, -264px) rotateX(27deg) rotateY(0deg) rotateZ(-10deg);
  transform: translate3d(0px, 5%, -264px) rotateX(27deg) rotateY(0deg) rotateZ(-10deg);
  -webkit-transition: -webkit-transform 2000ms cubic-bezier(.165, .84, .44, 1);
  transition: -webkit-transform 2000ms cubic-bezier(.165, .84, .44, 1);
  transition: transform 2000ms cubic-bezier(.165, .84, .44, 1);
  transition: transform 2000ms cubic-bezier(.165, .84, .44, 1), -webkit-transform 2000ms cubic-bezier(.165, .84, .44, 1);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.book-wrap:hover {
  -webkit-transform: translate3d(0px, 5%, -264px) rotateX(13deg) rotateY(0deg) rotateZ(-3deg);
  transform: translate3d(0px, 5%, -264px) rotateX(13deg) rotateY(0deg) rotateZ(-3deg);
}

.book-cover-left {
  -webkit-box-flex: 1;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  border-top-left-radius: 4%;
  border-bottom-left-radius: 4%;
  background-color: #2e1800;
  box-shadow: inset 4px -4px 4px 1px #635648, inset 7px -7px 4px 0 #221b14;
  -webkit-perspective: 4000px;
  perspective: 4000px;
  -webkit-transform: translate3d(0px, 0px, -1px);
  transform: translate3d(0px, 0px, -1px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.book-cover-right {
  -webkit-box-flex: 1;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  border-top-right-radius: 4%;
  border-bottom-right-radius: 4%;
  background-color: #2e1800;
  box-shadow: inset -4px -4px 4px 1px #635648, inset -7px -7px 4px 0 #221b14;
}

.layer1 {
  position: fixed;
  left: 0px;
  top: 0px;
  right: 0px;
  bottom: 0px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  margin: 20px 10px 10px;
  -webkit-box-pack: start;
  -webkit-justify-content: flex-start;
  -ms-flex-pack: start;
  justify-content: flex-start;
  -webkit-transform: translate3d(0px, 0px, 5px);
  transform: translate3d(0px, 0px, 5px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.page-left {
  -webkit-box-flex: 1;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  border-top-left-radius: 1%;
  border-bottom-left-radius: 1%;
  background-color: #fff;
  box-shadow: inset 0 0 26px 2px #d8cccc, -1px 1px 13px 0 rgba(34, 27, 20, .81);
}

.layer-text {
  position: fixed;
  left: 0px;
  top: 0px;
  right: 0px;
  bottom: 0px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  width: 97%;
  margin: 20px 10px 18px;
  -webkit-box-pack: start;
  -webkit-justify-content: flex-start;
  -ms-flex-pack: start;
  justify-content: flex-start;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
  -webkit-perspective: 4000px;
  perspective: 4000px;
  -webkit-perspective-origin: 50% 50%;
  perspective-origin: 50% 50%;
  -webkit-transform: translate3d(0px, 0px, 32px);
  transform: translate3d(0px, 0px, 32px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.layer-text.right {
  -webkit-transform: translate3d(-37px, 0px, 32px);
  transform: translate3d(-37px, 0px, 32px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.page-left-2 {
  position: relative;
  -webkit-box-flex: 1;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  border-top-left-radius: 18%;
  border-bottom-left-radius: 1%;
  background-color: #fff;
  box-shadow: inset 0 0 7px 4px hsla(0, 13%, 82%, .43), -1px 1px 13px 0 rgba(34, 27, 20, .49);
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
  -webkit-transform: rotateX(0deg) rotateY(17deg) rotateZ(0deg);
  transform: rotateX(0deg) rotateY(17deg) rotateZ(0deg);
  -webkit-transform-origin: 100% 50%;
  -ms-transform-origin: 100% 50%;
  transform-origin: 100% 50%;
  -webkit-transition: -webkit-transform 650ms cubic-bezier(.165, .84, .44, 1);
  transition: -webkit-transform 650ms cubic-bezier(.165, .84, .44, 1);
  transition: transform 650ms cubic-bezier(.165, .84, .44, 1);
  transition: transform 650ms cubic-bezier(.165, .84, .44, 1), -webkit-transform 650ms cubic-bezier(.165, .84, .44, 1);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.page-left-2:hover {
  -webkit-transform: rotateX(0deg) rotateY(7deg) rotateZ(0deg);
  transform: rotateX(0deg) rotateY(7deg) rotateZ(0deg);
}

.left-side {
  position: relative;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  width: 49%;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
  -webkit-perspective: 4000px;
  perspective: 4000px;
  -webkit-perspective-origin: 0% 50%;
  perspective-origin: 0% 50%;
  -webkit-transform: rotateX(0deg) rotateY(20deg) rotateZ(0deg);
  transform: rotateX(0deg) rotateY(20deg) rotateZ(0deg);
  -webkit-transform-origin: 100% 50%;
  -ms-transform-origin: 100% 50%;
  transform-origin: 100% 50%;
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.right-side {
  position: relative;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  width: 49%;
  -webkit-perspective: 4000px;
  perspective: 4000px;
  -webkit-perspective-origin: 0% 50%;
  perspective-origin: 0% 50%;
  -webkit-transform: rotateX(0deg) rotateY(-1deg) rotateZ(0deg);
  transform: rotateX(0deg) rotateY(-1deg) rotateZ(0deg);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.center {
  width: 3%;
  background-image: -webkit-radial-gradient(circle farthest-corner at 56% -8%, #fff 8%, transparent 0), -webkit-radial-gradient(circle farthest-corner at 50% 108%, #fff 8%, transparent 0), -webkit-linear-gradient(0deg, #635648, #2e1800 21%, #635648 30%, #2e1800 48%, #635648 68%, #2e1800 79%, #635648);
  background-image: radial-gradient(circle farthest-corner at 56% -8%, #fff 8%, transparent 0), radial-gradient(circle farthest-corner at 50% 108%, #fff 8%, transparent 0), linear-gradient(90deg, #635648, #2e1800 21%, #635648 30%, #2e1800 48%, #635648 68%, #2e1800 79%, #635648);
}

.corner-fold {
  position: absolute;
  left: 0px;
  top: 0px;
  width: 30px;
  height: 30px;
  border-right: 1px solid hsla(0, 13%, 82%, .55);
  border-bottom: 1px solid hsla(0, 13%, 82%, .55);
  background-image: -webkit-linear-gradient(315deg, transparent 47%, #f0f0f0 48%, #fff 55%, #f6f6f6);
  background-image: linear-gradient(135deg, transparent 47%, #f0f0f0 48%, #fff 55%, #f6f6f6);
  box-shadow: 6px 6px 9px -4px hsla(0, 13%, 82%, .53);
}

.corner {
  position: absolute;
  left: 0px;
  top: 27px;
  width: 5vw;
  height: 5vw;
  background-image: -webkit-linear-gradient(315deg, #fff 30%, transparent);
  background-image: linear-gradient(135deg, #fff 30%, transparent);
  box-shadow: inset 13px 0 17px -12px hsla(0, 13%, 82%, .43);
}

.corner2 {
  position: absolute;
  left: 28px;
  top: 0px;
  width: 5vw;
  height: 5vw;
  background-image: -webkit-linear-gradient(315deg, #fff 31%, transparent);
  background-image: linear-gradient(135deg, #fff 31%, transparent);
  box-shadow: inset 0 13px 17px -12px hsla(0, 13%, 82%, .43);
}

.page-text {
  position: relative;
  display: block;
  width: 80%;
  margin-top: 25px;
  margin-right: auto;
  margin-left: auto;
  font-family: Georgia, Times, 'Times New Roman', serif;
}

.page-text p {
  margin-bottom: 0px;
  font-size: 0.85vw;
  line-height: 1.5;
}

.page-text h3 {
  font-family: 'Palatino Linotype', 'Book Antiqua', Palatino, serif;
  font-style: italic;
}

.page-text h6 {
  font-family: 'PT Sans', sans-serif;
  font-size: 9px;
  text-decoration: none;
}

.page-text a {
  color: #da5a13;
}

.layer2 {
  position: fixed;
  left: 0px;
  top: 0px;
  right: 0px;
  bottom: 0px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  margin: 20px 10px 13px;
  -webkit-box-pack: start;
  -webkit-justify-content: flex-start;
  -ms-flex-pack: start;
  justify-content: flex-start;
  -webkit-transform: translate3d(2px, 0px, 10px);
  transform: translate3d(2px, 0px, 10px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.layer2.right {
  -webkit-transform: translate3d(-5px, 0px, 10px);
  transform: translate3d(-5px, 0px, 10px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.layer3 {
  position: fixed;
  left: 0px;
  top: 0px;
  right: 0px;
  bottom: 0px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  margin: 20px 10px 13px;
  -webkit-box-pack: start;
  -webkit-justify-content: flex-start;
  -ms-flex-pack: start;
  justify-content: flex-start;
  -webkit-transform: translate3d(4px, 0px, 20px);
  transform: translate3d(4px, 0px, 20px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.layer3.right {
  -webkit-transform: translate3d(-10px, 0px, 20px);
  transform: translate3d(-10px, 0px, 20px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.layer4 {
  position: fixed;
  left: 0px;
  top: 0px;
  right: 0px;
  bottom: 0px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  margin: 20px 10px 15px;
  -webkit-box-pack: start;
  -webkit-justify-content: flex-start;
  -ms-flex-pack: start;
  justify-content: flex-start;
  -webkit-transform: translate3d(6px, 0px, 30px);
  transform: translate3d(6px, 0px, 30px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.layer4.right {
  -webkit-transform: translate3d(-15px, 0px, 30px);
  transform: translate3d(-15px, 0px, 30px);
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.page-right-2 {
  position: relative;
  -webkit-box-flex: 1;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  border-top-right-radius: 1%;
  border-bottom-right-radius: 1%;
  background-color: #fff;
  box-shadow: inset 0 0 7px 4px hsla(0, 13%, 82%, .43), 1px 1px 13px 0 rgba(34, 27, 20, .49);
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
  -webkit-transform: rotateX(0deg) rotateY(-3deg) rotateZ(0deg);
  transform: rotateX(0deg) rotateY(-3deg) rotateZ(0deg);
  -webkit-transform-origin: 0% 50%;
  -ms-transform-origin: 0% 50%;
  transform-origin: 0% 50%;
  -webkit-transition: -webkit-transform 850ms ease;
  transition: -webkit-transform 850ms ease;
  transition: transform 850ms ease;
  transition: transform 850ms ease, -webkit-transform 850ms ease;
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.page-right-2:hover {
  -webkit-transform: rotateX(0deg) rotateY(-17deg) rotateZ(0deg);
  transform: rotateX(0deg) rotateY(-17deg) rotateZ(0deg);
}

.page-right {
  -webkit-box-flex: 1;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  border-top-right-radius: 1%;
  border-bottom-right-radius: 1%;
  background-color: #fff;
  box-shadow: inset 0 0 26px 2px #d8cccc, 1px 1px 13px 0 rgba(34, 27, 20, .81);
}

@media (max-width: 991px) {
  .scene {
    height: 56vw;
  }
  .book-wrap {
    width: 62vw;
  }
  .page-left-2 {
    border-top-left-radius: 20%;
  }
  .corner-fold {
    left: 0%;
    top: -1%;
    width: 3.5vw;
    height: 3.5vw;
    max-height: 35px;
    background-image: -webkit-linear-gradient(315deg, transparent 50%, #f0f0f0 51%, #fff 52%, #f6f6f6);
    background-image: linear-gradient(135deg, transparent 50%, #f0f0f0 51%, #fff 52%, #f6f6f6);
  }
  .corner {
    top: 8%;
  }
  .page-text h3 {
    margin-top: 2vw;
    margin-bottom: 1vw;
    font-size: 3vw;
  }
  .page-text h6 {
    margin-top: 1vw;
    margin-bottom: 1vw;
    font-size: 1vw;
  }
  .page-right-2 {
    padding-left: 9%;
  }
}

@media (max-width: 767px) {
  .book-wrap {
    width: 80vw;
    margin-bottom: 2vw;
  }
  .layer-text {
    width: 94%;
    -webkit-transform: translate3d(0px, 0px, 20px);
    transform: translate3d(0px, 0px, 20px);
  }
  .layer-text.right {
    -webkit-box-pack: end;
    -webkit-justify-content: flex-end;
    -ms-flex-pack: end;
    justify-content: flex-end;
    -webkit-transform: translate3d(-34px, 0px, 24px);
    transform: translate3d(-34px, 0px, 24px);
  }
  .page-left-2 {
    border-top-left-radius: 23%;
    -webkit-transform: rotateX(0deg) rotateY(17deg) rotateZ(0deg) translate(9px, 0px);
    transform: rotateX(0deg) rotateY(17deg) rotateZ(0deg) translate(9px, 0px);
  }
  .corner-fold {
    width: 4.5vw;
    height: 4.5vw;
    max-height: 29px;
    min-width: 29px;
    background-image: -webkit-linear-gradient(310deg, transparent 48%, #f0f0f0 49%, #fff 50%, #f6f6f6);
    background-image: linear-gradient(140deg, transparent 48%, #f0f0f0 49%, #fff 50%, #f6f6f6);
  }
  .page-text {
    margin-top: 2vw;
  }
  .page-text h3 {
    margin-top: 0vw;
    font-size: 3vw;
  }
  .layer2 {
    -webkit-transform: translate3d(2px, 0px, 6px);
    transform: translate3d(2px, 0px, 6px);
  }
  .layer2.right {
    -webkit-transform: translate3d(-5px, 0px, 6px);
    transform: translate3d(-5px, 0px, 6px);
  }
  .layer3 {
    -webkit-transform: translate3d(4px, 0px, 12px);
    transform: translate3d(4px, 0px, 12px);
  }
  .layer3.right {
    -webkit-transform: translate3d(-10px, 0px, 12px);
    transform: translate3d(-10px, 0px, 12px);
  }
  .layer4 {
    -webkit-transform: translate3d(6px, 0px, 18px);
    transform: translate3d(6px, 0px, 18px);
  }
  .layer4.right {
    -webkit-transform: translate3d(-15px, 0px, 18px);
    transform: translate3d(-15px, 0px, 18px);
  }
  .page-right-2 {
    width: 92%;
    -webkit-box-flex: 0;
    -webkit-flex: 0 auto;
    -ms-flex: 0 auto;
    flex: 0 auto;
  }
}

@media (max-width: 479px) {
  .scene {
    height: 69vw;
  }
  .corner-fold {
    width: 5.5vw;
    height: 5.5vw;
    min-width: 21px;
  }
}
</style>