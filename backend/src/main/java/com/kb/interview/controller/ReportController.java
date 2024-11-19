package com.kb.interview.controller;

import com.kb.interview.dto.report.Report;
import com.kb.interview.dto.report.ReportRequest;
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
    private final ReportService service;

    @PostMapping("")
    public ResponseEntity<Report> create(@RequestBody ReportRequest request) {
        Report report = service.createReport(request);

        if (report == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(report);
    }

    @GetMapping("/{rno}")
    public ResponseEntity<Report> getReportById(@PathVariable("rno") int rno) {
        Report report = service.getReportById(rno);

        if (report == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(report);
    }

    @GetMapping("")
    public ResponseEntity<List<Report>> getReportByMemberId(@RequestParam("mno") int mno) {
        List<Report> reports = service.getReportByMemberId(mno);

        if (reports == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }
}
