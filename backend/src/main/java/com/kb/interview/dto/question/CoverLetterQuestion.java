package com.kb.interview.dto.question;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterQuestion {
    private int cqno;
    private int clno;
    private int rno;
    private int number;
    private int questionType;
    private String question;
    private String answer;
    private String feedback;
}
