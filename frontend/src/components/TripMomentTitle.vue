<template>
    <h2 class="Title-sc-1sm6d0j-2 fmnYkt burited_point">트립 모먼트</h2>
    <div class="release_btn_con">
        <div @click="$emit('moveJoinView')" class="release_btn">포스팅하러 가기</div>
    </div>
</template>
<style scope>
    .Title-sc-1sm6d0j-2 {
    margin-top: 40px;
    padding: 24px 32px;
    font-size: 28px;
    color: rgb(15, 41, 77);
    line-height: 34px;
    text-align: center;
}
.index_trip_moment_con .release_btn_con {
    position: absolute;
    right: 10px;
    top: 24px;
}
.index_trip_moment_con .release_btn_con .release_btn {
    padding: 9px 20px 6px;
    color: #fff;
    background: #3264FF;
    border-radius: 2px;
    font-size: 16px;
}
</style>