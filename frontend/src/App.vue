<script setup>
import { ref, computed } from 'vue'
import PanolensPage from './views/PanolensPage.vue';
import MainPage from './views/MainPage.vue';
import Header from './components/Header.vue';
import Join from './views/JoinView.vue';
import MyCalendar from './views/CalendarView.vue';
import Admin from './views/AdminView.vue';
import Location from './views/LocationView.vue';
import MyPageView from './views/MyPageView.vue';

const page_=ref('main')

computed(showHeader)
  function showHeader() {
    return this.$route.meta.showHeader !== false;
  }
console.log(ref)

function selectPage(page){
  page_.value=page
  
}
</script>
<template>
  <PanolensPage/>
   <Header @selectPage="selectPage"/>
    <MainPage v-if="page_=='main'"/>
    <Join v-if="page_=='join'"/>
    <MyCalendar v-if="page_=='mycalendar'"/>
    <Admin v-if="page_=='admin'"/>
    <Location v-if="page_=='location'"/>
    <MyPageView v-if="page_=='mypage'"/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0;
  padding: 0;
}
</style>