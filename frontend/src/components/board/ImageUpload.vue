<template>
    <div class="wrapper bg-transparent">
        <div class="file-upload bg-transparent">
          <i  class="fa">&#xf0ee;</i>
            <input type="file" accept="image/*" multiple @change="uploadImage" ref="inputFile"/>
        </div>
    </div>
</template>
<script setup>
import { defineEmits, ref, defineProps, watch } from 'vue';    

const emit = defineEmits(['update-files','upload-click']);
const inputFile=ref(null);
const props=defineProps({
  uploadClick:Boolean,
})


watch(() => props.uploadClick, (newVal, oldVal) => {
  if (newVal !== oldVal) {
    inputFile.value.click()
  }
});



function uploadImage(event) {
  const files = event.target.files;
  if (files.length > 0) {
    emit('update-files', files);
  }
}


</script>
<style scoped>
.wrapper {
  width: 100%;
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;

  .file-upload {
    height: 100%;
    width: 100%;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: white;

    i {
      font-size: 15em;
      color: rgb(128, 128, 128);
    }

    input[type='file'] {
      height: 100%;
      width: 100%;
      position: absolute;
      top: 0;
      left: 0;
      opacity: 0;
      cursor: pointer;
    }
  }
}
</style>