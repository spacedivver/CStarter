package com.kb.interview.dto.report;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequest {
    private int mno;
    private String companyName;
    private String job;
}
