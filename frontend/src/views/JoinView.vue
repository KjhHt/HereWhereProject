<template>
  <body>
    <div class="container">
    <h1 class="logo">
      <img :src="imageData.logo" class="logo_icon">
    </h1>
    <div class="content">
        <!-- //from
        //input hidden등등 필요시 설정 -->
    </div>
      <div class="from-content">
        <div class="form_list">
          <label for="fileInput">
            <img :src="imageData.poto" class="poto_icon rounded-image" :style="{ width: imageSize, height: imageSize }">
            <span class="image_poto">(선택사항)프로필을 선택하세요</span>
          </label>
          <!-- 숨겨진 파일 입력 창 -->
          <input type="file" id="fileInput" ref="fileInput" style="display: none" @change="handleFileUpload">
          
          <div class="form_id" :class="{ 'error': !validUsername }">
            <img :src="imageData.id" class="id_icon" >
            <input type="text" class="input" id="id" name="id" placeholder="아이디" autocomplete="off" v-model="username"  @blur="validateUsername">
            <p v-show="!validUsername" class="input-error">{{ usernameErrorMessage }}</p>
          </div>
          
          <div class="form_pwd1" id="pwd" :class="{ 'error': !validPassword }">
            <img :src="imageData.lock" class="lock_icon">
            <input type="password" class="input pwd" id="pwd" name="pwd" placeholder="비밀번호" autocomplete="off" v-model="password" @blur="validatePassword">
            <p v-show="!validPassword" class="input-error">{{ passwordErrorMessage }}</p>
          </div>
          <div class="form_pwd2" id="pwd" :class="{ 'error': !validPasswordConfirmation }">
            <img :src="imageData.lock" class="lock_icon">
            <input type="password" class="input" id="pwdConfirm" name="pwdConfirm" placeholder="비밀번호 재확인" autocomplete="off" v-model="passwordConfirm" @blur="validatePasswordConfirmation" >
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
              <input type="radio"  id="select1" name="gender" class="gender1" v-model="selectedGender" value="male"><label for="select1">남자</label>
              <input type="radio"  id="select2"  name="gender" class="gender2" v-model="selectedGender" value="female"><label for="select2">여자</label>
            </div>
            <div class="form_MBTI" id="MBTI">
          <!-- 직접 입력을 위한 셀렉트 박스 -->
              <input type="text" class="test" v-model="customMBTI" placeholder="직접 입력" />
            </div>
            <div class="forms_MBTI" id="choices_MBTI">
              <!-- 오른쪽에 선택 가능한 셀렉트 박스 -->
              <select v-model="selectedRightMBTI" class="large-select" @change="updateSelectedMBTI(selectedRightMBTI)">
                <option value="" disabled selected>MBTI 선택</option>
                <option v-for="option in rightMbtiOptions" :key="option.value" :value="option.value">{{ option.label }}</option>
              </select>
            </div>
          </div>
          
          <div class="form_mail" id="mail" >
            <img :src="imageData.email" class="email_icon">
            <input type="text" class="input" id="phone" name="phone" placeholder="휴대폰" autocomplete="off" v-model="phoneNumber" @blur="validatePhoneNumber">
            <button type="button" class="btn btn-outline-dark" :class="bttn" @click="sendVerificationCode">보내기</button>
            <p v-show="!phoneNumber" class="input-error">{{ phoneNumberErrorMessage }}</p>
            <input v-if="showVerification" type="text" class="checkcode" id="verificationCode" name="verificationCode" placeholder="인증 코드" autocomplete="off" v-model="verificationCode">
            <button v-if="showVerification" type="button" class="btn btn-outline-dark confirm-btn"  @click="randomCode">인증 확인</button>
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
          <div class="join"></div>
          <button type="button" class="btn btn-outline-secondary" @click="clickSearchIcon">join</button>
          
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
      bttn: 'custom-class',
      showVerification: false,
      zonecode: '',
      roadAddress:'',
      detailAddress:'',
      phoneNumber: '', // 휴대폰 번호를 저장할 변수
      codeMessage: '',
      logo:'',
      name:'',
      username:'',
      password:'',
      passwordConfirm:'',
      customMBTI: '',
      selectedRightMBTI: '',
      verificationResultMessage: '',  // 인증 결과 메시지
      serverVerificationCode: '',
      verificationCode: '',
      imageSize: '150px',
      phoneNumberErrorMessage:'',
      nameErrorMessage:'',
      passwordErrorMessage:'',
      valpasswordErrorMessage:'',
      usernameErrorMessage: '',
      validUsername: true,
      validPassword: true,
      validName: true,
      validphone: true,
      validPasswordConfirmation: true, 
      selectedGender: '',  // 남자 또는 여자 선택
      selectedNationality: '',  // 내국인 또는 외국인 선택
      
      imageData: {
        id: require('@/assets/person-icon.png'),
        lock: require('@/assets/lock-icon.png'),
        mbti: require('@/assets/mbti-icon.png'),
        address: require('@/assets/addr-icon.png'),
        search: require('@/assets/search-icon.png'),
        email: require('@/assets/email-icon.png'),
        logo: require('@/assets/Here where.png'),
        poto: require('@/assets/poto.png'),
      },
      rightMbtiOptions: [
        { label: '직접 입력', value: '직접 입력' },
        { label: 'ISTJ', value: 'ISTJ' },
        { label: 'ISFJ', value: 'ISFJ' },
        { label: 'INFJ', value: 'INFJ' },
        { label: 'INTJ', value: 'INTJ' },
        { label: 'ISTP', value: 'ISTP' },
        { label: 'ISFP', value: 'ISFP' },
        { label: 'INFP', value: 'INFP' },
        { label: 'INTP', value: 'INTP' },
        { label: 'ESTP', value: 'ESTP' },
        { label: 'ESFP', value: 'ESFP' },
        { label: 'ENFP', value: 'ENFP' },
        { label: 'ENTP', value: 'ENTP' },
        { label: 'ESTJ', value: 'ESTJ' },
        { label: 'ESFJ', value: 'ESFJ' },
        { label: 'ENFJ', value: 'ENFJ' },
        { label: 'ENTJ', value: 'ENTJ' },
        // 다른 선택 옵션들을 필요에 따라 추가
      ],
    };
  },
  methods: {

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
    this.usernameErrorMessage='∙ 아이디를 입력해주세요';
  } 
  else if(!koreanPattern.test(this.username)) {
    this.usernameErrorMessage='아이디: 5~20자의 영문, 숫자와 특수기호(_),(-)만 사용 가능합니다';
  }
  else {
    const form=new FormData();
    form.append('username',this.username);
    axios.post('http://127.0.0.1:5000/duplicate',form)
        .then(response=>{
          if(response.data)
            this.usernameErrorMessage = '∙ 중복된 아이디';
          else{
            this.usernameErrorMessage = '∙ 사용 가능한 아이디 입니다.';
            return;
          } 
        })
        .catch(err=>console.log(err));
  }
  this.validUsername=false;
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
      const reader = new FileReader();

      // FileReader의 onload 이벤트 핸들러 내부에서 imageData.poto를 직접 업데이트
      reader.onload = () => {
        // imageData.poto를 이미지 데이터 URL로 업데이트
        this.imageData.poto = reader.result;
        // 이미지 크기를 동적으로 설정
        this.imageSize = '150px'; // 적절한 크기로 설정
      };

      // 파일을 읽어옵니다.
      reader.readAsDataURL(file);
    }
  },
  },
};

</script>

<style scoped>
.container{
  background: #fff;
  width: 530px;
  height: 1150px;
  border-top-left-radius: 25px;
  border-top-right-radius: 25px;
  border-bottom-left-radius: 25px;
  border-bottom-right-radius: 25px;

  
}
body{
  background: #eceff3;
}
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

.logo{
  
  height: 5%;
  width: 20%;
  margin-bottom: 50px;
  
  /* border: 1px solid red; */
}
.logo_icon{
  height: 150px;
  width: 150px;
  margin-top:10px ;
  /* border: 1px solid red; */
}
.form_list .input {
  font-style: oblique;
  box-sizing: border-box;
  width: 400px;
  font-size: 16px;
  line-height: 40px;
  align-items: center;
  position: relative;
  box-sizing: border-box;
  max-width: 100%;
  min-height: 50px;
  padding: 0 140px 0 50px;
  border: none;
  border: 1px solid #d4cfcf;
  transition: border-color 0.2s;
  margin-top: 10px; /* 위쪽 간격 조절 */
  border-top-left-radius: 25px;
    border-top-right-radius: 25px;
    border-bottom-left-radius: 25px;
    border-bottom-right-radius: 25px;
}
.form_gender{
  position: relative;
  width: 540px;
  height: 43px;
  left: 50.05%;
  margin-top: 6px;
  margin-bottom:0;
  transform: translate(-50%, -10%);
  box-sizing: border-box;
}

/* .select{
  position: relative;
  width: 450px;
  height: 150px;
  background: blue;
} */



.input:focus{
  outline: none;
  border: 2px solid #4CAF50;
  border-color: #4CAF50; /* 초록색 테두리로 변경 */
}

    
.id_icon, .name_icon, .birthday_icon,
.lock_icon, .address_icon, .email_icon{
  /* border: 1px solid red; */
  margin-left: 70px;
  z-index: 100;
  display: flex;
  position: absolute;
  margin-top: 25px;
  
}


.search_icon{
  /* border: 1px solid red; */
  margin-left: 405px;
  z-index: 100;
  display: flex;
  position: absolute;
  margin-top: 25px;
  width: 22px;
  height: 22px;
}

.form_list{
  margin-top: 10px;
}



.btn_show{
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

.list_item{
  box-sizing: border-box;
  width: 30%;
  padding-left: 10px;
}

.select {
    padding: 10px 70px ;
    margin-right: 150px;
    position: fixed;

    
}
.select input[type=radio]{
    display: none;
    position: relative;
}
.select input[type=radio]+label{
    display: inline-block;
    cursor: pointer;
    height: 36px;
    width: 100px;
    border: 1px solid #cac6c6;
    line-height: 35px;
    text-align: center;
    font-weight:bold;
    font-size:13px;
    position: fixed ;
    top: 0px; /* 선을 고정할 위치의 상단 여백 설정 */
    left: 1px;
    
  
    
}
.select input[type=radio]+label{
    background-color: #fff;
    color: #333;
    margin-right: -1px;
    margin-left: 0px;
    position: relative;
    /* border-top-right-radius: 20px; */

}
.select input[type=radio]:checked+label{
    background-color: #62a0fc;
    color: #fff;
    
}
/* .selects input[type=radio]+label{
  border-top-left-radius: 30px;
  border-bottom-left-radius: 30px;
} */

.gender1[type=radio]+label{
  border-top-left-radius: 22.5px;
  border-bottom-left-radius: 22.5px;
}
.gender2[type=radio]+label{
  border-top-right-radius: 22.5px;
  border-bottom-right-radius: 22.5px;
}
.gender3[type=radio]+label{
  border-top-left-radius: 22.5px;
  border-bottom-left-radius: 22.5px;
}
.gender4[type=radio]+label{
  border-top-right-radius: 22.5px;
  border-bottom-right-radius: 22.5px;
}

.btn{
  position: relative;
  top: 40px;
  width: 450px;
  height: 60px;
  border-top-left-radius: 30px;
  border-top-right-radius: 30px;
  border-bottom-left-radius: 30px;
  border-bottom-right-radius: 30px;
}
.join{
  position: relative;
  display: flex;
  border-bottom: 1px solid #d4cfcf;
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
  margin-left:60px ;
  
}
.form_id.error .input,
.form_pwd1.error .input,
.form_pwd2.error .input,
.form_pwdConfirm.error .input,
.form_name.error .input,
.form_mail.error .input {
  border: 2px solid red;
}

.image_poto{
  display: flex;
  /* border: 1px solid red; */
  font-size: 12px;
  margin-left: 0;
  height: 30px;
}
.rounded-image {
  border-radius: 50%;
}

.test{
  width: 80px;
  height: 29px;
  margin-left: 110px;
  margin-top: 13px;
  border: 1px solid#cac6c6;
  border-top-left-radius: 7px ;
  border-bottom-left-radius: 7px;
}
.forms_MBTI{
  position: absolute;
  margin-top: -29px;
  margin-left: 364px;
}
.large-select {
  width: 100px; /* 셀렉트 박스의 폭을 조절 */
  height: 29px; /* 셀렉트 박스의 높이를 조절 */
  border: 1px solid#cac6c6;
  border-top-right-radius: 7px ;
  border-bottom-right-radius: 7px;
}
.form_address .btn{
  position: relative;
  width: 50px;
  height: 30px;
  font-size: 12px;
  margin-top: -135px;
  margin-left: 5px;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
  border-bottom-right-radius: 5px;
  border-bottom-left-radius: 5px;
  border: 1px solid#cac6c6;
}
.addrnumber{
  margin-right: 200px;
  margin-top: 10px;
  width: 150px;
  border: 1px solid#cac6c6;
}
.addrnum{
  position: absolute;
  width: 350px;
  margin-top: -20px;
  margin-right: 0px;
  border: 1px solid#cac6c6;

}
.addradd{
  width: 200px;
  margin-top: 13px;
  margin-right: 150px;
  border: 1px solid#cac6c6;
}
.form_address{
  border: 1px solid#cac6c6;
  height: 150px;
  margin-top: 10px;
  width: 395px;
  margin-left: 55px;
  border-top-right-radius: 10px;
  border-top-left-radius: 10px;
  border-bottom-right-radius: 10px;
  border-bottom-left-radius: 10px;
}
.information{
  position: relative;
  width: 500px;
  margin-right:280px;
  font-size: 15px;
  font-style: oblique;
  color: #8a8989;
  /* border: 1px solid red; */
  padding-left: 20px;
  top: 5px;
}
.addr_icon{
  position: absolute;
  width: 16px;
  height: 16px;
  margin-top: 8px;
  margin-right: 25px;
}


.form_name .input{
  margin-top: 0px;

}
.name_icon{
  margin-top: 16px;
}
.form_mail {
  position: relative; /* 상위 컨테이너에 position: relative; 추가 */
}
.custom-class{
  position: absolute;
  display: block;
  margin-top: -22.5px;
  margin-left: 384px;
  font-size: 12px;
  width: 60px;
  height: 35px;
  border: 1px solid#cac6c6;
}
.checkcode{
  margin-top: 10px;
  margin-right: 230px;
  width: 150px;
  font-style: oblique;/* normal,italic,oblique =폰트 종류 */
  box-sizing: border-box;

  font-size: 16px;
  line-height: 30px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  border: 1px solid#cac6c6;
  max-width: 100%;

}
.confirm-btn{
  position: absolute;
  display: block;
  width: 65px;
  height: 35px;
  font-size: 10px;
  border: 1px solid#cac6c6;
  margin-top: 30px;
  margin-left: 220px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;

}
</style>