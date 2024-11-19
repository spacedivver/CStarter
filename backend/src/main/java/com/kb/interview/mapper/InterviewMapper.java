package com.kb.interview.mapper;

import com.kb.interview.dto.question.CoverLetterQuestion;
import com.kb.interview.dto.question.CoverLetterQuestionResponse;
import com.kb.interview.dto.tech.TechQuestion;
import com.kb.interview.dto.tech.TechQuestionResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterviewMapper {
    List<CoverLetterQuestionResponse> selectCoverLetterQuestions(int clno);
    CoverLetterQuestionResponse selectCoverLetterQuestion(@Param("clno") int clno,
                                                          @Param("number") int number,
                                                          @Param("questionType") int questionType);
    List<TechQuestionResponse> selectTechQuestion();
    TechQuestionResponse selectTechQuestionById(int bno);
    String selectCoverLetterQuestionAnswer(@Param("clno") int clno,
                                           @Param("number") int number,
                                           @Param("questionType") int questionType);
}