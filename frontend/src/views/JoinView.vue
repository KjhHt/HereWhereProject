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
                                              <div class="spinner-border" style="width: 3rem; height: 3rem;" role="status" v-if="loading">
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
                          <div class="modal-content">
                              <div class="modal-header">
                                  <h5 class="modal-title">프로필 이미지 AI생성</h5>
                                  
                              </div>
                              <div class="modal-body">
                                  <p style="width: 500px; height: 100px; top: 40px;">프로필 이미지가 적용되었습니다.</p>
                              </div>
                              <div class="modal-footer">
                                  <button type="button" class="btn btn-outline-info" :class="use" data-bs-dismiss="modal">Close</button>
                              </div>
                          </div>
                      </div>
                  </div>
                  <!-- 숨겨진 파일 입력 창 -->
  
                  <div class="form_id" :class="{ 'error': !validUsername }">
  
                      <img :src="imageData.id" class="id_icon">
                      <input type="text" class="input" id="id" name="id" placeholder="아이디" autocomplete="off" v-model="username" @blur="validateUsername">
                      <p v-show="!validUsername" class="input-error">{{ usernameErrorMessage }}</p>
                  </div>
  
                  <div class="form_pwd1" id="pwd" :class="{ 'error': !validPassword }">
                      <img :src="imageData.lock" class="lock_icon">
                      <input type="password" class="input pwd" id="pwd" name="pwd" placeholder="비밀번호" autocomplete="off" v-model="password" @blur="validatePassword">
                      <p v-show="!validPassword" class="input-error">{{ passwordErrorMessage }}</p>
                  </div>
                  <div class="form_pwd2" id="pwd" :class="{ 'error': !validPasswordConfirmation }">
                      <img :src="imageData.lock" class="lock_icon">
                      <input type="password" class="input" id="pwdConfirm" name="pwdConfirm" placeholder="비밀번호 재확인" autocomplete="off" v-model="passwordConfirm" @blur="validatePasswordConfirmation">
                      <p v-show="!validPasswordConfirmation" class="input-error">{{ valpasswordErrorMessage }}</p>
                  </div>
              </div>
              <div class="form_list">
                  <div class="form_name" :class="{ 'error': !validName }">
                      <img :src="imageData.id" class="name_icon">
                      <input type="text" class="input" id="name" name="name" placeholder="닉네임" autocomplete="off" v-model="name" @blur="validateName">
                      <p v-show="!validName" class="input-error">{{ nameErrorMessage }}</p>
                  </div>
                  <div class="form_gender" id="gender">
                      <div class="select">
                          <input type="radio" id="select1" name="gender" class="gender1" v-model="selectedGender" value="male"><label for="select1">남자</label>
                          <input type="radio" id="select2" name="gender" class="gender2" v-model="selectedGender" value="female"><label for="select2">여자</label>
                      </div>
                      <div class="form_MBTI" id="MBTI">
                          <!-- 직접 입력을 위한 셀렉트 박스 -->
                          <input type="text" class="test" v-model="customMBTI" placeholder="직접 입력" />
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
                          <div id="carouselExampleFade" class="carousel slide carousel-fade" >
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
                                  <div class="carousel-item" direction="right">
                                      <div class="title1">
                                          <h5>[나에게 쉬는 시간이 생겼을 때~]</h5>
                                      </div>
                                      <img :src="mbtidata.problem0" class="d-block w-75" alt="...">
                                  </div>
                                  <div class="carousel-item" direction="right">
                                      <div class="title1">
                                          <h5>[내가 엘리베이터를 탔을 때!]</h5>
                                      </div>
                                      <img :src="mbtidata.problem1" class="d-block w-75" alt="...">
                                  </div>
                                  <div class="carousel-item" direction="right">
                                      <div class="title1">
                                          <h5>[만약 친구가 차사고가 났다고 연락이 왔을 때?]</h5>
                                      </div>
                                      <img :src="mbtidata.problem2" class="d-block w-75" alt="...">
                                  </div>
                                  <div class="carousel-item" direction="right">
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
                                              <label class="label2" @click="custominput"> {{ mbtiString }} fsdfds</label>
                                            </div>
                                          </div>
                                      
                                  </div>
                              </div>
                          </div>
                        <div class="button-container2">
                          <div v-for="(button, index) in buttons" :key="index" v-show="isVisible(index)" class="chose2" :style="getButtonStyle(index)">
                              <button type="button" data-bs-target="#carouselExampleFade" @click="handleButtonClick(index)" data-bs-slide="next" class="chose" >
                                <a class="underline-btn" href="#">{{ button.label }}</a>
                              </button>
                          </div>
                        </div>
                          <!-- <div hidden>
                              <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next" class="chose"><a class="underline-btn" href="#">하..언제까지 기다려?(click)</a></button>
                              <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next" class="chose"><a class="underline-btn" href="#">추락하면 누워야 하나?(click)</a></button>
                          </div>
                          <div hidden>
                              <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next" class="chose"><a class="underline-btn" href="#">보험 얼마까지 될까?(click)</a></button>
                              <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next" class="chose"><a class="underline-btn" href="#">심하게 다친건 아니겠지?(click)</a></button>
                          </div>
                          <div hidden>
                              <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next" class="chose"><a class="underline-btn" href="#">계획은 있어야 나가지~(click)</a></button>
                              <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next" class="chose"><a class="underline-btn" href="#">아 몰라! 나가서 생각하자(click)</a></button>
                          </div> -->
                          
  
  
                          <div>
                              <button class="btn btn-outline-danger" @click="modalOpen" 
                              style="
                              position: fixed;
                              bottom: 0;
                              margin-top: 700px;
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
  
                  <div class="form_phone" id="phone-container">
                      <img :src="imageData.email" class="email_icon">
                      <input type="text" class="input" id="phone" name="phone" placeholder="휴대폰" autocomplete="off" v-model="phoneNumber" @blur="validatePhoneNumber">
                      <button type="button" class="btn btn-outline-dark" :class="bttn" @click="sendVerificationCode">보내기</button>
                      <p v-show="!phoneNumber" class="input-error">{{ phoneNumberErrorMessage }}</p>
                      <input v-if="showVerification" type="text" class="checkcode" id="verificationCode" name="verificationCode" placeholder="인증 코드" autocomplete="off" v-model="verificationCode">
                      <button v-if="showVerification" type="button" class="btn btn-outline-dark confirm-btn" @click="randomCode">인증 확인</button>
                      <p v-if="verificationResultMessage">{{ verificationResultMessage }}</p>
                  </div>
  
                  <div class="form_address" id="address">
                      <img :src="imageData.address" class="addr_icon">
                      <span class="information">주소 정보</span>
                      <input type="text" v-model="zonecode" placeholder="우편번호" class="addrnumber">
                      <input type="button" id="postcode" @click="openPostcode" value="검색" class="btn btn-outline-secondary"><br>
                      <input type="text" v-model="roadAddress" placeholder="지번주소" class="addrnum">
                      <input type="text" v-model="detailAddress" placeholder="상세주소" class="addradd">
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
          modalCheck: false,
          loading: false,
          showModal: false,
          showVerification: false,
          validUsername: true,
          validPassword: true,
          validName: true,
          validphone: true,
          validPasswordConfirmation: true,
          image_url: null,
          visibleIndex: null, // 초기에는 아무 버튼도 클릭되지 않음
          specialValue: [],
          mbtiString:'',
          customMBTI: '',
          x:'',
          y:'',
          prompt: '',
          messages: '',
          zonecode: '',
          roadAddress: '',
          detailAddress: '',
          phoneNumber: '', // 휴대폰 번호를 저장할 변수
          codeMessage: '',
          logo: '',
          name: '',
          username: '',
          password: '',
          passwordConfirm: '',
          verificationResultMessage: '', // 인증 결과 메시지
          serverVerificationCode: '',
          verificationCode: '',
          imageSize: '170px',
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
        
        methods: {
          joinMember(){
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
          axios.post('http://localhost:8080/joinMember',formData)
          .then(res => {
              console.log(res);
          })
          .catch(err => console.log(err));
          },
          custominput(){
            this.customMBTI = this.mbtiString
            return ;
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

          axios.post('http://127.0.0.1:5000/images_gpt', form, {
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

              axios.post('http://127.0.0.1:5000/kakao', kakao, {
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
              const data = {
                  to: this.phoneNumber,
              };

              axios.post('http://127.0.0.1:5000/coolsms', data, {
                      headers: {
                          'Content-Type': 'application/json',
                      },
                  })
                  .then(response => {
                      if (response.data) {
                          this.serverVerificationCode = response.data.random_number;
                          this.codeMessage = '발송되었음';
                          this.showVerification = true;
                          console.log(this.random_number)
                          console.log(this.serverVerificationCode)
                      }
                  })
                  .catch(error => {
                      console.error('Error sending SMS code:', error);
                  });
          } else {
              console.error('Invalid phone number');
          }
      },


      randomCode() {
          // 입력한 인증 번호와 서버에서 받은 인증 번호 비교
          if (this.verificationCode === this.serverVerificationCode) {
              this.verificationResultMessage = '인증 성공!';
          } else {
              this.verificationResultMessage = '인증 실패. 올바른 인증 번호를 입력하세요.';
          }
      },

      validateUsername() {
          // 아이디 유효성 검사 로직 추가
          const koreanPattern = /^[a-zA-Z0-9_-]{1,20}$/;
          //아이디: 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다
          if (!this.username) {
              this.usernameErrorMessage = '∙ 아이디를 입력해주세요';
          } else if (!koreanPattern.test(this.username)) {
              this.usernameErrorMessage = '아이디: 5~20자의 영문, 숫자와 특수기호(_),(-)만 사용 가능합니다';
          } else {
              const form = new FormData();
              form.append('username', this.username);
              axios.post('http://127.0.0.1:5000/duplicate', form)
                  .then(response => {
                      if (response.data)
                          this.usernameErrorMessage = '∙ 중복된 아이디';
                      else {
                          this.usernameErrorMessage = '∙ 사용 가능한 아이디 입니다.';
                          return;
                      }
                  })
                  .catch(err => console.log(err));
          }
          this.validUsername = false;
      },



      validatePassword() {
          // 비밀번호 유효성 검사 로직 추가
          console.log('Password:', this.password);
          if (!this.password) {
              this.validPassword = false;
              this.passwordErrorMessage = '∙ 비밀번호를 입력하세요.';
          } else {
              let errorMessage = '';

              if (this.password.trim() === '') {
                  errorMessage = '비밀번호를 입력하세요.';
              } else if (this.password.length < 4) {
                  errorMessage = '∙ 비밀번호는 최소 4자 이상이어야 합니다.';
              }

              this.validPassword = errorMessage === '';
              this.passwordErrorMessage = errorMessage;
          }
      },
      validatePasswordConfirmation() {
          if (!this.passwordConfirm) {
              this.validPasswordConfirmation = false;
              this.valpasswordErrorMessage = '∙ 비밀번호를 다시 입력하세요.';
          } else {
              this.validPasswordConfirmation = this.password === this.passwordConfirm;
              this.valpasswordErrorMessage = this.validPasswordConfirmation ? '' : '∙ 비밀번호가 일치하지 않습니다.';
          }
      },

      validateName() {
          if (!this.name) {
              this.validName = false;
              this.nameErrorMessage = '∙ 닉네임을 입력하세요.';
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
          }
      },

      validatePhoneNumber() {
          if (!this.phoneNumber) {
              // 휴대폰 번호가 비어있을 때
              // 여기에서 필요한 유효성 검사를 추가할 수 있습니다.
              // 현재는 비어있는 경우 에러 메시지만 표시합니다.
              this.validPhoneNumber = false;
              this.phoneNumberErrorMessage = '∙ 전화번호를 입력하세요.';
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
              this.imageSize = '150px';


          }
      },
      modalOpen() {
          this.modalCheck = !this.modalCheck
          document.querySelector(".modal-wrap").style.zIndex = 1000;
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
  height: 1400px;
  border-top-left-radius: 25px;
  border-top-right-radius: 25px;
  border-bottom-left-radius: 25px;
  border-bottom-right-radius: 25px;
}

.a1 {
  background: #dbd3d3;

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
  margin-top: 10px;
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

/* .select{
position: relative;
width: 450px;
height: 150px;
background: blue;
} */

.input:focus {
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
  margin-left: 250px;
  z-index: 1;
  display: flex;
  position: absolute;
  margin-top: 25px;
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
  position: fixed;
  top: 0px;
  /* 선을 고정할 위치의 상단 여백 설정 */
  left: 1px;
}

.select input[type=radio]+label {
  background-color: #fff;
  color: #333;
  margin-right: -1px;
  margin-left: 0px;
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
}

.gender2[type=radio]+label {
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
}


.btn {
  position: relative;
  top: 40px;
  width: 450px;
  height: 60px;
  border-top-left-radius: 30px;
  border-top-right-radius: 30px;
  border-bottom-left-radius: 30px;
  border-bottom-right-radius: 30px;
}

.join {
  position: relative;
  display: flex;
  border-bottom: 1px solid #858383;
  height: 0px;
  margin-top: 30px;
  width: 450px;
  margin-left: 30px;
}

.input-error {
  color: red;
  margin-bottom: -5px;
  margin-top: 2px;
  font-size: 12px;
  text-align: left;
  /* border: 1px solid red; */
  margin-left: 60px;
}

.form_id.error .input,
.form_pwd1.error .input,
.form_pwd2.error .input,
.form_pwdConfirm.error .input,
.form_name.error .input,
.form_phone.error .input {
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
  margin-left: 20px
}

.test {
  width: 90px;
  height: 35px;
  margin-left: 100px;
  margin-top: 10px;
  border: 1px solid#858383;
  border-top-left-radius: 7px;
  border-bottom-left-radius: 7px;
  position: relative;
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
height: 31px;
font-size: 12px;
margin-top: -81px;
left: -190px;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
  border-bottom-right-radius: 5px;
  border-bottom-left-radius: 5px;
  border: 1px solid#858383;
}

.addrnumber {
  margin-right: 199px;
  margin-top: 10px;
  width: 150px;
  border: 1px solid#858383;
  height: 30px;
}

.addrnum {
  position: absolute;
  width: 350px;
  margin-top: 13px;
  margin-right: 0px;
  border: 1px solid#858383;
  height: 30px;
}

.addradd {
    width: 200px;
    margin-top: 55px;
    margin-right: 200px;
    border: 1px solid#858383;
    height: 30px;
}

.form_address {
  border: 1px solid#858383;
  height: 200px;
  margin-top: 15px;
  width: 500px;
  margin-left: 230px;
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
  margin-top: 8px;
  margin-left: -8px;
}

.form_name .input {
  margin-top: 0px;
}

.name_icon {
  margin-top: 16px;
}

.form_phone {
  position: relative;
  margin-top:-20px;
  margin-bottom:20px;
  /* 상위 컨테이너에 position: relative; 추가 */
}

.custom-class {
  position: absolute;
  display: block;
  margin-top: -22.5px;
  margin-left: 395px;
  font-size: 10px;
  width: 60px;
  height: 35px;
  border: 1px solid#858383;
}

.checkcode {
  margin-top: 10px;
  margin-right: 230px;
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
  display: block;
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
}

.modal-class {
  width: 100px;
  height: 40px;
  font-size: 10px;
  top: 0;
  margin-bottom: 20px;
  margin-left: 10px;
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
  height: 40px;
  font-size: 10px;
  top: 0;
  margin-bottom: 20px;
  margin-left: 0px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.mbtimodal {
  width: 100px;
  height: 35.2px;
  font-size: 13px;
  top: 0;
  margin-top: -65px;
  margin-left: 288px;
  border-top-left-radius: 0px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 0px;
  border-bottom-right-radius: 10px;
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

* {
padding: 0;
margin: 0;
box-sizing: border-box;
}



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
  position: relative;
}



/* mbti 버튼 스타일 -----------------------------------------여기까지---------*/

</style>
