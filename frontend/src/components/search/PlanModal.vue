<template>
    <!-- Modal -->
    <div class="modal fade" id="addPlanModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                        여행지추가
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ref="closeBtn"></button>
                </div>
                <div class="modal-body">
                    <div class="schedule-card" v-for="(schedule,index) in props.scheduleList" :key="index" @click="addPlan(schedule)">
                        <div class="card-body d-flex justify-content-between">
                            <!-- Card Info -->
                            <div class="card-info">
                                <div class="card-info-head">
                                    <p class="mb-0"><strong>{{ schedule.schedule_title }}</strong></p>
                                </div>
                                <br/>
                                <div class="card-info-body">
                                    <div class="d-flex justify-content-around align-items-center">
                                        <div class="d-flex flex-column align-items-center">
                                            <label for="departure">여행 시작</label>
                                            <br>
                                            <input :value="formattedDate(schedule.schedule_startdate)" id="departure" name="departure" type="date" :min="today" readonly>
                                        </div>
                                        <span>~</span>
                                        <div class="d-flex flex-column align-items-center">
                                            <label for="arrival">여행 끝</label>
                                            <br>
                                            <input :value="formattedDate(schedule.schedule_enddate)" id="arrival" name="arrival" type="date" :min="today" readonly>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps,ref,defineEmits } from 'vue';
import { formattedDate } from '@/composable/custom';
import axios from 'axios';

const closeBtn= ref(null);
let plansList= ref([]);

const props= defineProps({
    scheduleList: Array,
    places: Object
})

const emit= defineEmits(['addPlan']);

function getPlanList(schedule){
    axios.get(process.env.VUE_APP_API_URL+'/getPlan',{
      params:{schedule_no: schedule.schedule_no}
    }).then(response=>{
      plansList.value= response.data;
    });
}

const addPlan= schedule=>{
    getPlanList(schedule)
    plansList.value.forEach(plan=>{
        if(plan.place_id===props.places.place_id) return
    });
    axios.post(process.env.VUE_APP_API_URL+'/addPlan',{
        plan_name: props.places.name,
        plan_img: props.places.photos[0].getUrl(),
        plan_placeid: props.places.place_id,
        schedule_no: schedule.schedule_no
    }).then(()=>{
        getPlanList(schedule);
        closeBtn.value.click();
        emit('addPlan',schedule);
    }).catch(error=>{
        console.log('플랜추가 실패',error)
        closeBtn.value.click();
    })
}
</script>

<style scoped>
.modal-content{
  width: 474px;
}
.modal-body {
  max-height: calc(100vh - 250px);
  width:472px;
  overflow-y: auto;
  overflow-x: auto;
}
.schedule-card {
  border: 1px solid #ddd;
  width: 420px;
  height: 150px;
  cursor: pointer;
  margin-bottom: 8px;
  border-radius: 10px;
  overflow: hidden; /* 이미지가 카드를 벗어나지 않도록 */
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.schedule-card:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 10px rgba(0,0,0,0.2);
}

.card-body {
  display: flex;
  justify-content: space-between;
  height: 100%;
  width: 100%;
}

.card-info {
  width: calc(100% - 150px); /* 내용과 이미지 사이의 비율을 조절 */
  padding: 10px; /* 내용 주변에 여백 추가 */
  text-align: left;
  overflow: hidden; /* 텍스트가 넘칠 경우 자르기 */
  white-space: nowrap; /* 텍스트가 한 줄로만 표시되도록 설정 */
  text-overflow: ellipsis; /* 텍스트가 넘칠 경우 마침표로 표시 */
}

.card-image> img{
    width: 148px;
    height: 148px;
}

#departure,
#arrival{
    text-align: center;
}

.card-info-head> p{
    margin-left:15px;
}
</style>