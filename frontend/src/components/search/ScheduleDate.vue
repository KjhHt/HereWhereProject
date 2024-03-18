<template>
    <div>
        <!-- Modal -->
        <div class="modal fade" ref="modalRef" id="scheduleDateModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" @click="popoverHide">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                <div class="modal-header">
                    <h5 v-if="Object.keys(props.schedule).length" class="modal-title" id="exampleModalLabel">
                        여행 일정 수정
                    </h5>
                    <h5 v-else class="modal-title" id="exampleModalLabel">
                        여행 일정 추가
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ref="closeBtn"></button>
                </div>
                <div class="modal-body">
                    <div  class="d-flex justify-content-around align-items-center">
                        <div class="d-flex flex-column align-items-center">
                            <label for="departure">Start-Travel</label>
                            <br>
                            <input v-model="departure" id="departure" name="departure" type="date" :min="today">
                        </div>
                        <span>~</span>
                        <div class="d-flex flex-column align-items-center">
                            <label for="arrival">End-Travel</label>
                            <br>
                            <input v-model="arrival" id="arrival" name="arrival" type="date" :min="departure">
                        </div>
                    </div>
                    <br/>
                    <div class="d-flex align-items-center justify-content-center">
                        <div class="form-floating mt-2 mb-3">
                            <input v-model="planTitle" type="text" class="form-control" id="planTitle">
                            <label for="planTitle">일정제목</label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" ref="popoverButton" @click="addSchedule" tabindex="0">
                        저장
                    </button>
                </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
import { defineEmits,ref,onMounted,defineProps,watch } from 'vue'
import { Popover } from 'bootstrap'
import { formattedDate } from '@/composable/custom';

let arrival= ref('');
let departure= ref('');
let planTitle= ref('');
const modalRef= ref(null);
const popoverButton= ref(null);
let datePopover= null;
let titlePopover= null;
const closeBtn= ref(null);

const emit= defineEmits(['addSchedule','updateSchedule','initialize']);

const props= defineProps({
    schedule: Object
});

onMounted(() => {
  datePopover = new Popover(popoverButton.value, {
    container: 'body',
    placement: 'top',
    content: '날짜를 선택해주세요',
    trigger: 'manual'
  });
  titlePopover = new Popover(popoverButton.value, {
    container: 'body',
    placement: 'top',
    content: '제목을 입력해주세요',
    trigger: 'manual'
  });
  modalRef.value.addEventListener('hidden.bs.modal', ()=>{
    departure.value= '';
    arrival.value= '';
    planTitle.value= '';
    emit('initialize');
  });
})

watch(()=>props.schedule, schedule=>{
    if(Object.keys(schedule).length){
        departure.value= formattedDate(schedule.schedule_startdate);
        arrival.value= formattedDate(schedule.schedule_enddate);
        planTitle.value= schedule.schedule_title;
    }
});

watch(()=>departure.value, ()=>{
    if(departure.value>= arrival.value) arrival.value= departure.value;
})

const getTodayDate=()=>{
    const today = new Date();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0' + today.getDate()).slice(-2);
    return `${today.getFullYear()}-${month}-${day}`;
}

const today= ref(getTodayDate())

const addSchedule = () => {
  if(departure.value.trim().length === 0 || arrival.value.trim().length === 0) {
    datePopover.show()
  } else {
    datePopover.hide()
    if(planTitle.value.trim().length === 0) titlePopover.show()
    else{
        titlePopover.hide()
        let schedule= {}
        schedule= {schedule_startdate:departure.value, schedule_enddate:arrival.value, schedule_title:planTitle.value};
        if(Object.keys(props.schedule).length){
            schedule.schedule_no= props.schedule.schedule_no;
            emit('updateSchedule',schedule);
        }else emit('addSchedule',schedule);
        closeBtn.value.click();
    }
  }
}

const popoverHide=(e)=>{
    if(e.target === popoverButton.value) return
    if(datePopover)
        datePopover.hide();
    if(titlePopover)
        titlePopover.hide();
}
    
</script>

<style scoped>
#planTitle{
    width: 400px;
}
</style>