<template>
    <SimpleTypeahead
        class="autocomplete form-control"
        placeholder="검색할 국가 혹은 통화 입력"
        :items="currencyCodes"
        @select-item="selectItem"
        :item-projection="itemProjection"
        :minInputLength="1"
        :default-item="selectedItem"
    />
</template>

<script setup>
import { defineProps, defineEmits, ref, onMounted } from 'vue';
import SimpleTypeahead from 'vue3-simple-typeahead';
import 'vue3-simple-typeahead/dist/vue3-simple-typeahead.css';

// eslint-disable-next-line no-unused-vars
const props = defineProps({
    currencyCodes: Array,
    currentCurrency:String
});

const emits = defineEmits(['selectCode']);

const selectedItem = ref('미국 달러/USD'); // 초기 값 설정

onMounted(()=>{
    selectItem(selectedItem.value)
})

function itemProjection(item) {
    return item.toString();
}

function selectItem(item) {
    emits('selectCode', item.split('/')[1]);
}
</script>

<style scoped>
/* 스타일링 코드는 그대로 유지 */
</style>