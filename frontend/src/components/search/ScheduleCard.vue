<template>
  <div class="schedule-card" @click="clickSchedule">
    <div class="card-body d-flex justify-content-between">
      <div class="card-info d-flex flex-column mt-0 pt-0">
        <div class="card-info-head d-flex flex-column">
          <div class="pTitle">
            <p>{{ props.schedule.schedule_title }}</p>
          </div>
        </div>
        <div class="card-info-body">
          <input :value="formattedDate(props.schedule.schedule_startdate)" id="departure" name="departure" type="date" readonly>
          <span>~</span>
          <input :value="formattedDate(props.schedule.schedule_enddate)" id="arrival" name="arrival" type="date" readonly>
        </div>
      </div>
      <div class="card-btnArea d-flex justify-content-center align-items-center">
        <button class="info-head-button btn btn-light" @click="e=>setSchedule(e)">수정</button>
        <button class="info-head-button btn btn-light" @click="e=>delSchedule(e)">삭제</button>
      </div>
    </div>
  </div>
  <div v-if="props.isActive">
    <div v-for="(plan,index) in plansList" :key="index" class="plan-card">
      <div class="plan-card-body">
        <div class="plan-card-info">
          <div class="plan-card-info-head">
            <p>{{ plan.plan_name }}</p>
          </div>
          <div class="plan-card-info-body">
            <button class="btn btn-light" @click="delPlan(plan)">삭제</button>
          </div>
          <div class="plan-card-info-bottom">
            <!--<a href="#" class="btn btn-primary" @click="passArrival">경로</a>-->
          </div>
        </div>
        <div class="plan-card-img">
          <div v-if="isLoading" class="text-center">
            <div class="spinner-border" role="status">
              <span class="visually-hidden">Loading...</span>
            </div>
          </div>
          <img :src="plan.plan_img" @load="onImageLoaded" alt="맛집 이미지" />
        </div> 
      </div>
    </div>
  </div>
</template>
    
<script setup>
import { defineProps, defineEmits, ref, watch } from 'vue';
import { formattedDate } from '@/composable/custom';
import axios from 'axios';

let plansList= ref([]);
let isLoading= ref(true);

const props=defineProps({
    schedule: Object,
    isActive: Boolean,
    index: Number,
    status: Number
});

const emit= defineEmits(['setSchedule','delSchedule','plansMarker','clickSchedule']);

const setSchedule=e =>{
  emit('setSchedule',props.schedule.schedule_no);
  e.stopPropagation();
}

const delSchedule=e =>{
  emit('delSchedule',props.schedule.schedule_no);
  e.stopPropagation();
}

watch(()=>props.isActive,async(isActive)=>{
  if(isActive) {
    await getPlanList(props.schedule);
    emit('plansMarker',plansList.value);
  }
});

watch(()=>props.status, ()=>{
  if(props.isActive){
    getPlanList(props.schedule);
  }
});

async function getPlanList(schedule){
  try{
    const response= await axios.get(process.env.VUE_APP_API_URL+'/getPlan',{
      params:{schedule_no: schedule.schedule_no}
    })
      plansList.value= response.data;
  }catch(error){
    console.log('플랜리스트 가져오기 실패',error);
  }
}

function clickSchedule(){
  emit('clickSchedule',props.index);
}

function onImageLoaded(){
  isLoading.value= false;
}

async function delPlan(plan){
  try{
    await axios.delete(process.env.VUE_APP_API_URL+'/delPlan',{
      params:{plan_no: plan.plan_no}
    });
    await getPlanList(props.schedule);
  }catch(error){
    console.log('플랜 삭제 실패',error);
  }
}

</script>

<style scoped>
.schedule-card {
    border: 1px solid #ddd;
    width: 375px;
    height: 130px;
    cursor: pointer;
    margin-bottom: 13px;
    overflow: hidden; /* 이미지가 카드를 벗어나지 않도록 */
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
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
.info-head-button {
  color: #000; /* 글씨색 설정, 색상은 예시 */
  border-radius: 5px; /* 버튼 둥글게 설정 */
  cursor: pointer; /* 마우스 커서를 포인터로 설정 */
}
.info-head-button:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 10px rgba(0,0,0,0.2);
}
.p text-end:first-child {
  margin-right: 5px;
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
.card-btnArea{
  width: 150px;
  height: 100%;
}
#arrival,
#departure{
    text-align:center;
}
.pTitle{
  margin-top: 25px;
  line-height:40px;
}
#arrival{
  width: 90px;
  margin-left:20px;
}
#departure{
  margin-left:10px;
  width: 90px;
}

.plan-card {
    border: 1px solid #ddd;
    width: 375px;
    height: 100px;
    cursor: pointer;
    margin-bottom: 13px;
    border-radius: 10px;
    overflow: hidden; /* 이미지가 카드를 벗어나지 않도록 */
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }

.plan-card:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 10px rgba(0,0,0,0.2);
}
.plan-card-info {
  margin-top: 10px;
  width: 60%; /* 내용과 이미지 사이의 비율을 조절 */
  padding: 10px; /* 내용 주변에 여백 추가 */
  text-align: center;
}
.plan-card-info-head {
  text-overflow: ellipsis;  /* 넘친 텍스트를 ...으로 표시 */
  white-space: nowrap;  /* 텍스트를 한 줄에 표시 */
  overflow: hidden;     /* 내용이 넘칠 경우 숨김 처리 */
  font-weight: bold;
}
.plan-card-body {
  display: flex;
  justify-content: space-between;
  height: 100%;
  width: 100%;
}

.plan-card-img{
  width: 40%; /* 내용과 이미지 사이의 비율을 조절 */
}

.plan-card-img img{
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 카드를 꽉 채우면서 비율을 유지 */
  border-radius: 10px;
  padding: 5px;
}
img {
  width: 172.5px;
  height: 100px;
}
</style>