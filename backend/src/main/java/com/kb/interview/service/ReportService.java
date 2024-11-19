package com.kb.interview.service;

import com.kb.interview.dto.report.Report;
import com.kb.interview.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportMapper mapper;

    public Report createReport(int mno, String companyName, String job) {
        Report report = Report.builder()
                .mno(mno)
                .companyName(companyName)
                .job(job)
                .build();

        int rno = mapper.insertReport(report);
        if (rno == 1) {
            return mapper.selectById(report.getRno());
        }
        return null;
    }

    public Report getReportById(int rno) {
        return mapper.selectById(rno);
    }

    public List<Report> getReportByMemberId(int mno) {
        return mapper.selectByMemberId(mno);
    }
}
