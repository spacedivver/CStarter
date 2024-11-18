package com.kb.interview.service;

import com.kb.interview.dto.coverletter.CoverLetter;
import com.kb.interview.dto.coverletter.CoverLetterRequest;
import com.kb.interview.mapper.CoverLetterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class CoverLetterService {
    private final CoverLetterMapper mapper;

    public CoverLetter create(CoverLetterRequest coverLetterRequest) {
        CoverLetter coverLetter = CoverLetter.builder()
                .mno(coverLetterRequest.getMno())
                .cpno(coverLetterRequest.getCpno())
                .jno(coverLetterRequest.getJno())
                .build();

        if (mapper.insert(coverLetter) == 0) {
            return null;
        }

        return mapper.selectById(coverLetter.getClno());
    }

    public CoverLetter getCoverLetterById(int clno) {
        return mapper.selectById(clno);
    }

    public List<CoverLetter> getCoverLetterByMemberId(int mno) {
        return mapper.selectByMemberId(mno);
    }
}
