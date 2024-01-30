<template>
  <!-- Comment Row -->
    <div class="d-flex comment-row m-t-0 w-100">
        <div class="p-2">
          <img src="https://i.imgur.com/Ur43esv.jpg" alt="user" width="50" class="rounded-circle">
        </div>
        <div class="comment-text w-100">
          <div class="row">
            <h6 class="col-7 text-start">{{ props.msg.comment_writer }}</h6>
            <span class="text-muted col-3">{{ props.msg.comment_createtime }}</span>
            <a class="col" role="button"><i class="bi bi-three-dots"></i></a>  
          </div>
            <span class="mb-2 d-block text-start">{{ props.msg.comment_content }}</span>
        </div>
    </div> <!-- Comment Row -->
    
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
    msg: Object,
});




</script>

<style scoped>
/* Option 2: Import via CSS */
@import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css");
i {
  color:gray;
}
.comment-row{
  display: flex;
  margin: 10px 0
}
.comment-text{
  padding-left: 10px
}
.m-b-15{
  margin-bottom: 10px
}
.btn-sm{
  padding: 0.25rem 0.5rem;
  font-size: 0.76563rem;
  line-height: 1.5;
  border-radius: 1px
}
.btn-cyan{
  color: #fff;
  background-color: #27a9e3;
  border-color: #27a9e3
}
.btn-cyan:hover{
  color: #fff;
  background-color: #1a93ca;
  border-color: #198bbe
}
.comment-widgets .comment-row:hover{
  background: rgba(0, 0, 0, 0.05)
}
</style>