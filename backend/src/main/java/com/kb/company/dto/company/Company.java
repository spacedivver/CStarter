package com.kb.company.dto.company;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    private int cpno;
    private String name;
    private String title;
    private String notice;
    private int isUserOption;
}
