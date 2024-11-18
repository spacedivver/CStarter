package com.kb.interview.controller;

import com.kb.interview.dto.question.CoverLetterQuestionResponse;
import com.kb.interview.service.InterviewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "InterviewController", tags = "면접")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interview")
public class InterviewController {
    private final InterviewService service;

    @GetMapping("/cover-letter/question")
    public ResponseEntity<List<CoverLetterQuestionResponse>> createQuestionOfCoverLetter(@RequestParam("clno") int clno, @RequestParam("count") int count) {
        List<CoverLetterQuestionResponse> response = service.createQuestions(clno, count);

        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }
}