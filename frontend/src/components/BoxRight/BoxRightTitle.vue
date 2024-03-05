<template>
    <h1 class="article-title">{{ title }}</h1>
</template>

<script setup>
import { ref , defineProps , watchEffect } from 'vue';

const title = ref('');

const props = defineProps({
    detailData: Object,
});

watchEffect(() => {
  if (props.detailData) {
    title.value = props.detailData['board_title'];
  }
});
</script>

<style scope>
    .photo-post-container .article-title {
        font-size: 18px;
        color: rgb(15, 41, 77);
        font-weight: bold;
        margin-right: 32px;
        margin-top: 12px;
        text-align: left;
    }
</style>