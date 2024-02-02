<template>
  <input type="file" accept="image/*" @change="uploadImage" ref="inputFile" class="d-none"/>
  <button class="search-button">
    <i @click="clickInput" class="fa fa-image" style="margin-right: 10px;"></i>
  </button>
</template>
<script setup>
import { ref,defineEmits } from 'vue';    
import axios from 'axios'

const emit = defineEmits(['searchLocation']);
const inputFile=ref(null);

function clickInput(){
  inputFile.value.click()
}

function uploadImage(event) {
  const files = event.target.files;
  if (files.length > 0) {
    const formData = new FormData();
    formData.append('image', files[0]);

    axios.post('http://127.0.0.1:5000/searchImage', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(response =>{ 
      console.log(response)
      const places=response.data.results[0]
      emit('searchLocation',places)
    })
    .catch(error => console.error('Error:', error));
  }
}



</script>
<style scoped>
.search-button{
  padding: 5px;
  background-color: white;
  color: #000; /* Set the color to black */
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

</style>