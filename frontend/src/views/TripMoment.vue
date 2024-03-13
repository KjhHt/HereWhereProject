<template>
    <SearchBar searchData
        @keywordChanged="updateKeyword"
        @search="search"
    />
    <div class="index_trip_moment_con">
        <TripMomentTitle @moveJoinView="$emit('selectPage', 'TripMomentWriteView')"/>
        <TripCard 
            :loadingModal="loadingModal"
            :averageProbabilities="averageProbabilities" 
            :boardList="boardList" 
            :no_BoardList="no_BoardList"
            @requestMoreData="loadMoreData"
            @moveDetailView="handleMoveDetailView"
        />
    </div>

    <!-- <button 
        style="display: none;" 
        id="selectPage" 
        @click="$emit('selectPage','TripMomentJoinView')">
    </button> -->

</template>
  
<script setup>
import { ref , onMounted , defineEmits } from 'vue';
import SearchBar from '@/components/SearchBar.vue';
import TripMomentTitle from '@/components/TripMomentTitle.vue'
import TripCard from '@/components/TripCard.vue'
import axios from 'axios';

const num = ref(0);
const boardList = ref([]);
const no_BoardList = ref(false);
const keyword = ref('');
const loadingModal = ref(false);
const averageProbabilities = ref('');
const emit = defineEmits(['selectPageData'])

const handleMoveDetailView = (data) => {
    emit('selectPageData','TripMomentDetailView',data);
}

// 이전과 같이 전역 변수로 commentProbabilitiesList를 선언합니다.
let commentProbabilitiesList = []; // 전역 또는 상태 관리 스토어에 저장하는 것을 추천

// getBoardList 함수 정의
const getBoardList = (keyword) => {
    // 게시판 데이터 호출
    axios.get(process.env.VUE_APP_API_URL+'/board', {
        params: {
            num: num.value, // num 변수가 정의되어 있어야 합니다.
            keyword: keyword,
        }
    })
    .then(res => {
        const newBoardList = res.data.map(item => {
            // 프로필 이미지 처리
            if(item.profileimage === '0')
                item.profileimage = require('@/assets/dino.jpg');
            else if(!item.profileimage.startsWith('http')){
                item.profileimage = `data:${res.headers['content-type']};base64,${item.profileimage}`;
            }

            // 게시판 이미지들 처리
            item.base64BoardImages = item.base64BoardImages.map(value => {
                return `data:${res.headers['content-type']};base64,${value}`;
            });

            // 좋아요 리스트 프로필 이미지 처리
            if(item.likeList && item.likeList.length > 0){
                item.likeList = item.likeList.map(likeItem => {
                    if(likeItem.profileimage === '0')
                        likeItem.profileimage = require('@/assets/dino.jpg');
                    else if(!likeItem.profileimage.startsWith('http')){
                        likeItem.profileimage = `data:${res.headers['content-type']};base64,${likeItem.profileimage}`;
                    }
                    return likeItem;
                });
            }
            return item;
        });

        // 새로운 게시물에 대한 comment_probability 리스트 업데이트
        const newCommentProbabilities = newBoardList.map(post => {
            return post.commentList.map(comment => {
                // '부정'인 경우, 100에서 comment_probability 값을 빼서 반환
                if(comment.comment_sentiment === '부정') {
                    return (100 - comment.comment_probability).toFixed(2);
                }
                // 그 외의 경우, comment_probability 값을 그대로 반환
                return comment.comment_probability;
            });
        });
        // 기존 리스트에 새로운 게시물의 comment_probability 리스트를 추가
        commentProbabilitiesList = [...commentProbabilitiesList, ...newCommentProbabilities];

        // 각 게시물의 comment_probability 평균 계산 함수
        const calculateAverageCommentProbability = () => {
            const averages = commentProbabilitiesList.map(commentProbabilities => {
                const sum = commentProbabilities.reduce((acc, probability) => acc + parseFloat(probability), 0);
                const average = sum / commentProbabilities.length;
                return average.toFixed(2);
            });
            return averages;
        }

        // 각 게시물의 comment_probability 평균 계산
        averageProbabilities.value = calculateAverageCommentProbability();

        // 기존 게시물 리스트 업데이트
        boardList.value = [...boardList.value, ...newBoardList];
        if(newBoardList.length === 0) {
            no_BoardList.value = true; // 더 이상 추가할 게시물이 없을 경우
        }

        loadingModal.value = false; // 로딩 모달 해제
    })
    .catch(err => console.log(err));
}



const loadMoreData = () => {
    num.value += 8;
    getBoardList(keyword.value);
}

const updateKeyword = (newKeyword) => {
  keyword.value = newKeyword;  // 검색어를 갱신
};

const search = () => {
    num.value = 0;  // 페이지 번호 초기화
    boardList.value = [];  // 게시글 목록 초기화
    no_BoardList.value = false; // 게시글옵저버 초기화
    loadingModal.value = true;
    getBoardList(keyword.value);  // 게시글 목록을 새로 불러
}

onMounted(() => {
    loadingModal.value = true;
    getBoardList('');
});

</script>

<style scoped>
.gl-cpt-search-big-contain {
    max-width: 1160px;
    height: 148px;
    background: #fff;
    border: 1px solid #f0f2f5;
    -webkit-box-shadow: 0 4px 16px 0 rgba(69,88,115,.2);
    box-shadow: 0 4px 16px 0 rgba(69,88,115,.2);
    margin: 0 auto;
    position: relative;
    z-index: 9;
    padding: 0 24px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border-radius: 2px;
}
div{
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline; 
}
.index_trip_moment_con{
    position: relative;
    background-color: #fff;
    max-width: 1160px;
    margin: 0px auto;
}
.index_trip_moment_con .bottom_desc {
    height: 40px;
    line-height: 20px;
    margin-bottom: 12px;
    font-size: 14px;
    word-break: break-all;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
}

</style>