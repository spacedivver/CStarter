package com.kb.interview.dto.report;

import com.kb.interview.dto.question.CoverLetterQuestion;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private int rno;
    private int mno;
    private int score;
    private String content;
    private String createdAt;
    private String companyName;
    private String job;
    private List<CoverLetterQuestion> feedback;
}
