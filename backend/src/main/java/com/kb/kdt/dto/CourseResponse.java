package com.kb.kdt.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {
    private int cno;
    private String courseName;
    private List<TechSkill> techSkills;
}
