<template>
    <div class="container mt-4">
        <h5 class="mb-4">Here Where News</h5>
        <div class="list-group">
            <a v-for="(item, index) in props.news" :key="index" :href="item.link" class="list-group-item list-group-item-action" target="_blank">
                <div class="d-flex w-100 justify-content-between align-items-center">
                    <h5 class="mb-1 news-title">{{ item.title }}</h5>
                </div>
            </a>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
    news: Array
});
</script>

<style scoped>
.mb-4 {
    text-align: left; /* 왼쪽 정렬 */
    color: #0288d1; /* Light Blue 700 */
}
.list-group-item {
    background: #80d8ff; /* Light Blue A100 */
    border-color: #29b6f6; /* Light Blue 400 */
}
.list-group-item:hover,
.list-group-item:focus {
    background-color: #4fc3f7; /* Light Blue 300 */
}
.list-group-item:visited {
    color: #ffffff; /* White */
}
.news-title {
    font-size: 18px;
    color: #ffffff; /* White */
}
</style>
