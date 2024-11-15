package com.kb.kdt.mapper;

import com.kb.kdt.dto.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> selectAllKDTCourse();
}
