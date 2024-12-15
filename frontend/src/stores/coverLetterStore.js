import { defineStore } from 'pinia';

export const useCoverLetterStore = defineStore('coverLetter', {
    state: () => ({
        clno: null,
        companyName: '',
        job: ''
    }),
    actions: {
        setCoverLetterData(data) {
            this.clno = data.clno;
            this.companyName = data.companyName;
            this.job = data.job;
        }
    }
});
