package com.kb.interview.controller;

import com.kb.interview.dto.question.CoverLetterQuestion;
import com.kb.interview.dto.report.Report;
import com.kb.interview.dto.report.ReportResponse;
import com.kb.interview.service.CoverLetterService;
import com.kb.interview.service.ReportService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "ReportController", tags = "리포트")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;
    private final CoverLetterService coverLetterService;

    @GetMapping("/{rno}")
    public ResponseEntity<Report> getReportById(@PathVariable("rno") int rno) {
        Report report = reportService.getReportById(rno);

        if (report == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(report);
    }

    @GetMapping("")
    public ResponseEntity<List<Report>> getReportByMemberId(@RequestParam("mno") int mno) {
        List<Report> reports = reportService.getReportByMemberId(mno);

        if (reports == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{rno}/feedback")
    public ResponseEntity<ReportResponse> getReportInfoById(@PathVariable("rno") int rno) {
        List<CoverLetterQuestion> feedback = coverLetterService.getCoverLetterQuestionByReport(rno);
        Report report = reportService.getReportById(rno);

        ReportResponse response = ReportResponse.builder()
                .rno(report.getRno())
                .mno(report.getMno())
                .score(report.getScore())
                .content(report.getContent())
                .createdAt(report.getCreatedAt())
                .companyName(report.getCompanyName())
                .job(report.getJob())
                .feedback(feedback)
                .build();

        return ResponseEntity.ok(response);
    }
}
