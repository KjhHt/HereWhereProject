<template>
    <div class="card-container"
        :style="loadingModal ? { 
            display: 'flex', 
            justifyContent: 'center',
            alignItems: 'center',
            position: 'fixed',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
        }:{}" >

        <!-- 모달 -->
        <MypageLoadingModal v-if="loadingModal"></MypageLoadingModal>
        <!-- 모달 끝 -->
        <div class="CardStyle-z6mgtl-11 hzJTqM index_card_con" 
            v-for="(item,index) in boardList" 
            :key="item.board_no"
            :ref="index === boardList.length - 1 ? setLastItem : undefined"
        >
            <!-- 카드의 내용을 표현하는 코드를 여기에 추가 -->
            <div class="sub_con" @click="$emit('moveDetailView',item)">
                <div class="layer_con"></div>
                <div class="tag_con">
                    <div v-for="(tag,index) in item.boardTags" :key="index" class="tag" style="margin-left: 16px">{{tag}}</div>
                </div>
                <div style="padding-bottom:106%" class="ImgWarpStyle-z6mgtl-13 kOBKyz">
                <div class="img">
                    <img :src="item.base64BoardImages[0]" />
                </div>
            </div>
            <div class="bottom_con">
                <div class="bottom_desc" style="-webkit-box-orient: vertical; text-align: left;">{{ item.board_content }}</div>
                    <div class="bottom_user_con">
                        <div class="bottom_user_left">
                            <img class="user_avatar" :src="item.profileimage" />
                            <span>{{ item.board_writer }}</span>
                        </div>
                        <div class="bottom_user_right">
                            <i class="fas fa-thumbs-up"></i>
                            <span style="margin-left: 2px;">{{ item.like_count }}</span>
                        </div>
                    </div>      
            </div>
            </div>   
        </div>
            
    </div>

</template>
<script setup>
import { ref, defineProps , watch, defineEmits } from 'vue'
import MypageLoadingModal from '@/components/MyPage/MypageLoadingModal.vue';

const props = defineProps({
  boardList: Array,
  no_BoardList:Object,
  loadingModal:Object,
});

const lastItem = ref(null);

const emit = defineEmits(['requestMoreData']);

const observer = new IntersectionObserver((entries) => {
  entries.forEach((entry) => {
    if (entry.isIntersecting) {
      emit('requestMoreData');
    }
  });
}, { threshold: 0.9 });

const setLastItem = el => {
  if (lastItem.value) {
    observer.unobserve(lastItem.value);
  }
  lastItem.value = el;
  if (el && !props.no_BoardList) {
    observer.observe(el);
  }
};

const stopObserving = () => {
    if (lastItem.value) {
        observer.unobserve(lastItem.value);
        lastItem.value = null;
    }
    observer.disconnect();
};

watch(() => props.boardList, () => {
    if (lastItem.value && !props.no_BoardList) {
        observer.observe(lastItem.value);
    }
}, { deep: true });

watch(() => props.no_BoardList, () => {
    if(props.no_BoardList){
        stopObserving();
    }
}, { deep: true });


</script>

<style scope lang="scss">
.card-container {
  display: flex;
  flex-wrap: wrap;
  overflow-y: auto;
}

.CardStyle-z6mgtl-11 {
  flex-basis: 25%;
  margin-bottom: 20px
}

.index_card_con {
    padding: 0 8px!important;
}

.hzJTqM {
    width: 25%;
    display: inline-block;
    box-sizing: border-box;
    padding: 0px 10px;
    cursor: pointer;
    vertical-align: top;
}

.index_card_con .sub_con {
    position: relative;
}

.index_card_con .sub_con .layer_con {
    position: absolute;
    bottom: 106px;
    height: 80px;
    
    background: linear-gradient(180deg,rgba(15,41,77,0),rgba(15,41,77,.5));
    width: 100%;
}

.index_card_con .sub_con .tag_con .tag {
    border-radius: 2px;
    color: #fff;
    line-height: 16px;
    display: inline-block;
    padding: 2px 8px;
    font-size: 12px;
    margin-left: 12px;
    border: 1px solid #fff;
}

.index_card_con .sub_con .tag_con {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    height: 22px;
    overflow: hidden;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    position: absolute;
    bottom: 122px;
    z-index: 9;
}

.kOBKyz {
    background: rgb(235, 235, 242);
    width: 100%;
    height: 0px;
    padding-bottom: 56%;
    overflow: hidden;
    margin: 0px;
    position: relative;
    border-radius: 2px;
}

.kOBKyz .img {
    position: absolute;
    width: 100%;
    height: 100%;
}
.hzJTqM .img {
    position: absolute;
    border-radius: 2px;
    overflow: hidden;
    width: 100%;
    height: 100%;
}

.kOBKyz .img img {
    transition: all 1.2s ease 0s;
    width: 100%;
    object-fit: cover;
    height: 100%;
    font-family: "object-fit: cover";
}

.hzJTqM .img img {
    transition: all 1.2s ease 0s;
    width: 100%;
    object-fit: cover;
    height: 100%;
}

img {
    border-style: none;
}


.index_card_con .bottom_con {
    padding: 16px 16px 13px;
    border: 1px solid #dadfe6;
    border-top: none;
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

.index_card_con .bottom_con .bottom_user_con {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
}

.index_card_con .bottom_con .bottom_user_con .bottom_user_left {
    -webkit-box-flex: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-right: 8px;
    display: flex;
}

.index_card_con .bottom_con .bottom_user_con .user_avatar {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    vertical-align: middle;
    margin-right: 8px;
}

.index_card_con .bottom_con .bottom_user_con .bottom_user_right {
    -ms-flex-negative: 0;
    flex-shrink: 0;
}

.index_card_con .bottom_con .bottom_user_con .support_count {
    vertical-align: bottom;
    margin-right: 5px;
}

.index_card_con .bottom_con .bottom_user_right i {
    color: #455873;
    font-size: 14px;
}

.fas fa-thumbs-up {
    margin-right: 8px;
}

.index_card_con .bottom_con .bottom_user_right span {
    font-size: 13px;
    color: #455873;
    line-height: 18px;
}


</style>