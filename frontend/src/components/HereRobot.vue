<template>
  <div class="chatbot-container">
    <button class="chatbot-toggle-btn" @click="toggleChat">
      <i class="fas fa-robot"></i>
    </button>

    <section v-show="isChatOpen" ref="chatSection" class="msger">
      <header class="msger-header">
        <div class="msger-header-title">
          <i class="fas fa-comment-alt"></i> Herero
        </div>
      </header>

      <main class="msger-chat" ref="chatWindow">
        <div v-if="isLoading" class="loading-indicator">Loading...</div>
        <div v-for="message in messages" :key="message.id" :class="['msg', message.type]">
          <div class="msg-img">
            <img :src="message.avatar" class="img-fluid rounded-circle">
          </div>
          <div class="msg-bubble">
            <div class="msg-info">
              <div class="msg-info-name">{{ message.name }}</div>
              <div class="msg-info-time">{{ message.time }}</div>
            </div>
            <div v-if="!message.recommend || message.recommend.length === 0" class="msg-text">{{ message.text }}</div>
            <div class="msg-buttons" v-if="message.buttons">
              <button v-for="button in message.buttons" :key="button" @click="sendMessage(button)" class="msg-button">{{ button }}</button>
            </div>
            <div v-if="message.recommend && message.recommend.length > 0" class="recommendation">
              <DestinationCard :info="message.recommend[0]" @moveToLocation="onMoveToLocation"/>
            </div>
            <div v-if="message.flight && Object.keys(message.flight).length > 0" class="flight-info">
              <FlightCard :info="message.flight" @moveToFlight="onMoveToFlight"/>
            </div>
            <div v-for="(event, index) in message.schedule" :key="index" class="event-card">
              <ScheduleCard :info="convertScheduleTupleToObject(event)" @moveToLocation="onMoveToLocationLatLng"/>
            </div>
            <div v-if="message.recommend && message.recommend.length > 0" class="msg-text">{{ message.text }}</div>
          </div>
        </div>
      </main>

      <!-- Add a span for the microphone inside the input field -->
      <div class="msger-inputarea" @click.prevent="stopSynthesis">
        <input type="text" class="msger-input" v-model="newMessage" @input="handleInput" @keypress.enter="sendMessageFromInput" placeholder="Enter your message...">
        <span class="msger-mic-icon" @click="startSTT">
          <i class="fas fa-microphone"></i>
        </span>
        <!-- 기존 Send 버튼을 아래와 같이 수정 -->
        <button type="submit" class="msger-send-btn" @click="sendMessageFromInput" :class="{'tts-playing': isSpeaking}">
          <!-- TTS가 실행 중일 때와 아닐 때 아이콘을 동적으로 변경 -->
          <i class="fas" :class="{'fa-microphone-slash': isSpeaking, 'fa-paper-plane': !isSpeaking}"></i>
        </button>
      </div>

    </section>
  </div>
</template>


<script setup>
import { ref, onMounted, watch, nextTick,defineEmits } from 'vue';
import { gsap } from 'gsap';
import axios from 'axios';
import hereroAvatar from '@/assets/herero.png'
import DestinationCard from '@/components/chat/DestinationCard.vue'
import FlightCard from '@/components/chat/FlightCard.vue';
import ScheduleCard from '@/components/chat/ScheduleCard.vue';
import SpeechToText from '@/composable/stt.js'
import TextToSpeech from '@/composable/tts.js'

const emits=defineEmits(['moveToLocation','moveToFlight','moveToLocationLatLng'])
function onMoveToLocation(destination){
  emits('moveToLocation',destination);
  toggleChat();
}

function onMoveToFlight(info){
  const flight_info={origin:info.originCode,destination:info.destinationCode,adults:1}
  emits('moveToFlight',flight_info);
  toggleChat();
}

function onMoveToLocationLatLng(destination){
  const latlng=[destination.latitude,destination.longitude]
  emits('moveToLocationLatLng',latlng)
  toggleChat();
}




const newMessage = ref('');
const messages = ref([]);
const chatWindow = ref(null);
const chatSection = ref(null);
const isChatOpen = ref(false);
const typingIndicator = '...';
const isSpeaking = ref(false);
const isRecognizing=ref(false);
const vuex=localStorage.getItem('vuex')
const stt=new SpeechToText(newMessage,isRecognizing);
const tts=new TextToSpeech();
watch(()=>vuex,storage=>{
  if(!storage) return
})

const vuexStore = JSON.parse(localStorage.getItem('vuex'));
const userInfo = vuexStore.loginStore.userInfo;
const username = userInfo.id;
const name=userInfo.name;
const mbti = userInfo.mbti;
const user_profile=localStorage.getItem('profileImage')

function startSTT(){
  stt.startRecognition();
}


watch(isRecognizing.value, (recognizing) => {
  if(!recognizing) sendMessage(newMessage.value);
});


function toggleChat() {
  isChatOpen.value = !isChatOpen.value;
  if(isChatOpen.value && messages.value.length==1) tts.startSynthesis(messages.value[0].text)
}



const initialize = async () => {
  const url = `${process.env.VUE_APP_PYTHON_API_URL}/chat_initialize`;
  try {
    const response = await axios.get(url, {
      params: {
        username, mbti
      }
    });
    // 서버 응답을 바탕으로 메시지 배열 업데이트
    const serverMessage = response.data;
    const formattedMessage = {
      id: messages.value.length + 1,
      type: 'left-msg',
      name: serverMessage.name, // 여기서 'Herero'라는 이름을 사용합니다.
      text: serverMessage.text,
      time: new Date().toLocaleTimeString(),
      avatar: hereroAvatar, // 실제 경로로 교체 필요
      buttons: serverMessage.buttons,
      recommend:serverMessage.recommend|[],
      flight:serverMessage.flight|{},
      schedule:serverMessage.schedule|[],
    };
    messages.value.push(formattedMessage);
  } catch (error) {
    console.error("채팅 초기화 중 에러 발생:", error);
    // 에러 처리 로직
  }
};



function convertScheduleTupleToObject(tuple) {
  return {
    placeId: tuple[0],
    placeName: tuple[1],
    imageUrl: tuple[2], // 이 부분은 서버에서 올바른 이미지 URL로 변환 필요
    googlePlaceId: tuple[3],
    dateTime: new Date(tuple[4]).toLocaleString('ko-KR'), // 날짜 형식 변환
    duration: tuple[5],
    latitude: tuple[6],
    longitude: tuple[7]
  };
}

function scrollToBottom() {
  nextTick(() => {
    chatWindow.value.scrollTop = chatWindow.value.scrollHeight;
  });
}

const isLoading = ref(false); // 로딩 상태 추가

async function sendMessage(text) {
  isLoading.value = true; // 메시지 전송 시작 시 로딩 상태 활성화
  const message = {
    id: messages.value.length + 1,
    type: 'right-msg',
    name,
    text: text,
    time: new Date().toLocaleTimeString(),
    avatar: user_profile
  };
  messages.value.push(message);
  scrollToBottom();
  newMessage.value = '';
  try {
    const response = await axios.post(`${process.env.VUE_APP_PYTHON_API_URL}/assistant`,{text});
    
    // 서버로부터 응답 받은 데이터 처리
    const { data } = response;
    let textWithRecommend=data.text
    if(data.type === 'message') {
      if(data.recommend && data.recommend.length > 0) {
        // 첫 번째 recommend의 description을 추가
        textWithRecommend += "\n" + data.recommend[0].description;
      }
      const serverMessage = {
        id: messages.value.length + 1,
        type: 'left-msg', // 서버 메시지는 왼쪽에 표시
        name: data.name, // 'herero'
        text: textWithRecommend,
        time: new Date().toLocaleTimeString(),
        avatar: hereroAvatar, // 서버(또는 챗봇)의 아바타 경로, 필요에 따라 수정
        buttons: data.buttons, // 버튼이 있다면 버튼 데이터 포함
        recommend:data.recommend||[],
        flight:data.flight||{},
        schedule:data.schedule||[],
      };
      messages.value.push(serverMessage);
      tts.startSynthesis(serverMessage.text);
      tts.isSpeaking=true;
    }
  } catch (error) {
    console.error("메시지 전송 중 에러 발생:", error);
  } finally {
    isLoading.value = false; // 요청 완료 후 로딩 상태 비활성화
    scrollToBottom(); // 스크롤을 맨 아래로 이동하여 최신 메시지 표시
  }
  
}


watch(isChatOpen, (newValue) => {
  if (newValue) {
    gsap.fromTo(chatSection.value,
      { y: 400, opacity: 0 },
      { y: 0, opacity: 1, duration: 0.4, ease: "power2.out" }
    );
    scrollToBottom();
  } else {
    gsap.to(chatSection.value,
      { y: 50, opacity: 0, duration: 0.4, ease: "power2.in" }
    );
  }
});

onMounted(() => {
  gsap.set(chatSection.value, { y: 100 });
  scrollToBottom();
  initialize()
});

function handleInput(event) {
  // Stop typing animation when the user is typing
  const currentText = event.target.value;
  if (currentText !== '') {
    messages.value = messages.value.filter(m => m.text !== typingIndicator);
  }
}

// TTS 시작 및 정지를 관리하는 함수들을 수정
function sendMessageFromInput() {
  if (isSpeaking.value) {
    stopSpeech(); // TTS가 실행 중일 때, TTS 정지 함수 호출
  } else {
    if (!newMessage.value.trim()) return;
    sendMessage(newMessage.value);
  }
}

function stopSpeech() {
  tts.stopSynthesis(); // TTS 스피치를 중지하는 함수 호출
  tts.isSpeaking = false; // TTS 실행 상태를 false로 업데이트
}



</script>

<style scoped>
/* 로딩 인디케이터 스타일 */
.loading-indicator {
  text-align: center;
  padding: 10px;
}
/* 챗봇 토글 버튼 스타일 */
.chatbot-toggle-btn {
  position: fixed;
  right: 20px;
  bottom: 20px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #025CDB;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

/* 챗봇 인터페이스 스타일 업데이트 */
.msger {
  position: fixed;
  bottom: 70px; /* 토글 버튼 위의 위치 조정 */
  right: 20px;
  width: 350px;
  height: 500px; /* 세로 크기 고정 */
  display: flex;
  flex-direction: column;
  border-radius: 15px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.2);
  overflow: hidden;
  transition: all 0.3s ease;
}

.msger-header {
  background-color: #025CDB;
  color: white;
  height: 2em;
  text-align: center;
  line-height: 2em;
}

.msger-inputarea {
  position: relative; /* 포지션 기준점 설정 */
  display: flex;
  align-items: center;
  padding: 10px;
  border-top: 2px solid #ddd;
  background-color: white;
}

.msger-input {
  flex-grow: 1;
  padding: 10px 45px 10px 10px; /* 오른쪽 패딩을 마이크 아이콘 자리만큼 줌 */
  border-radius: 20px;
  border: none;
  background-color: #ddd;
}

.msger-mic-icon {
  position: absolute; /* 절대 위치 */
  right: 90px; /* 오른쪽에서 65px 떨어진 곳에 위치 */
  top: 50%; /* 위에서 50% 위치 */
  transform: translateY(-50%); /* 세로 중앙 정렬을 위한 변환 */
  color: #025CDB; /* 아이콘 색상 */
  cursor: pointer; /* 커서를 손가락 모
양으로 변경 /
z-index: 10; / 다른 요소들 위에 나타나게 함 */
}

.msger-send-btn {
margin-left: 10px;
background-color: #025CDB;
color: white;
border: none;
padding: 10px 20px;
border-radius: 20px;
cursor: pointer;
font-size: 16px;
}

.msger-send-btn:hover {
background-color: #6ca4f3;
}


.msger-chat {
  flex: 1;
  overflow-y: scroll;
  padding: 10px;
  background-color: #fcfcfe;
}

.msger-chat::-webkit-scrollbar {
  width: 6px;
}

.msger-chat::-webkit-scrollbar-track {
  background: #ddd;
}

.msger-chat::-webkit-scrollbar-thumb {
  background: #bdbdbd;
}

.msg {
  display: flex;
  align-items: flex-end;
  margin-bottom: 10px;
}

.msg-buttons {
  display: flex;
  flex-direction: column; /* 버튼을 세로로 정렬합니다 */
  align-items: center; /* 버튼을 가운데로 정렬합니다 */
  margin-top: 10px;
  width: 100%;
}

.msg-button {
  background-color: #579ffb; /* 버블 색상과 일치하도록 설정합니다 */
  color: white;
  border: none;
  border-radius: 20px; /* 모서리를 둥글게 처리합니다 */
  padding: 10px 0; /* 버튼 내부의 패딩을 조절합니다 */
  margin-bottom: 5px; /* 버튼 사이의 마진을 조절합니다 */
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s; /* 배경색 변경시 부드러운 전환 효과 */
  flex: none; /* flex-grow, flex-shrink, flex-basis를 none으로 설정하여 크기를 고정합니다 */
  width: auto; /* 버튼의 너비를 내용에 맞게 설정합니다 */
  width: calc(100% - 40px); /* 버블의 최대 너비에서 좌우 패딩을 고려하여 너비를 조정합니다 */
}


.msg-button:hover {
  background-color: #4e68ff;
}

.left-msg .msg-img, .right-msg .msg-img {
  width: 50px;
  height: 50px;
  background: #ddd;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  border-radius: 50%;
}

.left-msg {
  flex-direction: row;
}

.right-msg {
  flex-direction: row-reverse;
}

/* 메시지 버블 스타일 */
.msg-bubble {
  padding: 10px 20px;
  border-radius: 15px;
  margin: 0 10px;
  width: 250px;
  word-wrap: break-word;
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 버블 내용을 왼쪽 정렬 */
  /* 기존 스타일 유지 및 min-width 추가 */
  min-width: 100px; /* 최소 너비 설정 */
  max-width: calc(100% - 80px); /* 최대 너비 설정, 여백 고려 */
}

/* 왼쪽 메시지 버블 스타일 */
.left-msg .msg-bubble {
  background: #ececec;
  border-radius: 15px 15px 15px 0;
  left: 0;
  align-self: flex-start; /* 버블을 좌측에 정렬 */
}

/* 오른쪽 메시지 버블 스타일 */
.right-msg .msg-bubble {
  background: #579ffb;
  color: white;
  border-radius: 15px 15px 0 15px;
  right: 0;
  align-self: flex-end; /* 버블을 우측에 정렬 */
}

/* 메시지 정보(시간, 이름) 스타일 */
.msg-info {
  display: flex;
  width: 100%; /* 메시지 정보가 버블의 전체 너비를 차지하도록 조정 */
  justify-content: space-between;
  margin-bottom: 5px;
}

/* 메시지 텍스트 스타일 */
.msg-text {
  text-align: left; /* 텍스트 왼쪽 정렬 */
  width: 100%; /* 텍스트가 버블의 전체 너비를 차지하도록 조정 */
  margin-bottom: 10px;
}

.msg-info-name {
  font-weight: bold;
}

.msg-info-time {
  font-size: 0.85em;
}

.msger-send-btn.tts-playing {
  background-color: #f44336; /* 예: TTS 실행 중 버튼 색상 */
}
.msger-send-btn i.fa-microphone-slash {
  color: white; /* 정지 아이콘 색상 */
}
.msger-send-btn i.fa-paper-plane {
  color: white; /* 기본 아이콘 색상 */
}


</style>