package com.kb.interview.dto.report;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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
    private String createdAt;
    private String companyName;
    private String job;
}
