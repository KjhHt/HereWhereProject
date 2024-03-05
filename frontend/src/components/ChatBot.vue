<template>
  <!-- 채팅 UI 및 메시지 출력 영역 -->
  <Chat :onSend="sendMessage" :chat="messages" />
</template>

<script setup>
import { Chat } from "@chat-ui/vue3";
import { ref } from 'vue';

// 채팅 메시지를 저장할 배열
const messages = ref([
  // { message: '나 챗봇이다', type: 'chatbot', timestamp: new Date().toLocaleTimeString() },
]);

// 사용자가 메시지를 입력하고 전송할 때 호출되는 함수
async function sendMessage(input) {
  if (!input.trim()) return; // 입력이 공백인 경우 처리하지 않음

  // 사용자가 입력한 메시지 채팅창에 추가
  messages.value.push({ message: input, type: 'person', timestamp: new Date().toLocaleTimeString() });

  try {
    // 서버로 메시지를 전송하고 챗봇 응답을 받음
    const response = await fetch(process.env.VUE_APP_PYTHON_API_URL+'/assistant', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ prompt: input })
    });

    // 서버 응답을 JSON 형식으로 파싱
    const data = await response.json();

    // 챗봇 응답 메시지 채팅창에 추가
    messages.value.push({ message: data.response, type: 'chatbot', timestamp: new Date().toLocaleTimeString() });
  } catch (error) {
    console.error('Error sending message to assistant:', error);
  }
}
</script>
