package com.kb.interview.dto.question;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterSubQuestionRequest {
    private int rno;
    private int mno;
    private int clno;
    private int number;
}
