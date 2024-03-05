<template>
    <div class="input_view">
       <div class="textarea_container">
         <div class="textarea2" id="textarea2" contenteditable="true" @input="updateComment" placeholder="댓글을 남겨주세요"></div>
           <div class="submit" id="submit-button" @click="addComment" style="color:#3264FF88">확인</div>
       </div>
       <div class="input_phaise_container" data-resource="like">
           <div>
               <svg v-if="!detailData.like" @click="updateLikes1" width="32" height="32" viewBox="0 0 256 256">
                   <path d="M21.333 229.333V95.411h54.814l74.519-74.078 34.41 34.207-17.826 39.87H240l-34.544 133.923H21.333zM150.666 51.637L86 115.919v91.987h102.73l23.49-91.067h-78.138l25.285-56.554-8.701-8.648zm-86.222 65.202H42.89v91.067h21.555v-91.067z" fill="#34475e" class="transform-group"></path>
               </svg>
               <svg v-else @click="updateLikes1" width="32" height="32" viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                  <path d="M58.667 101.333v117.334h-32V101.333h32zM144 26.667L170.667 48l-21.334 53.333h85.334l-28.718 117.334H80V101.333l64-74.666z" fill="#f5594a">
                  </path>
               </svg>
               <span class="input_number">{{ like_count }}</span>
           </div>
       </div>    
   </div>
</template>

<script setup>
import { ref , defineProps , watchEffect, defineEmits } from 'vue';
import axios from 'axios';

const newComment = ref('');
const board_no = ref('');


const emit = defineEmits(['updateComment','updateLikes1']);

const props = defineProps({
    detailData: Object,
    like_count: Object,
});

watchEffect(() => {
  if (props.detailData) {
   board_no.value = props.detailData['board_no'];
  }
});

const updateComment = (event) => {
    newComment.value = event.target.innerText;
};

const addComment = () => {
   const userComment = newComment.value;
   const vuexStore = JSON.parse(localStorage.getItem('vuex'));
   const name = vuexStore.loginStore.userInfo.name;
   const data = {
    board_no : board_no.value,
    comment_content : userComment,
    comment_writer : name
  }
   axios.post(`${process.env.VUE_APP_API_URL}/user/commentInsert`,data)
   .then( res => {
      if(res.status === 200){
         emit('updateComment',Date.now());
      }
   })
   .catch(err => console.log(err))
   document.getElementById('textarea2').innerText = '';
}

const updateLikes1 = () => {
    emit('updateLikes1');
}

</script>
<style scope>
.photo-post-container .input_view {
   position: relative;
   bottom: 0px;
   left: 0px;
   width: 100%;
   box-sizing: border-box;
   display: flex;
   flex-direction: row;
   -webkit-box-pack: center;
   justify-content: center;
   -webkit-box-align: center;
   align-items: center;
   background-color: white;
   padding: 12px 32px 12px 16px;
   box-shadow: rgba(53, 58, 64, 0.19) 0px 0px 8px 0px;
}
.photo-post-container .textarea_container {
   flex: 10 1 0%;
   color: rgb(15, 41, 77);
   padding: 0px 16px;
   border: 1px solid rgb(218, 223, 230);
   border-radius: 4px;
   display: flex;
   flex-direction: row;
   -webkit-box-pack: justify;
   justify-content: space-between;
   -webkit-box-align: center;
   align-items: center;
    
}
.photo-post-container .textarea2 {
   margin: 16px 0px;
   flex: 1 1 0%;
   max-width: 305px;
   min-height: 22px;
   max-height: 92px;
   line-height: 22px;
   text-align: left;
   overflow: auto;
   
}
.photo-post-container .textarea2:focus {
   outline: none;
}
.photo-post-container .textarea2[placeholder]:empty::before {
   content: attr(placeholder);
   color: rgb(133, 146, 166);

}

.photo-post-container .submit {
   cursor: pointer;
   margin-left: 16px;
   font-weight: bold;
   font-size: 16px;
}
.photo-post-container .input_phaise_container {
   display: flex;
   flex-direction: column;
   -webkit-box-pack: center;
   justify-content: center;
   -webkit-box-align: center;
   align-items: center;
   margin-left: 26px;
   cursor: pointer;
}

.photo-post-container .input_number {
   color: rgb(69, 88, 115);
   font-size: 14px;
   font-weight: normal;
   position: absolute;
   top: 16px;
}
</style>