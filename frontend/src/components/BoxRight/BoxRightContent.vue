<template>
    <div class="content" style="position:relative; overflow:hidden; ">
        <div style="white-space:pre-line;word-break:break-word" class="content_expand">
            <a v-for="tag in tags" :key="tag" class="topicItem">#{{ tag }}</a>
            <br/>
            {{ content }}
        </div>
    </div>
</template>

<script setup>
import { ref , defineProps , watchEffect } from 'vue';

const tags = ref([]);
const content = ref('수많은 Lp판들 사이에 별마당 도서관 뷰를 볼 수 있는 이곳 바이닐 수원점은 각 자리마다 텐테이블과 개인 헤드셋이 비치되어있어 내가 듣고싶은 lp를 들으면서 여유를 즐길 수 있는 낭만적인 공간이에요 ...수많은 Lp판들 사이에 별마당 도서관 뷰를 볼 수 있는 이곳 바이닐 수원점은 각 자리마다 텐테이블과 개인 헤드셋이 비치되어있어 내가 듣고싶은 lp를 들으면서 여유를 즐길 수 있는 낭만적인 공간이에요 ...');

const props = defineProps({
    detailData: Object,
});

watchEffect(() => {
  if (props.detailData) {
    tags.value = props.detailData['boardTags'];
    content.value = props.detailData['board_content'];
  }
});

</script>
<style scope>
.photo-post-container .content {
    margin: 10px 32px 8px 0px;
}

.photo-post-container .content_expand {
    font-size: 16px;
    line-height: 24px;
    text-align: left;
}

.photo-post-container .topicItem {
    color: rgb(50, 100, 255);
    font-size: 16px;
    font-weight: normal;
    height: 16px;
    letter-spacing: 0px;
    line-height: 16px;
    margin-bottom: 12px;
    margin-right: 15px;
    word-break: break-word;
}

</style>