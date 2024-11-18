package com.kb.interview.mapper;

import com.kb.interview.dto.question.CoverLetterQuestion;
import com.kb.interview.dto.question.CoverLetterQuestionResponse;

import java.util.List;

public interface InterviewMapper {
    List<CoverLetterQuestionResponse> selectCoverLetterQuestion(int clno);
}