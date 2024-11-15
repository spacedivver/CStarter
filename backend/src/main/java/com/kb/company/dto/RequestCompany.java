package com.kb.company.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCompany {
    private String name;
    private String title;
    private int isUserOption;
}
