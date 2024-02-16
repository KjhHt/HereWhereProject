<template class="a2">
  <body class="a1">
      <div class="container">
          <!-- <h1 class="logo">
              <img :src="imageData.logo" class="logo_icon">
          </h1> -->
          <div class="3">
              <div class="form_list">
          <label for="fileInput">
              <img :src="imageData.poto" class="poto_icon rounded-image" :style="{ width: imageSize, height: imageSize }" @click.prevent="handleImageClick">
              <p class="profill">※프로필은 선택 사항입니다.</p>
              <input type="file" id="fileInput" ref="fileInput" style="display: none" @change="handleFileUpload" >
              <span class="image_poto"></span>
              
              <button type="button" class="btn btn-outline-dark" :class="pcfile" id="pcfile" @click="openFileInput">PC파일</button>
              <button type="button" class="btn btn-outline-dark" :class="modal" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">이미지생성</button>
          </label>
                  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                      <div class="modal-dialog">
                          <div class="modal-content">
                              <div class="modal-header">
                                  <h5 class="modal-title" id="exampleModalLabel">내가 원하는 이미지 생성기</h5>
                                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                              </div>
                              <div class="modal-body" >
                                  <form>
                                      <div class="mb-3">
                                          <label for="recipient-name" class="col-form-label">이미지 결과</label>
                                          <div id="image-container">
                                              <img v-if="image_url" :src="image_url" alt="Generated Image" :style="{ width: '50%', height: '50%' }">
                                              <div class="spinner-border" style="width: 3rem; height: 3rem; margin-top:20px;" role="status" v-if="loading">
                                                  <span class="visually-hidden">Loading...</span>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="mb-3">
                                          <label for="message-text" class="col-form-label"></label>
                                          <textarea class="form-control" id="message-text" placeholder="생성하고 싶은 이미지 텍스트로 입력후 이미지 생성 버튼을 눌러주세요!"></textarea>
                                      </div>
                                  </form>
                              </div>
                              <div class="modal-footer">
                                  <button type="button" class="btn btn-outline-danger" :class="closs" data-bs-dismiss="modal" id="myModal" @click="closeModal">Close</button>
                                  <button type="button" class="btn btn-outline-info" :class="use" @click="useProfile" data-bs-toggle="modal" data-bs-target="#exampleModal2" data-bs-whatever="@mdo">프로필 사용</button>
                                  <button type="button" class="btn btn-outline-success" :class="use" @click="images_gpt">이미지 생성</button>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="modal" tabindex="-1" id="exampleModal2">
                      <div class="modal-dialog">
                          <div class="modal-content" style="margin-top: 230px;">
                              <div class="modal-header">
                                  <h5 class="modal-title">프로필 이미지 AI생성</h5>
                                  
                              </div>
                              <div class="modal-body">
                                  <p style="width: 470px; height: 50px; top: 40px;  text-align: center;">프로필 이미지가 적용되었습니다.</p>
                              </div>
                              <div class="modal-footer">
                                  <button type="button" class="btn btn-outline-info" :class="use" data-bs-dismiss="modal">Close</button>
                              </div>
                          </div>
                      </div>
                  </div>
                  <!-- 숨겨진 파일 입력 창 -->
  
                  <div class="form_id" :class="{ 'error': !validUsername ,'success': validUsernamesuccess }">
  
                      <img :src="imageData.id" class="id_icon">
                      <input type="text" class="input" id="id" name="id" placeholder="아이디" autocomplete="off" v-model="username" @blur="validateUsername">
                      <p v-show="!validUsername" class="input-error">{{ usernameErrorMessage }}</p>
                      <p v-show="validUsernamesuccess && validUsername" class="input-success">∙ 사용 가능한 아이디 입니다.</p>
                  </div>
  
                  <div class="form_pwd1" id="pwd" :class="{ 'error': !validPassword,'success': passwordsuccess }">
                      <img :src="imageData.lock" class="lock_icon">
                      <input type="password" class="input pwd" id="pwd" name="pwd" placeholder="비밀번호" autocomplete="off" v-model="password" @blur="validatePassword">
                      <p v-show="!validPassword" class="input-error">{{ passwordErrorMessage }}</p>
                      <p v-show="passwordsuccess && validPassword" class="input-success">∙ 올바른 비밀번호 형식입니다.</p>
                  </div>
                  <div class="form_pwd2" id="pwd" :class="{ 'error': !validPasswordConfirmation,'success': passwordsuccess2 }">
                      <img :src="imageData.lock" class="lock_icon">
                      <input type="password" class="input" id="pwdConfirm" name="pwdConfirm" placeholder="비밀번호 재확인" autocomplete="off" v-model="passwordConfirm" @blur="validatePasswordConfirmation">
                      <p v-show="!validPasswordConfirmation" class="input-error">{{ valpasswordErrorMessage }}</p>
                      <p v-show="passwordsuccess2 && validPasswordConfirmation" class="input-success">∙ 비밀번호가 일치합니다.</p>
                  </div>
                  <div class="form_name" :class="{ 'error': !validName,'success': validNamesuccess }">
                      <img :src="imageData.id" class="name_icon">
                      <input type="text" class="input" id="name" name="name" placeholder="닉네임" autocomplete="off" v-model="name" @blur="validateName">
                      <p v-show="!validName" class="input-error">{{ nameErrorMessage }}</p>
                      <p v-show="validNamesuccess && validName" class="input-success">∙ 사용가능한 닉네임 입니다.</p>
                  </div>
                  <div class="form_phone" id="phone-container" :class="{ 'error': !validPhoneNumber,'success': PhoneNumbersuccess }">
                      <img :src="imageData.email" class="email_icon">
                      <input type="text" class="input" id="phone" name="phone" placeholder="휴대전화 (-)제외" autocomplete="off" v-model="phoneNumber" @blur="validatePhoneNumber">
                      <button type="button" class="btn btn-outline-dark" :class="bttn" @click="sendVerificationCode">인증</button>
                      <p v-show="!validPhoneNumber" class="input-error">{{ phoneNumberErrorMessage }}</p>
                      <p v-show="PhoneNumbersuccess && validPhoneNumber" class="input-success">∙ 올바른 전화번호 형식입니다. 인증 버튼을 눌러주세요.</p>
                      <input v-if="showVerification " type="text" class="checkcode" id="verificationCode" name="verificationCode" placeholder="인증 코드" autocomplete="off" v-model="verificationCode">
                      <button v-if="showVerification" type="button" class="btn btn-outline-dark confirm-btn"  @click="randomCode">인증 확인</button>
                      <p v-if="timehidden" class="input-error">남은 시간: {{ formatTime(remainingTime) }}</p>
                      <p v-if="verificationResultMessage" class="input-success">{{ verificationResultMessage }}</p>
                      <p v-if="verificationResultMessagesuccess" class="input-error">{{ verificationResultMessagesuccess }}</p>
                  </div>
              </div>
                  <div class="form_gender" id="gender" :class="{'error' : !validSelect}">
                      <div class="select">
                          <input  type="radio" id="select1" name="gender" class="gender1" v-model="selectedGender" value="male"><label for="select1">남자</label>
                          <input type="radio" id="select2" name="gender" class="gender2" v-model="selectedGender" value="female"><label for="select2">여자</label>
                          <p v-show="!validSelect" >{{selecterrorMessage}}</p>
                        </div>
                        
                      
                      <div class="form_MBTI" id="MBTI" :class="{'error' : !validMBTI,'success': validMBTIsuccess }">
                          <!-- 직접 입력을 위한 셀렉트 박스 -->
                          <input type="text" class="test" v-model="customMBTI" placeholder="직접 입력"  @blur="validateMBTI"/>
                          <p v-show="!validMBTI" class="input-error" style="text-align: center; margin-top: -0px;">{{MBTIerrorMessage}}</p>
                          <p v-show="validMBTIsuccess && validMBTI" class="input-success">∙ 유효한 형식입니다.</p>
                      </div>
                      <!-- MBTI 퀴즈 모달 -->
  
                  </div>
                  <button class="btn btn-outline-dark" :class="mbtimodal" style="border: 1px solid #858383;" @click="modalOpen">MBTI 검사</button>
                  <div class="modal-wrap" v-show="modalCheck" @click="modalOpen">
                      <div class="modal-container" @click.stop="">
                          <div class="title">
                              <h4>MBTI 간편검사</h4>
                          </div>
  
                          <!--  모달창 content  -->
                          <div id="carouselExample" class="carousel slide" >
                              <div class="carousel-inner">
                                  <div class="carousel-item active">
                                      <div class="titlemain" >
                                          <h4>[시작 설명]</h4>
                                          <h5>문제는 총 4개의 문제가 제공됩니다.<h6>소요 시간 30초</h6></h5>
                                          <h5>E 와 I 차이 : 외향형과 내향형의 검사 </h5>
                                          <h5>S와 N 차이 : 감각형과 직관형의 검사 </h5>
                                          <h5>T 와 F 차이 : 사고형과 감각형의 검사 </h5>
                                          <h5>J 와 P 차이 : 판단형과 인식형의 검사 </h5><br>
                                          <h6>간단한 질문들을 통해 자신에 MBTI를 알아봐여!</h6>
                                          <h6>자신에 MBTI에 맞게 추천 맛집과 성향에 맞는 가개를 소개해준답니다.</h6>
                                          
                                      </div>
                                  </div>
                                  <div class="carousel-item" >
                                      <div class="title1">
                                          <h5>[나에게 쉬는 시간이 생겼을 때~]</h5>
                                      </div>
                                      <img :src="mbtidata.problem0" class="d-block w-75" alt="...">
                                  </div>
                                  <div class="carousel-item" >
                                      <div class="title1">
                                          <h5>[내가 엘리베이터를 탔을 때!]</h5>
                                      </div>
                                      <img :src="mbtidata.problem1" class="d-block w-75" alt="...">
                                  </div>
                                  <div class="carousel-item" >
                                      <div class="title1">
                                          <h5>[만약 친구가 차사고가 났다고 연락이 왔을 때?]</h5>
                                      </div>
                                      <img :src="mbtidata.problem2" class="d-block w-75" alt="...">
                                  </div>
                                  <div class="carousel-item" >
                                      <div class="title1">
                                          <h5>[친구들과 함께 간 여행, 숙소에서 짐을 풀고 나가자! 했을 때]</h5>
                                      </div>
                                      <img :src="mbtidata.problem3" class="d-block w-75" alt="...">
                                  </div>
                                  <div class="carousel-item">
                                      <div class="title1">
                                          <h3>당신의 mbti는</h3>
                                      </div>
                                      <div><h2>(↓) 커서를 가져다가 대보세요</h2></div>
                                        
                                          <div class="outer" @click="modalOpen">
                                            <div class="inner">
                                              <label class="label2" @click="custominput"> {{ mbtiString }} </label>
                                            </div>
                                          </div>
                                      
                                  </div>
                              </div>
                          </div>
                        <div class="button-container2">
                          <div v-for="(button, index) in buttons" :key="index" v-show="isVisible(index)" class="chose2" :style="getButtonStyle(index)">
                              <button type="button" data-bs-target="#carouselExample" @click="handleButtonClick(index)" data-bs-slide="next" class="chose" >
                                <a class="underline-btn" href="#">{{ button.label }}</a>
                              </button>
                          </div>
                        </div>
        
                          <div>
                              <button class="btn btn-outline-danger" @click="modalOpen" 
                              style="
                              position: fixed;
                              bottom: 0;
                              margin-top: 690px;
                              width: 1000px;
                              margin-left: -500px;
                              border-top-left-radius: 10px;
                              border-top-right-radius: 10px;
                              border-bottom-left-radius: 10px;
                              border-bottom-right-radius: 10px;
                              border: 1px solid #cac6c6;
                              ">Close</button>
                          </div>
                        </div>
                  </div>
  
                  
  
                  <div class="form_address" id="address" :class="{ 'error': !validAddress }">
                      <img :src="imageData.address" class="addr_icon">
                      <span class="information">주소 정보</span>
                      <input type="text" v-model="zonecode" placeholder="우편번호" class="addrnumber" @blur="validateAddress" >
                      <input type="button" id="postcode" @click="openPostcode" value="검색" class="btn btn-outline-secondary" ><br>
                      <input type="text" v-model="roadAddress" placeholder="지번주소" class="addrnum" @blur="validateAddress">
                      <input type="text" v-model="detailAddress" placeholder="상세주소" class="addradd" @blur="validateAddress">
                      <p v-show="!validAddress" class="input-error" style="margin-left: 30px; margin-top: 30px;">∙ 주소를 입력해 주세요.</p>
                  </div>
                  <div hidden>
                      <input type="text" v-model="x" placeholder="x좌표">
                      <input type="text" v-model="y" placeholder="y좌표">
                  </div>
                  <div class="join">
                  <button type="button" class="btn btn-outline-secondary" @click="joinMember">join</button>
                </div>
          </div>
      </div>
  </body>
  <input type="file" ref="fileInput" style="display: none" @change="handleFileUpload">

</template>


<script>
import axios from 'axios'


export default {
  name: 'app',
  components: {

  },

  data() {
      return {
          mbtimodal: 'mbtimodal',
          pcfile: 'pcfile-class',
          closs: 'closs-class',
          use: 'ues-class',
          modal: 'modal-class',
          bttn: 'custom-class',
          passwordsuccess:false,
          passwordsuccess2:false,
          PhoneNumbersuccess:false,
          validUsernamesuccess:false,
          validNamesuccess:false,
          validMBTIsuccess:false,
          modalCheck: false,
          loading: false,
          showModal: false,
          validUsername: true,
          validPassword: true,
          validName: true,
          validPasswordConfirmation: true,
          validPhoneNumber:true,
          timehidden:false,
          validSelect: true,
          validMBTI:true,
          validAddress:true,
          MBTIerrorMessage:"",
          timer: null,
          remainingTime: 180,
          verificationCodeSent: false,
          showVerification: false,
          selectedGenders: null,
          image_url: null,
          specialValue: [],
          mbtiString:'',
          customMBTI: "",
          phoneNumber: '', // 휴대폰 번호를 저장할 변수
          selecterrorMessage:'',
          x:'',
          y:'',
          prompt: '',
          messages: '',
          zonecode: '',
          roadAddress: '',
          detailAddress: '',
          codeMessage: '',
          logo: '',
          name: '',
          username: '',
          password: '',
          passwordConfirm: '',
          selectErrorMessage: "남자 또는 여자를 선택하세요.",
          verificationResultMessage: '', // 인증 결과 메시지
          verificationResultMessagesuccess:'',
          serverVerificationCode: '',
          verificationCode: '',
          imageSize: '250px',
          phoneNumberErrorMessage: '',
          nameErrorMessage: '',
          passwordErrorMessage: '',
          valpasswordErrorMessage: '',
          usernameErrorMessage: '',
          selectedGender: '', // 남자 또는 여자 선택
          selectedNationality: '',
          profileImage:'',
          buttons: [
              { label: '시작하려면 여기' },
              { label: '역시 집이 짱이지(click)' },
              { label: '역시 밖이 짱이야(click)' },
              { label: '하..언제까지 기다려?(click)' },
              { label: '추락하면 누워야 하나?(click)' },
              { label: '보험 얼마까지 될까?(click)' },
              { label: '심하게 다친건 아니겠지?(click)' },
              { label: '계획은 있어야 나가지~(click)' },
              { label: '아 몰라! 나가서 생각하자(click)' },
          ], 
          visibleIndexes: [0, 1],
          currentGroupIndex: 0,
          mbtidata: {
              problem0: require('@/assets/mbti/problem0-img.png'),
              problem1: require('@/assets/mbti/problem1-img.png'),
              problem2: require('@/assets/mbti/problem2-img.png'),
              problem3: require('@/assets/mbti/problem3-img.png'),
          },

          imageData: {
              id: require('@/assets/person-icon.png'),
              lock: require('@/assets/lock-icon.png'),
              address: require('@/assets/addr-icon.png'),
              search: require('@/assets/search-icon.png'),
              email: require('@/assets/email-icon.png'),
              logo: require('@/assets/Here where.png'),
              poto: require('@/assets/poto.png'),
              },
          };
      },
      watch: {
        selectedGenders() {
          this.validateGender();
        }
      },
      
        
        methods: {
          startTimer() {
              if (!this.timer) {
                this.timer = setInterval(() => {
                  this.remainingTime -= 1;

                  if (this.remainingTime === 0) {
                    this.stopTimer();
                  }
                }, 1000);
              }
            },
            stopTimer() {
              clearInterval(this.timer);
              this.timer = null;
              console.log('3분이 지났습니다. 다시 인증 코드를 요청하세요.');
            },
            formatTime(seconds) {
              const minutes = Math.floor(seconds / 60);
              const remainingSeconds = seconds % 60;
              return `${this.pad(minutes)}:${this.pad(remainingSeconds)}`;
            },
            pad(value) {
              return value < 10 ? `0${value}` : value;
            },

          beforeDestroy() {
            this.stopTimer();
          },

          validateAddress() {
            // 주소 유효성 검사 로직 추가
            this.validAddress = !!this.zonecode && !!this.roadAddress && !!this.detailAddress;
            return this.validAddress;
          },
        
    

          validateMBTI() {
            if (this.customMBTI.trim() === "") {
              this.validMBTI = false;
              this.MBTIerrorMessage = "※ MBTI는 필수 입력 항목입니다.";
              console.log(this.MBTIerrorMessage)
            } else {
              const validMBTIPattern = /^[IESNTFJP]{4}$/; // I, E, S, N, T, F, J, P 중 4글자
              if (!validMBTIPattern.test(this.customMBTI.toUpperCase())) {
                this.validMBTI = false;
                this.MBTIerrorMessage = "※ 올바른 MBTI 형식이 아닙니다. (예: I, E, S, N, T, F, J, P 중 4글자)";
                console.log(this.MBTIerrorMessage)
              } else {
                this.validMBTI = true;
                this.MBTIerrorMessage = ""; // 유효성 검사 통과 시 메시지 초기화
                this.validMBTIsuccess = this.validMBTI
              }
            }
          },

          validateGender() {
            this.validselect = this.selectedGenders !== null;
          },
          
          joinMember(){
            const requiredFields = [
            this.username,
            this.password,
            this.passwordConfirm,
            this.name,
            this.phoneNumber,
            this.selectedGender,
            this.customMBTI
            ];
            const emptyFields = [
            !this.username,
            !this.password,
            !this.passwordConfirm,
            !this.name,
            !this.phoneNumber,
            !this.selectedGender,
            !this.customMBTI
            // 추가 필드가 있다면 여기에 계속 추가
          ];
          
            if (requiredFields.every(field => field) && !emptyFields.some(field => field)) {
          // 유효성 통과: 회원가입 수행
          const formData = new FormData();
          formData.append('id', this.username);
          formData.append('pwd', this.password);
          formData.append('name', this.name);
          formData.append('gender', this.selectedGender);
          formData.append('mbti', this.customMBTI);
          formData.append('tel', this.phoneNumber);
          formData.append('address', this.roadAddress+' '+this.detailAddress);
          formData.append('profileImage', this.profileImage);
          formData.append('lat', this.x);
          formData.append('lng', this.y);
          axios.post(process.env.VUE_APP_API_URL+'/joinMember',formData)
          .then(res => {
            console.log(res);
          })
          .catch(err => console.log(err));
          this.joinMembering();
            }else {
              // 유효성 미통과: 해당 지점에 대한 간략한 설명을 표시
              alert('회원가입에 필요한 정보를 올바르게 입력해주세요.');
          }
        },
            joinMembering() {
            // 회원가입 로직 수행
            // ...
            alert('회원가입이 완료되었습니다!');
          },

          custominput(){
            this.customMBTI = this.mbtiString
            return this.modalOpen;
          },

          getButtonStyle(index) {
            if (index === 0) {
              return {
                // 0번째 버튼에 적용될 스타일
                margin: '200px', // 원하는 스타일을 여기에 추가
              };
            } else {
              return {}; // 다른 버튼들에는 별도의 스타일을 적용하지 않음
            }
          },
          handleButtonClick(index) {
            // 버튼의 label에 따라 특정 값을 설정
            switch (this.buttons[index].label) {
              case '역시 집이 짱이지(click)':
                this.specialValue.push('I');
                console.log( this.specialValue)
                break;
              case '역시 밖이 짱이야(click)':
                this.specialValue.push('E');
                console.log( this.specialValue)
                break;
              case '하..언제까지 기다려?(click)':
                this.specialValue.push('S');
                console.log( this.specialValue)
                break;
              case '추락하면 누워야 하나?(click)':
                this.specialValue.push('N');
                console.log( this.specialValue)
                break;
              case '보험 얼마까지 될까?(click)':
                this.specialValue.push('T');
                console.log( this.specialValue)
                break;
              case '심하게 다친건 아니겠지?(click)':
                this.specialValue.push('F');
                console.log( this.specialValue)
                break;
              case '계획은 있어야 나가지~(click)':
                this.specialValue.push('J');
                console.log( this.specialValue)
                break;
              case '아 몰라! 나가서 생각하자(click)':
                this.specialValue.push('P');
                console.log( this.specialValue)
                break;
              default:
                break;
            }
            this.mbtiString = this.specialValue.join('');
            console.log(this.mbtiString);
            // 클릭된 버튼에 따라 현재 보이는 그룹을 전환
            if (this.currentGroupIndex === 0 && (index === 0)) {
              this.currentGroupIndex = 1;
            } else if (this.currentGroupIndex === 1 && (index === 1 || index === 2)) {
              this.currentGroupIndex = 2;
            } else if (this.currentGroupIndex === 2 && (index === 3 || index === 4)) {
              this.currentGroupIndex = 3;
            } else if (this.currentGroupIndex === 3 && (index === 5 || index === 6)) {
              this.currentGroupIndex = 4;
            }else if (this.currentGroupIndex === 4 && (index === 7 || index === 8)) {
              this.currentGroupIndex = 5;
            }

    
  },
  isVisible(index) {
    // 현재 버튼이 속한 그룹이 currentGroupIndex와 일치하면 보여줌
    if (this.currentGroupIndex === 0) {
      return index === 0 ;
    } else if (this.currentGroupIndex === 1) {
      return index === 1 || index === 2;
    } else if (this.currentGroupIndex === 2) {
      return index === 3 || index === 4;
    } else if (this.currentGroupIndex === 3) {
      return index === 5 || index === 6;
    }else if (this.currentGroupIndex === 4) {
      return index === 7 || index === 8;
    }
  },
      images_gpt() {
          this.loading = true;
          // Get the text from the textarea
          const text = document.getElementById('message-text').value;

          // Check if there's any text
          if (!text) {
              console.error('부적절한 단어임');
              return;
          }

          // Send the text to your server for image generation
          const form = new FormData();
          form.append('prompt', text);

          axios.post(process.env.VUE_APP_PYTHON_API_URL+'/images_gpt', form, {
                  headers: {
                      'Content-Type': 'application/json',
                  },
                  responseType: 'arraybuffer',
              })
              .then(response => {
                  console.log(response.data);
                  if (response.data instanceof ArrayBuffer) {
                  //this.imageData.poto=response.data;
                  const base64Image = btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''));
                  this.image_url = `data:image/jpeg;base64,${base64Image}`;
                  
                  
                  // if (response.data && response.data.image_url) {
                    //     console.log('이미지 결과:', response.data);
                    //     this.image_url = response.data.image_url;
                    //     this.loading = false
                    // } else {
                      //     console.error('이미지 생성 실패');
                      // }
                      const blob = new Blob([response.data], { type: 'image/jpeg' });

                    // Create File from Blob
                  const file = new File([blob], `${text}.jpg`, { type: 'image/jpeg' });

                  console.log(file);
                  this.profileImage = file;
                    } else {
                    console.error('이미지 생성 실패');
                    }

                    this.loading = false;
                    })
              .catch(err => console.log(err));
      },

      openPostcode() {
          new window.daum.Postcode({
              oncomplete: (data) => {
              console.log(data);
              this.zonecode = data.zonecode;
              console.log(this.zonecode);
              this.roadAddress = data.roadAddress;
              console.log(this.roadAddress);

              // 주소 정보를 받은 후에 axios.post 요청을 보내도록 함
              const kakao = new FormData();
              kakao.append('address', this.roadAddress);

              axios.post(process.env.VUE_APP_PYTHON_API_URL+'/kakao', kakao, {
                  headers: {
                  'Content-Type': 'application/json',
                  },
              })
              .then(response => {
                  if (response.data.error) {
                      console.log('오류:', response.data.error);
                  }else{
                      this.x = response.data.x;
                      console.log(this.x);
                      this.y = response.data.y;
                      console.log(this.y);
                  }
                  
              })
              .catch(err => console.log(err));
              }
          }).open();
      },

      sendVerificationCode() {
          if (this.validPhoneNumber) {
            this.remainingTime = 180;
            this.stopTimer();
              const data = {
                  to: this.phoneNumber,
              };

              axios.post(process.env.VUE_APP_PYTHON_API_URL+'/coolsms', data, {
                      headers: {
                          'Content-Type': 'application/json',
                      },
                  })
                  .then(response => {
                      if (response.data) {
                          this.serverVerificationCode = response.data.random_number;
                          this.codeMessage = '발송되었습니다.';
                          this.showVerification = true;
                          this.timehidden = true;
                          console.log(this.random_number)
                          console.log(this.serverVerificationCode)
                          this.remainingTime= 180;
                          this.startTimer();
                      }
                  })
                  .catch(error => {
                      console.error('Error sending SMS code:', error);
                  });
          } else {
              console.error('Invalid phone number');
          }
          return 
      },


      randomCode() {
          // 입력한 인증 번호와 서버에서 받은 인증 번호 비교
          if (this.verificationCode === this.serverVerificationCode) {
              this.verificationResultMessage = '인증 성공!';
              this.stopTimer()
          } else {
              this.verificationResultMessagesuccess = '인증 실패. 올바른 인증 번호를 입력하세요.';
          }
      },

      validateUsername() {
          // 아이디 유효성 검사 로직 추가
          this.validUsername=false;
          const koreanPattern = /^[a-zA-Z0-9_-]{5,20}$/;
          //아이디: 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다
          if (!this.username) {
            this.usernameErrorMessage = '※ 아이디를 입력은 필수입니다.';
          } else if (!koreanPattern.test(this.username)) {
            this.usernameErrorMessage = '아이디: 5~20자의 영문, 숫자와 특수기호(_),(-)만 사용 가능합니다';
          } else {
            const id = this.username;
            axios.get(process.env.VUE_APP_API_URL+'/findById', {
                params: {
                    id: id
                }
            })
            .then(res => {
              console.log(res.data);
              if(res.data){
              this.usernameErrorMessage ='∙ 사용 가능합니다.';
              console.log(this.validUsernamesuccess)
              this.validUsernamesuccess= this.validUsername=true;
              }
              else {
              this.usernameErrorMessage = '∙ 중복된 아이디 입니다. ';
              }
            })
            .catch(err => {
              console.error(err);
            });
          }
        //     const form = new FormData();
        //     form.append('username', this.username);
        //     axios.post('http://127.0.0.1:5000/duplicate', form)
        //     .then(response => {
              
        //       if (response.data)
        //       this.usernameErrorMessage = '∙ 중복된 아이디';
        //     else {
        //       this.usernameErrorMessage = ' ';
        //       console.log(this.validUsernamesuccess)
        //       this.validUsernamesuccess= this.validUsername=true;
              
              
              
        //       return;
        //     }
        //   })
        //   .catch(err => console.log(err));
        // }
        
          
      },



      validatePassword() {
          // 비밀번호 유효성 검사 로직 추가
          console.log('Password:', this.password);
          if (!this.password) {
              this.validPassword = false;
              this.passwordErrorMessage = '※ 비밀번호를 입력은 필수입니다.';
          } else {
              let errorMessage = '';

              if (this.password.trim() === '') {
                  errorMessage = '비밀번호를 입력하세요.';
              } else if (this.password.length < 4) {
                  errorMessage = '∙ 비밀번호는 최소 4자 이상이어야 합니다.';
              }

              this.validPassword = errorMessage === '';
              this.passwordErrorMessage = errorMessage;
              
              this.passwordsuccess= this.validPassword;
              console.log(this.passwordsuccess)
          }
      },
      validatePasswordConfirmation() {
          if (!this.passwordConfirm) {
              this.validPasswordConfirmation = false;
              this.valpasswordErrorMessage = '※ 비밀번호를 다시 입력은 필수입니다.';
          } else {
              this.validPasswordConfirmation = this.password === this.passwordConfirm;
              this.valpasswordErrorMessage = this.validPasswordConfirmation ? '' : '∙ 비밀번호가 일치하지 않습니다.';
          }
          this.passwordsuccess2= this.validPassword;
          console.log(this.passwordsuccess2)
      },

      validateName() {
          if (!this.name) {
              this.validName = false;
              this.nameErrorMessage = '※ 닉네임을 입력은 필수입니다.';
          } else {
              let errorMessage = '';

              if (this.name.trim() === '') {
                  errorMessage = '∙ 닉네임을 입력하세요.';
              } else if (this.name.length < 2) {
                  errorMessage = '∙ 닉네임은 최소 2자 이상이어야 합니다.';
              } else if (/[0-9]/.test(this.name)) {
                  errorMessage = '∙ 닉네임에 숫자를 사용할 수 없습니다.';
              } else if (!/^[가-힣a-zA-Z]+$/.test(this.name)) {
                  errorMessage = '∙ 닉네임에는 한글과 영어만 사용 가능합니다.';
              }

              this.validName = errorMessage === '';
              this.nameErrorMessage = errorMessage;
              this.validNamesuccess = this.validName;
          }
      },

      validatePhoneNumber() {
          if (!this.phoneNumber) {
              // 휴대폰 번호가 비어있을 때
              // 여기에서 필요한 유효성 검사를 추가할 수 있습니다.
              // 현재는 비어있는 경우 에러 메시지만 표시합니다.
              this.validPhoneNumber = false;
              this.phoneNumberErrorMessage = '※ 전화번호를 입력은 필수입니다.';
          } else {
              // 유효성 검사 로직을 추가할 수 있습니다.
              let errorMessage = '';

              if (this.phoneNumber.trim() === '') {
                  errorMessage = '∙ 전화번호를 입력하세요.';
              } else if (!/^[0-9]{10,11}$/.test(this.phoneNumber)) {
                  errorMessage = '∙ 올바른 전화번호 형식이 아닙니다.';
              }

              this.validPhoneNumber = errorMessage === '';
              this.phoneNumberErrorMessage = errorMessage;
          }
          this.PhoneNumbersuccess = this.phoneNumber
      },

      handleFileUpload(event) {
          // 파일 업로드 처리
          const input = event.target;
          const files = input.files;


          if (files && files.length > 0) {
              // 선택한 파일 정보 업데이트
              const file = files[0];
              this.profileImage = file;
              const reader = new FileReader();

              // FileReader의 onload 이벤트 핸들러 내부에서 imageData.poto를 직접 업데이트
              reader.onload = () => {
                  // imageData.poto를 이미지 데이터 URL로 업데이트
                  this.imageData.poto = reader.result;
                  console.log('dd',this.imageData.poto);
                  // 이미지 크기를 동적으로 설정
              };

              // 파일을 읽어옵니다.
              reader.readAsDataURL(file);
          }else{
            console.log('No file selected');
            // 데이터 값을 0으로 설정
            this.profileImage = 0;
            
          }
      },
      openFileInput() {
          // 파일 업로드 input 요소를 클릭합니다.
          this.$refs.fileInput.click();
      },
      handleImageClick() {

      },
      useProfile() {
          if (this.image_url) {
              console.log('image_url:', this.image_url);
              this.imageData.poto = this.image_url;
              this.imageSize = '250px';


          }
      },
      modalOpen() {
          this.modalCheck = !this.modalCheck
          document.querySelector(".modal-wrap").style.zIndex = 9999;
      },
  },
};
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #3975b1;
  margin-top: 60px;
  min-height: 100%;
  position: relative;
  padding-bottom: 160px;
}
.container {
  background: #ffffff;
  display: inline-block;
  width: 1000px;
  height: 1300px;
}

.a1 {
  background: #ffffff;

}



.logo {
  height: 5%;
  width: 20%;
  margin-bottom: 50px;
  /* border: 1px solid red; */
}

.logo_icon {
  height: 150px;
  width: 150px;
  margin-top: 10px;
  /* border: 1px solid red; */
}

.form_list .input {
  font-style: oblique;
  box-sizing: border-box;
  width: 500px;
  font-size: 16px;
  line-height: 40px;
  align-items: center;
  position: relative;
  box-sizing: border-box;
  max-width: 100%;
  min-height: 50px;
  padding: 0 140px 0 50px;
  border: none;
  border: 1px solid #858383;
  transition: border-color 0.2s;
  margin-top: 20px;
  /* 위쪽 간격 조절 */
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
}

.form_gender {
  position: relative;
  width: 540px;
  height: 43px;
  left: 50.05%;
  margin-top: 6px;
  margin-bottom: 0;
  transform: translate(-50%, -10%);
  box-sizing: border-box;
}




.input:focus,
.test:focus,
.addrnumber:focus,
.addrnum:focus,
.addradd:focus {
  outline: none;
  border: 2px solid #4CAF50;
  border-color: #4CAF50;
  /* 초록색 테두리로 변경 */
} 


.id_icon,
.name_icon,
.birthday_icon,
.lock_icon,
.address_icon,
.email_icon {
  /* border: 1px solid red; */
  margin-left: 20px;
  z-index: 1;
  display: flex;
  position: absolute;
  margin-top: 35px;
  display: inline-block;
}

.search_icon {
  /* border: 1px solid red; */
  margin-left: 405px;
  z-index: 1;
  display: flex;
  position: absolute;
  margin-top: 25px;
  width: 22px;
  height: 22px;
}

.form_list {
  margin-top: 10px;
}

.btn_show {
  position: absolute;
  height: 4%;
  width: 30px;
  padding: 0 10px;
  border: none;
  border-top-right-radius: 10px;
  cursor: pointer;
  margin-left: -40px;
  margin-top: 60px;
  overflow: hidden;
  z-index: 1000;
  visibility: hidden;
}

.list_item {
  box-sizing: border-box;
  width: 30%;
  padding-left: 10px;
}

.select {
  padding: 10px 70px;
  margin-right: 150px;
  position: fixed;
}

.select input[type=radio] {
  display: none;
  position: relative;
}

.select input[type=radio]+label {
  display: inline-block;
  cursor: pointer;
  height: 40px;
  width: 120px;
  border: 1px solid #858383;
  line-height: 35px;
  text-align: center;
  font-weight: bold;
  font-size: 15px;
  top: 0px;
  /* 선을 고정할 위치의 상단 여백 설정 */
  left: 1px;
}

.select input[type=radio]+label {
  background-color: #fff;
  color: #333;
  margin-right: -1px;
  position: relative;
  /* border-top-right-radius: 20px; */
}

.select input[type=radio]:checked+label {
  background-color: #212529;
  color: #fff;
}


.gender1[type=radio]+label {
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  margin-left: -50px;
}

.gender2[type=radio]+label {
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
}


.btn {
  position: relative;
  top: 40px;
  width: 500px;
  height: 70px;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
}

.join {
  position: relative;
  display: inline-block;
  border-bottom: 1px solid #858383;
  height: 0px;
  margin-top: 30px;
  width: 500px;
  margin-left: 10px;
}




  /* 초록색 테두리 정의 */
  .form_pwd1.success .input,
  .form_pwd2.success .input,
  .form_phone.success .input,
  .form_id.success .input, 
  .form_name.success .input,
  .form_MBTI.success .test{
  
      border: 2px solid #4caf50; /* 초록색 테두리 */
  }
  .form_pwd1 .input-success,
  .form_pwd2 .input-success,
  .form_phone .input-success,
  .form_id .input-success,
  .form_name .input-success
   {
      color: #4caf50; /* 초록색 텍스트 */
      font-size: 12px;
      display: block;
      margin-bottom: -10px;
      margin-top: 6px;
      font-size: 12px;
      text-align: left;
      margin-left: 250px;
  }
  .form_MBTI .input-success{
      color: #4caf50; /* 초록색 텍스트 */
      font-size: 12px;
      display: block;
      margin-bottom: -10px;
      margin-top: 6px;
      font-size: 12px;
      text-align: center;
      margin-left: 160px;
  }
.input-error {
  display: block;
  color: red;
  margin-bottom: -10px;
  margin-top: 6px;
  font-size: 12px;
  text-align: left;
  /* border: 1px solid red; */
  margin-left: 250px;
}

.form_id.error .input,
.form_pwd1.error .input,
.form_pwd2.error .input,
.form_pwdConfirm.error .input,
.form_name.error .input,
.form_phone.error .input,
.form_MBTI.error .test,
.form_address.error .addradd,
.form_address.error .addrnum,
.form_address.error .addrnumber {
  border: 2px solid red;
}

.image_poto {
  display: flex;
  /* border: 1px solid red; */
  font-size: 12px;
  margin-left: 0;
  height: 30px;
  z-index: 100;
}

.rounded-image {
  border-radius: 25%;
  margin-left: -230px;
  margin-bottom:0px;
  margin-top: 50px;
}

.test {
  width: 110px;
  height: 39.3px;
  margin-left: 152px;
  margin-top: 11.3px;
  border: 1px solid#858383;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  position: relative;
  text-align: center;
  z-index: 10;
}


.large-select {
  width: 100px;
  /* 셀렉트 박스의 폭을 조절 */
  height: 29px;
  /* 셀렉트 박스의 높이를 조절 */
  border: 1px solid#858383;
  border-top-right-radius: 7px;
  border-bottom-right-radius: 7px;
}

.form_address .btn {
position: relative;
width: 50px;
height: 35px;
font-size: 12px;
margin-top: -83px;
left: -245px;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
  border-bottom-right-radius: 5px;
  border-bottom-left-radius: 5px;
  border: 1px solid#858383;
}

.addrnumber {
  margin-right: 249px;
  margin-top: 20px;
  width: 150px;
  border: 1px solid#858383;
  height: 35px;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
  border-bottom-right-radius: 5px;
  border-bottom-left-radius: 5px;
}

.addrnum {
  position: absolute;
  width: 400px;
  margin-top: 13px;
  margin-right: 0px;
  border: 1px solid#858383;
  height: 35px;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
  border-bottom-right-radius: 5px;
  border-bottom-left-radius: 5px;
}

.addradd {
    width: 250px;
    margin-top: 60px;
    margin-right: 200px;
    border: 1px solid#858383;
    height: 35px;
    border-top-right-radius: 5px;
  border-top-left-radius: 5px;
  border-bottom-right-radius: 5px;
  border-bottom-left-radius: 5px;
}

.form_address {
  border: 1px solid#858383;
  height: 200px;
  margin-top: 15px;
  width: 500px;
  display: inline-block;
  border-top-right-radius: 15px;
  border-top-left-radius: 15px;
  border-bottom-right-radius: 15px;
  border-bottom-left-radius: 15px;
}

.information {
    position: relative;
    width: 500px;
    margin-right: 350px;
    font-size: 17px;
    font-style: oblique;
    color: #8a8989;
    padding-left: 20px;
    top: 7px;
}

.addr_icon {
  position: absolute;
  width: 16px;
  height: 16px;
  margin-top: 10px;
  margin-left: 18px;
}



.form_phone {
  position: relative;
  margin-bottom: 20px;
}

.custom-class {
  position: absolute;
  display: inline-block;
  margin-top: -12.5px;
  margin-left: -70px;
  font-size: 8px;
  width: 60px;
  height: 35px;
  border: 1px solid#858383;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.checkcode {
  margin-top: 20px;
  margin-right: 320px;
  width: 150px;
  font-style: oblique;
  /* normal,italic,oblique =폰트 종류 */
  box-sizing: border-box;
  font-size: 16px;
  line-height: 30px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  border: 1px solid#858383;
  max-width: 100%;
}

.confirm-btn {
  position: absolute;
  display: inline-block;
  width: 65px;
  height: 35px;
  font-size: 10px;
  border: 1px solid#858383;
  margin-top: 30px;
  margin-left: 220px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  margin-left: -315px;
  margin-top: 63px;
}

.modal-class {
  position: absolute;
  display: inline-block;
  width: 100px;
  height: 50px;
  font-size: 12px;
  top: 280px;
  margin-left: 51px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.closs-class {
  width: 100px;
  height: 40px;
  font-size: 10px;
  top: 0;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.ues-class {
  width: 100px;
  height: 40px;
  font-size: 12px;
  top: 0;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.pcfile-class {
  width: 100px;
  height: 50px;
  font-size: 12px;
  top: 0;
  margin-left: 50px;
  position:absolute;
  display: inline-block;
  /*z-index: 9990;*/
  margin-top: 210px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.mbtimodal {
  width: 117px;
  height: 40px;
  font-size: 13px;
  top: 0;
  margin-top: -59px;
  margin-left: 379px;
  border-top-left-radius: 0px;
  border-top-right-radius: 15px;
  border-bottom-left-radius: 0px;
  border-bottom-right-radius: 15px;
}

.white-bg {
  position: relative;
  width: 100%;
  background: white;
  border-radius: 8px;
  padding: 20px;
}

/* dimmed */

.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
}

/* modal or popup */

.modal-container {
  position: relative;
  top: 45%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 1000px;
  height: 800px;
  background: #fffbfb;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}

.title {
  position: fixed;
  display: flex;
  top: 30px;
  font-size: 20px;
  border-bottom: 1px solid rgb(83, 77, 77);
}
.titlemain{

margin-top: 50px;
margin-left: 150px;
position: center;
width: 420px;

}


.title1 {
  position: center;
  margin-top: 50px;
  height: 60px;
  font-weight: bold;
  margin-left: -250px;
}

.active {
  /* border: 1px solid red; */
  position: relative;
  margin-top: 0px;
  margin-left: 120px;
}

.underline-btn {
  display: inline-block;
  padding: 1em 0;
  border-radius: 0;
  color: #ffffff;
  font-weight: bold;
  font-size: 0.678rem;
  letter-spacing: 2px;
  text-transform: uppercase;
  text-decoration: none;
  position: relative;
  top:-6px;
  &:before,
  &:after {
      content: '';
      display: block;
      position: absolute;
      height: 1px;
      width: 0;
  }
  &:before {
      transition: width 0s ease, background .4s ease;
      left: 0;
      right: 0;
      bottom: 6px;
  }
  &:after {
      right: 2.2%;
      bottom: 6px;
      background: #ffffff;
      transition: width .4s ease;
  }
  &:hover {
      &:before {
          width: 97.8%;
          background: #fcfcfc;
          transition: width .4s ease;
      }
      &:after {
          width: 97.8%;
          background: 0 0;
          transition: all 0s ease;
      }
  }
}

.chose {
  margin-left: 100px;
  margin-right: 100px;
  width: 200px;
  height: 60px;
  margin-top:20px;
  border: 1px solid rgb(204, 206, 202);
  background-color: #000000;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}
.button-container2{
display: flex;      /* Flex Container로 설정 */
justify-content: space-between;  /* 가로 방향으로 공간을 균등하게 배분 */
width: 700px;
margin-left: 70px;
}
/* mbti 버튼 스타일 --------------------------------------------------*/





.outer {
position: relative;
margin: auto;
width: 300px;
height: 300px;
margin-left: 200px;
margin-top: 100px;
cursor: pointer;
}

.inner {
width: inherit;
text-align: center;
}

.label2 { 
font-size: 4.8em; 
line-height: 4em;
text-transform: uppercase;
color: #000000;
transition: all .3s ease-in;
opacity: 0;
cursor: pointer;
}

.inner:before, .inner:after {
position: absolute;
content: '';
height: 1px;
width: inherit;
background: #869c21;
left: 0;
transition: all .3s ease-in;
}

.inner:before {
top: 50%; 
transform: rotate(45deg);  
}

.inner:after {  
bottom: 50%;
transform: rotate(-45deg);  
}

.outer:hover label {
opacity: 1;
}

.outer:hover .inner:before,
.outer:hover .inner:after {
transform: rotate(0);
}

.outer:hover .inner:before {
top: 0;
}

.outer:hover .inner:after {
bottom: 0;
}
.modal-body{
  position: relative;
}
.fade{
 top: 150px;
 z-index: 9999;
}
.profill{
  position: absolute;
  display: inline-block;
  margin-top: 15px;
  margin-left: -200px;
}




/* mbti 버튼 스타일 -----------------------------------------여기까지---------*/

</style>
