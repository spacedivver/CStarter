package com.kb.interview.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetter {
    private int clno;
    private int uno;
    private Date createdAt;
}
