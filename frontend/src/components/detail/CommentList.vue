<template>
    <div class="chat__body row" id="chat__body">
  
      <CommentMessage
        v-for="(msg, index) in props.msgData"
        :key="index"
        :msg="msg"
      >
      </CommentMessage>
    </div>
  </template>
  
<script setup>
import CommentMessage from "@/components/detail/CommentMessage";
import { defineProps } from "vue";

const props=defineProps({
  msgData : {
    type: Array,
  },
})


</script>
  
<style scoped>
  .chat__body {
    padding: 1rem 0;
    overflow: auto;
    scroll-behavior: smooth;
  }
  
  .chat__body::-webkit-scrollbar {
    display: none;
  }
</style>