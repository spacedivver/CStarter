package com.kb.kdt.mapper;

import com.kb.kdt.dto.Course;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface CourseMapper {
    List<Integer> getAllCourse();
//    public List<String> getTechSkill(int cno);
}
