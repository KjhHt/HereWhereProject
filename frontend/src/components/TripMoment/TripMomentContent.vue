<template>
    <div class="PublishContentStyle__PublishContentContainer-sc-y2hrvp-0 mt24">
        <div class="jsx-2964752759 publish-title">
            여행 스토리를 공유해주세요!
            <span class="jsx-2964752759 supplement">
                <!--(선택)-->
            </span>
        </div>
        <div class="jsx-2964752759 input">
            <div name="share"
                id="textarea" 
                placeholder="나만의 여행 스토리를 다른 여행자와 함께 공유해보세요!" 
                class="jsx-2964752759 textarea" 
                contenteditable="true" 
                data-tribute="true"
                @input="updateContent"
                >
                {{ content }}
            </div> 
        </div>
    </div>
</template>
<script setup>
import { ref,defineEmits } from 'vue';
const emit = defineEmits(['updateContent'])
const content = ref('');
const updateContent = (event) => {
    content.value = event.target.innerText;
    emit('updateContent', content.value);
};

</script>
<style scope>
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .mt24 {
    margin-top: 24px;
}
@media (min-width: 374px) and (max-width: 768px){
.mt24 {
    margin-top: 10px;
}
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .publish-title {
    font-size: 18px;
    line-height: 22px;
    color: rgb(0, 0, 0);
    display: flex;
    -webkit-box-align: center;
    align-items: center;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .supplement {
    display: inline-block;
    margin-left: 5px;
    color: rgb(206, 206, 206);
}
.PublishContentStyle__PublishContentContainer-sc-y2hrvp-0 .input {
    position: relative;
    margin-top: 10px;
    padding: 8px 8px 26px;
    width: 100%;
    height: 262px;
    box-sizing: border-box;
    border: 1px solid rgb(229, 229, 229);
}
.PublishContentStyle__PublishContentContainer-sc-y2hrvp-0 .textarea {
    height: 100%;
    overflow-y: scroll;
    scrollbar-color: transparent transparent;
}
[contenteditable='true']:empty:before {
    content: attr(placeholder);
    display: block;
    color: #ced2d9;
}
.textarea {
    text-align: left; 
    font-size: 14px;
}
.textarea:focus {
    outline: none;
}

</style>