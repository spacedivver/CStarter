// src/stores/speechStore.js
import { defineStore } from 'pinia';

export const useSpeechStore = defineStore('speech', {
  state: () => ({
    speechSynthesis: null,
    isSpeaking: false,
  }),
  actions: {
    initializeSpeech() {
      this.speechSynthesis = window.speechSynthesis;
    },
    speak(text) {
      if (this.speechSynthesis) {
        const utterance = new SpeechSynthesisUtterance(text);
        utterance.onend = () => {
          this.isSpeaking = false;
        };
        this.isSpeaking = true;
        this.speechSynthesis.speak(utterance);
      }
    },
    stopSpeaking() {
      if (this.speechSynthesis) {
        this.speechSynthesis.cancel();
        this.isSpeaking = false;
      }
    },
  },
});
