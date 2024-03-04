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
              <h5 class="modal-title">{{ name }}님의 결제 내역</h5>
            </div>
            <div class="modal-body">
              <!-- 호텔 결제 내역 -->
              <h6 class="modal_header"><i class="fas fa-bed"></i> 호텔 결제 내역</h6>
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
              <!-- 항공권 결제 내역 -->
              <h6 class="modal_header"><i class='fas fa-plane-departure'></i> 항공권 결제 내역</h6>
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
                    <td>{{ Number(item.reservation_price).toLocaleString() }}원</td>
                    <td><a :href="item.reservation_receipturl" target="_blank">영수증 보기</a></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="modal-footer">
              <!-- 이 부분에 footer 내용 추가 -->
              <button type="button" class="btn btn-primary" @click="closeModal">닫기</button>
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
const vuexStore = JSON.parse(localStorage.getItem('vuex'));
const userInfo = vuexStore.loginStore.userInfo;
const name = userInfo.name;

const emit = defineEmits('handleItemClick');

const textClick = (value) => {
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
  background-color: #9893ea; 
  border-radius: 10px 10px 0 0; 
  text-align: center;
  display: inline-block;
}
.modal-title {
  margin: 0;
  font-size: 1.8rem;
  color: #333333;
  font-weight: bold;
}
.modal-body {
  padding: 20px;
}
.modal_header{
  font-size: 20px;
  font-weight: bold;
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

/* Footer 스타일 */
.modal-footer {
  padding: 10px;
  border-top: 1px solid #e9ecef;
  background-color: #f8f9fa;
  text-align: right;
}
</style>
