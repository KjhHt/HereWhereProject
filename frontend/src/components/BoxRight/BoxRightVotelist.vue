<template>
    <div class="vote_list">
       <div style="width:24px;height:24px;margin-right:16px;cursor:pointer" data-resource="likelist">
            <svg v-if="!detailData.like" @click="updateLikes" width="24" height="24" viewBox="0 0 256 256">
                <path d="M21.333 229.333V95.411h54.814l74.519-74.078 34.41 34.207-17.826 39.87H240l-34.544 133.923H21.333zM150.666 51.637L86 115.919v91.987h102.73l23.49-91.067h-78.138l25.285-56.554-8.701-8.648zm-86.222 65.202H42.89v91.067h21.555v-91.067z" fill="#34475e" class="transform-group">
                </path>
            </svg>
            <svg v-else @click="updateLikes" width="24" height="24" viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                <path d="M58.667 101.333v117.334h-32V101.333h32zM144 26.667L170.667 48l-21.334 53.333h85.334l-28.718 117.334H80V101.333l64-74.666z" fill="#f5594a">
                </path>
            </svg>
       </div>
       <div class="vote_head_container" v-for="liker in likersFiltered" :key="liker.name" @click="handleClick(liker)" >
           <img class="vote_head_image" :alt="liker.name" :src="liker.profileimage">
       </div>
       <svg v-if="likersFiltered.length > 4" width="32" height="32" viewBox="0 0 256 256" style="cursor:pointer">
           <path d="M128 240c-61.856 0-112-50.144-112-112S66.144 16 128 16s112 50.144 112 112-50.144 112-112 112zM64 138.667h21.333v-21.334H64v21.334zm53.333 0h21.334v-21.334h-21.334v21.334zm53.334 0H192v-21.334h-21.333v21.334z" fill="#DADFE6">    
           </path>
       </svg>
       <div class="vote_text">{{ like_count }} 명이 해당 게시물을 좋아합니다</div>
   </div>
</template>
<script setup>
import { ref,computed,defineProps,watchEffect,defineEmits } from 'vue';

const likeList = ref([]);

const props = defineProps({
   detailData: Object,
   like_count: Object,
});

watchEffect(() => {
    const vuexStore = JSON.parse(localStorage.getItem('vuex'));
    const id = vuexStore.loginStore.userInfo.id;
    if (props.detailData) {
        if (props.detailData['likeList'] !== undefined && props.detailData['likeList'] !== null) {
            likeList.value = props.detailData['likeList'];
            const index = likeList.value.findIndex(value => value.like_id === id);
            if (index !== -1) {
                const matchedUser = likeList.value.splice(index, 1)[0]; // 일치하는 회원 제거
                likeList.value.unshift(matchedUser); // 제거한 회원을 배열의 맨 앞에 추가
            }
            console.log(likeList.value);
        }

        

    }
});

const likersFiltered = computed(() => {
 return likeList.value ? likeList.value.slice(0, 4) : [];
});

const emit = defineEmits(['updateLikes','openModal']);
const updateLikes = () => {
    emit('updateLikes');
}
const handleClick = () => {
  emit('openModal', likeList.value);
}

</script>
<style scope>
.photo-post-container .vote_list {
    margin-left: 32px;
    display: flex;
    flex-direction: row;
    -webkit-box-align: center;
    align-items: center;
    -webkit-box-pack: start;
    justify-content: flex-start;
    height: 68px;
    overflow: hidden;
}
.photo-post-container .vote_head_container {
    width: 28px;
    height: 28px;
    border-radius: 50%;
    margin: 0px 8px 0px 0px;
    overflow: hidden;
    background: rgb(218, 223, 230);
    cursor: pointer;
}
.photo-post-container .vote_head_image {
    width: 100%;
    height: 100%;
}
.photo-post-container .vote_text {
    margin-left: 8px;
    font-size: 14px;
    color: rgb(133, 146, 166);
    line-height: 22px;
    cursor: pointer;
}
</style>