<template>
    <SimpleTypeahead
        placeholder="검색할 국가 혹은 통화 입력"
        :items="currencyCodes"
        @select-item="selectItem"
        :item-projection="itemProjection"
        class="d-block"
        :minInputLength="1"
    />
</template>
<script setup>
import { defineProps, defineEmits } from 'vue';
import SimpleTypeahead from 'vue3-simple-typeahead'
import 'vue3-simple-typeahead/dist/vue3-simple-typeahead.css'
// eslint-disable-next-line no-unused-vars
const props=defineProps({
    currencyCodes:Array
})

const emits=defineEmits(['selectCode'])


function itemProjection(item) {
    // item이 문자열을 반환하거나, 문자열로 변환될 수 있는지 확인
    return item.toString();
}


function selectItem(item){
    emits('selectCode',item.split('/')[1])
}





</script>
<style scoped>
    
</style>