package com.kb.kdt.service;

import com.kb.kdt.dto.Course;
import com.kb.kdt.dto.CourseResponse;
import com.kb.kdt.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseMapper mapper;

    public List<CourseResponse> getAllKDTCourse() {
        List<Course> courses = mapper.selectAllKDTCourse();
        List<CourseResponse> courseResponses = new ArrayList<>();

        for (Course course: courses) {
            courseResponses.add(CourseResponse.builder()
                            .cno(course.getCno())
                            .courseName(course.getCourseName())
                            .techSkills(mapper.selectTechSkills(course.getCno()))
                            .build());
        }

        return courseResponses;
    }
}
