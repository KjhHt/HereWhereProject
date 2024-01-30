<template>
  <header class="header">
    <div class="container">
      <div class="header-content d-flex flex-wrap align-items-center justify-content-between">
        <div class="logo-container">
          <img
            class="logo-image"
            src="@/assets/Rectangle 4137.png"
            alt="Logo"
          />
          <a @click="$emit('selectPage','main')" class="logo-link">
            <div class="header-text">HERE WHERE</div>
          </a>
        </div>
        <nav class="nav">
          <a @click="$emit('selectPage','location')" class="nav-item">위치검색</a>
          <a @click="$emit('selectPage','board')" class="nav-item">게시판</a>
          <a @click="$emit('selectPage','admin')" class="nav-item">관리자</a>
          <a @click="$emit('selectPage','mycalendar')" class="nav-item">캘린더</a>
          <a @click="$emit('selectPage','mypage')" class="nav-item">마이페이지</a>
          <a @click="$emit('selectPage','join')" class="nav-item">회원가입</a>
          <div class="text-end">
            <!-- 로그인 전 상태: -->
              <div v-if="!isLoggedIn">
                <button class="btn btn-outline-light me-2" @click="openLoginModal">로그인</button>
              </div>

              <!-- 로그인 후 상태: -->
              <div v-else>
                <div class="profile-dropdown">
                <img :src="src" alt="Cat Icon" class="cat-image" @click="toggleDropdown" />
                <!-- 드롭다운 메뉴 -->
                  <div v-if="isDropdownOpen" class="dropdown-menu">
                    <router-link to="/mypage" class="dropdown-item">마이페이지</router-link>
                    <button class="dropdown-item" @click="logoutAndNavigateToMain">로그아웃</button>
                  </div>
                </div>
              </div>
          </div> 
        </nav>
      </div>
    </div>
    <LoginModal :showModal="loginModalVisible" @closeModal="closeLoginModal" />
  </header>
</template>

<script>
import LoginModal from '@/views/LoginModal.vue';
import axios from 'axios';
//import loginStore from '../store/index'

export default {
  name: 'app-header',
  components: {
    LoginModal,
  },
  data() {
    return {
      src : '',
      loginModalVisible: false,
      isDropdownOpen: false,
    };
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },
  unmounted() {
    window.removeEventListener('scroll', this.handleScroll);
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.loginStore.isLogin;
    },
  },
  watch:{
    '$store.state.loginStore.isLogin': {
      handler(newValue){
        if(newValue){ // 로그인 했을때
          const vuexStore = JSON.parse(localStorage.getItem('vuex'));
          const profileimage = vuexStore.loginStore.userInfo.profileimage;
          console.log(profileimage);
          if( profileimage === '0' ){
            this.src = require('@/assets/dino.jpg');
          }
          else{
            if(profileimage.startsWith("D:")){
              const pathSegments = profileimage.split('\\');
              const lastSegment = pathSegments[pathSegments.length - 1];
              axios.get(`http://localhost:8080/profile/${lastSegment}`)
              .then(res => {
                const dataURI = `data:${res.headers['content-type']};base64,${res.data}`;
                this.src = dataURI;
              })
              .catch(err => console.log(err))
            }
            else{
              this.src = profileimage;
            }
          }
        }
      },
      immediate:true,
    },
  },
  methods: {
    
    openLoginModal() {
      this.loginModalVisible = true;
    },
    closeLoginModal() {
      this.loginModalVisible = false;
    },
    toggleDropdown() {
      // 드롭다운 메뉴 열고 닫기 전환
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    logoutAndNavigateToMain() {
      this.$store.dispatch('logout')
    },
    
  },
};
</script>

<style scoped>


/* 드롭다운 메뉴 스타일 위치 */
.profile-dropdown {
  position: relative;
  display: inline-block;
}
/* Cat 이미지 스타일 */
.cat-image {
  width: 40px;
  height: 40px;
  flex-shrink: 0;
  border-radius: 100px;
  cursor: pointer;
}
/* 드롭다운 메뉴 스타일 추가 */
.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: #fff;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 8px;

  
}

.dropdown-item {
  padding: 8px;
  text-align: center;
  text-decoration: none;
  color: #000;
  cursor: pointer;
}

.header {
  justify-content: center;
  box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.12);
  background-color: #fff;
  display: flex;
  
  
  position: relative;
  z-index: 9998;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

@media (max-width: 991px) {
  .header {
    flex-wrap: wrap;
  }
}

.header-text {
  color: #000;
  align-self: center;
  flex-grow: 1;
  flex-basis: auto;
  margin: auto 0;
  font: 500 28px/129% Roboto, sans-serif;
  text-decoration: underline; /* 기본 밑줄 추가 */
  text-decoration-color: white; /* 밑줄 색상 흰색으로 설정 */ 
}

@media (max-width: 991px) {
  .header-text {
    max-width: 100%;
  }
}

.nav {
  justify-content: space-between;
  background-color: #fff;
  display: flex;
  gap: 20px;
  margin: auto 0;
  align-items: center;
  cursor: pointer;
}

.nav-item {
  color: #000;
  font: 400 16px/150% Roboto, sans-serif;
  text-decoration: none;
}



.header-fixed {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  background-color: #fff;
  box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.12);
}

.container {
  max-width: 1440px;
  margin: 0 auto;
  padding: 20px;
}
.btn {
  width: 100%;
  color: #000; /* 텍스트 색상을 검은색으로 설정 */
  border-color: #000; /* 테두리 색상을 검은색으로 설정 */
}

.logo-container {
  display: flex;
  align-items: center;
}

.logo-image {
  width: 40px;
  height: 40px;
  flex-shrink: 0;
  margin-right: 20px;
  border-radius: 100px;
}



</style>