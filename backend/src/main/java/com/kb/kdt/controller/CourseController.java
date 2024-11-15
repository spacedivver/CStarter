package com.kb.kdt.controller;

import com.kb.kdt.dto.Course;
import com.kb.kdt.dto.CourseResponse;
import com.kb.kdt.service.CourseService;
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
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService service;

    @GetMapping("")
    public ResponseEntity<List<CourseResponse>> getAllCourse() {
        List<CourseResponse> courses = service.getAllKDTCourse();

        if (courses.size() == 0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(courses);
    }
}
