<template>
  <div>
    <!-- 모달 열기 버튼 -->
    <button @click="fetchReservation" class="btn btn-primary">결제 내역</button>
    <!-- 모달 컨텐츠 -->
    <transition name="modal">
      <div v-if="showModal" class="modal" tabindex="-1" role="dialog" @click="closeModal">
        <div class="modal-dialog centered modal-lg" role="document" @click.stop>
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">결제 내역</h5>
              <button type="button" class="close-icon" @click="closeModal">
                <i class="fas fa-times"></i>
              </button>
            </div>
            <div class="modal-body">
              <!-- 호텔 결제 내역 -->
              <h6>호텔 결제 내역</h6>
              <table class="table">
                <thead>
                  <tr>
                    <th scope="col">결제 번호</th>
                    <th scope="col">날짜</th>
                    <th scope="col">상품 정보</th>
                    <th scope="col">가격</th>
                    <th scope="col">영수증</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item, index in reservationItems" :key="index">
                    <td>{{ item.reservation_no }}</td>
                    <td>{{ item.reservation_purchastime }}</td>
                    <td @click="textClick([item.reservation_lat,item.reservation_lng])">{{ item.reservation_pricename }}</td>
                    <td>{{ Number(item.reservation_price).toLocaleString() }}원</td>
                    <td><a :href="item.reservation_receipturl" target="_blank">영수증 보기</a></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import axios from 'axios';

const showModal = ref(false);
const reservationItems = ref([]);

const emit = defineEmits('handleItemClick');

const textClick = (value) => {
  console.log(value)
  emit('handleItemClick', value);
}

async function fetchReservation() {
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const userId = vuexStore.loginStore.userInfo.id;
  
  // 예약 정보 가져오기
  const response = await axios.get(`${process.env.VUE_APP_API_URL}/reservation`, {
    params: { userId }
  });

  // 가져온 데이터를 날짜 순으로 정렬 
  reservationItems.value = response.data.sort((a, b) => {
    const dateA = new Date(a.reservation_purchastime);
    const dateB = new Date(b.reservation_purchastime);
    return dateB - dateA;  
  });
  console.log(reservationItems.value[0].reservation_lat)
  showModal.value = true;
}

const closeModal = () => {
  showModal.value = false; 
};
</script>

<style scoped>
.modal {
  display: block;
  position: fixed;
  z-index: 1050;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5);
}
.modal-dialog.modal-lg {
  max-width: 70%; /* 원하는 가로 크기를 % 또는 px 단위로 설정하세요. */
}

.modal-dialog {
  position: relative;
  margin: 10% auto;
  padding: 10px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}
.modal-header {
  position: relative;
  padding: 10px;
  background-color: #e6e6fa; /* 헤더 배경색 변경 */
  color: #333333; /* 헤더 텍스트 색상 변경 */
  border-radius: 10px 10px 0 0; /* 헤더의 좌우 상단 모서리만 둥글게 */
}
.modal-title {
  margin: 0;
  font-size: 1.8rem;
  text-align: center; /* 텍스트 가운데 정렬 */
}
.modal-body {
  padding: 20px;
}

/* 애니메이션 스타일 */
.modal-enter-active, .modal-leave-active {
    transition: opacity .5s;
  }
.modal-enter-from, .modal-leave-to {
  opacity: 0;
}
.modal-enter-to, .modal-leave-from {
  opacity: 1;
}

/* 닫기 아이콘 스타일 */
.close-icon {
  position: absolute;
  right: 20px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.5rem;
  color: black; /* 검은색 아이콘 */
}
</style>
