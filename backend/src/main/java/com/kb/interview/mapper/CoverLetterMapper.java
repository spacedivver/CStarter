package com.kb.interview.mapper;

import com.kb.interview.dto.coverletter.CoverLetter;
import com.kb.interview.dto.coverletter.CoverLetterAnswer;
import com.kb.interview.dto.coverletter.CoverLetterAnswerRequest;
import com.kb.interview.dto.coverletter.CoverLetterAnswerSaveRequest;

import java.util.List;
import java.util.stream.LongStream;

public interface CoverLetterMapper {
    int insert(CoverLetter coverLetter);
    CoverLetter selectById(int clno);
    List<CoverLetter> selectByMemberId(int mno);
    int insertAnswer(CoverLetterAnswer answer);
    int updateAnswer(CoverLetterAnswerSaveRequest answer);
}