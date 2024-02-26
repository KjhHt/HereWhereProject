<template>
  <div>
    <!-- 모달 열기 버튼 -->
    <button @click="fetchReservation" class="btn btn-primary">결제 내역</button>
    <!-- 모달 컨텐츠 -->
    <div v-if="showModal" class="modal" tabindex="-1" role="dialog">
      <div class="modal-dialog centered modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">결제 내역</h5>
            <button type="button" class="close" @click="closeModal">
              <span aria-hidden="true">&times;</span>
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
                  <td>{{ item.reservation_pricename }}</td>
                  <td>{{ item.reservation_price }}</td>
                  <td><a :href="item.reservation_receipturl" target="_blank">영수증 보기</a></td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeModal">닫기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const showModal = ref(false);
const reservationItems = ref([]);

async function fetchReservation() {
  // 여기서 결제 내역을 가져오는 비동기 작업 수행
  // 결제 내역을 가져온 후 reservationItems에 할당
  const vuexStore = JSON.parse(localStorage.getItem('vuex'));
  const userId = vuexStore.loginStore.userInfo.id;
  
  // 예약 정보 가져오기
  const response = await axios.get(`${process.env.VUE_APP_API_URL}/reservation`, {
    params: { userId }
  });
  reservationItems.value = response.data;
  console.log(reservationItems)
  console.log(reservationItems.value)
  showModal.value = true; // 모달 열기
}

const closeModal = () => {
  showModal.value = false; // 모달 닫기
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
.modal-dialog {
  position: relative;
  margin: 10% auto;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}
.modal-header {
  position: relative;
  padding-bottom: 20px;
  border-bottom: 1px solid #dee2e6;
}
.modal-title {
  margin: 0;
}
.modal-body {
  padding: 20px;
}
.modal-footer {
  padding-top: 20px;
  text-align: right;
  border-top: 1px solid #dee2e6;
}

</style>
