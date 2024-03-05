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
        <!-- <div v-if="loadingModal">
            <img src="@/assets/vacations.gif" alt="Trip-moment" style="margin-top: 200px;" />
        </div> -->
        <div class="loader" v-if="loadingModal">
            <div class="loader__trampoline"></div>
            <div class="loader__jumper">
            <div class="loader__jumper-face">
                <div class="loader__jumper-eyes"></div>
                <div class="loader__jumper-mouth"></div>
            </div>
            </div>
            <div class="loader__text">Loading...</div>
        </div>
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

@import url("https://fonts.googleapis.com/css2?family=Archivo+Black&display=swap");
$black: #000038;
$white: #fff;
$pink: #eb80b1;
$green: #80c0a1;
$light-grey: #bdc3c6;
$grey: #919ea3;
$dark-grey: #576078;

$animation-duration: 10s;
$jumper-animation-timing: cubic-bezier(0.52, -0.01, 0.14, 1);

.loader {
  margin-top: 10rem;
  $component-class: &;
  position: relative;
  &__trampoline {
    position: relative;
    width: 12rem;
    height: 4rem;
    border-radius: 50%;
    background: radial-gradient(ellipse at 50% 50%, $black, $dark-grey 35%);
    background-size: 200% 200%;
    background-position: 50% 50%;
    border: 0.2rem solid $light-grey;
    box-shadow: 0 1.2rem 1rem 0 rgba($black, 0.5);
    &:before,
    &:after {
      content: "";
      position: absolute;
      width: 0.2rem;
      height: 1rem;
      background-color: $light-grey;
    }
    &:before {
      bottom: 0;
      left: 0.8rem;
    }
    &:after {
      bottom: 0;
      right: 0.8rem;
    }
  }
  &__jumper {
    position: absolute;
    top: 0;
    left: 5rem;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
    background: radial-gradient(circle at 70% 70%, $pink, $black 500%);
    overflow: hidden;
  }
  &__jumper-face {
    width: 100%;
    height: 100%;
  }
  &__jumper-eyes {
    position: absolute;
    top: 35%;
    left: 20%;
    display: flex;
    justify-content: space-between;
    width: 60%;
    height: 0.3rem;
    &:before,
    &:after {
      content: "";
      width: 0.3rem;
      height: 0.3rem;
      border-radius: 50%;
      background: radial-gradient(
        circle at 70% 30%,
        $white 15%,
        $black 15%,
        $black
      );
    }
  }
  &__jumper-mouth {
    position: absolute;
    top: 60%;
    left: calc(35% - 0.1rem);
    width: 30%;
    height: 0.1rem;
    border-radius: 0 0 1rem 1rem;
    border: 0.1rem solid $black;
    border-top: none;
  }
  &__text {
    color: $black;
    text-align: center;
    text-transform: uppercase;
    padding-top: 2rem;
  }
  // Animation
  &__trampoline {
    @keyframes bounce-trampoline {
      5% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 45%;
      }
      10%,
      15% {
        transform: translateY(-0.1rem);
        box-shadow: 0 1.3rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 50%;
      }
      25% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 45% 45%;
      }
      30%,
      35% {
        transform: translateY(-0.1rem);
        box-shadow: 0 1.3rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 50%;
      }
      45% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 60% 45%;
      }
      50%,
      55% {
        transform: translateY(-0.1rem);
        box-shadow: 0 1.3rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 50%;
      }
      65% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 40% 40%;
      }
      70%,
      75% {
        transform: translateY(-0.1rem);
        box-shadow: 0 1.3rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 50%;
      }
      85% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 60% 35%;
      }
      95% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 48% 50%;
      }
      100% {
        transform: none;
        background-position: 50% 50%;
      }
    }
    animation: bounce-trampoline;
    animation-duration: $animation-duration;
    animation-timing-function: $jumper-animation-timing;
    animation-iteration-count: infinite;
  }
  &__jumper {
    @keyframes bounce-jumper {
      5% {
        transform: translateY(0.5rem);
        transform-origin: 50% calc(50% - 0.5rem);
      }
      15% {
        transform: translate(0.5rem, -5rem) rotate(20deg);
        transform-origin: calc(50% - 0.5rem) calc(50% + 5rem);
      }
      23% {
        transform: translate(1rem, 0.5rem) rotate(30deg);
        transform-origin: calc(50% - 1rem) calc(50% + 0.5rem);
      }
      35% {
        transform: translate(-1rem, -8rem) rotate(-10deg);
        transform-origin: calc(50% + 1rem) calc(50% + 5rem);
      }
      43% {
        transform: translate(-2rem, 0.5rem) rotate(-20deg);
        transform-origin: calc(50% + 2rem) calc(50% - 0.5rem);
      }
      55% {
        transform: translate(1.5rem, -6rem) rotate(10deg);
        transform-origin: calc(50% - 4rem) calc(50% + 6rem);
      }
      63% {
        transform: translate(3.5rem, 0.5rem) rotate(10deg);
        transform-origin: calc(50% - 3.5rem) calc(50% - 0.5rem);
      }
      75% {
        transform: translate(0, -8rem) rotate(-20deg);
        transform-origin: 50% calc(50% + 10rem);
      }
      83% {
        transform: translate(-3rem, -0.5rem) rotate(-30deg);
        transform-origin: calc(50% + 3rem) calc(50% + 0.5rem);
      }
      95% {
        transform: translate(1rem, 0) rotate(20deg);
        transform-origin: calc(50% - 1rem) 50%;
      }
      100% {
        transform: none;
        transform-origin: 50% 50%;
      }
    }
    animation: bounce-jumper;
    animation-duration: $animation-duration;
    animation-timing-function: $jumper-animation-timing;
    animation-iteration-count: infinite;
  }
  &__jumper-face {
    @keyframes bounce-face {
      5% {
        transform: translateY(0.5rem);
      }
      15% {
        transform: translate(0.5rem, -3rem) rotate(20deg);
      }
      24% {
        transform: translate(1rem, 0.5rem) rotate(30deg);
      }
      35% {
        transform: translate(-1rem, -3rem) rotate(-10deg);
      }
      44% {
        transform: translate(-0.5rem, 0.5rem) rotate(-20deg);
      }
      55% {
        transform: translate(0, -0.5rem) rotate(10deg);
      }
      64% {
        transform: translate(0rem, 0.5rem) rotate(10deg);
      }
      75% {
        transform: translate(0, -1rem) rotate(-20deg);
      }
      84% {
        transform: translate(-0.5rem, 0.5rem) rotate(-30deg);
      }
      95% {
        transform: translate(0.5rem, -0.5rem) rotate(-10deg);
      }
      100% {
        transform: none;
      }
    }
    animation: bounce-face;
    animation-duration: $animation-duration;
    animation-timing-function: ease;
    animation-iteration-count: infinite;
  }
  &__jumper-mouth {
    @keyframes bounce-mouth {
      15%,
      75% {
        height: 0.4rem;
        border-radius: 0 0 2rem 2rem;
      }
    }
    animation: bounce-mouth $animation-duration;
    animation-duration: $animation-duration;
    animation-timing-function: ease;
    animation-iteration-count: infinite;
  }
  &__text {
    @keyframes bounce-text {
      5% {
        transform: translateY(0);
      }
      15% {
        transform: translateY(-0.25rem);
      }
      22% {
        transform: translateY(0);
      }
      35% {
        transform: translateY(-0.25rem);
      }
      42% {
        transform: translateY(0);
      }
      55% {
        transform: translateY(-0.25rem);
      }
      62% {
        transform: translateY(0);
      }
      75% {
        transform: translateY(-0.25rem);
      }
      82% {
        transform: translateY(0);
      }
      95% {
        transform: translateY(0);
      }
      100% {
        transform: none;
      }
    }
    animation: bounce-text;
    animation-duration: $animation-duration;
    animation-timing-function: ease;
    animation-iteration-count: infinite;
  }
}
</style>