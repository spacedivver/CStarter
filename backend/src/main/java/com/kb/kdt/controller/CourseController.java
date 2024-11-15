package com.kb.kdt.controller;

import com.kb.kdt.dto.Course;
import com.kb.kdt.service.CourseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService service;

    @GetMapping("")
    public ResponseEntity<List<Integer>> getAllCourse() {
        List<Integer> courses = service.getAllCourse();

        if (courses.size() == 0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(courses);
    }
}
