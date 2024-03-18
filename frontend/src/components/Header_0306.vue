<template>
  <header class="header">
    <div class="container">
      <div class="header-content d-flex flex-wrap align-items-center justify-content-between">
        <div class="logo-container">
          <a @click="$emit('selectPage','main')" class="logo-link">
            <!-- <div class="header-text">HERE WHERE</div> -->
            <img
            class="logo-image"
            src="@/assets/HereWhereLogoMini.png"
            alt="Logo"
            />
          </a>
        </div>
        <nav class="nav">
          <!-- 채팅 페이지 -->
          <a @click="$emit('selectPage','test')" class="nav-item" @mouseover="showTooltip('채팅')" @mouseout="hideTooltip">
            <div class="tooltip-wrapper">
              <img src="@/assets/chatting.png" alt="Location Icon" class="nav-icon" />
              <span v-if="tooltip === '채팅'" class="tooltip">채팅</span>
            </div>
          </a>
          <!-- 채팅 페이지 -->
          <a @click="$emit('selectPage','location')" class="nav-item" @mouseover="showTooltip('위치검색')" @mouseout="hideTooltip">
            <div class="tooltip-wrapper">
              <img src="@/assets/location.png" alt="Location Icon" class="nav-icon" />
              <span v-if="tooltip === '위치검색'" class="tooltip">위치검색</span>
            </div>
          </a>
          <a @click="$emit('selectPageFlight','flightreserve','Y')" class="nav-item" @mouseover="showTooltip('항공권검색')" @mouseout="hideTooltip">
        <div class="tooltip-wrapper">
          <img src="@/assets/board.png" alt="Board Icon" class="nav-icon" />
          <span v-if="tooltip === '항공권검색'" class="tooltip">항공권검색</span>
        </div>
      </a>

      <!--
      <a @click="$emit('selectPage','admin')" class="nav-item" @mouseover="showTooltip('관리자')" @mouseout="hideTooltip">
        <div class="tooltip-wrapper">
          <img src="@/assets/admin.png" alt="Admin Icon" class="nav-icon" />
          <span v-if="tooltip === '관리자'" class="tooltip">관리자</span>
        </div>
      </a>

      <a @click="$emit('selectPage','mycalendar')" class="nav-item" @mouseover="showTooltip('캘린더')" @mouseout="hideTooltip">
        <div class="tooltip-wrapper">
          <img src="@/assets/calendar.png" alt="Calendar Icon" class="nav-icon" />
          <span v-if="tooltip === '캘린더'" class="tooltip">캘린더</span>
        </div>
      </a>
      -->

      <a @click="$emit('selectMyPage','mypage')" class="nav-item" @mouseover="showTooltip('마이페이지')" @mouseout="hideTooltip">
        <div class="tooltip-wrapper">
          <img src="@/assets/mypage.png" alt="MyPage Icon" class="nav-icon" />
          <span v-if="tooltip === '마이페이지'" class="tooltip">마이페이지</span>
        </div>
      </a>

      <!--
      <a @click="$emit('selectPage','join')" class="nav-item" @mouseover="showTooltip('회원가입')" @mouseout="hideTooltip">
        <div class="tooltip-wrapper">
          <img src="@/assets/join.png" alt="Join Icon" class="nav-icon" />
          <span v-if="tooltip === '회원가입'" class="tooltip">회원가입</span>
        </div>
      </a>
      -->
      
      <a @click="$emit('selectPage','trip-moment')" class="nav-item" @mouseover="showTooltip('트립모먼트')" @mouseout="hideTooltip">
        <div class="tooltip-wrapper">
          <img src="@/assets/trip-moment.png" alt="Trip-moment Icon" class="nav-icon" />
          <span v-if="tooltip === '트립모먼트'" class="tooltip">트립모먼트</span>
        </div>
      </a>

      <a @click="$emit('selectPage','translation')" class="nav-item" @mouseover="showTooltip('번역')" @mouseout="hideTooltip">
        <div class="tooltip-wrapper">
          <img src="@/assets/trip-moment.png" alt="Trip-moment Icon" class="nav-icon" />
          <span v-if="tooltip === '번역'" class="tooltip">번역</span>
        </div>
      </a>
      
          <div class="text-end">
            <!-- 로그인 전 상태: -->
              <div v-if="!isLoggedIn">
                <button class="btn btn-outline-light me-2" @click="openLoginModal">로그인</button>
              </div>

              <!-- 로그인 후 상태: -->
              <div v-else>
                <div class="profile-dropdown" id="notification">
                <img src="@/assets/notice_on.png" alt="Notice" class="cat-image" @click="toggleNoticeDropdown" />
                <div id="count" v-if="noticeCountData > 0">{{ noticeCountData }}</div>
                <!-- 드롭다운 메뉴 -->
                  <div v-if="isNoticeDropdownOpen" class="dropdown-menu" style="width: 360px;">
                  <!--알림 -->
                  <div>
                    <div>
                      <div class="cont">
                        <p class="alarm">알&nbsp;&nbsp;림</p>
                      </div>
                    </div>
                    <div v-for="(notice, index) in noticeListData" :key="index" class="mes">
                      <div class="cover">
                        <div class="profile-image">
                          <img :src="notice.profileimage">
                        </div>
                        <div class="content">
                          <div class="text-content">
                            <div class="follow-request">
                              <span><strong>{{ notice.follow_senderid }}</strong>님이 {{ notice.notice_content }}</span>
                            </div>
                            <div class="timer" v-if="!followStatus[notice.notice_no]">{{ formatTimeAgo(notice.notice_createtime) }}</div>
                          </div>                          
                          <div class="chose-button" v-if="!followStatus[notice.notice_no]">
                            <button type="button" class="btn btn-dark" id="btnSuccess" @click="followSuccess(notice.notice_no)">확인</button>
                            <button type="button" class="btn btn-dark" id="btnFail" @click="followFail(notice.notice_no)">거절</button>
                          </div>
                          <div class="chose-button" v-if="followStatus[notice.notice_no]">
                            <div v-if="followStatus[notice.notice_no] === 'accepted'">팔로우 추가 완료했습니다.</div>
                            <div v-if="followStatus[notice.notice_no] === 'rejected'">거절하셨습니다.</div>  
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!--알림 끝-->
                  </div>
                </div>

                <div class="profile-dropdown">
                <img :src="src" alt="Login" class="cat-image" @click="toggleDropdown" />
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
    <LoginModal :showModal="loginModalVisible"
     @closeModal="closeLoginModal"
     @closeModalAndGoToRegistration="handleCloseModalAndGoToRegistration"
    />
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
  props: {
    noticeListData : Array,
    noticeCountData : Object
  },
  data() {
    return {
      src : '',
      loginModalVisible: false,
      isDropdownOpen: false,
      isNoticeDropdownOpen: false,
      tooltip: '',
      followStatus: {},
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
          if( profileimage === '0' ){
            this.src = require('@/assets/dino.jpg');
            localStorage.setItem('profileImage', require('@/assets/dino.jpg'));
          }
          else{
            if(profileimage.startsWith("D:") || profileimage.startsWith("E:")){
              const pathSegments = profileimage.split('\\');
              const lastSegment = pathSegments[pathSegments.length - 1];
              axios.get(`${process.env.VUE_APP_API_URL}/profile/${lastSegment}`)
              .then(res => {
                const dataURI = `data:${res.headers['content-type']};base64,${res.data}`;
                this.src = dataURI;
                localStorage.setItem('profileImage', dataURI);
              })
              .catch(err => console.log(err))
            }
            else{
              this.src = profileimage;
              localStorage.setItem('profileImage', profileimage);
            }
          }
        }
      },
      immediate:true,
    },
  },
  methods: {
    formatTimeAgo(time){
      const currentTime = new Date();
      const postedTime = new Date(time);
      const timeDifference = currentTime - postedTime;

      const seconds = Math.floor(timeDifference) / (1000);
      const minutes = Math.floor(timeDifference / (1000 * 60));
      const hours = Math.floor(timeDifference / (1000 * 60 * 60));
      const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
      
      if (seconds < 60) {
          return `${seconds.toFixed(0)}초 전`;
      } else if (minutes < 60) {
          return `${minutes}분 전`;
      } else if (hours <= 23) {
          return `${hours}시간 전`;
      } else {
          return `${days}일 전`;
      }
    },
    handleCloseModalAndGoToRegistration(){
      this.$emit('selectPage', 'join');
    },
    followSuccess(notice_no){
      console.log('followSuccess 함수');
      console.log('notice_no : ',notice_no);
      console.log(this.noticeListData);
      console.log(this.noticeListData.value);
      axios.get(process.env.VUE_APP_API_URL+'/successFollow',{
        params : {
          notice_no:notice_no
        }
      })
      .then(res=>{
        this.followStatus[notice_no] = 'accepted';
        console.log(res,' followSuccess 서버에서 옴');
      })
      .catch(err=>console.log(err))
      
    },
    followFail(notice_no){
      console.log('followFail 함수');
      axios.get(process.env.VUE_APP_API_URL+'/failFollow',{
        params : {
          notice_no:notice_no
        }
      })
      .then(res=>{
        this.followStatus[notice_no] = 'rejected';
        console.log(res,' FailFollow 서버에서 옴');
      })
      .catch(err=>console.log(err))
    },
    showTooltip(text) {
      this.tooltip = text;
    },

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
    toggleNoticeDropdown() {
      this.$emit('resetCount');
      // 드롭다운 메뉴 열고 닫기 전환
      this.isNoticeDropdownOpen = !this.isNoticeDropdownOpen;
    },
    logoutAndNavigateToMain() {
      this.$store.dispatch('logout')
    },
    
  },
};
</script>

<style scoped>

#notification {
  position: relative;
  display: inline-block;
}
#count {
  position: absolute;
  top: -5px;
  right: -5px;
  width: 20px;
  height: 20px;
  background-color: red;
  color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 14px;
}



/* 알림창 커스텀 */
/*0302 여기서부터*/
.cover{
padding-top: 0px;
min-width: 0px;
margin-top: 0px;
margin-bottom: 0px;
border-bottom-width: 0px;
border-bottom-style: solid;
min-height: 44px;
padding-bottom: 0px;
padding-left: 8px;
border-right-style: solid;
flex-shrink: 1;
border-left-width: 0px;
border-right-width: 0px;
box-sizing: border-box;
border-top-width: 0px;
display: flex;
align-items: center;
border-left-style: solid;
padding-right: 8px;
justify-content: space-between;
flex-direction: row;
position: relative;
margin-left: 0px;
z-index: 0;
flex-grow: 1;
border-top-style: solid;
margin-right: 0px;
}

.profile-image{
margin-top: 8px;
margin-bottom: 8px;
margin-right: 12px;
flex-direction: column;
display: flex;
position: relative;
align-self: flex-start;
}

.profile-image img{
vertical-align: bottom;
display: inline-block;
position: relative;
z-index: 0;
height: 56px;
width: 56px;
border-radius: 50%;
}

.content{
padding-top: 12px;
padding-bottom: 12px;
min-width: 0px;
margin-top: 0px;
margin-bottom: 0px;
min-height: 0px;
border-bottom-width: 0px;
border-bottom-style: solid;
border-right-style: solid;
flex-shrink: 1;
border-left-width: 0px;
padding-left: 0px;
flex-direction: column;
border-right-width: 0px;
box-sizing: border-box;
border-top-width: 0px;
display: flex;
padding-right: 0px;
border-left-style: solid;
justify-content: space-between;
align-items: stretch;
position: relative;
margin-left: 0px;
z-index: 0;
flex-grow: 1;
border-top-style: solid;
margin-right: 0px;
flex-basis: 0px;
}

.text-content{
flex-direction: column;
display: flex;
margin-bottom: -5px;
margin-top: -5px;
}
.follow-request{
margin-bottom: 5px;
margin-top: 5px;
min-width: 0px;
max-width: 100%;
color: #050505;
text-align: left;
overflow-wrap: break-word;
display: block;
word-break: break-word;
line-height: 1.3333;
font-weight: 400;
font-size: 0.9375rem;
-webkit-box-orient: vertical;
-webkit-line-clamp: 3;
display: -webkit-box;
overflow-x: hidden;
overflow-y: hidden;
position: relative;
}
.chose-button{
margin-top: 10px;
box-sizing: border-box;
display: flex;
flex-shrink: 0;
align-items: stretch;
flex-direction: row;
position: relative;
z-index: 0;
flex-wrap: nowrap;
}
#btnSuccess {
  width: 50%;
  background-color: #0866FF;
  color: white;
  border-radius: 5px;
  margin-right: 5px;
  border-color:white;
}

#btnFail {
  width: 50%;
  background-color: #e4e6eb;
  color: black !important; 
  border-radius: 5px;
  margin-left: 5px;
  border-color:white;
}
/*0302 여기까지*/ 

.bi-bell-fill{
  width: 40px;
  height: 40px;
}

.alarm{
margin-top: 4px;
margin-left: 150px;
  position: absolute;
}
.chose{
  position: absolute;
  margin-top: 10px;
  margin-left:200px ;
}
.cont{
  background-color: #74BCF7;
  position: center;
  height: 40px;
  margin-top: -10px;
  font-size: 25px;
  color: #fff;
  text-align: center;
}
.mes{
  padding-left: 8px;
  padding-right: 8px;
  border-bottom: 1px solid gainsboro;
}
.request{
  font-weight: bold;
  /* border: 1px solid red; */
  height: 29px;
  margin-top: 0px;
  background-color: rgb(255, 255, 255);
}
.timer{
  margin-top:5px;
  margin-bottom:5px;
  min-width:0px;
  max-width: 100%;
  text-align: left;
  overflow-wrap: break-word;
  display: block;
  word-break: break-word;
  line-height: 1.2308;
  font-size: 0.8125rem;
  overflow-x: hidden;
  overflow-y: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  position: relative;
  padding-bottom: 1px;

  color: #0866FF;

  font-weight: 600;

}
/* 알림창 커스텀 끝*/



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
  padding-bottom: 0px;
  
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
  display: flex;
  align-items: center; /* 이미지를 수직 가운데 정렬 */
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
  width: 200px;
  height: 50px;
  flex-shrink: 0;
  margin-right: 20px;
  border-radius: 100px;
}

.tooltip-wrapper {
  position: relative;
  display: inline-block;
}


.tooltip {
  position: absolute;
  background-color: #000;
  color: #fff;
  padding: 4px;
  border-radius: 4px;
  font-size: 14px;
  white-space: nowrap;
  opacity: 0; 
  transition: opacity 0.2s ease;
  top: 100%; 
  left: 50%; 
  transform: translateX(-50%); 
}


.nav-item:hover .tooltip {
  opacity: 1;
}

</style>