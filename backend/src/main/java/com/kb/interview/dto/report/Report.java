package com.kb.interview.dto.report;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {
    private int rno;
    private int mno;
    private int score;
    private String content;
}
