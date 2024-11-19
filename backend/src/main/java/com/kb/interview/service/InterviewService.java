package com.kb.interview.service;

import com.kb.interview.dto.question.CoverLetterQuestion;
import com.kb.interview.dto.question.CoverLetterQuestionRequest;
import com.kb.interview.dto.question.CoverLetterQuestionResponse;
import com.kb.interview.dto.question.CoverLetterSubQuestionRequest;
import com.kb.interview.mapper.CoverLetterMapper;
import com.kb.interview.mapper.InterviewMapper;
import com.kb.interview.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class InterviewService {
    private final InterviewMapper interviewMapper;
    private final CoverLetterMapper coverLetterMapper;

    public List<CoverLetterQuestionResponse> createQuestions(int rno, CoverLetterQuestionRequest request) {
        // python 실행해서 질문지 생성하기
        coverLetterMapper.insertQuestion(
                CoverLetterQuestion.builder()
                        .clno(request.getClno())
                        .rno(rno)
                        .number(1)
                        .questionType(0)
                        .question("IBK시스템에서 금융 코어뱅킹 시스템 개발자로 성장하고 싶은 이유를 구체적으로 설명해 주실 수 있나요?")
                        .build());
        coverLetterMapper.insertQuestion(
                CoverLetterQuestion.builder()
                        .clno(request.getClno())
                        .rno(rno)
                        .number(2)
                        .questionType(0)
                        .question("Spring의 의존성 주입(Dependency Injection)에 대해 설명해 주시겠어요?")
                        .build());
        coverLetterMapper.insertQuestion(
                CoverLetterQuestion.builder()
                        .clno(request.getClno())
                        .rno(rno)
                        .number(3)
                        .questionType(0)
                        .question("IBK기업은행 인턴십에서 지점별 이수관 문제를 해결하기 위한 회의에 참여했다고 했습니다. 그때 제안했던 아이디어에 대해 자세히 설명해 주실 수 있나요?")
                        .build());
        // python 실행해서 질문지 생성하기

        List<CoverLetterQuestionResponse> responses = interviewMapper.selectCoverLetterQuestions(request.getClno());

        for (CoverLetterQuestionResponse response: responses) {
            response.setRno(rno);
        }

        return responses;
    }

    public CoverLetterQuestionResponse createSubQuestion(CoverLetterSubQuestionRequest request) {
        // python 실행해서 꼬리질문 생성하기
       CoverLetterQuestion subQuestion = CoverLetterQuestion.builder()
                .clno(request.getClno())
                .rno(request.getRno())
                .number(request.getNumber())
                .questionType(1)
                .question(request.getNumber() + "번 질문에 대한 꼬리 질문")
                .build();
        coverLetterMapper.insertQuestion(subQuestion);
        // python 실행해서 꼬리질문 생성하기

        return interviewMapper.selectCoverLetterQuestion(request.getRno(), request.getNumber(), 1);
    }
}