package com.kb.interview.dto.tech;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechQuestion {
    private int tqno;
    private int tsno;
    private int number;
    private int question;
}
