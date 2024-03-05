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
                        {{ comment.comment_writer }}
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
.comment-content {
    width: 100%;
    border-top: 1px solid rgb(234, 234, 234);
    box-sizing: border-box;
    padding-left: 32px;
    padding-right: 32px;
}
.comment-content .comment_num {
    margin-top: 24px;
    margin-bottom: 24px;
    font-size: 16px;
    color: rgb(15, 41, 77);
    line-height: 24px;
    text-align: left;
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
    line-height: 24px;
    font-size: 16px;
    font-weight: bold;
    color: rgb(15, 41, 77);
    
    position: relative;
    top: 0%;
    left: 0%;
    transform: translate(0%, 0%);
    text-align: left;
    width: 100%;
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