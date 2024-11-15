package com.kb.interview.controller;

import com.kb.interview.dto.CoverLetter;
import com.kb.interview.service.CoverLetterService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "CoverLetterController", tags = "자기소개서")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cover-letter")
public class CoverLetterController {
    private final CoverLetterService service;

    @PostMapping("")
    public ResponseEntity<CoverLetter> createCoverLetter(@RequestParam("uno") int uno) {
        CoverLetter coverLetter = service.create(uno);

        if (coverLetter == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coverLetter);
    }
}
