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
                    <div class="schedule-card" v-for="(schedule,index) in props.scheduleList" :key="index" @click="addPlan(index)">
                        <div class="card-body d-flex justify-content-between">
                            <!-- Card Info -->
                            <div class="card-info">
                                <div class="card-info-head">
                                    <p class="mb-0">{{ schedule.title }}</p>
                                </div>
                                <br/>
                                <div class="card-info-body">
                                    <div class="d-flex justify-content-around align-items-center">
                                        <div class="d-flex flex-column align-items-center">
                                            <label for="departure">여행 시작</label>
                                            <br>
                                            <input :value="schedule.departure" id="departure" name="departure" type="date" :min="today" readonly>
                                        </div>
                                        <span>~</span>
                                        <div class="d-flex flex-column align-items-center">
                                            <label for="arrival">여행 끝</label>
                                            <br>
                                            <input :value="schedule.departure" id="arrival" name="arrival" type="date" :min="today" readonly>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-image">
                                <img v-if="schedule.plans.length !==0" :src="setPhotoUrl_(schedule.plans[0].photos)" alt="일정 여행지사진" />
                                <img v-else :src="setPhotoUrl_(null)" alt="일정 여행지사진" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps,ref,defineEmits,watch } from 'vue';
import { setPhotoUrl_ } from '@/composable/custom';

const closeBtn= ref(null);
let scheduleList= [];

const emit= defineEmits(['']);

const props= defineProps({
    scheduleList: Array,
    places: Object
})

watch(()=>props.scheduleList, ()=>{
    scheduleList= props.scheduleList
},{deep:true})

const addPlan=(index)=>{
    console.log('머임',scheduleList)
    scheduleList[index].plans.push(props.places)
    emit('addPlan',scheduleList)
    closeBtn.value.click()
}
</script>

<style scoped>
.schedule-card {
  border: 1px solid #ddd;
  width: 467px;
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