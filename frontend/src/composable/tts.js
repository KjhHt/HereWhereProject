// eslint-disable-next-line no-unused-vars
class TextToSpeech {
  constructor() {
    this.synthesis = window.speechSynthesis;
    this.utterance = new SpeechSynthesisUtterance();
    this.selectedVoice = null;
    this.isSpeaking = false;
    this.initVoices();
  }

  initVoices() {
    this.synthesis.onvoiceschanged = () => {
      this.updateVoices();
    };
  }

  updateVoices() {
    const voices = this.synthesis.getVoices().filter(voice => voice.lang === 'ko-KR');
    if (voices.length > 0) {
      this.selectedVoice = voices[0];
      this.utterance.voice = this.selectedVoice;
    }
  }

  startSynthesis(text) {
    if (!this.selectedVoice || !this.synthesis.getVoices().includes(this.selectedVoice)) {
      this.updateVoices(); // Ensure the selected voice is up-to-date
    }

    if (this.isSpeaking) {
      this.stopSynthesis();
    }

    this.utterance.text = text;
    this.utterance.onend = () => {
      this.isSpeaking = false;
    };
    this.utterance.onerror = (e) => {
      console.error('Speech synthesis error:', e);
    };

    if (this.selectedVoice) {
      this.utterance.voice = this.selectedVoice;
      this.synthesis.speak(this.utterance);
      this.isSpeaking = true;
    } else {
      console.log('Selected voice not found');
    }
  }

  stopSynthesis() {
    if (this.synthesis.speaking) {
      this.synthesis.cancel();
      this.isSpeaking = false;
    }
  }
}

export default TextToSpeech;