package com.kb.interview.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetter {
    private int clno;
    private int mno;
    private int cpno;
    private int jno;
    private LocalDate createdAt;
}
