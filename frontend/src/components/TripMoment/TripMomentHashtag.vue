<template>
    <div class="PublishTopicStyle__PublishTopicContainer-sc-4eicjo-0 mt24">
        <div class="publish-title">
            <svg viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                <defs></defs>
                <path d="M229.333 26.667v202.666H26.667V26.667h202.666zm-98.666 48h-19.734l-6.4 20.8H82.667L78.4 114.133h20.267L89.6 143.467H67.733l-4.266 18.666h20.266l-5.866 19.2H97.6l5.867-19.2H131.2l-5.867 19.2h19.734l5.866-19.2H172.8l4.267-18.666H156.8l9.067-29.334h21.866L192 95.467h-20.267l6.4-20.8H158.4l-6.4 20.8h-27.733l6.4-20.8zm16 39.466l-9.067 29.334h-27.733l9.066-29.334h27.734z" fill="#455873" class="transform-group">              
                </path>
            </svg>
            추천 해시태그
            <span class="supplement">(선택)</span>
        </div>
        <div class="PublishTopicStyle__TopicWrap-sc-4eicjo-1">
            <span v-for="(tag, index) in tags" :key="index" class="topic-item" :class="{ 'topic-item-select': selectedTagIndices.includes(index) }" @click="toggleSelect(index)">
                {{'#'+tag}}
                <span class="delete-tag" @click.stop="deleteTag(index)">X</span>
            </span>
        </div>
    </div>
</template>

<script setup>
import { ref , defineProps , watch , defineEmits } from 'vue'

let tags = ref(['국내여행', '해외여행','커플끼리','등산','파리','이태원','교토','마카오','제주도호텔','계곡'])
let selectedTagIndices = ref([]);
let selectedTags = ref({});

const emit = defineEmits(['deleteTag','updateTags']);

const props = defineProps({
    newTags: Array,
});

watch(() => props.newTags.length, () => {
    const uniqueNewTags = props.newTags.filter(newTag => {
        // 해당 태그가 tags에 이미 있는지 확인하고, 없으면 true를 반환함
        return !tags.value.includes(newTag);
    });
    // 중복되지 않는 태그만 tags에 추가
    tags.value = tags.value.concat(uniqueNewTags);
});

function deleteTag(index) {
    const deletedTagData = tags.value.splice(index, 1)[0];
    selectedTagIndices.value = selectedTagIndices.value.filter(i => i !== index)
    emit('deleteTag', deletedTagData);
}
function toggleSelect(index) {
    if (selectedTagIndices.value.includes(index)) {
        selectedTagIndices.value = selectedTagIndices.value.filter(i => i !== index)
        delete selectedTags.value[index];
    } 
    else {
        selectedTagIndices.value.push(index)
        selectedTags.value[index] = tags.value[index];
    }
    const tagValues = Object.values(selectedTags.value);
    const returnData = tagValues.join(',');
    emit('updateTags', returnData);
}
</script>
<style scope>
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .mt24 {
    margin-top: 24px;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .publish-title {
    font-size: 18px;
    line-height: 22px;
    color: rgb(0, 0, 0);
    display: flex;
    -webkit-box-align: center;
    align-items: center;
}
.PublishTopicStyle__PublishTopicContainer-sc-4eicjo-0 .publish-title svg {
    display: none;
}
.PublishIndexStyle__PublishCenterContainer-sc-1q48sbw-1 .supplement {
    display: inline-block;
    margin-left: 5px;
    color: rgb(206, 206, 206);
}
.PublishTopicStyle__TopicWrap-sc-4eicjo-1 {
    padding-top: 10px;
    text-align: left;
}
.PublishTopicStyle__TopicWrap-sc-4eicjo-1 .topic-item {
    font-size: 12px;
    color: rgb(51, 51, 51);
    text-align: center;
    line-height: 24px;
    background: rgb(245, 245, 249);
    border-radius: 2px;
    height: 24px;
    display: inline-block;
    padding: 0px 6px;
    margin-right: 12px;
    margin-bottom: 12px;
    position: relative;
}
.delete-tag {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 15px;
    height: 15px;
    color: white;
    background-color: black;
    border-radius: 50%;
    font-size: 15px;
    position: absolute;
    top: -10px;
    right: -10px;
    cursor: pointer;
}
.PublishTopicStyle__TopicWrap-sc-4eicjo-1 .topic-item-select {
color: rgb(38, 129, 255);
background: rgb(237, 244, 255);
}
</style>