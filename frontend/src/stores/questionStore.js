// src/stores/questionStore.js
import { defineStore } from 'pinia';

export const useQuestionStore = defineStore('question', {
    state: () => ({
        questions: [] // 질문 리스트를 저장할 배열
    }),
    actions: {
        setQuestions(data) {
            this.questions = data; // 질문 리스트 저장
        }
    }
});
