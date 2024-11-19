package com.kb.interview.dto.question;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterQuestionRequest {
    private int mno;
    private int clno;
    private int questionCount;
    private String companyName;
    private String job;
}
