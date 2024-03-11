<template>
    <div class="schedule-card">
      <div class="card-body d-flex justify-content-between">
        <div class="card-info">
          <div class="card-info-head">
            <p>{{ props.schedule.schedule_title }}</p>
          </div>
          <br/>
          <div class="card-info-body">
            <div class="d-flex justify-content-around align-items-center">
                <div class="d-flex flex-column align-items-center">
                    <label for="departure">여행 시작</label>
                    <br>
                    <input :value="formattedDate(props.schedule.schedule_startdate)" id="departure" name="departure" type="date" readonly>
                </div>
                <span>~</span>
                <div class="d-flex flex-column align-items-center">
                    <label for="arrival">여행 끝</label>
                    <br>
                    <input :value="formattedDate(props.schedule.schedule_enddate)" id="arrival" name="arrival" type="date" readonly>
                </div>
            </div>
          </div>
          <div class="card-info-bottom">
            <!--<a href="#" class="btn btn-primary" @click="passArrival">경로</a>-->
            <!--<p>편의시설: {{ props.hotel.convenience }}</p>-->
          </div>
        </div>
        <div class="card-image">
            <!--<img v-if="plan.photos.length !==0" :src="setPhotoUrl_(schedule.plans[0].photos)" alt="일정 여행지사진" />-->
            <img :src="setPhotoUrl_(null)" alt="일정 여행지사진" />
        </div>
      </div>
    </div>
</template>
    
<script setup>
import { defineProps } from 'vue';
import { setPhotoUrl_,formattedDate } from '@/composable/custom';

const props=defineProps({
    schedule: Object,
})
</script>

<style scoped>
.schedule-card {
    border: 1px solid #ddd;
    width: 375px;
    height: 150px;
    cursor: pointer;
    margin-bottom: 13px;
    border-radius: 10px;
    overflow: hidden; /* 이미지가 카드를 벗어나지 않도록 */
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.schedule-card:hover {
    transform: scale(1.05);
    box-shadow: 0 2px 10px rgba(0,0,0,0.2);
}
.card-info {
    margin: 10px 0;
    width: 60%; /* 내용과 이미지 사이의 비율을 조절 */
    padding: 10px 0; /* 내용 주변에 여백 추가 */
    text-align: center;
    font-size: 14px;
}
.card-info-head {
    text-overflow: ellipsis;  /* 넘친 텍스트를 ...으로 표시 */
    white-space: nowrap;  /* 텍스트를 한 줄에 표시 */
    overflow: hidden;     /* 내용이 넘칠 경우 숨김 처리 */
    font-weight: bold;
}
.card-info-head> p{
  margin-bottom: 0;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;

}
.card-body {
    height: 100%;
    width: 100%;
}
.card-image> img{
  width: 150px;
  height: 150px;
}
#arrival,
#departure{
    text-align:center;
}
</style>