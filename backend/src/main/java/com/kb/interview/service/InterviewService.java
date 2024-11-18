package com.kb.interview.service;

import com.kb.interview.dto.question.CoverLetterQuestionResponse;
import com.kb.interview.mapper.InterviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class InterviewService {
    private final InterviewMapper interviewMapper;

    public List<CoverLetterQuestionResponse> createQuestions(int clno, int count) {
        // python 실행해서 질문지 생성하기
        return interviewMapper.selectCoverLetterQuestion(clno);
    }
}