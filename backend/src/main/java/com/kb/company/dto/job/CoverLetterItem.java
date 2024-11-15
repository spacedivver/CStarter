package com.kb.company.dto.job;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterItem {
    private int number;
    private String content;
    private int textCount;
}
