package com.kb.interview.controller;

import com.kb.interview.dto.coverletter.CoverLetter;
import com.kb.interview.dto.coverletter.CoverLetterRequest;
import com.kb.interview.service.CoverLetterService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "CoverLetterController", tags = "자기소개서")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cover-letter")
public class CoverLetterController {
    private final CoverLetterService service;
    @PostMapping("")
    public ResponseEntity<CoverLetter> createCoverLetter(@RequestBody CoverLetterRequest coverLetterRequest) {
        CoverLetter coverLetter = service.create(coverLetterRequest);

        if (coverLetter == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coverLetter);
    }

    @GetMapping("/{clno}")
    public ResponseEntity<CoverLetter> getCoverLetter(@PathVariable("clno") int clno) {
        CoverLetter coverLetter = service.getCoverLetterById(clno);

        if (coverLetter == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coverLetter);
    }

    @GetMapping("")
    public ResponseEntity<List<CoverLetter>> getCoverLetterById(@RequestParam(value = "mno") int mno) {
        List<CoverLetter> coverLetters = service.getCoverLetterByMemberId(mno);

        if (coverLetters == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coverLetters);
    }
}
