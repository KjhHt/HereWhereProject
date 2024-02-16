<template>
    <div class="index_trip_moment_con">
    <div class="card-container">
        <div class="CardStyle-z6mgtl-11 hzJTqM index_card_con" v-for="photo in photos" :key="photo.image" v-show="photos.length">
            <div class="sub_con">
                <div class="layer_con"></div>
                <div class="tag_con">
                    <div class="tag" v-for="tag in photo.tags" :key="tag" style="margin-left: 16px">{{ tag }}</div>
                </div>
                <div style="padding-bottom:106%" class="ImgWarpStyle-z6mgtl-13 kOBKyz">
                    <div class="img">
                        <img :src="photo.image" />
                    </div>
                </div>
                <div class="bottom_con">
                    <div class="bottom_desc" style="-webkit-box-orient: vertical">{{ photo.description }}</div>
                    <div class="bottom_user_con">
                        <div class="bottom_user_left">
                            <img class="user_avatar" :src="photo.user.avatar" />
                            <span>{{ photo.user.name }}</span>
                        </div>
                        <div class="bottom_user_right">
                            <i class="fas fa-thumbs-up"></i>
                            <span>{{ photo.user.likes }}</span>
                        </div>
                    </div>      
                </div>
            </div>   
        </div>
        <div v-show="!photos.length" class="empty-container">
            <img src="@/assets/no_image.png" />
            <p class="message">아직 등록한 글이 없습니다! 글을 등록해 보세요!</p>
        </div>
    </div>
</div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const photos = ref([
  {
          tags: ['2024소원이벤트', '푸꾸옥여행'],
          image: 'https://ak-d.tripcdn.com/images/1mi40224x8tzgi6zoECCA_C_405_455_R5.jpg_.webp?proc=source/trip',
          description: '같은 카페 웬만하면 두 번은 잘 안가는데, 푸꾸옥 하이랜드 ☕️커피숍은 전통 베트남식 인테리어가 너무 예쁘고,',
          user: {
              avatar: 'https://images.unsplash.com/photo-1513721032312-6a18a42c8763?w=152&h=152&fit=crop&crop=faces',
              name: 'janedoe_',
              likes: 3
          }
      },
      {
          tags: ['2024소원이벤트', '수원스타필드'],
          image: 'https://ak-d.tripcdn.com/images/1mi0q224x8tzddfjz5DD5_C_405_455_R5.jpg_.webp?proc=source/trip',
          description: '#2024소원이벤트 #수원스타필드 #바이닐스타필드수원 수많은 Lp판들 사이에 별마당 도서관 뷰를 볼 수 있는 이곳',
          user: {
            avatar: 'https://images.unsplash.com/photo-1513721032312-6a18a42c8763?w=152&h=152&fit=crop&crop=faces',
              name: 'janedoe_',
              likes: 3
          }
      },
      {
          tags: ['2024소원이벤트', '푸꾸옥숙소추천'],
          image: 'https://ak-d.tripcdn.com/images/1mi01224x8tzgvaf4DA39_C_405_455_R5.jpg_.webp?proc=source/trip',
          description: '이 마사지샵은 족욕시간이 마사지 시간에서 제외된다고 사장님이 말씀해 주셨는데, ‘오~!! 뭔가 제대로 해 주시는 구나~’라는 생',
          user: {
            avatar: 'https://images.unsplash.com/photo-1513721032312-6a18a42c8763?w=152&h=152&fit=crop&crop=faces',
              name: 'janedoe_',
              likes: 5
          }
      },

      
   
    // ...
  ]);
  </script>
  
  <style scoped>
  .empty-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.message {
    margin-top: 20px;
}
  .card-container {
    display: flex;
    flex-wrap: wrap;
    overflow-y: auto;
  }
  
  .CardStyle-z6mgtl-11 {
    flex-basis: calc(100% / 3); /* 한 줄에 3개의 이미지를 나타내도록 수정 */
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