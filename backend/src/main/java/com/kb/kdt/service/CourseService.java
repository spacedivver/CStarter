package com.kb.kdt.service;

import com.kb.kdt.dto.Course;
import com.kb.kdt.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseMapper courseMapper;

    public List<Course> getAllKDTCourse() {
        return courseMapper.selectAllKDTCourse();
    }
}
