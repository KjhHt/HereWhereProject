<template>
    <div class="Container-sc-1cqmift-0 comment-content">
            <div class="comment_num">
                {{ comments.length }} 댓글
            </div>
            <div class="scrollCardContent">
            <div class="Container-sc-10c2rpq-0 comment-body cell"  v-for="comment in comments" :key="comment.comment_writer">
                <div class="user">
                    <img class="img" :alt="comment.comment_writer" :src="comment.profileimage">
                    <div class="title">
                        <span>{{ comment.comment_writer }}</span>
                        <svg :class="comment.comment_result==5?'super-happy':''" width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"><path d="M12,17.5C14.33,17.5 16.3,16.04 17.11,14H6.89C7.69,16.04 9.67,17.5 12,17.5M8.5,11A1.5,1.5 0 0,0 10,9.5A1.5,1.5 0 0,0 8.5,8A1.5,1.5 0 0,0 7,9.5A1.5,1.5 0 0,0 8.5,11M15.5,11A1.5,1.5 0 0,0 17,9.5A1.5,1.5 0 0,0 15.5,8A1.5,1.5 0 0,0 14,9.5A1.5,1.5 0 0,0 15.5,11M12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4A8,8 0 0,1 20,12A8,8 0 0,1 12,20M12,2C6.47,2 2,6.5 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z" /></svg>
                        <svg :class="comment.comment_result==4?'happy':''" width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"><path d="M20,12A8,8 0 0,0 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20A8,8 0 0,0 20,12M22,12A10,10 0 0,1 12,22A10,10 0 0,1 2,12A10,10 0 0,1 12,2A10,10 0 0,1 22,12M10,9.5C10,10.3 9.3,11 8.5,11C7.7,11 7,10.3 7,9.5C7,8.7 7.7,8 8.5,8C9.3,8 10,8.7 10,9.5M17,9.5C17,10.3 16.3,11 15.5,11C14.7,11 14,10.3 14,9.5C14,8.7 14.7,8 15.5,8C16.3,8 17,8.7 17,9.5M12,17.23C10.25,17.23 8.71,16.5 7.81,15.42L9.23,14C9.68,14.72 10.75,15.23 12,15.23C13.25,15.23 14.32,14.72 14.77,14L16.19,15.42C15.29,16.5 13.75,17.23 12,17.23Z" /></svg>
                        <svg :class="comment.comment_result==3?'neutral':''" width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"><path d="M8.5,11A1.5,1.5 0 0,1 7,9.5A1.5,1.5 0 0,1 8.5,8A1.5,1.5 0 0,1 10,9.5A1.5,1.5 0 0,1 8.5,11M15.5,11A1.5,1.5 0 0,1 14,9.5A1.5,1.5 0 0,1 15.5,8A1.5,1.5 0 0,1 17,9.5A1.5,1.5 0 0,1 15.5,11M12,20A8,8 0 0,0 20,12A8,8 0 0,0 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20M12,2A10,10 0 0,1 22,12A10,10 0 0,1 12,22C6.47,22 2,17.5 2,12A10,10 0 0,1 12,2M9,14H15A1,1 0 0,1 16,15A1,1 0 0,1 15,16H9A1,1 0 0,1 8,15A1,1 0 0,1 9,14Z" /></svg>
                        <svg :class="comment.comment_result==2?'sad':''" width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"><path d="M20,12A8,8 0 0,0 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20A8,8 0 0,0 20,12M22,12A10,10 0 0,1 12,22A10,10 0 0,1 2,12A10,10 0 0,1 12,2A10,10 0 0,1 22,12M15.5,8C16.3,8 17,8.7 17,9.5C17,10.3 16.3,11 15.5,11C14.7,11 14,10.3 14,9.5C14,8.7 14.7,8 15.5,8M10,9.5C10,10.3 9.3,11 8.5,11C7.7,11 7,10.3 7,9.5C7,8.7 7.7,8 8.5,8C9.3,8 10,8.7 10,9.5M12,14C13.75,14 15.29,14.72 16.19,15.81L14.77,17.23C14.32,16.5 13.25,16 12,16C10.75,16 9.68,16.5 9.23,17.23L7.81,15.81C8.71,14.72 10.25,14 12,14Z" /></svg>
                        <svg :class="comment.comment_result==1?'super-sad':''" width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"><path d="M12,2C6.47,2 2,6.47 2,12C2,17.53 6.47,22 12,22A10,10 0 0,0 22,12C22,6.47 17.5,2 12,2M12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4A8,8 0 0,1 20,12A8,8 0 0,1 12,20M16.18,7.76L15.12,8.82L14.06,7.76L13,8.82L14.06,9.88L13,10.94L14.06,12L15.12,10.94L16.18,12L17.24,10.94L16.18,9.88L17.24,8.82L16.18,7.76M7.82,12L8.88,10.94L9.94,12L11,10.94L9.94,9.88L11,8.82L9.94,7.76L8.88,8.82L7.82,7.76L6.76,8.82L7.82,9.88L6.76,10.94L7.82,12M12,14C9.67,14 7.69,15.46 6.89,17.5H17.11C16.31,15.46 14.33,14 12,14Z" /></svg>
                    </div>
                </div>
                <div class="content">
                    {{ comment.comment_content }}
                </div>
                <div class="footer">
                    <div class="post_time">
                        {{ comment.comment_createtime }}
                    </div>
                    <div class="helpful_container" @click="toggleLike(comment)">
                        <svg v-if="comment.user_liked === 'F'" width="16" height="16" viewBox="0 0 256 256" >
                            <path d="M21.333 229.333V95.411h54.814l74.519-74.078 34.41 34.207-17.826 39.87H240l-34.544 133.923H21.333zM150.666 51.637L86 115.919v91.987h102.73l23.49-91.067h-78.138l25.285-56.554-8.701-8.648zm-86.222 65.202H42.89v91.067h21.555v-91.067z" fill="#34475e" class="transform-group">
                            </path>
                        </svg>
                        <svg v-else width="16" height="16" viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                            <path d="M58.667 101.333v117.334h-32V101.333h32zM144 26.667L170.667 48l-21.334 53.333h85.334l-28.718 117.334H80V101.333l64-74.666z" fill="#f5594a">
                            </path>
                        </svg>
                        <div class="helpful_like">
                            {{ comment.like_count }} 
                        </div>
                    </div>
                </div>
                <div style="height:15px"></div>
                <div class="devider"></div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref , defineProps , watch } from 'vue';
import axios from 'axios';
// import EmotionList from '@/components/BoxRight/EmotionList.vue';

const props = defineProps({
    detailData: Object,
    lastUpdated : Number,
});

const comments = ref([]);

const commentListData = async (board_no) => {
    try {
        const res = await axios.get(process.env.VUE_APP_API_URL+'/comment',{
            params : {
                board_no : board_no,
            }
        })
        const newCommentList = res.data.map(item => {
            //프로필이 0일때와 base64문자열을 헤더붙여주기
            if(item.profileimage === '0')
                item.profileimage = require('@/assets/dino.jpg');
            else if(!item.profileimage.startsWith('http')){
                item.profileimage = `data:${res.headers['content-type']};base64,${item.profileimage}`;
            }
            return item;
        });
        comments.value = newCommentList;
    } catch(err) {
        console.log(err)
    }
}

watch([() => props.detailData, () => props.lastUpdated], async ([newDetailData, newLastUpdated], [oldDetailData, oldLastUpdated]) => {
  if (newDetailData !== oldDetailData || newLastUpdated !== oldLastUpdated) {
    if (newDetailData['board_no'] !== undefined && newDetailData['board_no'] !== null) { // board_no 값이 undefined나 null이 아닌지 확인
      await commentListData(newDetailData['board_no']);
    }
  }
}, { immediate: true });


// 좋아요 눌렀을 시 
const toggleLike = (comment) => {
    let isLike = null;
    // 회원인지 비회원인지 id값으로 판단
    if (localStorage.getItem('access_token')) {
        // 눌렀었는지 안눌렀었는지
        if(comment.user_liked === 'F')
            isLike = true;
        else
            isLike = false;
        const vuexStore = JSON.parse(localStorage.getItem('vuex'));
        const id = vuexStore.loginStore.userInfo.id;
        const comment_no = comment.comment_no;
        axios.post(process.env.VUE_APP_API_URL+'/commentLike',{
            comment_no: comment_no,
            id: id,
            isLike: isLike,
        })
        .then(res => {
            if(res.status === 200){
                if(comment.user_liked === 'F'){
                    comment.like_count++;
                    comment.user_liked = 'T';
                }
                else{
                    comment.like_count--;
                    comment.user_liked = 'F';
                }
            }
        })
        .catch(err=>console.log(err))
    }
    else {
        // 비로그인 상태
        alert('로그인을 먼저 진행해주세요!');
    }
};

</script>

<style scope>

.super-happy {
	fill: rgb(0, 109, 217);
}

.happy {
	fill: rgb(0, 204, 79);
}

.neutral {
	fill: rgb(232, 214, 0);
}

.sad {
	fill: rgb(229, 132, 0);
}

.super-sad {
	fill: rgb(239, 42, 16);
}


.comment-content {
    width: 100%;
    border-top: 1px solid rgb(234, 234, 234);
    box-sizing: border-box;
    padding-left: 32px;
    padding-right: 32px;
    min-height: 249px;
}
.comment-content .comment_num {
    margin-top:10px;
    margin-bottom: 10px;
    font-size: 16px;
    color: rgb(15, 41, 77);
    line-height: 24px;
    text-align: left;
}
.scrollCardContent{
    width: 446px;
    height: 198px;
}


.comment-body {
    width: 100%;
    position: relative;
}
.comment-body .user {
    margin-top: 16px;
    display: flex;
    flex-direction: row;
    -webkit-box-pack: start;
    justify-content: flex-start;
    -webkit-box-align: center;
    align-items: center;
}
.comment-body .img {
    flex-shrink: 0;
    width: 28px;
    height: 28px;
    border-radius: 50%;
    overflow: hidden;
    margin-right: 8px;
    background-color: rgb(245, 247, 250);
}
.comment-body .title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    line-height: 24px;
    font-size: 16px;
    font-weight: bold;
    color: rgb(15, 41, 77);
    width: 100%;
}

.comment-body .title span{
    min-width: 272px;
    text-align: left;
}

.comment-body .content {
    margin: 0px 0px 0px 36px;
    line-height: 24px;
    font-size: 16px;
    color: rgb(15, 41, 77);
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 4;
    overflow: hidden;
    word-break: break-all;
    text-align: left;
}
.comment-body .footer {
    margin-left: 36px;
    margin-top: 8px;
    margin-right: 0px;
    line-height: 16px;
    font-size: 12px;
    color: rgb(133, 146, 166);
    display: flex;
    flex-direction: row;
    -webkit-box-pack: justify;
    justify-content: space-between;
}
.comment-body .post_time {
    line-height: 16px;
    font-size: 12px;
    color: rgb(133, 146, 166);
}
.comment-body .helpful_container {
    display: flex;
    flex-direction: row;
    -webkit-box-pack: start;
    justify-content: flex-start;
    cursor: pointer;
}
.comment-body .helpful_like {
    margin-left: 4px;
    line-height: 16px;
    font-size: 12px;
    color: rgb(133, 146, 166);
}
.comment-body .devider {
    height: 1px;
    width: calc(100% + 0px);
    background-color: rgb(218, 223, 230);
    position: absolute;
    bottom: 0px;
}

</style>