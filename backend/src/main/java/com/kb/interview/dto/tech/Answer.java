package com.kb.interview.dto.tech;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    private int asno;
    private int tqno;
    private int rno;
    private String answer;
    private String feedback;
}
