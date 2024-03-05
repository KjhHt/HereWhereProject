<template>
    <SearchBar searchData
        @keywordChanged="updateKeyword"
        @search="search"
    />
    <div class="index_trip_moment_con">
        <TripMomentTitle @moveJoinView="$emit('selectPage', 'TripMomentWriteView')"/>
        <TripCard 
            :loadingModal="loadingModal"
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

const emit = defineEmits(['selectPageData'])

const handleMoveDetailView = (data) => {
    emit('selectPageData','TripMomentDetailView',data);
}

const getBoardList = (keyword) => {
    //게시판 데이터 호출
    axios.get(process.env.VUE_APP_API_URL+'/board',{
        params : {
            num : num.value,
            keyword : keyword,
        }
    })
    .then(res=>{
        const newBoardList = res.data.map(item => {
            //프로필이 0일때와 base64문자열을 헤더붙여주기
            if(item.profileimage === '0')
                item.profileimage = require('@/assets/dino.jpg');
            else if(!item.profileimage.startsWith('http')){
                item.profileimage = `data:${res.headers['content-type']};base64,${item.profileimage}`;
            }
            //게시판 이미지들도 배열로 가져오니 위와 동일
            item.base64BoardImages = item.base64BoardImages.map(value => {
                return `data:${res.headers['content-type']};base64,${value}`;
            });
            //좋아요 리스트 프로필이미지도 위와 동일하니 똑같이 해주자
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
        boardList.value = [...boardList.value, ...newBoardList];
        console.log(boardList.value);
        // 응답받은 데이터가 없으면 observer를 해제합니다.
        if(newBoardList.length === 0) {
            no_BoardList.value = true;
        }
        loadingModal.value = false;
    })
    .catch(err=>console.log(err))
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