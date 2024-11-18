package com.kb.interview.dto.coverletter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterAnswerRequest {
    private int clno; // 자기소개서
    private int cino; // 항목
    private String answer;
}
