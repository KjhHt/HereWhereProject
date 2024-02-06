<template>
    <button @click="writeForm" >글작성</button>
    <WriteForm v-if="open" @close="writeForm"/>
</template>
  
<script setup>
import { ref } from 'vue'
import WriteForm from '@/components/board/WriteForm.vue'
const open=ref(false)
const writeForm=()=>{
    open.value=!open.value;
}


</script>
  
<style>

</style>