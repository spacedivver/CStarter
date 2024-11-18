package com.kb.interview.dto.coverletter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterAnswerSaveRequest {
    private int cano;
    private String answer;
}
