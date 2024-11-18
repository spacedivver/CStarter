package com.kb.kdt.service;

import com.kb.kdt.dto.Course;
import com.kb.kdt.dto.CourseResponse;
import com.kb.kdt.dto.TechSkill;
import com.kb.kdt.mapper.CourseMapper;
import com.kb.kdt.mapper.TechSkillMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class TechSkillService {
    private final TechSkillMapper mapper;

    public List<TechSkill> getAllTechSkill() {
        return mapper.selectAllTechSkill();
    }
}
