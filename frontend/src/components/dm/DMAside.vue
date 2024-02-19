<template>
	<aside>
		<header>
			<input type="text" placeholder="search">
		</header>
		<ul>
			<li v-for="(user, index) in props.followList" :key="index" @click="$emit('updateChatList', user,index)">
				<img :src="user.profileImage" alt="">
				<div>
					<h2>{{ user.name }}</h2>
					<h3>
						<span class="status orange"></span>
						offline
					</h3>
				</div>
			</li>
		</ul>
	</aside>
</template>
<script setup>
import { defineProps } from "vue";

const props=defineProps({
	followList : {
		type: Array,
	},
})
</script>
<style scoped>
aside{
	width:260px;
	height:800px;
	background-color:#3b3e49;
	display:inline-block;
	font-size:15px;
	vertical-align:top;
}
aside header{
	padding:30px 20px;
}
aside input{
	width:100%;
	height:50px;
	line-height:50px;
	padding:0 50px 0 20px;
	background-color:#5e616a;
	border:none;
	border-radius:3px;
	color:#fff;
	background-image:url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/ico_search.png);
	background-repeat:no-repeat;
	background-position:170px;
	background-size:40px;
}
aside input::placeholder{
	color:#fff;
}
aside ul::-webkit-scrollbar {
  width: 0; 
}
aside ul{
	padding-left:0;
	margin:0;
	list-style-type:none;
	overflow-y:scroll;
	height:690px;
}
aside li{
	padding:10px 0;
	text-align: left;
}
aside li:hover{
	background-color:#5e616a;
	cursor: pointer;
}
aside li img{
	display: inline-block;
	border-radius:50%;
	margin-left:20px;
	margin-right:8px;
	width: 55px;
	height: 55px;
}
aside li div{
	display:inline-block;
	vertical-align:top;
	margin-top:12px;
}
aside li h2{
	font-size:14px;
	color:#fff;
	font-weight:normal;
	margin-bottom:5px;
}
aside li h3{
	font-size:12px;
	color:#7e818a;
	font-weight:normal;
}
.status{
	width:8px;
	height:8px;
	border-radius:50%;
	display:inline-block;
	margin-right:7px;
}
.green{
	background-color:#58b666;
}
.orange{
	background-color:#ff725d;
}
.blue{
	background-color:#6fbced;
	margin-right:0;
	margin-left:7px;
}
</style>