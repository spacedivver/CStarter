package com.kb.kdt.service;

import com.kb.board.mapper.BoardMapper;
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
    private final CourseMapper mapper;
    private final BoardMapper m;

    public List<Integer> getAllCourse() {
        return mapper.getAllCourse();
    }
}
