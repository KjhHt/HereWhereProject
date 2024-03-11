<template>
    <div class="layerContainer" @click.self="emit('closeModal')" style="background: rgba(17, 17, 17, 0.3); position: fixed; inset: 0px; text-align: center; z-index: 9999;">
        <div class="LayerContainer-sc-16ecu2q-0 iwjgOX">
            <div class="vote-list-module reviews-model">
                <div class="close" @click.stop="emit('closeModal')">
                    <svg width="24" height="24" viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                    <defs></defs>
                    <path d="M202.667 38.249l15.084 15.084-74.67 74.665 74.67 74.669-15.084 15.084-74.67-74.67-74.664 74.67-15.084-15.084 74.666-74.67-74.666-74.664L53.333 38.25l74.665 74.666 74.669-74.666z" fill="#333333" class="transform-group"></path>
                    </svg>
                </div>
                <div class="title title-fans">
                    팔로우
                </div>
                <div class="header-friend-wrap">
                    <div class="header-friend-content" id="scrollDom">
                        <div class="header-friend-item" v-for="liker in likers" :key="liker.name">
                            <div class="avatar">
                                <div class="lazyload-wrapper ">
                                    <img alt="avatar" :src="liker.image" style="object-fit: cover;">
                                </div>
                            </div>
                            <div class="infor">
                                <p class="name">
                                    {{ liker.name }}
                                </p>
                                
                            </div>
                            <div class="follow-btn-false" data-isfollow="false" data-followtype="multi" data-tabindex="0">
                                팔로우
                            </div>
                            <div class="follow-btn-true" data-isfollow="true" data-followtype="multi" data-tabindex="0">
                                팔로우됨
                            </div>
                        </div>
                         
                    </div>      
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
import { defineEmits, defineProps } from 'vue';

const emit = defineEmits(['closeModal']);
defineProps({
    likers: Array,
});

</script>
<style scope>
.iwjgOX .vote-list-module {
    padding: 24px;

    width: 610px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: rgb(255, 255, 255);
    border-radius: 2px;
    text-align: left;
}
.iwjgOX .vote-list-module .close {
    position: absolute;
    top: 16px;
    right: 16px;
    width: 24px;
    height: 24px;
    border-radius: 4px;
    background: none;

}
.iwjgOX .vote-list-module .title {
    font-weight: bold;
    font-size: 24px;
    color: rgb(51, 51, 51);
    line-height: 30px;
    position: relative;
    top: 0; 
    text-align: left;
    margin-top: 9px;
}
.iwjgOX .vote-list-module .header-friend-wrap .header-friend-content {
    height: 474px;
    overflow: auto;
}
.iwjgOX .vote-list-module .header-friend-wrap .header-friend-content .header-friend-item {
    display: flex;
    flex-direction: row;
    -webkit-box-align: center;
    align-items: center;
    -webkit-box-pack: center;
    justify-content: center;
    padding: 20px 0px;
    border-bottom: 1px solid rgb(218, 223, 230);
}
.iwjgOX .vote-list-module .header-friend-wrap .header-friend-content .header-friend-item .avatar {
    width: 52px;
    height: 52px;
    margin-right: 8px;
    position: relative;
}
.iwjgOX .vote-list-module .header-friend-wrap .header-friend-content .header-friend-item .avatar img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
}
.iwjgOX .vote-list-module .header-friend-wrap .header-friend-content .header-friend-item .infor {
    flex: 1 1 0%;
    margin-right: 12px;
    display: flex;
    flex-direction: column;
    -webkit-box-pack: center;
    justify-content: center;

}
.iwjgOX .vote-list-module .header-friend-wrap .header-friend-content .header-friend-item .infor .name {
    font-size: 16px;
    color: rgb(15, 41, 77);
    line-height: 20px;
    font-weight: bold;
    margin-bottom: 0;
}
.iwjgOX .vote-list-module .header-friend-wrap .header-friend-content .header-friend-item .follow-btn-false {
    min-width: 56px;
    background: rgb(39, 129, 255);
    border-radius: 2px;
    color: rgb(255, 255, 255);
    font-size: 12px;
    line-height: 12px;
    padding: 8px 16px;
    text-align: center;
    display: inline-block;
    cursor: pointer;
}
.iwjgOX .vote-list-module .header-friend-wrap .header-friend-content .header-friend-item .follow-btn-true {
    min-width: 56px;
    background: grey; 
    border-radius: 2px;
    color: rgb(255, 255, 255);
    font-size: 12px;
    line-height: 12px;
    padding: 8px 16px;
    text-align: center;
    display: inline-block;
    cursor: pointer;
}
</style>