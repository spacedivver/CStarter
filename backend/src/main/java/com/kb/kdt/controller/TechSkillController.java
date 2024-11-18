package com.kb.kdt.controller;

import com.kb.kdt.dto.TechSkill;
import com.kb.kdt.service.TechSkillService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "CourseController", tags = "KDT 강의 정보")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tech-skill")
public class TechSkillController {
    private final TechSkillService service;

    @GetMapping("")
    public ResponseEntity<List<TechSkill>> getAllTechSkill() {
        List<TechSkill> techSkills = service.getAllTechSkill();

        if (techSkills == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(techSkills);
    }
}
