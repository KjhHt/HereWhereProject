<template>
    <div class="PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-0">
        <div class="publish-title">
            제목 추가
        <span class="supplement">
            <!--(선택)-->
        </span>
        </div>
    <input type="text" placeholder="이곳에 제목을 입력하세요!" v-model="title">    
    </div>    
</template>

<script setup>
import { ref,watch,defineEmits } from 'vue';
const emit = defineEmits(['updateTitle'])
const title = ref('');
watch(title, () => {
    emit('updateTitle', title.value);
});
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
.PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-0 .publish-title {
    margin-top: 24px;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .supplement {
    display: inline-block;
    margin-left: 5px;
    color: rgb(206, 206, 206);
}

.PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-0 input {
    width: 100%;
    height: 20px;
    padding: 8px 0px;
    border-bottom: 0.5px solid rgb(206, 210, 218);
}
.PublishTitleStyle__PublishTitleContainer-sc-1h6hl3g-0 input::placeholder {
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