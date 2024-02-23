<template>
  <!-- 팝업 -->
  <div class="popup-content rounded-4" style="display:none" ref="popupRef">
    <div class="card">
      <div class="card-body">
        <!-- 일정 추가와 일정 목록 버튼 -->
        <!--
        <div class="d-flex justify-content-between pt-5">
          <h2 :class="{ active: showSchedule }" @click="showSchedule = true">일정 추가</h2>
          <h2 :class="{ active: !showSchedule }" @click="showSchedule = false">일정 목록</h2>
        </div>
        -->
          <!-- 일정 추가 내용 -->
          <div v-if="showSchedule">
            <div class="card-img">
              <img src="https://maps.googleapis.com/maps/api/place/js/PhotoService.GetPhoto?1sATplDJYXjCBf7CKcCOSNyDB4NwMfy6jL0YpEfcEq-vhe16kqhgPQqff4C1m0m0iCnlC8Xvga6orEZgy-6thbF5qpPYgGIs7Aq_asoqXzavWlYBL0neY9SH_Il97zAOzl2UHBAhWLYBTcC1zxMgrScSdSFlkhs6X5vao4OqZzDtVS6-N9EGQK&4u400&5m1&2e1&callback=none&key=AIzaSyA16otojrK96bOUH8jhs4Fp4gC3Glt2xrY&token=41574">
            </div>
            <h3 class="pt-3">
              화성행궁
            </h3>
              <input type="text" class="form-control-sm input-border-bottom-only" ref="calendarRef" placeholder="날짜를 선택해주세요"/>
          </div>
          <!-- 일정 목록 내용 -->
          <div v-else>  
            <h2 class="card-title">일정 목록</h2>
            <p class="card-text">현재 등록된 일정 목록에 대한 내용입니다.</p>
          </div>
        </div>
      <button id="closePopup" type="button" 
            class="btn-close btn-close-white position-absolute top-0 end-0 m-2" 
            aria-label="Close" ref="closeRef" @click="hidePopup">
        X
      </button>
    </div>
  </div>
</template> 

<script setup>
import { ref,defineProps,watchEffect } from "vue"

const closeRef= ref(null)
const popupRef= ref(null)
const showSchedule= ref(true)
const photoUrl= ref()
const calendarRef= ref(null)

const props= defineProps({
  plan: Object
})

watchEffect(()=>{
  if(props.plan) {
    console.log(props.plan)
    console.log(photoUrl)
  }
})

const hidePopup=()=>{
  popupRef.value.style.display= 'none'
}

</script>

<style scoped>
.popup-content {
  position: absolute;
  display: block;
  z-index: 1100;
  background-color: #fefefe;
  padding: 0px;
  width: 40%;
  height: 50%;
  left: 30%;
  top: 25%;
}

#closePopup {
  color: #aaaaaa;
  font-size: 20px;
  font-weight: bold;
}

#closePopup:hover,
#closePopup:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
.active {
  background-color: #007bff;
}

.card-img> img{
  width: 100%;
  height: 250px;
}

.input-border-bottom-only {
  border: none;
  border-bottom: 1px solid #ced4da;
}
</style>