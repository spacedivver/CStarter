package com.kb.interview.mapper;

import com.kb.interview.dto.CoverLetter;
import com.kb.kdt.dto.Course;

import java.util.List;

public interface CoverLetterMapper {
    int insert(CoverLetter coverLetter);
    CoverLetter selectById(int clno);
    List<CoverLetter> selectByMemberId(int mno);
}