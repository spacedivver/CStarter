package com.kb.interview.dto.coverletter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterAIModelRequest {
    private int rno;
    private int clno;
    private int number;
    private int questionType;
}
