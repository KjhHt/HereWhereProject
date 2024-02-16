<template>
    <SearchBar/>
    <div class="index_trip_moment_con">
        <TripMomentTitle/>
        <TripCard/>
    </div>
</template>
  
<script setup>
import SearchBar from '@/components/SearchBar.vue';
import TripMomentTitle from '@/components/TripMomentTitle.vue'
import TripCard from '@/components/TripCard.vue'

</script>

<style scoped>
.gl-cpt-search-big-contain {
    max-width: 1160px;
    height: 148px;
    background: #fff;
    border: 1px solid #f0f2f5;
    -webkit-box-shadow: 0 4px 16px 0 rgba(69,88,115,.2);
    box-shadow: 0 4px 16px 0 rgba(69,88,115,.2);
    margin: 0 auto;
    position: relative;
    z-index: 9;
    padding: 0 24px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border-radius: 2px;
}
div{
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline; 
}
.index_trip_moment_con{
    position: relative;
    background-color: #fff;
    max-width: 1160px;
    margin: 0px auto;
}
.index_trip_moment_con .bottom_desc {
    height: 40px;
    line-height: 20px;
    margin-bottom: 12px;
    font-size: 14px;
    word-break: break-all;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
}

</style>