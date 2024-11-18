package com.kb.interview.dto.coverletter;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterRequest {
    private int mno;
    private int cpno;
    private int jno;
    List<CoverLetterAnswerRequest> answers;
}
