<template>
  <div v-if="showModal" class="modal" @click="closeModalByBackground">
    <div class="modal-content"  @click.stop>
      <img src="@/assets/X_small.png" alt="close-modal" class="close-image" @click="closeModal" />
      <!-- LoginPage의 디자인에 맞게 수정한 내용 시작 -->
  
      <main class="form-signin w-100 m-auto">
        <form @submit.prevent="login">
          <div class="form-container">
              <div class="icon-container">
                  <img src="@/assets/person.png" alt="Login-house" class="house-image" />
              </div>
            <h1 class="h3 mb-3 fw-normal">로그인</h1>

            <div class="form-floating">
              <input v-model="id" type="id" class="form-control" id="modalFloatingInput" placeholder="아이디">
              <label for="modalFloatingInput">아이디를 입력해주세요</label>
            </div>
            <div class="form-floating">
              <input v-model="pwd" type="pwd" class="form-control" id="modalFloatingPassword" placeholder="비밀번호">
              <label for="modalFloatingPassword">비밀번호를 입력해주세요</label>
            </div>

            <div class="d-flex justify-content-between align-items-center my-3">
              <a class="registration-link" @click="closeModalAndGoToRegistration">회원가입 하시겠습니까?</a>
            </div>
            <div class="login-btn-group">
              <button class="btn btn-primary w-100 py-2" type="submit">로그인</button>
            </div>
            
            <!-- 소셜 로그인 부분 시작 -->
            <div class="social-login-divider" style="margin-bottom: 10px;">
              <div class="social-login-text">소셜 계정으로 간편 로그인</div>
            </div>
            <ul class="social_login" id="wrap_social_login">
              <li class="login_naver">
                <a href="/oauth2/authorization/naver" class="sl_naver ga_data_layer" data-provider="naver">
                  <span class="wrap_icon">
                    <img src="@/assets/naver_circle.png" alt="Naver Icon" class="icon" style="width: 50px; height: 50px;">
                 </span>
                </a>
              </li>
               <li class="login_kakao">
                <a href="/oauth2/authorization/kakao" class="sl_kakao ga_data_layer" data-provider="kakao">
                  <span class="wrap_icon">
                    <img src="@/assets/kakao_big.png" alt="Kakao Icon" class="icon" style="width: 50px; height: 50px;">
                 </span>
                </a>
              </li>
              <li class="login_google">
                 <a href="/oauth2/authorization/google" class="sl_google ga_data_layer" data-provider="google"> 
                  <span class="wrap_icon">
                    <img src="@/assets/google_big.png" alt="Google Icon" class="icon" style="width: 50px; height: 50px;">
                 </span>
                </a>
              </li>
              <li class="login_facebook">
                <a href="/oauth2/authorization/facebook" class="sl_facebook ga_data_layer" data-provider="facebook">
                  <span class="wrap_icon">
                    <img src="@/assets/facebook_circle.png" alt="Facebook Icon" class="icon" style="width: 50px; height: 50px;">
                 </span>
                </a>
              </li>
            </ul>
          <!-- 소셜 로그인 부분 끝 -->
          </div>
        </form>
      </main>
      <!-- LoginPage의 디자인에 맞게 수정한 내용 끝 -->
    </div>
  </div>
</template>

<script>
//import axios from 'axios';
export default {
  props: {
    showModal: Boolean,
  },
  data() {
    return {
      id: '',
      pwd: '',
    };
  },
  methods: {
    closeModalByBackground() {
    // 배경을 클릭하면 모달을 닫음
      this.closeModal();
    },
    closeModalAndGoToRegistration() {
    // 모달 닫기
      this.closeModal();
    
    // 회원가입 페이지로 이동
      this.goToRegistration();
    },
    closeModal() {
      this.$emit('closeModal');
    },
    login() {
      const saveData = {}
      saveData.id = this.id
      saveData.pwd = this.pwd
      this.$store.dispatch('login',saveData)
      this.closeModal();
    },
    goToRegistration() {
      this.$router.push('/join');
    },
  },
};
</script>

<style scoped>
/* LoginPage의 디자인에 맞게 수정한 내용에 필요한 스타일 추가 */
.modal {
/* 모달 스타일 추가 */
position: fixed;
top: 0;
left: 0;
width: 100%;
height: 100%;
background-color: rgba(0, 0, 0, 0.5);
display: flex;
justify-content: center;
align-items: center;
}

.modal-content {
/* 모달 내용 스타일 추가 */
background-color: #fff;
padding: 20px;
border-radius: 10px;
max-width: 400px;
width: 100%;
overflow: auto;
box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.social_login li {
list-style-type: none; /* 목록 마커 제거 */
}

.form-signin {
width: 100%;
max-width: 400px;
}

.registration-link {
cursor: pointer;
margin-left: auto;
}
    
.registration-link{
-webkit-text-size-adjust: none;
font-family: -apple-system,BlinkMacSystemFont,helvetica,"Apple SD Gothic Neo",sans-serif;
display: inline-block;
font-size: 14px;
line-height: 17px;
text-decoration: none;
color: #888;
}
    
.login-btn-group {
display: flex;
flex-direction: column;
gap: 10px;
}
    /* 아이콘 정렬을 위한 스타일 */
.icon-container {
text-align: center;
display: flex;
justify-content: center;
align-items: center;
margin-bottom: 1rem; /* 텍스트와의 간격을 조절 */
}
.h3 {
user-select: text; /* 텍스트 선택을 가능하게 함 */
color: #000000; /* 텍스트 색상을 검정색으로 지정 */
}
.form-floating label {
user-select: text; /* 텍스트 선택을 가능하게 함 */
color: #000000; /* 텍스트 색상을 검정색으로 지정 */
}



.close-image {
cursor: pointer;
align-self: flex-end; /* 맨 오른쪽 정렬 */
}
.social-login-divider {
border-bottom: 1px solid #000; /* 실선 스타일을 지정 */
text-align: center;
margin: 20px 0; /* 간격을 조절합니다. */
}

.social-login-text {
user-select: text; /* 텍스트 선택을 가능하게 함 */
color: #000000; /* 텍스트 색상을 검정색으로 지정 */
background-color: #fff;
padding: 0 10px; /* 텍스트 좌우 간격을 지정 */
position: relative;
top: -10px; /* 텍스트를 실선 위에 겹쳐 표현하기 위해 위치 조절. */
}



.social_login {
  width: 100%;
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  padding-top: 24px;
  margin: 0;
  padding: 0;
}


.social_login li {
flex: 1;
text-align: center;
justify-content: flex-start; /*내용을 시작 부분에 정렬 */
margin-left: 5px;
}
.social_login li:first-child {
margin-left: 0; /* 첫 번째 버튼에 대한 마진 제거 */
}

.social_login li + li {
margin-left: 5px; /* 두 번째부터는 왼쪽 마진 추가 */
}
.social_login li img {
display: block;
margin: 0 auto; /* 중앙 정렬을 위해 추가 */
}
.wrap_icon {
display: inline-block;
margin: 0 5px; /* 이미지 간격 조절 */
}

.social_login li.login_naver {
margin-left: 0; /* 네이버 로그인 버튼 왼쪽으로 정렬 */
}

.social-login-divider[data-v-f7af2618] {
  border-bottom: 1px solid #888;;
  text-align: center;
  margin: 20px 0;
}
</style>