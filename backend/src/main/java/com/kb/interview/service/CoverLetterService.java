package com.kb.interview.service;

import com.kb.company.dto.job.CoverLetterItem;
import com.kb.company.mapper.CompanyMapper;
import com.kb.interview.dto.coverletter.CoverLetter;
import com.kb.interview.dto.coverletter.CoverLetterAnswer;
import com.kb.interview.dto.coverletter.CoverLetterAnswerSaveRequest;
import com.kb.interview.dto.coverletter.CoverLetterRequest;
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

    public CoverLetter create(CoverLetterRequest coverLetterRequest) {
        CoverLetter coverLetter = CoverLetter.builder()
                .mno(coverLetterRequest.getMno())
                .cpno(coverLetterRequest.getCpno())
                .jno(coverLetterRequest.getJno())
                .build();

        if (coverLetterMapper.insert(coverLetter) == 0) {
            return null;
        }

        return coverLetterMapper.selectById(coverLetter.getClno());
    }

    public CoverLetter getCoverLetterById(int clno) {
        return coverLetterMapper.selectById(clno);
    }

    public List<CoverLetter> getCoverLetterByMemberId(int mno) {
        return coverLetterMapper.selectByMemberId(mno);
    }

    public List<CoverLetterAnswer> createAnswers(CoverLetter coverLetter) {
        List<CoverLetterItem> items = companyMapper.selectCoverLetterItem(coverLetter.getJno());
        List<CoverLetterAnswer> answers = new ArrayList<>();

        for (CoverLetterItem item: items) {
            System.out.println("번호>>" + item);
        }

        for (CoverLetterItem item: items) {
            System.out.println("번호>>" + item.getCino());
            CoverLetterAnswer coverLetterAnswer = CoverLetterAnswer.builder()
                    .clno(coverLetter.getClno())
                    .cino(item.getCino())
                    .build();
            coverLetterMapper.insertAnswer(coverLetterAnswer);
        }

        return answers;
    }

    public void updateAnswers(List<CoverLetterAnswerSaveRequest> answers) {
        for (CoverLetterAnswerSaveRequest answer: answers) {
            coverLetterMapper.updateAnswer(answer);
        }
    }
}
