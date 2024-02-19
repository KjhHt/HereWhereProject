<!-- <script setup>
import axios from '@axios'
import { PerfectScrollbar } from 'vue3-perfect-scrollbar'

const props = defineProps({
  togglerIcon: {
    type: String,
    required: true,
    default: 'mdi-robot-confused-outline',
  },
})

const router = useRouter()

const messages = ref([]) 
const isLoading = ref(false)  // 로딩 상태를 저장
const userInput = ref('') // 사용자의 입력을 저장할 ref를 선언

//챗봇 서버로 메세지 넘기고 받기
async function sendMessage() {
  messages.value.push({
    type: 'send',
    content: userInput.value,
  })

  isLoading.value = true  // 로딩 상태를 시작

  const response = await axios.post('http://localhost:5000/ChatAI', {
    message: userInput.value,
  })

  userInput.value = '' 

  isLoading.value = false  // 로딩 상태를 종료

  console.log(response.data.answer)

  // 서버로부터 받은 메시지를 채팅창에 추가합니다.
  messages.value.push({
    type: 'receive',
    content: response.data.answer,
  })
  
}

// 아이콘을 누르면 채팅창이 초기화되는 함수를 선언합니다.
//function resetChat() {
//  messages.value = []
//}
</script>

<template>
  <IconBtn>
    <VIcon
      :icon="props.togglerIcon"
      size="x-large"
      color="success"
    />

    <VMenu
      activator="parent"
      offset="20px"
      location="bottom end"
      :close-on-content-click="false"
    >
      <VCard
        width="550"
        max-height="700"
        class="d-flex flex-column"
      >
        <VCardItem class="py-4">
          <VCardTitle>ChatBot</VCardTitle>

          <template #append>
            <IconBtn>
              <VIcon icon="mdi-robot-confused-outline" />
            </IconBtn>
          </template>
        </VCardItem>

        <VDivider />

        <PerfectScrollbar :options="{ wheelPropagation: false }">
          <VRow class="ma-0 mt-n1">
            <div class="chat-container">
              <div class="receive">
                <p class="chat-bubble">
                  안녕하세요! 어떤 도움이 필요하신가요?
                </p>
              </div>
              <div
                v-for="(message, index) in messages"
                :key="index"
                :class="message.type"
              >
                <p class="chat-bubble">
                  {{ message.content }}
                </p>
              </div>
            </div>
          </VRow>
        </PerfectScrollbar>
        <VCol cols="12">
          <VRow>
            <VCol cols="9">
              <VTextField
                v-model="userInput"
                prepend-inner-icon="mdi-comment-outline"
                label="메시지 입력"
              />
            </VCol>
            <VCol cols="2">
              <VBtn
                size="x-large"
                @click="sendMessage"
              >
                전송
                <VProgressCircular
                  v-if="isLoading"
                  indeterminate
                  color="#8B4513"
                  width="3" 
                  size="20"
                />
              </VBtn>
            </VCol>
          </VRow>
        </VCol>
      </VCard>
    </VMenu>
  </IconBtn>
</template>

<style lang="scss">
/* 채팅방 스타일 */
.chat-container {
  display: flex;
  flex-direction: column;
  inline-size: 100%;
  margin-block: 0;
  margin-inline: auto;
  max-inline-size: 530px;
}

.chat-bubble {
  border-radius: 20px;
  margin-block-end: 10px;
  max-inline-size: 100%;
  padding-block: 10px;
  padding-inline: 20px;
}

.send {
  align-self: flex-end;
  border-radius: 40px 0 0 40px;
  background-color: #86e93a;
  font-weight: bold;
  margin-block-start: 10px;
  max-inline-size: 70%;
  text-align: end;
}

.receive {
  align-self: flex-start;
  border-radius: 0 40px 40px 0;
  background-color: #ad59b4;
  color: white;
  margin-block-start: 10px;
  max-inline-size: 70%;
  text-align: start;
}
</style> -->
