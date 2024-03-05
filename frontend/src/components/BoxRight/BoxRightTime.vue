<template>
    <div class="time">작성일: {{ postDate }}</div>
</template>

<script setup>
import { ref , defineProps , watchEffect } from 'vue';

const postDate = ref('');

const props = defineProps({
    detailData: Object,
});

watchEffect(() => {
  if (props.detailData) {
    postDate.value = props.detailData['board_createtime'];
  }
});

</script>

<style scope>
.photo-post-container .time {
    font-size: 12px;
    color: rgb(133, 146, 166);
    margin-top: 4px;
    margin-bottom: 12px;
    margin-left: 32px;
    text-align: left;
}
</style>