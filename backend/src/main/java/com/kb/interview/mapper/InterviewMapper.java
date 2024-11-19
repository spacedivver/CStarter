package com.kb.interview.mapper;

import com.kb.interview.dto.question.CoverLetterQuestion;
import com.kb.interview.dto.question.CoverLetterQuestionResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterviewMapper {
    List<CoverLetterQuestionResponse> selectCoverLetterQuestions(int clno);
    CoverLetterQuestionResponse selectCoverLetterQuestion(@Param("rno") int rno,
                                                          @Param("number") int number,
                                                          @Param("questionType") int questionType);
}