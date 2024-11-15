package com.kb.company.dto.job;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponse {
    private int jno;
    private String type;
    private List<CoverLetterItem> coverLetterItems;
}
