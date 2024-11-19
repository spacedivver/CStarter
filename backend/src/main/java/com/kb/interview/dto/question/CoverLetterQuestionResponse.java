package com.kb.interview.dto.question;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterQuestionResponse {
    int rno;
    int cqno;
    int number;
    String question;
}