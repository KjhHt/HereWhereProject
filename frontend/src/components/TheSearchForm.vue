<template>
    <div class="container-info">
      <ProfileTop/>
      
      <PhotoList/>
    </div>
    </template>
    <script setup>
    import ProfileTop from '@/components/ProfileTop.vue';
    import PhotoList from '@/components/PhotoList.vue';
    </script>
    <style scoped>
    .container-info {
      position:relative;
      margin: 0 auto;
      display: flex;
      flex-direction: column;
    }
    
@media screen and (min-width: 600px) {
  .container-info {
    width: 80%; 
  }
}


@media screen and (min-width: 900px) {
  .container-info {
    width: 60%;
  }
}
    
    h3 {
      color: #333;
      margin-bottom: 1rem;
      text-align: center;
    }
    </style>