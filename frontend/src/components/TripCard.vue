<template>
    <div class="card-container">
            <div class="CardStyle-z6mgtl-11 hzJTqM index_card_con" v-for="item in items" :key="item.image">
                <!-- 카드의 내용을 표현하는 코드를 여기에 추가 -->
                <div class="sub_con">
                    <div class="layer_con"></div>
                    <div class="tag_con">
                        <div class="tag" v-for="tag in item.tags" :key="tag" style="margin-left: 16px">{{ tag }}</div>
                    </div>
                    <div style="padding-bottom:106%" class="ImgWarpStyle-z6mgtl-13 kOBKyz">
                    <div class="img">
                        <img :src="item.image" />
                    </div>
                </div>
                <div class="bottom_con">
                    <div class="bottom_desc" style="-webkit-box-orient: vertical">{{ item.description }}</div>
                        <div class="bottom_user_con">
                            <div class="bottom_user_left">
                                <img class="user_avatar" :src="item.user.avatar" />
                                <span>{{ item.user.name }}</span>
                            </div>
                            <div class="bottom_user_right">
                                <i class="fas fa-thumbs-up"></i>
                                <span>{{ item.user.likes }}</span>
                            </div>
                        </div>      
                </div>
                </div>   
            </div>
        </div>
</template>
<script setup>
import { ref } from 'vue'

// 예시용 데이터
const items = ref([
    {
        tags: ['유럽여행', '데빈성', '슬로바키아'],
        image: 'https://ak-d.tripcdn.com/images/1mi63224x8tzxrulx8D9D_C_405_455_R5.jpg_.webp?proc=source/trip',
        description: '브라티슬라바에서 버스를타고 30분 정도 데빈 이란 지역에 위치한 성... 데빈성은 브라티',
        user: {
            avatar: 'https://ak-d.tripcdn.com/images/0a213224x8tv0z80d2170.jpg',
            name: '으호호',
            likes: 3
        }
    },
    {
        tags: ['2024소원이벤트', '푸꾸옥관광지'],
        image: 'https://ak-d.tripcdn.com/images/1mi0n224x8tzpqnuuEDCB_C_405_455_R5.jpg_.webp?proc=source/trip',
        description: '#2024소원이벤트 그랜드 월드 시계탑의 다른 이름은 데이트 타워고, 여기가 그랜드 월드 랜드마크라고 해도 과언이 아니예요. 데이트 타워란 이름은..',
        user: {
            avatar: 'https://ak-d.tripcdn.com/images/0a25i22348eafjd402469.jpg',
            name: '만득이의 세계여행',
            likes: 5
        }
    },
    {
        tags: ['2024소원이벤트', '푸꾸옥숙소추천'],
        image: 'https://ak-d.tripcdn.com/images/1mi1a224x8tzpo3r322CF_C_405_455_R5.jpg_.webp?proc=source/trip',
        description: 'La Mer Resort Phu Quoc은 🏡정원이 너무 예쁜 리조트였어요. 게다가 🏊♂️수영장과 정원은 넓은 편이었어요. 정원에 그네도 있고, 흔들의자도 있고',
        user: {
            avatar: 'https://ak-d.tripcdn.com/images/0a25i22348eafjd402469.jpg',
            name: '만득이의 세계여행',
            likes: 5
        }
    },
    {
        tags: ['방콕여행'],
        image: 'https://ak-d.tripcdn.com/images/1mi0a224x8tze6do05CFB_C_405_455_R5.jpg_.webp?proc=source/trip',
        description: '사람없이 즐길 수 있는 인피니티풀🩵 방콕 신상 5성급 호텔 이스틴 그랜드 호텔 파야타이 Eastin Grand Hotel Phayathai',
        user: {
            avatar: 'https://ak-d.tripcdn.com/images/0a2022224x34rf5e8FE09.jpg',
            name: '뽀라b',
            likes: 3
        }
    },
    
    
    // 추가 데이터...
])
</script>

<style scope>
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