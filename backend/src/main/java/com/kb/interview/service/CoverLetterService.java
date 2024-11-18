package com.kb.interview.service;

import com.kb.company.mapper.CompanyMapper;
import com.kb.interview.dto.coverletter.*;
import com.kb.interview.dto.coverletter.CoverLetterRequest;
import com.kb.interview.dto.coverletter.CoverLetterResponse;
import com.kb.interview.mapper.CoverLetterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class CoverLetterService {
    private final CoverLetterMapper coverLetterMapper;
    private final CompanyMapper companyMapper;

    public CoverLetterResponse createCoverLetterAndAnswer(CoverLetterRequest request) {
        CoverLetter coverLetter = createCoverLetter(request);
        List<CoverLetterAnswer> answers = createAnswers(coverLetter.getClno(), request);

        return CoverLetterResponse.builder()
                .clno(coverLetter.getClno())
                .mno(coverLetter.getMno())
                .cpno(coverLetter.getCpno())
                .jno(coverLetter.getJno())
                .answers(answers)
                .build();
    }

    public CoverLetter createCoverLetter(CoverLetterRequest request) {
        CoverLetter coverLetter = CoverLetter.builder()
                .cpno(request.getCpno())
                .jno(request.getJno())
                .mno(request.getMno())
                .build();
        coverLetterMapper.insertCoverLetter(coverLetter);
        return coverLetter;
    }

    private List<CoverLetterAnswer> createAnswers (int clno, CoverLetterRequest request) {
        List<CoverLetterAnswer> answers = new ArrayList<>();

        for (CoverLetterAnswerRequest answer: request.getAnswers()) {
            CoverLetterAnswer coverLetterAnswer = CoverLetterAnswer.builder()
                    .cino(answer.getCino())
                    .clno(clno)
                    .answer(answer.getAnswer())
                    .build();
            coverLetterMapper.insertAnswer(coverLetterAnswer);
            answers.add(coverLetterAnswer);
        }

        return answers;
    }

    public CoverLetter getCoverLetterById(int clno) {
        return coverLetterMapper.selectById(clno);
    }

    public List<CoverLetter> getCoverLetterByMemberId(int mno) {
        return coverLetterMapper.selectByMemberId(mno);
    }
}
