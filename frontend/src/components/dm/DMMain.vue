<template>
	<main>
		<header>
			<img :src="followList[followIndex].profileImage" alt="">
			<div>
				<h2>{{ followList[followIndex].name }}</h2>
				<h3>already 1902 messages</h3>
			</div>
			<img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/ico_star.png" alt="">
		</header>
		<ul id="chat">
			<li v-for="(value, index) in chatList" :key="index" :class="value.dm_type">
				<div class="entete">
					<span class="status green"></span>
					<h2>{{ value.dm_sender_id }}</h2>
					<h3>{{ formatTime(value.dm_sendtime) }}</h3>
				</div>
				<div class="triangle"></div>
				<div class="message">
					{{ value.dm_content }}  
				</div>
			</li>
		</ul>
		<footer>
			<textarea placeholder="Type your message" v-model="newMessage"></textarea>
			<a href="#" @click="sendMessage">Send</a>
		</footer>
	</main>
</template>
<script setup>
import { defineProps, ref, onMounted, onUnmounted, defineExpose } from "vue";
import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs';
import axios from "axios";
import { nextTick } from 'vue';

const stompClient = ref(null);
const newMessage = ref('');

const props = defineProps({
  followList : {
    type: Array,
  },
  chatList : {
    type: Array,
  },
  followIndex : {
	type: Number,
  },
  addMessage: {
    type: Function,
  },
});

onMounted(() => {
  connect();
});

onUnmounted(() => {
  disconnect();
});

function connect() {
  var socket = new SockJS(`${process.env.VUE_APP_API_URL}/wss`)
  stompClient.value = Stomp.over(socket)
  stompClient.value.connect({}, onConnected, onError)
}

function disconnect() {
  if (stompClient.value != null) {
    stompClient.value.disconnect();
  }
}

function onConnected() {
  // 서버에 메시지를 보낼 때
  //stompClient.value.send("/app/chat.sendMessage",{},JSON.stringify({dm_content: '사용자와 채팅을 나눠보세요!'}));
  stompClient.value.subscribe(`/topic/${props.chatList[0].dm_chat_id}`, onMessageReceived)
  
}

function onError() {
  console.log('onError부분 웹 소켓 서버에 문제가 있다')
}

function formatTime(dateString) {
    let date = new Date(dateString);  // dateString을 Date 객체로 변환
    let hours = date.getHours();
    let minutes = date.getMinutes();
    let ampm = hours >= 12 ? '오후' : '오전';

    hours = hours % 12;
    hours = hours ? hours : 12; 
    minutes = minutes < 10 ? '0' + minutes : minutes;

    return ampm + ' ' + hours + ':' + minutes;
}

function onMessageReceived(payload) {
	const vuexStore = JSON.parse(localStorage.getItem('vuex'));
	const id = vuexStore.loginStore.userInfo.id;
	var receivedMessage = JSON.parse(payload.body);

	const message = {
		dm_sender_id: receivedMessage.dm_sender_id,
		dm_sendtime: new Date(), 
		dm_content: receivedMessage.dm_content,
		dm_type: receivedMessage.dm_sender_id === id ? 'me' : 'you',
	};
	props.addMessage(message);

	nextTick(() => {
		const chatElement = document.getElementById('chat');
		chatElement.scrollTop = chatElement.scrollHeight;
	});
}

async function sendMessage() {
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const send_id = vuexStore.loginStore.userInfo.id;
  const name = vuexStore.loginStore.userInfo.name;
  
  try {
    await axios.post(`${process.env.VUE_APP_API_URL}/user/insertMessage`,{
      dm_sender_id : send_id,
      dm_recipient_id : props.followList[props.followIndex].id,
      dm_content : newMessage.value
    });
    
    stompClient.value.send(`/app/chat.${props.chatList[0].dm_chat_id}sendMessage`,{}, JSON.stringify({
      dm_sender_id : send_id,
      dm_content: newMessage.value
    }));
    
    nextTick(() => {
      const chatElement = document.getElementById('chat');
      chatElement.scrollTop = chatElement.scrollHeight;
    });

    // 이제 여기서 알림을 보내는 요청을 보냅니다.
    await axios.post(`${process.env.VUE_APP_API_URL}/pushSend`, {
      id : props.followList[props.followIndex].id,  // 알림을 받을 사용자의 ID
      title: '새 메시지가 도착했습니다.',  // 알림의 제목
      message: name+' : '+newMessage.value  // 알림의 내용
    });

	newMessage.value = '';
    
  } catch (err) {
    console.error(err);
  }
}

defineExpose({ connect, disconnect, onConnected, onError, onMessageReceived, sendMessage })
</script>
<style scoped>
main{
	width:490px;
	height:800px;
	display:inline-block;
	font-size:15px;
	vertical-align:top;
}
main header{
	height:110px;
	padding:30px 20px 30px 40px;
}
main header > *{
	display:inline-block;
	vertical-align:top;
}
main header img:first-child{
	width: 55px;
	height: 55px;
	border-radius:50%;
}
main header img:last-child{
	width:24px;
	margin-top:8px;
}
main header div{
	margin-left:10px;
	margin-right:145px;
}
main header h2{
	font-size:16px;
	margin-bottom:5px;
}
main header h3{
	font-size:14px;
	font-weight:normal;
	color:#7e818a;
}
main footer{
	height:155px;
	padding:20px 30px 10px 20px;
}
main footer textarea{
	resize:none;
	border:none;
	display:block;
	width:100%;
	height:80px;
	border-radius:3px;
	padding:20px;
	font-size:13px;
	margin-bottom:13px;
}
main footer textarea::placeholder{
	color:#ddd;
}
main footer a{
	text-decoration:none;
	text-transform:uppercase;
	font-weight:bold;
	color:#6fbced;
	vertical-align:top;
	margin-left:383px;
	margin-top:5px;
	display:inline-block;
}

#chat{
	padding-left:0;
	margin:0;
	list-style-type:none;
	overflow-y:scroll;
	height:535px;
	border-top:2px solid #fff;
	border-bottom:2px solid #fff;
}
#chat li{
	padding:10px 30px;
}
#chat h2,#chat h3{
	display:inline-block;
	font-size:13px;
	font-weight:normal;
}
#chat h3{
	color:#bbb;
}
#chat .entete{
	margin-bottom:5px;
}
#chat .message{
	padding:20px;
	color:#fff;
	line-height:25px;
	max-width:90%;
	display:inline-block;
	text-align:left;
	border-radius:5px;
}
#chat .me{
	text-align:right;
}
#chat .you {
	text-align: left;
}
#chat .you .message{
	background-color:#58b666;
}
#chat .me .message{
	background-color:#6fbced;
}
#chat .triangle{
	width: 0;
	height: 0;
	border-style: solid;
	border-width: 0 10px 10px 10px;
}
#chat .you .triangle{
	border-color: transparent transparent #58b666 transparent;
	margin-left:15px;
}
#chat .me .triangle{
	border-color: transparent transparent #6fbced transparent;
	margin-left:375px;
}



</style>