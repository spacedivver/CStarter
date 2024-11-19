package com.kb.interview.mapper;

import com.kb.interview.dto.coverletter.CoverLetter;
import com.kb.interview.dto.coverletter.CoverLetterAnswer;
import com.kb.interview.dto.question.CoverLetterQuestion;

import java.util.List;

public interface CoverLetterMapper {
    int insertCoverLetter(CoverLetter coverLetter);
    CoverLetter selectById(int clno);
    List<CoverLetter> selectByMemberId(int mno);
    int insertAnswer(CoverLetterAnswer answer);
    int insertQuestion(CoverLetterQuestion question);
    int updateSubQuestion(CoverLetterQuestion question);
}