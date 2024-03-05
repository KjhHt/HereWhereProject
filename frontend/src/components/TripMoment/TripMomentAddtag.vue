<template>
    <div class="PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-1">
        <div class="publish-title">
            태그 추가
        <span class="supplement">
            <!--(선택)-->
        </span>
        </div>
        <div class="location-input">
            <input type="text" placeholder="이곳에 태그을 입력하세요!" v-model="tag" @keyup.enter="emitTag">    
        </div>
    </div>    
</template>

<script setup>
import { ref,defineEmits } from 'vue';
const emit = defineEmits(['updateTag'])
const tag = ref('');
const tagData = ref([]);

const emitTag = () => {
    tagData.value.push(tag.value);
    emit('updateTag', {labels:[...tagData.value]});
    tag.value = ''; // 태그를 전송한 후 input 필드를 비움
};

</script>
<style scope>
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .publish-title {
    font-size: 18px;
    line-height: 22px;
    color: rgb(0, 0, 0);
    display: flex;
    -webkit-box-align: center;
    align-items: center;
}
.PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-1 .publish-title {
    margin-top: 24px;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .supplement {
    display: inline-block;
    margin-left: 5px;
    color: rgb(206, 206, 206);
}

.PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-1 .location-input {
    width: 282px;
    height: 40px;
    position: relative;
}

.PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-1 input {
    width: 100%;
    height: 20px;
    padding: 8px 0px;
    border-bottom: 0.5px solid rgb(206, 210, 218);
}
.PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-1 input::placeholder {
    font-size: 14px;  
    color: #ced2d9;  
}
input {
    background: none;
    outline: none;
    border: 0;
    font-size: inherit;
    color: inherit;
    outline: none;
    border: 0;
    margin: 0;
    padding: 0;
    overflow: visible;
    font-size: 100%;
    line-height: 1.15;
    margin: 0;
}
input[type="text" i] {
    padding-block: 4px;
    padding-inline: 2px;
}

</style>