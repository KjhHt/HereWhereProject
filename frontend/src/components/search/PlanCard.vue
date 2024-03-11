<template>
  <div class="plan-card">
    <div class="card-body">
      <div class="card-info">
        <div class="card-info-head">
          <p>{{ props.plan.plan_name }}</p>
        </div>
        <div class="card-info-body">
          <p> 추가일자 {{ formattedDate(props.plan.plan_registrationdate) }}</p>
        </div>
        <div class="card-info-bottom">
          <!--<a href="#" class="btn btn-primary" @click="passArrival">경로</a>-->
        </div>
      </div>
      <div class="card-img">
        <img :src="props.plan.plan_img" alt="맛집 이미지" />
      </div> 
    </div>
  </div>
</template>
  
<script setup>
  import { defineProps } from 'vue';
  import { formattedDate } from '@/composable/custom';

  const props=defineProps({
    plan: Object
  })

  //const emit= defineEmits(['passArrival'])
  /*
  const passArrival=()=>{
    emit('passArrival', props.plan)
  }*/

</script>
  
<style scoped>
  .plan-card {
    border: 1px solid #ddd;
    width: 375px;
    height: 190px;
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
.card-info {
  margin-top: 10px;
  width: 60%; /* 내용과 이미지 사이의 비율을 조절 */
  padding: 10px; /* 내용 주변에 여백 추가 */
  text-align: center;
}
.card-info-head {
  text-overflow: ellipsis;  /* 넘친 텍스트를 ...으로 표시 */
  white-space: nowrap;  /* 텍스트를 한 줄에 표시 */
  overflow: hidden;     /* 내용이 넘칠 경우 숨김 처리 */
  font-weight: bold;
}
.card-body {
  display: flex;
  justify-content: space-between;
  height: 100%;
  width: 100%;
}

.card-img{
  width: 40%; /* 내용과 이미지 사이의 비율을 조절 */
}

.card-img img{
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 카드를 꽉 채우면서 비율을 유지 */
  border-radius: 10px;
  padding: 5px;
}
img {
  width: 172.5px;
  height: 172.5px;
}

</style>