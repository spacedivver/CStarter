package com.kb.company.dto.company;

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
