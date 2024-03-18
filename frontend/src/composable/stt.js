
class SpeechToText {
  constructor(newMessageRef,isRecognizing) {
      this.transcript = '';
      this.isRecognizing = isRecognizing;
      this.recognition = null;
      this.timeoutId = null;
      this.initRecognition();
      this.newMessage=newMessageRef
  }

  initRecognition() {
      if (!('webkitSpeechRecognition' in window)) {
          console.log('브라우저가 STT를 지원하지 않습니다.');
          return;
      }

      this.recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
      this.recognition.lang = 'ko-KR';
      this.recognition.interimResults = false;

      this.recognition.onspeechstart = () => {
          console.log('음성 인식 시작!');
          this.resetTimeout();
      };

      this.recognition.onspeechend = () => {
          console.log('음성 인식 종료!');
          this.recognition.stop();
          this.isRecognizing.value = false;
          
      };

      this.recognition.onresult = (event) => {
          this.transcript = Array.from(event.results)
                                .map(result => result[0].transcript)
                                .join("");
          console.log('인식된 텍스트:', this.transcript);
          this.newMessage.value = this.transcript;
          this.resetTimeout();
      };

      this.recognition.onerror = (event) => {
          console.error('음성 인식 오류:', event.error);
          clearTimeout(this.timeoutId);
      };
  }

  resetTimeout() {
      clearTimeout(this.timeoutId);
      this.timeoutId = setTimeout(() => {
          if (this.isRecognizing.value) {
              this.recognition.stop();
              this.isRecognizing.value = false;
              console.log('음성 입력이 감지되지 않아 인식을 중단합니다.');
          }
      }, 5000); // 5초 동안 음성 입력이 없으면 인식 중단
  }

  startRecognition() {
      if (this.isRecognizing.value) {
          this.recognition.stop();
          this.isRecognizing.value = false;
          clearTimeout(this.timeoutId);
      } else {
          this.transcript = '';
          this.recognition.start();
          this.isRecognizing.value = true;
          this.resetTimeout();
      }
  }
}

export default SpeechToText;
