package com.kb.kdt.mapper;

import com.kb.kdt.dto.Course;
import com.kb.kdt.dto.TechSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TechSkillMapper {
    List<TechSkill> selectAllTechSkill();
}
