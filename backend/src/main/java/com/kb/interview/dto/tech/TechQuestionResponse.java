package com.kb.interview.dto.tech;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechQuestionResponse {
    private int bno;
    private String type;
    private String title;
}
