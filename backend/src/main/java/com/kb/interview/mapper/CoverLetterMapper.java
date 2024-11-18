package com.kb.interview.mapper;

import com.kb.interview.dto.coverletter.CoverLetter;
import com.kb.interview.dto.coverletter.CoverLetterAnswer;

import java.util.List;

public interface CoverLetterMapper {
    int insertCoverLetter(CoverLetter coverLetter);
    CoverLetter selectById(int clno);
    List<CoverLetter> selectByMemberId(int mno);
    int insertAnswer(CoverLetterAnswer answer);
}