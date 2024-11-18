package com.kb.interview.dto.coverletter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterAnswerRequest {
    private int cino; // 항목 순번
    private String answer;
}
